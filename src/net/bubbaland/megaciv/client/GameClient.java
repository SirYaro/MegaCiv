package net.bubbaland.megaciv.client;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.SwingWorker;
import javax.websocket.ClientEndpoint;
import javax.websocket.DeploymentException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.bubbaland.megaciv.game.Game;
import net.bubbaland.megaciv.game.Stopwatch;
import net.bubbaland.megaciv.game.User;
import net.bubbaland.megaciv.messages.*;
import net.bubbaland.sntp.SntpClient;

/**
 * MegaCivilization game client that handles communication with the game server and updates game data as necessary.
 * 
 * @author Walter Kolczynski
 * 
 */

@ClientEndpoint(decoders = { ServerMessage.MessageDecoder.class }, encoders = { ClientMessage.MessageEncoder.class })
public class GameClient implements Runnable {

	// Frequency (in secs) of synchronization of time with server
	private final static int			SNTP_POLL_INTERVAL			= 30000;

	// Default trade timer length (in secs)
	private final static int			STARTING_TIMER_LENGTH_SEC	= 300;

	// Format for log timestamps
	private SimpleDateFormat			timestampFormat;

	// Connection session with server
	private Session						session;

	// Create an SNTP client to synchronize timing with server
	private final SntpClient			sntpClient;

	private boolean						isConnected;

	// Game data; updated by server when necessary
	private volatile Game				game;

	// Users connected to server; updated by server when necessary
	private volatile ArrayList<User>	userList;

	// User data for this client
	private User						user;

	// Timer for trade sessions
	private final Stopwatch				stopwatch;

	// URI for server address
	private final URI					uri;

	/**
	 * Create a new client that connects to the specified server
	 * 
	 * @param serverUrl
	 *            URL of game server
	 */
	public GameClient(final String serverUrl) {
		this.session = null;
		this.game = null;
		this.user = new User();
		this.userList = new ArrayList<User>();
		this.isConnected = false;
		this.timestampFormat = new SimpleDateFormat("[yyyy MMM dd HH:mm:ss]");
		this.stopwatch = new Stopwatch(GameClient.STARTING_TIMER_LENGTH_SEC);

		this.uri = URI.create(serverUrl);
		this.sntpClient = new SntpClient(this.uri.getHost(), this.uri.getPort() + 1, SNTP_POLL_INTERVAL);
	}

	/**
	 * Handle server disconnection
	 */
	@OnClose
	public void connectionClosed() {
		this.session = null;
		this.log("Connection closed!");
		this.isConnected = false;
	}

	/**
	 * Get game data. Game data is updated by server when necessary.
	 * 
	 * @return The Game data.
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * Get the currently open session.
	 * 
	 * @return The currently open session.
	 */
	public Session getSession() {
		return this.session;
	}

	/**
	 * Get the SNTP client (for time synchronization).
	 * 
	 * @return The client's SNTP client.
	 */
	public SntpClient getSntpClient() {
		return this.sntpClient;
	}

	/**
	 * Get the trading timer.
	 * 
	 * @return The client's trade timer.
	 */
	public Stopwatch getStopwatch() {
		return this.stopwatch;
	}

	/**
	 * Get this client's user data.
	 * 
	 * @return The current user.
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * Get list of users connected to server. This is updated by server as necessary.
	 * 
	 * @return A list of the connected users
	 */
	public ArrayList<User> getUserList() {
		return this.userList;
	}

	/**
	 * Find out if the client is connected to the server.
	 * 
	 * @return A boolean specifying whether client is connected to server.
	 */
	public boolean isConnected() {
		return this.isConnected;
	}

	/**
	 * Load game save data from file and send to server.
	 * 
	 * @param file
	 *            File containing save data.
	 */
	public void loadGame(File file) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			this.sendMessage(new LoadGameMessage(mapper.readValue(file, Game.class)));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Print a message to standard out with a timestamp.
	 *
	 * @param message
	 *            Message to log.
	 */
	public void log(String message) {
		final String timestamp = timestampFormat.format(new Date());
		// Print message to console
		System.out.println(timestamp + " " + message);
	}

	/**
	 * Handle error in communicating with the server
	 *
	 * @param session
	 *            The session that caused the error.
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		this.log("Error receiving message from " + session.getRequestURI());
		throwable.printStackTrace();
	}

	/**
	 * Handle a new message from the server.
	 *
	 * @param message
	 *            The message from the server.
	 * @param session
	 *            The session that received the message.
	 */
	@OnMessage
	public void onMessage(ServerMessage message, Session session) {
		String messageType = message.getClass().getSimpleName();
		switch (messageType) {
			case "GameDataMessage": // Received updated game data
				this.game = ( (GameDataMessage) message ).getGame();
				// this.log(this.game.toString());
				break;
			case "UserListMessage": // Received an updated user list
				this.userList = ( (UserListMessage) message ).getUserList();
				break;
			case "TimerMessage": // Received a timer synchronization message
				this.stopwatch.remoteEvent((TimerMessage) message, this.sntpClient.getOffset());
				break;
			default:
		}
	}

	/**
	 * Handle when a connection to the server is first established.
	 *
	 * @param session
	 *            The session that has just been activated.
	 * @param config
	 *            The configuration used to configure this endpoint.
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		this.session = session;
		this.log("Now connected to " + session.getRequestURI());
		this.isConnected = true;
		if (this.user.getUserName().equals("")) {
			this.user.setUserName(session.getId().substring(0, 7));
		}
		this.sendMessage(new SetUserMessage(this.user));

		this.sntpClient.start();
	}

	/**
	 * Run the client by connecting to the server.
	 * 
	 */
	@Override
	public void run() {
		final ClientManager clientManager = ClientManager.createClient();
		try {
			clientManager.connectToServer(this, this.uri);
		} catch (DeploymentException | IOException exception) {
			this.log("Couldn't connect to " + this.uri);
			this.connectionClosed();
		}
	}

	/**
	 * Save current game data to a file.
	 * 
	 * @param file
	 *            A file to save game data in.
	 */
	public void saveGame(File file) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(file, this.game);
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Send a message to the server. Most messages are updates to the game state based on user input.
	 *
	 * @param session
	 *            The session connected to the server.
	 * @param message
	 *            The message to be delivered.
	 */
	public void sendMessage(final ClientMessage message) {
		( new SwingWorker<Void, Void>() {
			@Override
			public Void doInBackground() {
				GameClient.this.session.getAsyncRemote().sendObject(message);
				return null;
			}

			@Override
			public void done() {

			}
		} ).execute();
	}

	/**
	 * Get the date-time format to be used for log timestamps.
	 * 
	 * @param timestampFormat
	 *            The date-time format to be used for log timestamps.
	 */
	protected void setTimestampFormat(SimpleDateFormat timestampFormat) {
		this.timestampFormat = timestampFormat;
	}

	/**
	 * Set user data for this client.
	 * 
	 * @param user
	 *            The new user data.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Determine whether a user name is already present in the server user list.
	 * 
	 * @param userName
	 *            User name to check.
	 * @return A boolean specifying whether this is a duplicate user name.
	 */
	public boolean userNameExists(String userName) {
		return this.userList.stream().filter(user -> user.compareTo(this.user) != 0)
				.anyMatch(user -> userName.equals(user.getUserName()));
	}


}
