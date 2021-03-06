package net.bubbaland.megaciv.game;

import java.awt.Color;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.bubbaland.gui.StringTools;

public enum Technology {

	ADVANCED_MILITARY("Advanced Military", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 543488111062214027L;

		{
			add(Type.CIVICS);
		}
	}, 240, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 543488111062214027L;

		{
			put(Type.CIVICS, 20);
			put(Type.SCIENCE, 5);
		}
	}, "1) In conflicts, you may choose to remove tokens from areas adjacent by land. After each round of token removal a new check for token majority must be made. You may decide to wait for other token conflicts to be resolved first.\n"
			+ "2) You are allowed to cause conflict in areas containing units belonging to players holding Cultural Ascendancy.\n"
			+ "Civil Disorder: Reduce 1 additional city."),

	AGRICULTURE("Agriculture", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 4537011915744500590L;

		{
			add(Type.CRAFTS);
		}
	}, 120, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 5136626143188907828L;

		{
			put(Type.CRAFTS, 10);
			put(Type.SCIENCE, 5);
		}
	}, "The population limit of '0', '1' and '2' areas on the board is increased by 1 for you as long as these areas do not contain any other players's units or barbarion tokens.\n"
			+ "Famine: If you are the primary victim, take 5 additional damage."),

	ANATOMY("Anatomy", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 7031037930756667181L;

		{
			add(Type.SCIENCE);
		}
	}, 270, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 4796628152310683164L;

		{
			put(Type.CRAFTS, 5);
			put(Type.SCIENCE, 20);
		}
	}, "(*) Upon purchase, you may choose to acquire a science card with an undiscounted cost price of less then 100 for free.\n"
			+ "Epidemic: If you are a secondary victim, prevent 5 damage."),

	ARCHITECHTURE("Architechture", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 666701458904134122L;

		{
			add(Type.ARTS);
		}
	}, 140, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 7031037930756667181L;

		{
			put(Type.ARTS, 10);
			put(Type.SCIENCE, 5);
		}
	}, "Once per turn, when constructing a city you may choose to pay up to half of the required number of tokens from treasury."),

	ASTRONAVIGATION("Astronavigation", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -7541771179129766109L;

		{
			add(Type.SCIENCE);
		}
	}, 80, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -8094721076572675469L;

		{
			put(Type.RELIGION, 5);
			put(Type.SCIENCE, 10);
		}
	}, "Your ships are allowed to move through open sea areas."),

	CALENDAR("Calendar", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 2775690088569515817L;

		{
			add(Type.SCIENCE);
		}
	}, 180, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -9189085704930732192L;

		{
			put(Type.CIVICS, 5);
			put(Type.SCIENCE, 10);
		}
	}, "Famine: Prevent 5 damage.\n" + "Cyclone: Reduce 2 less selected cities."),

	CARTOGRAPHY("Cartography", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -2089788045159380055L;

		{
			add(Type.SCIENCE);
		}
	}, 160, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -7541771179129766109L;

		{
			put(Type.ARTS, 5);
			put(Type.SCIENCE, 10);
		}
	}, "During the Trade Cards Acquisition phase, you may acquire additional trade cards from stack 2 for 5 treasury tokens and/or from stack 7 for 13 treasury tokens per card.\n"
			+ "Piracy: If you are the primary victim, the beneficiary selects and replaces 1 additional coastal city."),

	CLOTH_MAKING("Cloth Making", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -4927290471028918556L;

		{
			add(Type.CRAFTS);
		}
	}, 50, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 6788126994932865702L;

		{
			put(Type.ARTS, 5);
			put(Type.CRAFTS, 10);
		}
	}, "Your ships are allowed to move 5 steps."),

	COINAGE("Coinage", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -6260558911185446176L;

		{
			add(Type.SCIENCE);
		}
	}, 90, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 7422937738884993143L;

		{
			put(Type.CIVICS, 5);
			put(Type.SCIENCE, 10);
		}
	}, "You may choose to increase or decrease your tax rate by 1.\n"
			+ "Corruption: Discard 5 additional points of face value."),

	CULTURAL_ASCENDANCY("Cultural Ascendancy", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 1917499061422154540L;

		{
			add(Type.ARTS);
		}
	}, 280, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -2089788045159380055L;

		{
			put(Type.ARTS, 20);
			put(Type.RELIGION, 5);
		}
	}, "1) Players are not allowed to cause conflict in areas containing your units, except for areas where a conflict situation already occurs. This does not count for players holding Cultural Ascendancy or Advanced Military.\n"
			+ "2) Your units are protected against the effect of Politics.\n"
			+ "3) Your default city support rate is increased by 1."),

	DEISM("Deism", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -2440070450841210384L;

		{
			add(Type.RELIGION);
		}
	}, 70, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 7735122367960984928L;

		{
			put(Type.CRAFTS, 5);
			put(Type.RELIGION, 10);
		}
	}, "Superstition: Reduce 1 less city."),

	DEMOCRACY("Democracy", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -8961620213522975422L;

		{
			add(Type.CIVICS);
		}
	}, 220, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -1431225172754741956L;

		{
			put(Type.ARTS, 5);
			put(Type.CIVICS, 20);
		}
	}, "During the Tax Collection phase you collect tax as usual but your cities do not revolt as a result of a shortage in tax collection.\n"
			+ "Civil War: Select 10 less unit points.\n" + "Civil Disorder: Reduce 1 less city."),

	DIASPORA("Diaspora", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -3633799324062178157L;

		{
			add(Type.RELIGION);
		}
	}, 270, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -6260558911185446176L;

		{
			put(Type.ARTS, 5);
			put(Type.RELIGION, 20);
		}
	}, "Special ability: You may choose to take up 5 of your tokens from the board and place them anywhere on the board, providing that no population limits are exceeded."),

	DIPLOMACY("Diplomacy", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 9190460740872090026L;

		{
			add(Type.ARTS);
		}
	}, 160, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -1928809453165954426L;

		{
			put(Type.ARTS, 10);
			put(Type.CIVICS, 5);
		}
	}, "Players are not allowed to move tokens into areas containing your cities, except for areas where a conflict situation already occurs. This does not count for players holding Diplomacy or Military"),

	DRAMA_AND_POETRY("Drama and Poetry", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -2784357428872004066L;

		{
			add(Type.ARTS);
		}
	}, 80, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 2508167620104851316L;

		{
			put(Type.ARTS, 10);
			put(Type.RELIGION, 5);
		}
	}, "Civil War: Select 5 less unit points.\n" + "Civil Disorder: Reduce 1 less city."),

	EMPIRICISM("Empiricism", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 1791410751966280831L;

		{
			add(Type.SCIENCE);
		}
	}, 60, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -2440070450841210384L;

		{
			put(Type.ARTS, 5);
			put(Type.CIVICS, 5);
			put(Type.CRAFTS, 5);
			put(Type.RELIGION, 5);
			put(Type.SCIENCE, 10);
		}
	}, "�"),

	ENGINEERING("Engineering", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -5500908068718899766L;

		{
			add(Type.CRAFTS);
			add(Type.SCIENCE);
		}
	}, 160, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 4694052891551787431L;

		{
			put(Type.CRAFTS, 10);
			put(Type.SCIENCE, 10);
		}
	}, "1) Other players require 8 tokens to succesfully attack your cities. Your cities are then replaced by 6 tokens. This does not apply when the attacking player also holds Engineering.\n"
			+ "2) You recquire 6 tokens to succesfully attack other player's cities. Their cities are then replaced by 5 tokens. This does not apply when defending player also holds Engineering.\n"
			+ "Earthquake: Your city is reduced instead of destroyed.\n" + "Flood: Prevent 5 damage."),

	ENLIGHTENMENT("Enlightenment", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 6341639742157385191L;

		{
			add(Type.RELIGION);
		}
	}, 160, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 5521633594418271025L;

		{
			put(Type.CRAFTS, 5);
			put(Type.RELIGION, 10);
		}
	}, "Superstition: Reduce 1 less city.\n"
			+ "Slave Revolt: Your city support rate is decreased by 1 during the resolution of Slave Revolt.\n"
			+ "Epidemic: If you are the primary victim, prevent 5 damage.\n"
			+ "Regression: For each step backward, you may choose to prevent the effect by destroying 2 of your cities (if possible non-coastal)."),

	FUNDAMENTALISM("Fundamentalism", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 1554440447155252840L;

		{
			add(Type.RELIGION);
		}
	}, 150, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -3633799324062178157L;

		{
			put(Type.ARTS, 5);
			put(Type.RELIGION, 10);
		}
	}, "Special ability: You may choose to destroy all units in an area adjacent by land to your units. Barbarion tokens, pirate cities and units belonging to players holding Fundamentalism or Philosophy are unaffected.\n"
			+ "Regression: Your marker is moved backward 1 additional step."),

	LAW("Law", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -5918056052373259084L;

		{
			add(Type.CIVICS);
		}
	}, 150, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 1957237424868790614L;

		{
			put(Type.CIVICS, 10);
			put(Type.RELIGION, 5);
		}
	}, "Tyranny: The beneficiary selects and annexes 5 less unit points.\n" + "Civil Disorder: Reduce 1 less city.\n"
			+ "Corruption: Discard 5 less points of face value."),

	LIBRARY("Library", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -2916839170015241620L;

		{
			add(Type.SCIENCE);
		}
	}, 220, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -5216957773213537577L;

		{
			put(Type.ARTS, 5);
			put(Type.SCIENCE, 20);
		}
	}, "(*) You may discount the cost of any one (1) other Civilization Advance that you purchase in the same turn as Library by 40 points.\n"
			+ "Regression: Your marker is moved backward 1 less step."),

	LITERACY("Literacy", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 5169135957860699045L;

		{
			add(Type.ARTS);
			add(Type.CIVICS);
		}
	}, 110, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -2784357428872004066L;

		{
			put(Type.ARTS, 10);
			put(Type.CIVICS, 10);
			put(Type.CRAFTS, 5);
			put(Type.RELIGION, 5);
			put(Type.SCIENCE, 5);
		}
	}, "�"),

	MASONRY("Masonry", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -4759806750315959504L;

		{
			add(Type.CRAFTS);
		}
	}, 60, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 3886876214796908165L;

		{
			put(Type.CRAFTS, 10);
			put(Type.SCIENCE, 5);
		}
	}, "Cyclone: Reduce 1 less of your selected cities."),

	MATHEMATICS("Mathematics", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -3009806632786713328L;

		{
			add(Type.ARTS);
			add(Type.SCIENCE);
		}
	}, 250, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -2931247979993348637L;

		{
			put(Type.ARTS, 20);
			put(Type.CIVICS, 10);
			put(Type.CRAFTS, 10);
			put(Type.RELIGION, 10);
			put(Type.SCIENCE, 20);
		}
	}, "�"),

	MEDICINE("Medicine", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -2539202365013314947L;

		{
			add(Type.SCIENCE);
		}
	}, 140, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -5500908068718899766L;

		{
			put(Type.CRAFTS, 5);
			put(Type.SCIENCE, 10);
		}
	}, "Epidemic: Prevent 5 damage."),

	METALWORKING("Metalworking", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 5911386512837685867L;

		{
			add(Type.CRAFTS);
		}
	}, 90, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 153540437145224724L;

		{
			put(Type.CIVICS, 5);
			put(Type.CRAFTS, 10);
		}
	}, "In conflicts, for each round of token removal all other players not holding Metalworking must remove their token first."),

	MILITARY("Military", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -4006498687693613256L;

		{
			add(Type.CIVICS);
		}
	}, 170, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 695307583627107598L;

		{
			put(Type.CIVICS, 10);
			put(Type.CRAFTS, 5);
		}
	}, "1) Your movement phase is after all other players not holding Military have moved.\n"
			+ "2) You are allowed to move tokens into areas containing cities belonging to players holding Diplomacy."),

	MINING("Mining", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 5253442009242988444L;

		{
			add(Type.CRAFTS);
		}
	}, 230, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 1554440447155252840L;

		{
			put(Type.CRAFTS, 20);
			put(Type.SCIENCE, 5);
		}
	}, "1) During the Trade Cards Acquisition phase, you may acquire additional trade cards from stack 6 and/or stack 8 for 13 treasury tokens per card.\n"
			+ "2) Treasury tokens are worth 2 points when purchasing Civilization Advances.\n"
			+ "Slave Revolt: Your city support rate is increased by 1 during the resolution of Slave Revolt."),

	MONARCHY("Monarchy", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 496790214147322466L;

		{
			add(Type.CIVICS);
		}
	}, 60, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 5522467210991958034L;

		{
			put(Type.RELIGION, 5);
			put(Type.CIVICS, 10);
		}
	}, "You may choose to increase your tax rate by 1.\n" + "Barbarian Hordes: 5 less barbarian tokens are used.\n"
			+ "Tyranny: The beneficiary selects and annexes 5 additional unit points."),

	MONOTHEISM("Monotheism", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 4934854144715439723L;

		{
			add(Type.RELIGION);
		}
	}, 240, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -1655573250084512512L;

		{
			put(Type.CIVICS, 5);
			put(Type.RELIGION, 20);
		}
	}, "Special Ability: You may choose to annex all units in an area adjacent by land to your units. Barbarian tokens, pirate cities and units belonging to players holding Monotheism or Theology are unaffected.\n"
			+ "Iconoclasm and Heresy: Reduce 1 additional city."),

	MONUMENT("Monument", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -8973446464730384373L;

		{
			add(Type.CRAFTS);
			add(Type.RELIGION);
		}
	}, 180, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -2916839170015241620L;

		{
			put(Type.CRAFTS, 10);
			put(Type.RELIGION, 10);
		}
	}, "(*) Acquire 20 additional points of credit tokens in any combination of colors."),

	MUSIC("Music", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -7075830267764064545L;

		{
			add(Type.ARTS);
		}
	}, 80, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 8451559203862867209L;

		{
			put(Type.ARTS, 10);
			put(Type.RELIGION, 5);
		}
	}, "Civil War: Select 5 less unit points.\n" + "Civil Disorder: Reduce 1 less city."),

	MYSTICISM("Mysticism", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -8854104313973154017L;

		{
			add(Type.ARTS);
			add(Type.RELIGION);
		}
	}, 50, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 7736326899144584278L;

		{
			put(Type.ARTS, 5);
			put(Type.RELIGION, 5);
		}
	}, "Superstition: Reduce 1 less city."),

	MYTHOLOGY("Mythology", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 3776335466840823949L;

		{
			add(Type.RELIGION);
		}
	}, 60, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -4759806750315959504L;

		{
			put(Type.ARTS, 5);
			put(Type.RELIGION, 10);
		}
	}, "Slave Revolt: Your city support rate is decreased by 1 during the resolution of Slave Revolt."),

	NAVAL_WARFARE("Naval Warfare", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 5691558771919442457L;

		{
			add(Type.CIVICS);
		}
	}, 160, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 3731070778448469111L;

		{
			put(Type.CIVICS, 10);
			put(Type.CRAFTS, 5);
		}
	}, "1) You ships are allowed to carry 6 tokens.\n"
			+ "2) In conflicts, you may choose to remove ships from the conflict area instead of tokens. After each round of token removal a new check for token majority must be made.\n"
			+ "Piracy: If you are the primary victim, the beneficiary selects and replaces 1 less coastal city. You may not be selected as a secondary victim.\n"
			+ "Civil Disorder: Reduce 1 additional city."),

	PHILOSOPHY("Philosophy", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -4374972777215825826L;

		{
			add(Type.RELIGION);
			add(Type.SCIENCE);
		}
	}, 220, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -4771461426299412253L;

		{
			put(Type.RELIGION, 20);
			put(Type.SCIENCE, 20);
		}
	}, "Iconoclasm and Heresy: Reduce 2 less cities.\n"
			+ "You units are protected against the effect of Fundamentalism.\n"
			+ "Civil War: Select 5 additional unit points."),

	POLITICS("Politics", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -4994407058710027570L;

		{
			add(Type.ARTS);
		}
	}, 230, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -2539202365013314947L;

		{
			put(Type.ARTS, 20);
			put(Type.SCIENCE, 5);
		}
	}, "Special ability: You may choose one of two options:\n" + "1) Gain up to 5 treasury tokens from stock.\n"
			+ "2) Annex all units in an area adjacent by land to your units. Pay treasury tokens equal to the number of units annexed or the effect is cancelled. Barbarian tokens, pirate cities and units belonging to players holding Politics or Cultural Ascendancy are unaffected.\n"
			+ "Barbarian Hordes: 5 additional barbarian tokens are used."),

	POTTERY("Pottery", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -8676377178777198075L;

		{
			add(Type.CRAFTS);
		}
	}, 60, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 3062557596876721335L;

		{
			put(Type.ARTS, 5);
			put(Type.CRAFTS, 10);
		}
	}, "Famine: Prevent 5 damage."),

	PROVINCIAL_EMPIRE("Provincial Empire", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -441460963738213208L;

		{
			add(Type.CIVICS);
		}
	}, 260, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 7052218266071942939L;

		{
			put(Type.CIVICS, 20);
			put(Type.RELIGION, 5);
		}
	}, "Special ability: You may choose to select up to five players that have units adjacent by land or water to your units. These players must choose and give you a commodity card with a face value of at least 2. Players holding Provincial Empire or Public Works may not be selected.\n"
			+ "Barbarian Hordes: 5 additional barbarian tokens are used.\n"
			+ "Tyranny: The beneficiary selects and annexes 5 additional unit points."),

	PUBLIC_WORKS("Public Works", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -7961471806561558566L;

		{
			add(Type.CIVICS);
		}
	}, 230, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -4006498687693613256L;

		{
			put(Type.CIVICS, 20);
			put(Type.CRAFTS, 5);
		}
	}, "1) Areas containing your cities may also contain 1 of your tokens.\n"
			+ "2) You are protected against the effect of Provincial Empire."),

	RHETORIC("Rhetoric", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 3994229895436249390L;

		{
			add(Type.ARTS);
		}
	}, 130, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -960754153608837939L;

		{
			put(Type.ARTS, 10);
			put(Type.CIVICS, 5);
		}
	}, "During the Trade Cards Acquisition phase, you may acquire additional trade cards from stack 3 for 9 treasury tokens per card."),

	ROADBUILDING("Roadbuilding", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 3571359703616253921L;

		{
			add(Type.CRAFTS);
		}
	}, 220, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -2736113322038089891L;

		{
			put(Type.CRAFTS, 20);
			put(Type.SCIENCE, 5);
		}
	}, "1) When moving over land, your tokens may move 2 areas. Tokens thare are in a conflict situation after one step are not allowed to move any further.\n"
			+ "2) You hand limit of trade cards is increased by 1.\n"
			+ "Epidemic: If you are the primary victim, take 5 additional 5 damage."),

	SCULPTURE("Sculpture", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = -3874817310479091119L;

		{
			add(Type.ARTS);
		}
	}, 50, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 496790214147322466L;

		{
			put(Type.ARTS, 10);
			put(Type.CIVICS, 5);
		}
	}, "Tyranny: The beneficiary selects and annexes 5 less unit points."),

	THEOCRACY("Theocracy", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 636290723589213937L;

		{
			add(Type.CIVICS);
			add(Type.RELIGION);
		}
	}, 80, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 6482966938428166524L;

		{
			put(Type.CIVICS, 5);
			put(Type.RELIGION, 5);
		}
	}, "Iconoclasm and Heresy: You may choose to discard 2 commodity cards to prevent the city reduction effect for you."),

	THEOLOGY("Theology", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 3211276437628234433L;

		{
			add(Type.RELIGION);
		}
	}, 250, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -9112762614196718408L;

		{
			put(Type.RELIGION, 20);
			put(Type.SCIENCE, 5);
		}
	}, "Iconoclasm and Heresy: Reduce 3 cities less.\n" + "Your units are protected against the effect of Monotheism."),

	TRADE_EMPIRE("Trade Empire", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 9033594237975915479L;

		{
			add(Type.CRAFTS);
		}
	}, 260, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -8973446464730384373L;

		{
			put(Type.CIVICS, 5);
			put(Type.CRAFTS, 20);
		}
	}, "Once per turn, you may choose to use 1 substitute commodity card of at least the same face value when turning in any incomplete set of commodity cards.\n"
			+ "Cyclone: Select and reduce 1 additional city adjacent to the open sea areas.\n"
			+ "Epidemic: If you are the primary victim, take 5 additional damage."),

	TRADE_ROUTES("Trade Routes", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 5565619966939636491L;

		{
			add(Type.CRAFTS);
		}
	}, 180, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 1000314777045839493L;

		{
			put(Type.CRAFTS, 10);
			put(Type.RELIGION, 5);
		}
	}, "Special ability: You may choose to discard any number of commodity cards to gain treasure tokens at twice the face value of the commodity cards discarded this way."),

	UNIVERSAL_DOCTRINE("Universal Doctrine", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 1142150014099483271L;

		{
			add(Type.RELIGION);
		}
	}, 160, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -2947007409629951038L;

		{
			put(Type.CIVICS, 5);
			put(Type.RELIGION, 10);
		}
	}, "Special ability: You may choose to annex 1 pirate city or up to 5 barbarian tokens anywhere on the board.\n"
			+ "Superstition: Reduce 1 additional city."),

	URBANISM("Urbanism", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 7378454955481019071L;

		{
			add(Type.CIVICS);
		}
	}, 50, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -8854104313973154017L;

		{
			put(Type.CIVICS, 10);
			put(Type.SCIENCE, 5);
		}
	}, "Once per turn, when constructing a wilderness city you may choose to use up to 4 tokens from areas adjacent by land."),

	WONDER_OF_THE_WORLD("Wonder of the World", new ArrayList<Technology.Type>() {
		private static final long serialVersionUID = 4210281195834584120L;

		{
			add(Type.ARTS);
			add(Type.CRAFTS);
		}
	}, 290, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = -56138410585717230L;

		{
			put(Type.ARTS, 20);
			put(Type.CRAFTS, 20);
		}
	}, "1) During the Trade Cards Acquisition phase, you may acquire 1 additional trade card for free from a stack number that is higher than your number of cities in play.\n"
			+ "2) Wonders of the World counts as a city during the A.S.T.-alteration phase.\n"
			+ "Corruption: Discard 5 additional points of face value."),

	WRITTEN_RECORD("Written Record", new ArrayList<Technology.Type>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2478856674377406249L;

		{
			add(Type.CIVICS);
			add(Type.SCIENCE);
		}
	}, 60, new HashMap<Technology.Type, Integer>() {
		private static final long serialVersionUID = 8548758843910116379L;

		{
			put(Type.CIVICS, 5);
			put(Type.SCIENCE, 5);
		}
	}, "(*) Acquire 10 additional points of credit tokens in any combination of colors.");

	public enum Type {
		ARTS("blue", Color.BLUE, Color.WHITE, "images/Arts.png"),
		CIVICS("red", Color.RED, Color.WHITE, "images/Civics.png"),
		CRAFTS("orange", Color.ORANGE, Color.BLACK, "images/Crafts.png"),
		RELIGION("yellow", Color.YELLOW, Color.BLACK, "images/Religion.png"),
		SCIENCE("green", Color.GREEN, Color.BLACK, "images/Science.png");

		public String getHtmlColor() {
			return this.htmlColor;
		}

		public Color getTextColor() {
			return this.textColor;
		}

		public Color getColor() {
			return this.color;
		}

		public URL getIconURL() {
			return this.iconUrl;
		}

		private final String	htmlColor;
		private final Color		color, textColor;
		private final URL		iconUrl;

		private Type(String htmlColor, Color color, Color textColor, String iconName) {
			this.htmlColor = htmlColor;
			this.color = color;
			this.textColor = textColor;
			this.iconUrl = Technology.class.getResource(iconName);
		}

	};

	public final static class techCostComparator implements Comparator<Technology> {
		private Civilization civ;

		public techCostComparator() {
			this.civ = null;
		}

		public techCostComparator(Civilization civ) {
			this.civ = civ;
		}

		public int compare(Technology tech1, Technology tech2) {
			if (civ == null) {
				return Integer.compare(tech1.baseCost, tech2.baseCost);
			} else {
				return Integer.compare(this.civ.getCost(tech1), this.civ.getCost(tech2));
			}
		}
	}

	private final static HashMap<Technology, HashMap<Technology, Integer>> TECH_CREDITS;
	static {
		TECH_CREDITS = new HashMap<Technology, HashMap<Technology, Integer>>() {
			private static final long serialVersionUID = -3028972728419108243L;

			{
				put(ADVANCED_MILITARY, new HashMap<Technology, Integer>());
				put(AGRICULTURE, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 1L;

					{
						put(DEMOCRACY, 20);
					}
				});
				put(ANATOMY, new HashMap<Technology, Integer>());
				put(ARCHITECHTURE, new HashMap<Technology, Integer>() {
					/**
					* 
					*/
					private static final long serialVersionUID = -5691575576141773051L;

					{
						put(MINING, 20);
					}
				});
				put(ASTRONAVIGATION, new HashMap<Technology, Integer>() {
					/**
					* 
					*/
					private static final long serialVersionUID = -3926050370819280779L;

					{
						put(CALENDAR, 10);
					}
				});
				put(CALENDAR, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -7358949462706803239L;

					{
						put(PUBLIC_WORKS, 20);
					}
				});
				put(CARTOGRAPHY, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -2182972486670609991L;

					{
						put(LIBRARY, 20);
					}
				});
				put(CLOTH_MAKING, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 608388059952309350L;

					{
						put(NAVAL_WARFARE, 10);
					}
				});
				put(COINAGE, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 2414946767475902483L;

					{
						put(TRADE_ROUTES, 10);
					}
				});
				put(CULTURAL_ASCENDANCY, new HashMap<Technology, Integer>());
				put(DEISM, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 1L;
					{
						put(FUNDAMENTALISM, 10);
					}
				});
				put(DEMOCRACY, new HashMap<Technology, Integer>());
				put(DIASPORA, new HashMap<Technology, Integer>());
				put(DIPLOMACY, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 1917499061422154540L;

					{
						put(PROVINCIAL_EMPIRE, 20);
					}
				});
				put(DRAMA_AND_POETRY, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -1924242606591739472L;

					{
						put(RHETORIC, 10);
					}
				});
				put(EMPIRICISM, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 2800580277858469998L;

					{
						put(MEDICINE, 10);
					}
				});
				put(ENGINEERING, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -8961620213522975422L;

					{
						put(ROADBUILDING, 20);
					}
				});
				put(ENLIGHTENMENT, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 7755598784455616612L;

					{
						put(PHILOSOPHY, 20);
					}
				});
				put(FUNDAMENTALISM, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 7614342863146910011L;

					{
						put(MONOTHEISM, 20);
					}
				});
				put(LAW, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 9190460740872090026L;

					{
						put(CULTURAL_ASCENDANCY, 20);
					}
				});
				put(LIBRARY, new HashMap<Technology, Integer>());
				put(LITERACY, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -8801555888662344887L;

					{
						put(MATHEMATICS, 20);
					}
				});
				put(MASONRY, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 1791410751966280831L;

					{
						put(ENGINEERING, 10);
					}
				});

				put(MATHEMATICS, new HashMap<Technology, Integer>());
				put(MEDICINE, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -8024797919387478872L;
					{
						put(ANATOMY, 20);
					}
				});

				put(METALWORKING, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 6341639742157385191L;

					{
						put(MILITARY, 10);
					}
				});

				put(MILITARY, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -4052425467290599144L;

					{
						put(ADVANCED_MILITARY, 20);
					}
				});
				put(MINING, new HashMap<Technology, Integer>());
				put(MONARCHY, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -5918056052373259084L;

					{
						put(LAW, 10);
					}
				});
				put(MONOTHEISM, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -8201900549805050830L;

					{}
				});
				put(MONUMENT, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -5750080934165932155L;

					{
						put(WONDER_OF_THE_WORLD, 20);
					}
				});
				put(MUSIC, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 5169135957860699045L;

					{
						put(ENLIGHTENMENT, 10);
					}
				});
				put(MYSTICISM, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -1852470882734529318L;

					{
						put(MONUMENT, 10);
					}
				});
				put(MYTHOLOGY, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 1134509524946474103L;

					{
						put(LITERACY, 10);
					}
				});
				put(NAVAL_WARFARE, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -3009806632786713328L;

					{
						put(DIASPORA, 20);
					}
				});
				put(PHILOSOPHY, new HashMap<Technology, Integer>());
				put(POLITICS, new HashMap<Technology, Integer>());
				put(POTTERY, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 5911386512837685867L;

					{
						put(AGRICULTURE, 10);
					}
				});
				put(PROVINCIAL_EMPIRE, new HashMap<Technology, Integer>());
				put(PUBLIC_WORKS, new HashMap<Technology, Integer>());
				put(RHETORIC, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 5253442009242988444L;
					{
						put(POLITICS, 20);
					}
				});
				put(ROADBUILDING, new HashMap<Technology, Integer>());
				put(SCULPTURE, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -7321891526501761400L;

					{
						put(ARCHITECHTURE, 10);
					}
				});
				put(THEOCRACY, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 4934854144715439723L;

					{
						put(UNIVERSAL_DOCTRINE, 10);
					}
				});
				put(THEOLOGY, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 6927101003978070906L;

					{}
				});
				put(TRADE_EMPIRE, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 3839429846268643402L;

					{}
				});
				put(TRADE_ROUTES, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -7075830267764064545L;

					{
						put(TRADE_EMPIRE, 20);
					}
				});
				put(UNIVERSAL_DOCTRINE, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -1875215478631324386L;

					{
						put(THEOLOGY, 20);
					}
				});
				put(URBANISM, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 7154407411116544629L;

					{
						put(DIPLOMACY, 10);
					}
				});
				put(WONDER_OF_THE_WORLD, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = 3776335466840823949L;

					{}
				});
				put(WRITTEN_RECORD, new HashMap<Technology, Integer>() {
					private static final long serialVersionUID = -4990883935554849497L;

					{
						put(CARTOGRAPHY, 10);
					}
				});
			}
		};
	}

	private final int								MAX_HTML_WIDTH	= 50;

	@JsonProperty("name")
	private final String							name;
	@JsonProperty("text")
	private final String							text;
	@JsonProperty("baseCost")
	private final int								baseCost;
	private final int								vp;
	@JsonProperty("types")
	private final ArrayList<Technology.Type>		types;
	@JsonProperty("typeCredits")
	private final HashMap<Technology.Type, Integer>	typeCredits;

	Technology(String name, ArrayList<Technology.Type> types, int baseCost,
			HashMap<Technology.Type, Integer> typeCredits, String text) {
		this.name = name;
		this.types = types;
		this.baseCost = baseCost;
		this.vp = ( baseCost >= 200 ) ? 6 : ( baseCost >= 100 ) ? 3 : 1;
		this.typeCredits = typeCredits;
		this.text = text;
	}

	public String getName() {
		return this.name;
	}

	public String getText() {
		return this.text;
	}

	public ArrayList<Technology.Type> getTypes() {
		return this.types;
	}

	public int getBaseCost() {
		return this.baseCost;
	}

	public int getTechCredit(Technology tech) {
		return TECH_CREDITS.get(this).containsKey(tech) ? TECH_CREDITS.get(this).get(tech) : 0;
	}

	public int getTypeCredit(Type type) {
		return this.typeCredits.containsKey(type) ? this.typeCredits.get(type) : 0;
	}

	public int getVP() {
		return this.vp;
	}

	public String toHtmlString() {
		String s = "<html><strong>" + this.name + "<br/>";
		for (Technology.Type t : this.types) {
			s += "<span color=\"" + t.getHtmlColor() + "\">" + t.toString() + "</span>&nbsp;";
		}
		s = s + "</strong><br/>";
		s = s + "<strong>Base Cost</strong> " + this.baseCost + " (" + this.vp + " VP)<BR/>";
		s = s + "<strong>Credits</strong> ";
		for (Technology.Type t : this.typeCredits.keySet()) {
			s = s + "<span color=\"" + t.getHtmlColor() + "\">" + this.typeCredits.get(t) + " " + t.toString()
					+ "</span>&nbsp;";
		}
		for (Technology t : TECH_CREDITS.get(this).keySet()) {
			s = s + "<I>" + TECH_CREDITS.get(this).get(t) + " " + t.getName() + "</I>";
		}
		s = s + "<BR/>";
		String text = this.text.replace("\n", "<BR/>");
		s = s + StringTools.wrapHtml(text, MAX_HTML_WIDTH) + "</html>";
		return s;
	}

	public String toFullString() {
		String s = this.name + "\n";
		for (Technology.Type t : this.types) {
			s += t.toString() + " (" + t.getHtmlColor() + ") ";
		}
		s += "\n";
		s += "Base Cost: " + this.baseCost + "   VP: " + this.vp + "\n";
		s += "Credits: ";
		for (Technology.Type t : this.typeCredits.keySet()) {
			s += t.toString() + " " + this.typeCredits.get(t) + " ";
		}
		for (Technology t : TECH_CREDITS.get(this).keySet()) {
			s += t.name + " " + TECH_CREDITS.get(this).get(t) + " ";
		}
		s += "\n";
		s += this.text + "\n";
		return s;
	}
}