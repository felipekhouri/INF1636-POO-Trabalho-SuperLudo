package Swing;


import java.awt.Color;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class Controller implements Observer {
	private PrimFrame view;
	private Model model;
	private BoardPanel boardPanel;
	private boolean onPlay; 
	private int dice;
	private Color player;
	private Set<Rectangle2D.Double> tiles;

	
	public Controller(PrimFrame view, Model model) {
		this.view = view;
		view.addDiceListener(new DiceListener());
		
		boardPanel = view.getBoardPanel();
        boardPanel.addMouseListener(new BoardPanelMouseListener());
        
        this.model = model;
        this.model.addObserver(this);

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
		        
		        //configurar jogador
		        if (dice != 6){
		        	player = Controller.nextPlayer();
			        view.dicePanel.createPlayerRect(player);
		        }
			}
			
			catch(Exception ex) {
				System.out.println(ex.getMessage());
	            System.exit(1);

			}
	        
		}
	}
	
	//implementar o clique no tile ao inves do pawn
	private class BoardPanelMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
    		//todas as tiles na view
        	tiles = boardPanel.getTiles();
        	for (Rectangle2D.Double tile: tiles) {
        		if (tile.contains(e.getX(),e.getY())) {
        			if (onPlay) {
        				//se o tile tiver piao da cor do jogador então significa
        				if (model.checkTile(e.getX(),e.getY(), dice, player)) {;
        					onPlay = false;
        				}
        			}
        		}
        	}
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

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
		//devolveu tile, significa que é para desenhar piao na tela.
		if (arg  instanceof Tile[]) {
			//setting up tile representation
			Tile tile = (Tile) arg;
			TileType type = TileType.empty;
			int positionXY[]= new int[2];
			//posicao simulate
			positionXY[0] = 100;
			positionXY[1] = 200;
			Color[] pawnColors = new Color[tile.numPawns];

			
			if (tile.numPawns == 1) type = TileType.single;
			else if (tile.numPawns == 2) {
				for (int i = 0; i<tile.numPawns;i++) {
					pawnColors[i] = tile.currPawns[i].color;
				}
				
				if (pawnColors[0].equals(pawnColors[1])) {
					type = TileType.twoSameColor;
				}
				else {
					type = TileType.twoDifferentColor;
				}
			}
			TileRepresentation rep = new TileRepresentation(pawnColors,type,positionXY);
			//view.updateview();
			
			//pegar posição
			
			
		}
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

