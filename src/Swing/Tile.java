package Swing;

import java.awt.Color;
//Model tile mock

public class Tile {
	public static Tile tiles[];
	public Pawns currPawns[];
	int numPawns;
	boolean isBarrier;
	int positionIndex;
	
	public Tile(Pawns currPawns[], int numPawns, boolean isBarrier, int positionIndex) {
		this.currPawns = currPawns;
		this.numPawns = numPawns;
		this.isBarrier = isBarrier;
		this.positionIndex = positionIndex;
		
	}
	
	public Tile() {
		tiles = new Tile[52];
		getTiles();
	}
	
	
	private void getTiles() {
		for (int i = 0; i<52;i++) {
			Pawns aux[] = new Pawns[2];

			aux[0] = new Pawns(Color.RED);
						
			tiles[i] = new Tile(aux,1,false,i);
		}
	}
}
