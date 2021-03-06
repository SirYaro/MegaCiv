package net.bubbaland.megaciv.server;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.bubbaland.megaciv.*;
import net.bubbaland.megaciv.game.User;
import net.bubbaland.megaciv.messages.ClientMessage;
import net.bubbaland.megaciv.messages.ServerMessage;
import net.bubbaland.sntp.SntpClient;

@SuppressWarnings("unused")
@ServerEndpoint(decoders = { ClientMessage.MessageDecoder.class }, encoders = {
		ServerMessage.MessageEncoder.class }, value = "/")
public class ClientMessageReceiver {

	private User				user;
	private static GameServer	server	= null;

	public ClientMessageReceiver() {
		this.user = new User();
	}

	static void registerServer(GameServer server) {
		ClientMessageReceiver.server = server;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Initial hook when a client first connects (TriviaServerEndpoint() is automatically called as well)
	 *
	 * @param session
	 * @param config
	 */
	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		ClientMessageReceiver.server.log("User " + session.getId() + " connected");
		this.user.setUserName("User " + session.getId());
		ClientMessageReceiver.server.addSession(session, this);
		ClientMessageReceiver.server.sendGame(session);
		ClientMessageReceiver.server.sendClock(session);
	}

	/**
	 * Handle a message from the client
	 *
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onMessage(ClientMessage message, Session session) {
		server.processIncomingMessage(message, session);
	}

	/**
	 * Handle error in communicating with a client
	 *
	 * @param session
	 */
	@OnError
	public void onError(Session session, Throwable throwable) {
		if (server == null) {
			System.out.println("Server still null!");
		}
		server.communicationsError(session, throwable);
	}

	/**
	 * Handle a client disconnection
	 *
	 * @param session
	 */
	@OnClose
	public void onClose(Session session) {
		server.removeSession(session);
	}

}
