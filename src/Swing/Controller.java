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

import javax.imageio.ImageIO;

public class Controller implements Observer {
	private PrimFrame view;
	private Facade model;
	private BoardPanel boardPanel;
	private boolean onPlay; 
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

	}
	
	private class DiceListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			//configurar dado 
			
			try {
		        dice = Controller.rollDice();
		        view.dicePanel.lancarDado(dice);	
		        onPlay = true;
				System.out.println("Dice Rolled!");
		        
		        //configurar jogador
		        if (dice != 6){
		        	player = Controller.nextPlayer();
			        view.dicePanel.createPlayerRect(player);
		        }
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

	private static int rollDice() {
		Random random = new Random();
        int randomNumber = random.nextInt(6) + 1;
		return randomNumber;
	}
	
	private static Color nextPlayer() {
		Random random = new Random();
		Color[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
	    int index = random.nextInt(colors.length);
	      
	    return colors[index];
	  }

	private TileRepresentation makeTileRepresentation(Tile tile) {
		Color pawnColors[] = new Color[tile.getNumPawns()];
		TileType tileType;
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
		return new TileRepresentation(pawnColors, tileType, null)
	}

	@Override
	public void update(Observed arg) {
		// TODO Auto-generated method stub
		//devolveu tile, significa que é para desenhar piao na tela.
		System.out.println("arg ->" + arg.getClass());
		if (arg instanceof Tile[]) {
			Tile[] tiles = (Tile[]) arg;
			System.out.println("qualquer coisa ai");
			//setting up tile representation
			Tile tile = tiles[0];
			TileType type = TileType.empty;
			int positionXY[]= new int[2];
			//posicao simulate
			positionXY[0] = 300;
			positionXY[1] = 300;
			Color[] pawnColors = new Color[tile.getNumPawns()];

			
			if (tile.getNumPawns() == 1) type = TileType.single;
			else if (tile.getNumPawns() == 2) {
				Pawn pawnArray[] = tile.getCurrPawnsAsArray();
				for (int i = 0; i<tile.getNumPawns();i++) {
					pawnColors[i] = convertToPawnArray[i].getColor();
				}
				
				if (pawnColors[0].equals(pawnColors[1])) {
					type = TileType.twoSameColor;
				}
				else {
					type = TileType.twoDifferentColor;
				}
			}
			TileRepresentation rep = new TileRepresentation(pawnColors,type,positionXY);
			boardPanel.updateView(rep);
			//view.updateview();
			
			//pegar posição
			
			
		}
	}

	public Set<Rectangle2D.Double> getTiles() {
    	return this.view.getBoardPanel().getTiles();
	}

	public boolean isOnPlay() {
		return onPlay;
	}

	public void setOnPlay(boolean onPlay) {
		this.onPlay = onPlay;
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

		

		
		//fazer fun;cào
			
		

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

