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
}
