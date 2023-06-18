package Model;

import java.util.*;
import java.util.Set;

abstract public class Tile {
	protected Set<Pawn> currPawns = new HashSet<Pawn>();
	protected Tile nextTile = null;
	protected Tile previousTile = null;
	protected int numPawns = 0;
	protected boolean isBarrier = false;
	protected PawnPosition position;
	// public double x,y;
	
	public Tile() {}

	public Tile(int position, boolean isInFinalTiles) {
		this.position = new PawnPosition(position, isInFinalTiles);
	}

	// getters

	public Tile getNextTile() {
		return this.nextTile;
	}

	public Tile getPreviousTile() {
		return this.previousTile;
	}
	
	// public void setXY(double[] coordinates) {
	// 	if(coordinates.length == 2) {
	// 		x = coordinates[0];
	// 		y = coordinates[1];
	// 	}
	// }
	
	// retornar tile em array
	public Pawn[] getCurrPawnsAsArray() {
		Pawn array[] = new Pawn[numPawns];
		if(numPawns == 0) return array;
		int i = 0;
		for (Pawn p : currPawns) {
			array[i] = p;
			i++;
		}
		return array;

	}

	public boolean contains(Pawn p) {
		return currPawns.contains(p);
	}
	
	// functional
	abstract public boolean canAddPawn(Pawn p);

	abstract public void addPawn(Pawn p) throws MoveImpossibleException, PawnCapturedException;

	// default implementations

	public void removePawn(Pawn pawn) {
		currPawns.remove(pawn);
		numPawns = currPawns.size();
	}

	public boolean isBarrier() {
		// é barreira se tiver dois pioes da mesma cor
		Color color[] = new Color[numPawns];
		
		if(numPawns == 0)
			return false;
		
		int i = 0;
		for (Pawn p : currPawns) {
			color[i] = p.getColor();
			i++;
		}

		return getDuplicates(color);
	}

	public boolean getDuplicates(Object array[]) {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i].equals(array[j])) {
					return true;
				}
			}
		}
		return false;
	}

	public int getNumPawns() {
		return numPawns;
	}

	public PawnPosition getPosition() {
		return position;
	}

	protected void setNextTile(Tile t) {
		nextTile = t;
	}

	static protected int getFinalTileForColor(Color c) {
		switch(c) {
			case red:
			return 5;
			case green:
			return 11;
			case yellow:
			return 17;
			default:
			return 23;
		}
	}

	static protected int getEntryTileForColor(Color c) {
		switch(c) {
			case red:
			return 50;
			case green:
			return 11;
			case yellow:
			return 24;
			default:
			return 37;
		}
	}
}