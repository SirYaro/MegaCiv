package net.bubbaland.megaciv.messages;

import java.util.HashMap;

import net.bubbaland.megaciv.game.Civilization;
import net.bubbaland.megaciv.game.Game.Difficulty;
import net.bubbaland.megaciv.game.GameEvent;
import net.bubbaland.megaciv.game.GameEvent.EventType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class NewGameMessage implements ClientMessage {

	@JsonProperty("civNames")
	private final HashMap<Civilization.Name, String>	civNames;
	@JsonProperty("Difficulty")
	private final Difficulty							difficulty;
	@JsonProperty("Region")
	private final Civilization.Region					region;
	@JsonProperty("useCredits")
	private final boolean								useCredits;

	@JsonCreator
	public NewGameMessage(@JsonProperty("Region") Civilization.Region region,
			@JsonProperty("civNames") HashMap<Civilization.Name, String> newCivNames,
			@JsonProperty("Difficulty") final Difficulty difficulty,
			@JsonProperty("useCredits") final boolean useCredits) {
		this.region = region;
		this.civNames = newCivNames;
		this.difficulty = difficulty;
		this.useCredits = useCredits;
	}

	public Civilization.Region getRegion() {
		return this.region;
	}

	public HashMap<Civilization.Name, String> getCivNames() {
		return this.civNames;
	}

	public Difficulty getDifficulty() {
		return this.difficulty;
	}

	public boolean useCredits() {
		return this.useCredits;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventType getEventType() {
		return GameEvent.EventType.GAME_START;
	}
}
