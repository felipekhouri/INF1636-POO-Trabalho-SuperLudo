package Model;

class MoveImpossibleException extends PawnMoveException{
	public MoveImpossibleException() {
		super("Impossível mover o peão desejado");
	}
	public MoveImpossibleException(String msg) {
		super(msg);
	}
}
