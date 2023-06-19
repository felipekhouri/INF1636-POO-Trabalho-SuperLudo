package Swing;

import java.awt.Color;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.*;
import Model.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.*;

import javax.imageio.ImageIO;

public class Controller implements Swing.Observer {
	private PrimFrame view;
	private Facade model;
	private BoardPanel boardPanel;
	private int dice;
	private Color player;
	private Set<Rectangle2D.Double> tiles;
	private Set<TileRepresentation> occupiedTiles;

	
	public Controller() {

		this.view = new PrimFrame(); ;
		view.addDiceListener(new DiceListener());
		
		boardPanel = view.getBoardPanel();
        boardPanel.addMouseListener(new BoardPanelMouseListener(this));
        
        this.model = Facade.getInstance();
        this.model.addObserver(this);
		this.occupiedTiles = new HashSet<TileRepresentation>();

		view.setTitle("Minha Primeira GUI"); 
		view.setVisible(true);
		view.setSize(1000, 800);

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
		view.getLoadButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int resultado = fileChooser.showOpenDialog(null);
				if (resultado == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					loadInfo(file);
				}
			}
		});
		view.getNewGameButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
	}

	private void saveInfo(File file) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			Model.Color currentPlayer = model.getCurrPlayerColor();
			writer.write("currPlayer: " + currentPlayer);
			writer.newLine();
			writer.write("lastDiceRoll: " + model.getNTiles());
			System.out.println("nTiles: " + model.getNTiles());
			writer.newLine();
			if (model.getLastPlayedPawn() != null) {
				writer.write("lastPlayedPawn: " + model.getLastPlayedPawn().getColor() + "," + model.getLastPlayedPawn().getTile().getPosition().getNumber());
				writer.newLine();
			}
			writer.write("canPlayAgain: " + model.getCanPlayAgain());
			writer.newLine();
			writer.write("hasRolledDice: " + model.getHasRolledDice());
			writer.newLine();
			writer.write("DontRollDice:" + model.getDontRollDice());
			writer.newLine();
			writer.write("Pawn Positions:");
			writer.newLine();
			for (Player player : model.getPlayers()) {
				writer.write(player.getIsFirstPlay() + "|");
				for (Pawn pawn : player.getPawns()) {
					PawnPosition pPosition = model.getPawnPosition(pawn);
					writer.write(pPosition.getNumber() + "/");
					writer.write(pPosition.getIsInFinalTiles() + ",");
					if (pPosition.getNumber() == -1){
						System.out.println("MAIS UM -1");

					}
					
				}
				writer.newLine();
			}
			System.out.println("Informações salvas com sucesso!");
		} catch (IOException e) {
			System.err.println("Erro ao salvar as informações: " + e.getMessage());
		}
	}
	

	private void loadInfo(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String currentPlayer = "null";
			int lastDiceRoll = 1;
			String lastPawnColor = "null";
			int lastPawnPosition = 0;
			boolean canPlayAgain = false;
			boolean hasRolledDice = true;
			boolean dontRollDice = false;
			boolean lastPawn = model.getLastPlayedPawn() == null ? false : true;
			List<List<Integer>> pawnPositions = new ArrayList<>();
			List<List<Boolean>> pawnFinalTiles = new ArrayList<>();
			List<Boolean> isFirstPlayList = new ArrayList<>();

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("currPlayer:")) {
					currentPlayer = line.substring(line.indexOf(":") + 1).trim();
					// Atribui o jogador da vez
					System.out.println("Current Player: " + currentPlayer);
				} else if (line.startsWith("lastDiceRoll:")) {
					lastDiceRoll = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
					// Atribui o último resultado do dado
					System.out.println("Last Dice Roll: " + lastDiceRoll);
				} else if (line.startsWith("lastPlayedPawn:")) {
					String[] pawnInfo = line.substring(line.indexOf(":") + 1).trim().split(",");
					lastPawnColor = pawnInfo[0].trim();
					System.out.println("pawnInfo[1].trim()"+ pawnInfo[1].trim());
					lastPawnPosition = Integer.parseInt(pawnInfo[1].trim());
					// Atribui o último peão jogado
					System.out.println("Last Played Pawn: Color=" + lastPawnColor + ", Position=" + lastPawnPosition);
				} else if (line.startsWith("canPlayAgain:")) {
					canPlayAgain = Boolean.parseBoolean(line.substring(line.indexOf(":") + 1).trim());
					// Atribui a informação se pode jogar novamente
					System.out.println("Can Play Again: " + canPlayAgain);
				} else if (line.startsWith("hasRolledDice:")) {
					hasRolledDice = Boolean.parseBoolean(line.substring(line.indexOf(":") + 1).trim());
					// Atribui a informação se já lançou o dado
					System.out.println("Has Rolled Dice: " + hasRolledDice);
				}else if (line.startsWith("DontRollDice:")){
					dontRollDice = Boolean.parseBoolean(line.substring(line.indexOf(":") + 1).trim());
				} else if (line.equals("Pawn Positions:")) {
					// Leitura das posições dos peões
					while ((line = reader.readLine()) != null && !line.isEmpty()) {
						String[] values = line.split("\\|");
						System.out.println("\n\n\nVALUES: " + values[0]);
						String[] positions = values[1].split(",");
						System.out.println("POSITIONS: " + positions);
						List<Integer> pawnPositionList = new ArrayList<>();
						List<Boolean> pawnIsInFinalTileList = new ArrayList<>();
						isFirstPlayList.add(Boolean.parseBoolean(values[0]));
						for (String position : positions) {
							String[] posInfo = position.split("/");
							int posNumber = Integer.parseInt(posInfo[0].trim());
							boolean isInFinalTiles = Boolean.parseBoolean(posInfo[1].trim());
							pawnPositionList.add(posNumber);
							pawnIsInFinalTileList.add(isInFinalTiles);
							// Atribui a posição e a informação se está na reta final para cada peão
							System.out.println("Pawn Position: Number=" + posNumber + ", IsInFinalTiles=" + isInFinalTiles);
						}
						pawnPositions.add(pawnPositionList);
						pawnFinalTiles.add(pawnIsInFinalTileList);
					}
					// Atribui as posições dos peões
					System.out.println("Pawn Positions: " + pawnPositions);
					System.out.println("\n\n\nPawn FINAL TILES: " + pawnFinalTiles);
				}
			}
			model.setLoadedGame(pawnPositions, currentPlayer, lastDiceRoll, lastPawnPosition, lastPawnColor, canPlayAgain, hasRolledDice, lastPawn, pawnFinalTiles, dontRollDice, isFirstPlayList);
			reader.close();
			System.out.println("Informações carregadas com sucesso!");
		} catch (IOException e) {
			System.err.println("Erro ao carregar as informações: " + e.getMessage());
		}
	}
	
	public void reset() {
		File blankGameFile = new File("Swing/blankGame.txt");
		loadInfo(blankGameFile);
	}

	private class DiceListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//configurar dado 
			
			try {
		        dice = model.rollDice();	
			}
			
			catch(Exception ex) {
				System.out.println("EXCEPTION -> " + ex.getMessage());
	            System.exit(1);

			}
	        
		}
	}
	
	private void updateOccupiedTiles() {
		occupiedTiles = new HashSet<TileRepresentation>();
		for(Tile t: model.getOccupiedTiles()) {
			occupiedTiles.add(makeTileRepresentation(t));
		}
		for(Player p : model.getPlayers()) {
			makeInitialTileRepresentations(p.getColor(), p.nPawnsInInitialTile());
		}
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

	private void makeInitialTileRepresentations(Model.Color color, int nPawns) {
		double x, y;
		for(int i = 0; i < nPawns; i++) {
			x = model.tileSize;
			y = model.tileSize;
			if(i%2 == 1) x += 3 * model.tileSize;
			if(i > 1) y += 3 * model.tileSize;
			switch (color) {
				case red:
				break;
				case blue:
				y += 9 * model.tileSize;
				break;
				case yellow:
				y += 9 * model.tileSize;
				default: //case green
				x += 9 * model.tileSize;
				break;
				
			}
			occupiedTiles.add(new TileRepresentation(
				new Color[] {convertToAWT(color)},
				TileType.single,
				(int) x,
				(int) y
			));
		}
	}

	private TileRepresentation makeTileRepresentation(Tile tile) {
		Color pawnColors[] = new Color[tile.getNumPawns()];
		TileType tileType;
		int x, y;
		double[] position;
		position = model.getXY(tile.getPosition());
		x = (int)position[0];
		y = (int)position[1];
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
			pawnColors[i] = convertToAWT( tile.getCurrPawnsAsArray()[i].getColor() );
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

	public double getTileSize() {
		return model.tileSize;
	}

	@Override
	public void notify(Observed observed) {
		updateOccupiedTiles();
		if(!occupiedTiles.isEmpty()) {
			view.getBoardPanel().setTileRepresentations(occupiedTiles);
		}
		player = convertToAWT(model.getCurrPlayerColor());
		if(model.getNTiles() < 1 || model.getNTiles() > 6) {
			view.dicePanel.lancarDado(1);
		} else {
			view.dicePanel.lancarDado(model.getNTiles());
		}
		view.dicePanel.createPlayerRect(player);
		view.getBoardPanel().repaint();
		if(model.getEndGame()) {
			showFinalMessage(model.getSortedPlayers());
		}
	}

	public void play(double x, double y) {
		model.play(x, y);
	}	

	public Set<TileRepresentation> getOccupiedTiles() {
		return occupiedTiles;
	}

	public void showFinalMessage(Model.Player[] players){

        String p1 = players[0].getColor().toString();
        String p2 = players[1].getColor().toString();
        String p3 = players[2].getColor().toString();
        String p4 = players[3].getColor().toString();

        JOptionPane.showMessageDialog(null, "1o lugar: "+p1 + "\n2o lugar: " + p2 + "\n3o lugar: " + p3 + "\n4o lugar: " + p4);
        int choice = JOptionPane.showConfirmDialog(null, "Deseja começar um novo jogo?", "", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            reset();
        } 
        else {
            System.exit(1);
        }
    }

			
		

	}

/*
 * ### O que a View pede ao Controller

- Quando o usuário clica em lançar o dado, trigger o rollDice do Controller
- Quando o usuário clica em um peão, trigger o movePawn do Controller

### O que o Controller passa para a View

- Dependendo da situação o controller habilita a view a:
    - mexer um peão de posição
    - trocar a cor do jogador
 */


//implementacao para pawn
//Rectangle2D.Double[] pawns = boardPanel.getPawns();
//for (int i = 0; i < pawns.length; i++) {
//  if (pawns[i].contains(e.getX(), e.getY())) {
//  	//se o jogador tiver lancado o dado, pode jogar
//  	//checar a vez do jogador e a cor do pião
//  	if (onPlay) {
//  		//controller chama o movePawn do Model com dice.
//  		
//  		//model avisa para view que movimentou piao, e a view atualiza.
//  		System.out.println("Ellipse " + i + " tapped!");
//          onPlay = false;
//  	}
//      break;
//  }
//}

