package Model;

class PawnInFinalTileException extends PawnMoveException{
	public PawnInFinalTileException() {}
	public PawnInFinalTileException(Color playerColor) {
		super(String.format("Peão do jogador %s atingiu a casa final", playerColor));
	}
}

