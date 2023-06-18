package Model;

public class PawnPosition {
	private int number;
	private boolean isInFinalTiles;
	
	public PawnPosition(int number, boolean inFinalTiles) {
		this.number = number;
		isInFinalTiles = inFinalTiles;
	}
	
	public int getNumber() {
		return number;
	}
	
	public boolean getIsInFinalTiles() {
		return isInFinalTiles;
	}

	public boolean equals(PawnPosition p) {
		return ( (this.number == p.number) && (this.isInFinalTiles == p.isInFinalTiles) );
	}

	public int getDistanceToFinal(Color color) {
		if(this.getIsInFinalTiles())
			return (Tile.getFinalTileForColor(color) - this.number);
		int diff = Tile.getEntryTileForColor(color) - this.number;
		if (diff < 0) {
			return 52 + diff + 6;
		}
		return diff + 6;
	}

	public boolean closestToFinal(PawnPosition p2, Color color) {
		int d1 = getDistanceToFinal(color);
		int d2 = p2.getDistanceToFinal(color);
		return (d1 < d2);
	}
}
