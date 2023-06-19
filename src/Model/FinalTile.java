package Model;

import java.util.HashMap;
import java.util.Map;

class FinalTile extends Tile{
	
	protected Color color;
	private boolean isLastTile;
	
	public FinalTile(Color c, int num, boolean isLastTile) {
		color = c;
		position = new PawnPosition(num, true);
		this.isLastTile = isLastTile;
	}
	
	//se o next for null é o ultimo
	public boolean isLastTile() {
		return isLastTile;
	}

	@Override
	public boolean canAddPawn(Pawn p) {
		return true; //podemos adicionar até quatro peões da mesma cor na casa final 
	}
	
	@Override
	public boolean isBarrier() {
		return false; //nunca será barreira
	}

	@Override
	public void addPawn(Pawn p) {
		this.currPawns.add(p);
		numPawns++;
	}
	
	static Map<Color, FinalTile> finalTiles = new HashMap<Color, FinalTile>();
	
	//neste bloco de inicialização estático, vamos definir as casas da reta final de cada cor
	static {
		int increment;
		FinalTile firstTile, currTile;
		boolean isLastFinalTile;
		for(Color c: Color.values()) { //vamos criar uma "reta final" para cada cor
			isLastFinalTile = false;
			switch(c) {
				case red:
				increment = 0;
				break;
				case green:
				increment = 6;
				break;
				case yellow:
				increment = 12;
				break;
				default:
				increment = 18;
			}
			firstTile = new FinalTile(c, increment, false);
			currTile = new FinalTile(c, increment + 1, false);
			firstTile.setNextTile(currTile);
			for(int i = 0; i < 4; i++) {
				if (i == 3) isLastFinalTile = true;
				currTile.setNextTile(new FinalTile(c, increment + i + 2, isLastFinalTile));
				currTile = (FinalTile)currTile.getNextTile();
			}
			finalTiles.put(c, firstTile); //definindo a primeira casa da reta final para a cor
		}
	}
}
