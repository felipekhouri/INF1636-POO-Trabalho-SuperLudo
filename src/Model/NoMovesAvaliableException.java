package Model;

class NoMovesAvaliableException extends MoveEvaluationException{
	public NoMovesAvaliableException() {}
	public NoMovesAvaliableException(String msg) {
		super(msg);
	}
}

