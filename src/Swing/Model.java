// package Swing;

// import java.awt.Color;
// import java.util.*;

// @SuppressWarnings("deprecation")
// public class Model {
// 	Tile tiles[];
// 	private List<Observer> observers;
	

// 	public Model() {
// 		Tile tileAux = new Tile();
// 		observers = new ArrayList<>();
// 		tiles = tileAux.tiles;
// 	}
// 	public boolean checkTile(double posX, double posY, int dice, Color player) {
// 		//funcao de converter de x,y para indice
// 		//percorrer o vetor de tiles e verificar se naquele tile tem piao da cor do jogador ou não
// 		//se não tiver return false;
// 		//se tiver deve checar o tile i + dice
// 		//1. o move é possivel -> se não for gera MoveImpossible e os tiles não atualizam
// 		//2. o move gera captura -> se capturar gera PawnCaptured, e deve receber qual pião foi capturado e atualizar a view
// 		//3. o move cria barreira -> se criar gera Barrier, atualizar a view
// 		//Simulando 
// 		Tile tileStart = tiles[0];
// 		Tile tileEnd = tiles[dice];
// 		System.out.println("tileStart ->" + tileStart.positionIndex);
// 		System.out.println("tileEnd ->" + tileEnd.positionIndex);
// 		notifyObservers(tileStart, tileEnd);
// 		return true;
		
// 	}
	

// 	public void addObserver(Observer observer) {
// 	    observers.add(observer);
// 	}

// 	public void removeObserver(Observer observer) {
// 	    observers.remove(observer);
// 	}

// 	public void notifyObservers(Tile... tile) {
// 		for (Observer observer : observers) {
// 			observer.update(null, tile);
// 		}
// 	}


// }
	
// //}
// //
// //class Pawn {
// //	private int position;
// //	private Color color;
// //	
// //	public Pawn(int pos, Color color) {
// //		this.position = pos;
// //		this.color = color;
// //	}
// //	
// //	public void move(Pawn pawn, int units) {
// //		pawn.position += units;
// //	}
// //	
// //}


// //agora preciso pegar as cores dos pioes e passar devolta para a controller
// //Color[] pawnColors = new Color[2];
// //if (tile.numPawns > 0) {
// //	for (int i = 0; i<tile.currPawns.length;i++) {
// //		pawnColors[i] = tile.currPawns[i].color;
// //	}
// //}
// //notificar o controller
// //como enviar o tile para criar um representable?