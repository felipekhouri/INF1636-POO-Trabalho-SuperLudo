package Model;
import java.util.Set;

class BarrierFoundException extends MoveEvaluationException {
	private Set<Pawn> pawns;
	private int numPawns;
	
	
	public BarrierFoundException() {}
	public BarrierFoundException(Set<Pawn> pawns, int tilesMoved) {
		super(String.format("Você tirou %d e tem uma barreira", tilesMoved));
		this.pawns = pawns;
		numPawns = pawns.size();
	}
	
	public Set<Pawn> getPawnsInBarrier() {
		return pawns;
	}
	
	public int getNumPawns() {
		return numPawns;
	}
}
