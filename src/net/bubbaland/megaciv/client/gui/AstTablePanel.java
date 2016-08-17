package net.bubbaland.megaciv.client.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import org.apache.commons.lang3.text.WordUtils;

import net.bubbaland.gui.BubbaPanel;
import net.bubbaland.gui.LinkedLabelGroup;
import net.bubbaland.megaciv.client.GameClient;
import net.bubbaland.megaciv.game.Civilization;
import net.bubbaland.megaciv.game.Civilization.Age;
import net.bubbaland.megaciv.game.Game;
import net.bubbaland.megaciv.messages.AdvanceAstMessage;

public class AstTablePanel extends BubbaPanel {

	private static final long serialVersionUID = -1197287409680075891L;

	private enum Column {
		BUY, CIV, POPULATION, CITIES, TECHS, VP, AST01, AST02, AST03, AST04, AST05, AST06, AST07, AST08, AST09, AST10,
		AST11, AST12, AST13, AST14, AST15, AST16
	}

	private static final HashMap<Column, Civilization.SortOption>	sortHash	=
			new HashMap<Column, Civilization.SortOption>() {
																							private static final long serialVersionUID =
																									-3473350095491262976L;

																							{
																								put(Column.CIV,
																										Civilization.SortOption.AST);
																								put(Column.POPULATION,
																										Civilization.SortOption.MOVEMENT);
																								put(Column.CITIES,
																										Civilization.SortOption.CITIES);
																								// put(null,
																								// Civilization.SortOption.AST_POSITION
																								// );
																								put(Column.VP,
																										Civilization.SortOption.VP);
																								put(Column.AST01,
																										Civilization.SortOption.AST_POSITION);
																							}
																						};

	/** Sort icons */
	private static final ImageIcon									UP_ARROW	=
			new ImageIcon(BubbaPanel.class.getResource("images/upArrow.png"));
	private static final ImageIcon									DOWN_ARROW	=
			new ImageIcon(BubbaPanel.class.getResource("images/downArrow.png"));

	private static BufferedImage									FILLER_IMAGE;
	static {
		try {
			FILLER_IMAGE = ImageIO.read(AstTablePanel.class.getResource("images/filler.png"));
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	private HashMap<Integer, CivRow>	civRows;
	private Header						header;
	private FillerPanel					fillerPanel;

	private final LinkedLabelGroup		civNameGroup, statGroup, astGroup;

	private final GameClient			client;

	private HashMap<Column, Integer>	width;
	private HashMap<Column, Float>		fontSize;
	private int							rowHeight;

	private final GuiController			controller;

	private Civilization.SortOption		sortOption;
	private Civilization.SortDirection	sortDirection;

	public AstTablePanel(GameClient client, GuiController controller) {
		super(controller, new GridBagLayout());
		this.client = client;
		this.controller = controller;
		this.sortOption = Civilization.SortOption.AST;
		this.sortDirection = Civilization.SortDirection.ASCENDING;
		this.civRows = null;

		this.civNameGroup = new LinkedLabelGroup();
		this.statGroup = new LinkedLabelGroup();
		this.astGroup = new LinkedLabelGroup();

		// Set up layout constraints
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTH;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;

		this.header = new Header();

		constraints.weighty = 1.0;
		constraints.gridy = 1;
		this.fillerPanel = new FillerPanel();
		this.add(this.fillerPanel, constraints);

		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resizeFonts();
			}
		});

		loadProperties();

		this.updateGui(true);
	}

	public void resizeFonts() {
		this.civNameGroup.resizeFonts();
		this.statGroup.resizeFonts();
		this.astGroup.resizeFonts();
	}

	public synchronized void redoRows(ArrayList<Civilization.Name> civNames) {
		if (this.civRows != null) {
			for (CivRow row : this.civRows.values()) {
				row.remove();
			}
		}
		this.remove(this.fillerPanel);

		this.civRows = new HashMap<Integer, CivRow>();
		for (Civilization.Name name : civNames) {
			int rowNumber = civNames.indexOf(name) + 1;
			CivRow row = new CivRow(rowNumber);
			this.civRows.put(civNames.indexOf(name), row);
			row.loadProperties();
		}

		// final GridBagConstraints constraints = new GridBagConstraints();
		// constraints.fill = GridBagConstraints.BOTH;
		// constraints.anchor = GridBagConstraints.CENTER;
		// constraints.weightx = 1.0;
		// constraints.weighty = 10.0;
		// constraints.gridx = 0;
		// constraints.gridy = civNames.size() + 2;
		// constraints.gridwidth = Column.values().length;
		// this.fillerPanel = new FillerPanel();
		// this.add(this.fillerPanel, constraints);
		this.resizeFonts();
	}

	public synchronized void updateGui(boolean forceUpdate) {
		Game game = this.client.getGame();
		if (game == null) {
			return;
		}

		if (this.civRows == null || game.getNCivilizations() != this.civRows.size()) {
			this.redoRows(game.getCivilizationNames());
		}

		ArrayList<Civilization> sortedCivs =
				Civilization.sortBy(this.client.getGame().getCivilizations(), this.sortOption, this.sortDirection);

		if (sortedCivs.size() == 0 || this.header == null) {
			return;
		}

		for (Civilization civ : sortedCivs) {
			Civilization.Name name = civ.getName();
			CivRow row = this.civRows.get(sortedCivs.indexOf(civ));
			row.setName(name);
			for (Column col : EnumSet.allOf(Column.class)) {
				JComponent component = row.getComponent(col);
				String text = "";
				Color foregroundColor = Civilization.FOREGROUND_COLORS.get(name);
				Color backgroundColor = Civilization.BACKGROUND_COLORS.get(name);
				switch (col) {
					// case AST:
					// text = civ.getAst() + "";
					// break;
					case POPULATION:
						text = String.format("%1$2d", civ.getPopulation());
						break;
					case CITIES:
						text = String.format("%1$2d", civ.getCityCount());
						break;
					case CIV:
						text = WordUtils.capitalizeFully(name.toString()) + " (" + civ.getPlayer() + ")";
						break;
					case TECHS:
						text = String.format("%1$2d", civ.getTechs().size());
						component.setToolTipText(civ.getTechBreakdownString());
						break;
					case VP:
						if (civ.getCurrentAge() == Age.LATE_IRON && civ.onlyLateIron(sortedCivs)) {
							text = "*" + String.format("%1$3d", civ.getVP());
						} else {
							text = String.format("%1$3d", civ.getVP());
						}
						component.setToolTipText(civ.getVpBreakdownString());
						break;
					case BUY:
						row.buyButton.setEnabled(!civ.hasPurchased());
						continue;
					default:
						int astStep = Integer.parseInt(col.toString().substring(3));
						Civilization.Age age = civ.getAge(astStep);
						if (astStep <= civ.getAstPosition()) {
							text = String.format("%1$2d", ( astStep * Game.VP_PER_AST_STEP ));
							component.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
							foregroundColor = this.controller.getAstBackgroundColor(age);
							component.setToolTipText("");
						} else {
							component.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
							foregroundColor = this.controller.getAstForegroundColor(age);
							backgroundColor = this.controller.getAstBackgroundColor(age);
							if (astStep > 0 && civ.passAstRequirements(civ.getAge(astStep - 1))) {
								text = civ.passAstRequirements(age) ? "  " : "!";
							}
							if (age != Age.STONE) {
								component.setToolTipText(civ.astRequirementString(age));
							} else {
								component.setToolTipText("");
							}
						}
						component.setVisible(astStep <= this.client.getGame().lastAstStep());
						component.getParent().setVisible(astStep <= this.client.getGame().lastAstStep());


				}
				JLabel label = (JLabel) component;
				label.setText(text);
				label.setForeground(foregroundColor);
				label.setBackground(backgroundColor);
				label.getParent().setBackground(backgroundColor);
			}
		}

		this.header.updateGui(forceUpdate, sortedCivs.get(0));
		this.resizeFonts();
	}

	public void loadProperties() {
		Properties props = this.controller.getProperties();

		this.width = new HashMap<Column, Integer>();
		this.fontSize = new HashMap<Column, Float>();

		for (Column col : EnumSet.allOf(Column.class)) {
			int width;
			float fontSize;

			switch (col) {
				case CIV:
				case POPULATION:
				case CITIES:
				case TECHS:
				case BUY:
					// case AST:
				case VP:
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

		if (this.civRows != null) {
			for (CivRow panel : this.civRows.values()) {
				panel.loadProperties();
			}
		}

		this.header.loadProperties();

		this.resizeFonts();
		this.updateGui(true);
	}

	private class Header implements MouseListener {

		private final HashMap<Column, JLabel> colLabels;

		public Header() {

			final GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.anchor = GridBagConstraints.FIRST_LINE_START;
			constraints.weighty = 0.0;
			constraints.gridy = 0;

			this.colLabels = new HashMap<Column, JLabel>();

			for (Column col : EnumSet.allOf(Column.class)) {
				constraints.weightx = 0.0;
				constraints.gridx = col.ordinal();

				String string = WordUtils.capitalizeFully(col.toString());

				int justification = JLabel.CENTER;

				switch (col) {
					// case AST:
					// string = "AST";
					// break;
					case CIV:
						string = "Civilization (Player)";
						justification = JLabel.LEFT;
						constraints.weightx = 1.0;
						break;
					case POPULATION:
						string = "Pop";
						break;
					case CITIES:
						string = "Cities";
						break;
					case TECHS:
						string = "Adv";
						break;
					case VP:
						string = "VP";
						break;
					default:
						string = "";
						// constraints.weightx = 1.0;
				}

				JLabel label =
						AstTablePanel.this.enclosedLabelFactory(string, constraints, justification, JLabel.BOTTOM);
				this.colLabels.put(col, label);
				label.setName(col.toString());
				label.addMouseListener(this);
			}
		}

		public void loadProperties() {
			Properties props = AstTablePanel.this.controller.getProperties();
			int height = Integer.parseInt(props.getProperty("AstTable.Header.Height"));

			Color foreground =
					new Color(new BigInteger(props.getProperty("AstTable.Header.ForegroundColor"), 16).intValue());
			Color background =
					new Color(new BigInteger(props.getProperty("AstTable.Header.BackgroundColor"), 16).intValue());

			float fontSize = Float.parseFloat(props.getProperty("AstTable.Header.FontSize"));

			for (Column col : EnumSet.allOf(Column.class)) {
				int width = AstTablePanel.this.width.get(col);
				JLabel label = this.colLabels.get(col);
				BubbaPanel.setLabelProperties(label, width, height, foreground, background, fontSize);
			}
		}

		public void updateGui(boolean forceUpdate, Civilization firstCiv) {
			for (Column col : this.colLabels.keySet()) {
				JLabel label = this.colLabels.get(col);
				if (AstTablePanel.sortHash.get(col) == AstTablePanel.this.sortOption) {
					switch (AstTablePanel.this.sortDirection) {
						case ASCENDING:
							label.setIcon(DOWN_ARROW);
							break;
						case DESCENDING:
							label.setIcon(UP_ARROW);
							break;
					}
				} else {
					label.setIcon(null);
				}
				switch (col) {
					// case AST:
					case CIV:
					case POPULATION:
					case VP:
					case CITIES:
					case TECHS:
					case BUY:
						continue;
					default:

				}

				int astStep = Integer.parseInt(col.toString().substring(3));
				Civilization.Age age = firstCiv.getAge(astStep);
				label.setToolTipText(age + ": " + Civilization.AGE_REQUIREMENTS
						.get(AstTablePanel.this.client.getGame().getDifficulty()).get(age).getText());
				label.getParent().setBackground(AstTablePanel.this.controller.getAstBackgroundColor(age));
				label.getParent().setVisible(astStep <= AstTablePanel.this.client.getGame().lastAstStep());
			}
		}


		@Override
		public void mouseClicked(MouseEvent e) {
			final String source = ( (JComponent) e.getSource() ).getName();
			Column col = Column.valueOf(source);
			if (AstTablePanel.sortHash.get(col) == AstTablePanel.this.sortOption) {
				switch (AstTablePanel.this.sortDirection) {
					case ASCENDING:
						AstTablePanel.this.sortDirection = Civilization.SortDirection.DESCENDING;
						break;
					case DESCENDING:
						AstTablePanel.this.sortDirection = Civilization.SortDirection.ASCENDING;
						break;
				}
			} else {
				Civilization.SortOption newSort = AstTablePanel.sortHash.get(col);
				if (newSort != null) {
					AstTablePanel.this.sortOption = AstTablePanel.sortHash.get(col);
					AstTablePanel.this.sortDirection = Civilization.SortDirection.ASCENDING;
				}
			}
			AstTablePanel.this.updateGui(true);
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

	}

	private class CivRow implements ActionListener {

		private final HashMap<Column, JComponent>	components;
		private final JPopupMenu					contextMenu;
		private final JButton						buyButton;
		private final int							rowNumber;

		private Civilization.Name					name;

		public CivRow(int rowNumber) {
			this.rowNumber = rowNumber;

			this.contextMenu = new JPopupMenu();
			AstTablePanel.this.add(this.contextMenu);

			JMenuItem viewItem = new JMenuItem("View");
			viewItem.setActionCommand("View");
			viewItem.addActionListener(this);
			this.contextMenu.add(viewItem);

			JMenuItem editItem = new JMenuItem("Edit");
			editItem.setActionCommand("Edit");
			editItem.addActionListener(this);
			this.contextMenu.add(editItem);

			JMenuItem regressItem = new JMenuItem("Regress");
			regressItem.setActionCommand("Regress");
			regressItem.addActionListener(this);
			this.contextMenu.add(regressItem);

			this.buyButton = new JButton("Buy");
			this.buyButton.setActionCommand("Buy");
			this.buyButton.setMargin(new Insets(0, 0, 0, 0));
			this.buyButton.addActionListener(this);

			final GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.anchor = GridBagConstraints.FIRST_LINE_START;
			constraints.weighty = 1.0;
			constraints.gridy = this.rowNumber;

			this.components = new HashMap<Column, JComponent>();

			for (Column col : EnumSet.allOf(Column.class)) {
				int justification = JLabel.RIGHT;
				constraints.gridx = col.ordinal();
				constraints.weightx = 0.0;
				switch (col) {
					case BUY:
						AstTablePanel.this.add(this.buyButton, constraints);
						this.components.put(col, this.buyButton);
						continue;
					case CIV:
						justification = JLabel.LEFT;
						constraints.weightx = 1.0;
						break;
					case CITIES:
					case POPULATION:
					case TECHS:
					case VP:
						constraints.weightx = 1.0;
						break;
					default:
						justification = JLabel.CENTER;
						// constraints.weightx = 1.0;
				}
				JLabel label = AstTablePanel.this.enclosedLabelFactory("", constraints, justification, JLabel.CENTER);

				switch (col) {
					case CIV:
						label.add(this.contextMenu);
						label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						label.addMouseListener(new PopupListener(this.contextMenu));
						AstTablePanel.this.civNameGroup.addLabel(label);
						break;
					case POPULATION:
					case CITIES:
					case TECHS:
					case VP:
						label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						label.addMouseListener(new PopupListener(this.contextMenu));
						AstTablePanel.this.statGroup.addLabel(label);
						break;
					default:
						label.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						AstTablePanel.this.astGroup.addLabel(label);
				}
				this.components.put(col, label);
			}
		}

		public void loadProperties() {
			for (Column col : EnumSet.allOf(Column.class)) {
				if (col == Column.BUY) {
					BubbaPanel.setButtonProperties(this.buyButton, AstTablePanel.this.width.get(col),
							AstTablePanel.this.rowHeight, null, null, AstTablePanel.this.fontSize.get(col));
					continue;
				}
				// JLabel label = (JLabel) this.components.get(col);
				// BubbaPanel.setLabelProperties(label, AstTablePanel.this.width.get(col), AstTablePanel.this.rowHeight,
				// null, null, AstTablePanel.this.fontSize.get(col));
			}
		}

		public void remove() {
			for (JComponent component : this.components.values()) {
				AstTablePanel.this.remove(component);
				if (component instanceof JLabel) {
					AstTablePanel.this.remove(component.getParent());
				}
			}
		}

		public JComponent getComponent(Column col) {
			return components.get(col);
		}

		public void setName(Civilization.Name name) {
			this.name = name;
		}

		@Override
		public void actionPerformed(ActionEvent event) {
			String command = event.getActionCommand();
			switch (command) {
				case "Buy": {
					new TechnologyStoreDialog(AstTablePanel.this.client, AstTablePanel.this.controller, this.name);
				}
					break;
				case "View":
					( (MegaCivFrame) SwingUtilities.getWindowAncestor(AstTablePanel.this) )
							.addTab(WordUtils.capitalizeFully(this.name.toString()));
					break;
				case "Edit":
					new CivEditPanel(AstTablePanel.this.client, AstTablePanel.this.controller, this.name);
					break;
				case "Regress":
					if (JOptionPane.showConfirmDialog(null,
							"Confirm regression of " + WordUtils.capitalizeFully(name.toString()),
							"Confirm regression of " + WordUtils.capitalizeFully(name.toString()),
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.OK_OPTION) {
						AstTablePanel.this.client.sendMessage(
								new AdvanceAstMessage(new HashMap<Civilization.Name, Civilization.AstChange>() {
									private static final long serialVersionUID = -5493408662716651786L;

									{
										put(CivRow.this.name, Civilization.AstChange.REGRESS);
									}
								}));
					}
					break;
				default:
					AstTablePanel.this.client.log("ActionCommand " + command + " not implemented in "
							+ this.getClass().getSimpleName() + " yet!");
			}

		}

		private class PopupListener extends MouseAdapter {

			private final JPopupMenu menu;

			public PopupListener(JPopupMenu menu) {
				this.menu = menu;
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				this.checkForPopup(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				this.checkForPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				this.checkForPopup(e);
			}

			private void checkForPopup(MouseEvent event) {
				final JComponent source = (JComponent) event.getSource();
				if (event.isPopupTrigger()) {
					this.menu.show(source, event.getX(), event.getY());
				}
			}

		}
	}

	private class FillerPanel extends BubbaPanel {

		private static final long	serialVersionUID	= 1832956209036106492L;

		private int					width;
		private Image				scaledImage;

		public FillerPanel() {
			super(AstTablePanel.this.controller);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (width != this.getWidth()) {
				this.width = this.getWidth();
				double scale = ( width + 0.0 ) / FILLER_IMAGE.getWidth();
				this.scaledImage = FILLER_IMAGE.getScaledInstance(width, (int) ( FILLER_IMAGE.getHeight() * scale ),
						Image.SCALE_SMOOTH);
			}
			g.drawImage(scaledImage, 0, 0, this);
		}

	}
}
