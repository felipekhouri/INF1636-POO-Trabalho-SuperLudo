package Model;

import java.util.*;
import java.util.Set;

abstract class Tile {
	protected Set<Pawn> currPawns = new HashSet<Pawn>();
	protected Tile nextTile = null;
	protected Tile previousTile = null;
	protected int numPawns = 0;
	protected boolean isBarrier = false;
	
	public double x,y;
	
	public Tile() {

	}

	// getters

	public Tile getNextTile() {
		return this.nextTile;
	}

	public Tile getPreviousTile() {
		return this.previousTile;
	}
	
	public void setXY(double[] coordinates) {
		if(coordinates.length == 2) {
			x = coordinates[0];
			y = coordinates[1];
		}
	}
	
	// retornar tile em array
	public Pawn[] getCurrPawnsAsArray() {
		Pawn array[] = new Pawn[numPawns];
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
		numPawns--;
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

	protected void setNextTile(Tile t) {
		nextTile = t;
	}
}