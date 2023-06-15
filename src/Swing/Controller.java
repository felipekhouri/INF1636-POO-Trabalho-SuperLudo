package Swing;

import java.awt.Color;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import Model.*;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

public class Controller implements Swing.Observer {
	private PrimFrame view;
	private Facade model;
	private BoardPanel boardPanel;
	private int dice;
	private Color player;
	private Set<Rectangle2D.Double> tiles;
	private Set<TileRepresentation> occupiedTiles;

	public Controller() {

		this.view = new PrimFrame();
		;
		view.addDiceListener(new DiceListener());

		boardPanel = view.getBoardPanel();
		boardPanel.addMouseListener(new BoardPanelMouseListener(this));

		view.getSaveButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int resultado = fileChooser.showSaveDialog(null);
				if (resultado == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					saveInfo(file);
				}
				
			}
		});

		this.model = Facade.getInstance();
		this.model.addObserver(this);
		this.occupiedTiles = new HashSet<TileRepresentation>();

		view.setTitle("Minha Primeira GUI");
		view.setVisible(true);
		view.setSize(1000, 800);

	}

	private void saveInfo(File file) {
		Model.Color currentPlayer = model.getCurrPlayerColor();

		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write("Current Player:" + currentPlayer);
			writer.newLine();
			for(Player player : model.getPlayers()){
				writer.newLine();
				writer.newLine();
				writer.write("" + player.getColor());

				for(Pawn pawn : player.getPawns()){
					writer.newLine();
					writer.write("" + pawn.getTile().getPosition().getNumber());
				}
				
			}

			System.out.println("Informações salvas com sucesso!");
		} catch (IOException e) {
			System.err.println("Erro ao salvar as informações: " + e.getMessage());
		}
	}

	private class DiceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// configurar dado

			try {
				dice = model.rollDice();
			}

			catch (Exception ex) {
				System.out.println("EXCEPTION -> " + ex.getMessage());
				System.exit(1);

			}

		}
	}

	private void updateOccupiedTiles() {
		occupiedTiles = new HashSet<TileRepresentation>();
		for (Tile t : model.getOccupiedTiles()) {
			occupiedTiles.add(makeTileRepresentation(t));
		}
		System.out.printf("There are %d occupied tiles\n", model.getOccupiedTiles().size());
	}

	private Color convertToAWT(Model.Color color) {
		switch (color) {
			case red:
				return Color.RED;
			case green:
				return Color.GREEN;
			case yellow:
				return Color.YELLOW;
			default:
				return Color.BLUE;
		}
	}

	private static Color nextPlayer() {
		Random random = new Random();
		Color[] colors = { Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN };
		int index = random.nextInt(colors.length);

		return colors[index];
	}

	private TileRepresentation makeTileRepresentation(Tile tile) {
		System.out.printf("peao na posicao %d%s", tile.getPosition().getNumber(),
				tile.getPosition().getIsInFinalTiles() ? ", reta final" : "");
		Color pawnColors[] = new Color[tile.getNumPawns()];
		TileType tileType;
		int x, y;
		double[] position;
		position = model.getXY(tile.getPosition());
		x = (int) position[0];
		y = (int) position[1];

		if (tile.getNumPawns() >= 2) {
			if (tile.getCurrPawnsAsArray()[0].getColor() == tile.getCurrPawnsAsArray()[1].getColor()) {
				tileType = TileType.twoSameColor;
			} else {
				tileType = TileType.twoDifferentColor;
			}
		} else if (tile.getNumPawns() == 1) {
			tileType = TileType.single;
		} else {
			tileType = TileType.empty;
		}

		for (int i = 0; i < tile.getCurrPawnsAsArray().length; i++) {
			pawnColors[i] = convertToAWT(tile.getCurrPawnsAsArray()[i].getColor());
		}
		return new TileRepresentation(pawnColors, tileType, x, y);
	}

	public Set<Rectangle2D.Double> getTiles() {
		return this.view.getBoardPanel().getTiles();
	}

	public int getDice() {
		return dice;
	}

	public void setDice(int dice) {
		this.dice = dice;
	}

	public Color getPlayer() {
		return player;
	}

	public void setPlayer(Color player) {
		this.player = player;
	}

	public Facade getModel() {
		return model;
	}

	public void setModel(Facade model) {
		this.model = model;
	}

	@Override
	public void notify(Observed observed) {
		System.out.println("I was notified");
		updateOccupiedTiles();
		if (!occupiedTiles.isEmpty()) {
			view.getBoardPanel().setTileRepresentations(occupiedTiles);
		}
		player = convertToAWT(model.getCurrPlayerColor());
		view.dicePanel.lancarDado(model.getNTiles());
		view.dicePanel.createPlayerRect(player);
		view.getBoardPanel().repaint();
	}

	public void play(double x, double y) {
		System.out.println("tentou jogar nas posicoes");
		model.play(x, y);
	}

	public Set<TileRepresentation> getOccupiedTiles() {
		return occupiedTiles;
	}

	// fazer fun;cào

}

/*
 * ### O que a View pede ao Controller
 * 
 * - Quando o usuário clica em lançar o dado, trigger o rollDice do Controller
 * - Quando o usuário clica em um peão, trigger o movePawn do Controller
 * 
 * ### O que o Controller passa para a View
 * 
 * - Dependendo da situação o controller habilita a view a:
 * - mexer um peão de posição
 * - trocar a cor do jogador
 */

// implementacao para pawn
// Rectangle2D.Double[] pawns = boardPanel.getPawns();
// for (int i = 0; i < pawns.length; i++) {
// if (pawns[i].contains(e.getX(), e.getY())) {
// //se o jogador tiver lancado o dado, pode jogar
// //checar a vez do jogador e a cor do pião
// if (onPlay) {
// //controller chama o movePawn do Model com dice.
//
// //model avisa para view que movimentou piao, e a view atualiza.
// System.out.println("Ellipse " + i + " tapped!");
// onPlay = false;
// }
// break;
// }
// }
