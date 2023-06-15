package Model;

class NoMovesAvailableException extends MoveEvaluationException{
	public NoMovesAvailableException() {}
	public NoMovesAvailableException(String msg) {
		super(msg);
	}
}

