package net.bubbaland.megaciv.client.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import net.bubbaland.gui.BubbaGuiController;
import net.bubbaland.gui.BubbaPanel;
import net.bubbaland.megaciv.game.Civilization;
import net.bubbaland.megaciv.game.Game;

public class ScrollingAstPanel extends BubbaPanel {

	private static final long serialVersionUID = -1197287409680075891L;

	private enum Column {
		CIV, POPULATION, CITIES, AST, AST01, AST02, AST03, AST04, AST05, AST06, AST07, AST08, AST09, AST10, AST11, AST12, AST13, AST14, AST15, AST16
	}

	private final static HashMap<Column, ColumnData>	colData	= new HashMap<Column, ColumnData>() {
																	private static final long serialVersionUID = 1L;

																	{
																		put(Column.CIV, new ColumnData("Civ", 0));
																		put(Column.POPULATION,
																				new ColumnData("Pop", 1));
																		put(Column.CITIES, new ColumnData("Cities", 2));
																		put(Column.AST, new ColumnData("AST", 3));
																		put(Column.AST01, new ColumnData("", 4));
																		put(Column.AST02, new ColumnData("", 5));
																		put(Column.AST03, new ColumnData("", 6));
																		put(Column.AST04, new ColumnData("", 7));
																		put(Column.AST05, new ColumnData("", 8));
																		put(Column.AST06, new ColumnData("", 9));
																		put(Column.AST07, new ColumnData("", 10));
																		put(Column.AST08, new ColumnData("", 11));
																		put(Column.AST09, new ColumnData("", 12));
																		put(Column.AST10, new ColumnData("", 13));
																		put(Column.AST11, new ColumnData("", 14));
																		put(Column.AST12, new ColumnData("", 15));
																		put(Column.AST13, new ColumnData("", 16));
																		put(Column.AST14, new ColumnData("", 17));
																		put(Column.AST15, new ColumnData("", 18));
																		put(Column.AST16, new ColumnData("", 19));
																	}
																};


	private HashMap<Integer, RowPanel>					civRows;
	private HeaderPanel									headerPanel;

	private final GuiClient								client;

	private HashMap<Column, Integer>					width;
	private HashMap<Column, Float>						fontSize;
	private int											rowHeight;

	private final GuiController							controller;

	private Civilization.SortOption						sortOption;

	public ScrollingAstPanel(GuiClient client, GuiController controller) {
		super(controller, new GridBagLayout());
		this.client = client;
		this.controller = controller;
		this.sortOption = Civilization.SortOption.AST;
		this.civRows = null;

		// Set up layout constraints
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;

		loadProperties();

		this.headerPanel = new HeaderPanel(this.controller);
		this.add(this.headerPanel, constraints);

		this.updateGui(true);

	}

	public synchronized void redoRows(ArrayList<Civilization.Name> civNames) {
		if (this.civRows != null) {
			for (RowPanel panel : this.civRows.values()) {
				this.remove(panel);
			}
		}
		// Set up layout constraints
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;

		this.civRows = new HashMap<Integer, RowPanel>();
		for (Civilization.Name name : civNames) {
			RowPanel panel = new RowPanel(this.controller);
			constraints.gridy = civNames.indexOf(name) + 1;
			this.civRows.put(civNames.indexOf(name), panel);
			this.add(panel, constraints);
		}
	}

	public synchronized void updateGui(boolean forceUpdate) {
		loadProperties();

		Game game = this.client.getGame();
		if (this.civRows == null || game.getNCivilizations() != this.civRows.size()) {
			this.redoRows(game.getCivilizationNames());
		}

		ArrayList<Civilization> sortedCivs = Civilization.sortBy(this.client.getGame().getCivilizations(),
				this.sortOption);

		if (sortedCivs.size() == 0) {
			return;
		}

		Civilization firstCiv = sortedCivs.get(0);

		for (Civilization.Age age : Civilization.Age.values()) {
			int ageStart = firstCiv.getAgeStart(age);
			int ageEnd = this.client.getGame().lastAstStep();
			Civilization.Age nextAge = age.nextAge();
			if (nextAge != null) {
				ageEnd = firstCiv.getAgeStart(nextAge) - 1;
			}
			int diff = age == Civilization.Age.STONE ? ageEnd - ageStart : ageEnd - ageStart + 1;
			Color foregroundColor = this.controller.getAstForegroundColor(age);
			Color backgroundColor = this.controller.getAstBackgroundColor(age);

			BubbaPanel.setLabelProperties(this.headerPanel.getAgeHeader(age), width.get(Column.AST01) * diff,
					this.rowHeight, foregroundColor, backgroundColor, 14.0f);
		}

		for (Civilization civ : sortedCivs) {
			Civilization.Name name = civ.getName();
			RowPanel panel = this.civRows.get(sortedCivs.indexOf(civ));
			for (Column col : Column.values()) {
				JLabel label = panel.getLabel(col);
				String text = "";
				Color foregroundColor = this.controller.getCivForegroundColor(name);
				Color backgroundColor = this.controller.getCivBackgroundColor(name);
				switch (col) {
					case AST:
						text = civ.getAstPosition() + "";
						break;
					case POPULATION:
						text = civ.getPopulation() + "";
						break;
					case CITIES:
						text = civ.getCityCount() + "";
						break;
					case CIV:
						text = Game.capitalizeFirst(name.toString()) + " (" + civ.getPlayer() + ")";
						break;
					default:
						text = "";
						int astStep = Integer.parseInt(col.toString().substring(3));
						if (astStep > civ.getAstPosition()) {
							foregroundColor = this.controller.getAstForegroundColor(civ.getAge(astStep));
							backgroundColor = this.controller.getAstBackgroundColor(civ.getAge(astStep));
						}
						// System.out.println(this.getClass().getSimpleName() + " " + col + " " + civ.getAge(astStep));
						label.setVisible(astStep <= this.client.getGame().lastAstStep());
				}
				label.setText(text);
				setLabelProperties(label, this.width.get(col), this.rowHeight, foregroundColor, backgroundColor,
						this.fontSize.get(col));
			}


		}
	}

	public void loadProperties() {
		Properties props = this.controller.getProperties();

		this.width = new HashMap<Column, Integer>();
		this.fontSize = new HashMap<Column, Float>();

		for (Column col : Column.values()) {
			int width;
			float fontSize;

			switch (col) {
				case CIV:
				case POPULATION:
				case CITIES:
				case AST:
					width = Integer.parseInt(props.getProperty("AstTable." + col + ".Width"));
					fontSize = Float.parseFloat(props.getProperty("AstTable." + col + ".FontSize"));
					break;
				default:
					width = Integer.parseInt(props.getProperty("AstTable.AstStep.Width"));
					fontSize = Float.parseFloat(props.getProperty("AstTable.AstStep.FontSize"));
			}

			this.width.put(col, width);
			this.fontSize.put(col, fontSize);
		}

		this.rowHeight = Integer.parseInt(props.getProperty("AstTable.Row.Height"));

	}

	private class HeaderPanel extends BubbaPanel {

		private static final long						serialVersionUID	= 810881884756701202L;

		private final HashMap<Civilization.Age, JLabel>	ageLabels;

		public HeaderPanel(BubbaGuiController controller) {
			super(controller, new GridBagLayout());
			final GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.anchor = GridBagConstraints.SOUTH;
			constraints.weighty = 1.0;
			constraints.gridy = 0;

			for (Column col : Column.values()) {
				constraints.weightx = 0.0;
				constraints.gridx = colData.get(col).getColumnLocation();

				String string = Game.capitalizeFirst(col.toString());

				int width = ScrollingAstPanel.this.width.get(col);
				int height = ScrollingAstPanel.this.rowHeight;

				float fontSize = 14.0f;

				Color foreground = Color.WHITE;
				Color background = Color.BLACK;

				int justification = JLabel.CENTER;

				switch (col) {
					case CIV:
						constraints.weightx = 1.0;
						string = "Civilization (Player)";
						justification = JLabel.LEFT;
						break;
					case POPULATION:
						string = "Pop";
						break;
					case CITIES:
						break;
					case AST:
						string = "Pos";
						break;
					default:
						continue;
				}

				this.enclosedLabelFactory(string, width, height, foreground, background, constraints, fontSize,
						justification, JLabel.BOTTOM);
			}

			constraints.weightx = 0.0;

			this.ageLabels = new HashMap<Civilization.Age, JLabel>();
			for (Civilization.Age age : Civilization.Age.values()) {
				constraints.gridx = colData.get(Column.AST01).getColumnLocation() + age.ordinal();
				this.ageLabels.put(age, this.enclosedLabelFactory("", constraints, JLabel.LEFT, JLabel.BOTTOM));
			}

		}

		public JLabel getAgeHeader(Civilization.Age age) {
			return this.ageLabels.get(age);
		}

	}

	private class RowPanel extends BubbaPanel {

		private static final long				serialVersionUID	= 1L;

		private final HashMap<Column, JLabel>	labels;

		public RowPanel(BubbaGuiController controller) {
			super(controller, new GridBagLayout());
			final GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.anchor = GridBagConstraints.CENTER;
			constraints.weighty = 1.0;
			constraints.gridy = 0;

			this.labels = new HashMap<Column, JLabel>();

			for (Column col : Column.values()) {
				int justification = JLabel.RIGHT;
				switch (col) {
					case CIV:
						justification = JLabel.LEFT;
						constraints.weightx = 1.0;
						break;
					default:
						constraints.weightx = 0.0;
				}
				constraints.gridx = colData.get(col).getColumnLocation();
				JLabel label = this.enclosedLabelFactory("", constraints, justification, JLabel.CENTER);
				setLabelProperties(label, 100, 20, Color.BLACK, Color.WHITE, (float) 14.0);
				switch (col) {
					case CIV:
					case POPULATION:
					case CITIES:
					case AST:
						label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						break;
					default:
						label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
				}
				this.labels.put(col, label);
			}

			// this.setBorder(BorderFactory.createEmptyBorder());
			this.setBackground(Color.BLACK);
		}

		public JLabel getLabel(Column col) {
			return labels.get(col);
		}
	}

	private static class ColumnData {

		private final int colLocation;

		public ColumnData(String headerText, int colLocation) {
			this.colLocation = colLocation;
		}

		public int getColumnLocation() {
			return this.colLocation;
		}

	}

}
