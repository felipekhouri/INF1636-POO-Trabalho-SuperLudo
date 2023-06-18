package Model;

public class Pawn {
	private Color color;
	private boolean isInInitialTile = true;
	private boolean isInLastTile = false;
	private Tile currTile;
	
	public Pawn(Color color, Tile tile) {
		this.color = color;
		currTile = tile;
	}
	
	protected void move(int nTiles) 
	throws PawnInFinalTileException, PawnCapturedException, MoveImpossibleException 
	{	
		Tile auxTile = currTile;
		int tilesMoved = 0;
		PawnCapturedException pawnCaptured = null;
		
		if (!canMove(nTiles)) {
			throw new MoveImpossibleException();
		}
		
		while (tilesMoved < nTiles) {
			if (currTile.getNextTile() != null) {
				Tile tileAfterNext = auxTile.getNextTile().getNextTile();
				if (tileAfterNext != null && tileAfterNext instanceof ExitTile && ((ExitTile)tileAfterNext).getColor() == color) {
					auxTile = FinalTile.finalTiles.get(color);
					tilesMoved++;
					continue;
				}
			}
			auxTile = auxTile.getNextTile();
			tilesMoved++;
		}
		
		try {
			auxTile.addPawn(this); //chegamos à casa desejada, agora vamos ver se o peão pode parar lá
		} 
		catch (MoveImpossibleException e) {
			throw e; //se o peão não puder parar lá, relançamos esse erro
		}
		catch (PawnCapturedException e) {
			pawnCaptured = e; //se o peão puder parar lá mas isso significar comer outro peão, armazenamos a exceção 
		}
		
		//atualizando o valor da variável currTile
		currTile.removePawn(this);
		currTile = auxTile;
		
		//se a currTile está na "reta final", pode ser que o jogador esteja na casa final
		if(currTile instanceof FinalTile) {
			FinalTile finalTile = (FinalTile)currTile;
			if (finalTile.isLastTile()) {
				isInLastTile = true;
				throw new PawnInFinalTileException(); //se o jogador está na casa final, lançamos esta exceção avisando
			}
		}
		if(pawnCaptured != null) {
			throw pawnCaptured; //se, mais cedo, houve peão capturado, avisamos
		}
		//note que nunca ocorrerá de um jogador ter chegado à casa final e comer outra peça.
		//na reta final, só há peças da mesma cor
	}
	
	public boolean canMove(int nTiles) 
	{
		if (isInInitialTile) {
			return false;
		}
		int tilesMoved = 0;
		Tile auxTile = currTile;
		while (tilesMoved < nTiles) {
			auxTile = auxTile.getNextTile();
			if (auxTile == null || auxTile.isBarrier()) { //por curto-circuito, se for null, n terá problema
				return false;
			}
			tilesMoved++;
		}
		
		return auxTile.canAddPawn(this);
	}

	public boolean getIsInInitialTile() 
	{
		return isInInitialTile;
	}

	public void setIsInInitialTile(boolean bool) 
	{
		isInInitialTile = bool;
	}

	public boolean getIsInLastTile() 
	{
		return isInLastTile;
	}

	public void sendToInitial() {
		isInInitialTile = true;
		currTile.removePawn(this);
		this.currTile = Facade.getInstance().getExitTiles().get(color);
	}
	
	public Color getColor() 
	{
		return color;
	}

	public Tile getTile(){
		return currTile;
	}
	
	public void setTile(Tile tile){
		this.currTile = tile;
	}
	
}