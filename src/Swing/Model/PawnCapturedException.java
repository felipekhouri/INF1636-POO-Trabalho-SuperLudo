package Model;

class PawnCapturedException extends PawnMoveException {
	public Pawn capturedPawn;
	
	public PawnCapturedException() {}
	
	public PawnCapturedException(Pawn capturedPawn) {
		super(String.format("Peão do jogador %s capturado",capturedPawn.getColor()));
		this.capturedPawn = capturedPawn;
	}

	public Pawn getCapturedPawn() {
		return capturedPawn;
	}
}
