package Model;

import java.util.HashMap;
import java.util.Map;

class FinalTile extends Tile{
	
	protected Color color;
	
	public FinalTile(Color c, int num) {
		color = c;
		position = new PawnPosition(num, true);
	}
	
	//se o next for null é o ultimo
	public boolean isLastTile() {
		if (getNextTile() == null) {
			//the next is the last
			return true;
		}
		return false;
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
		for(Color c: Color.values()) { //vamos criar uma "reta final" para cada cor
			switch(c) {
				case red:
				increment = 0;
				break;
				case green:
				increment = 5;
				break;
				case yellow:
				increment = 10;
				break;
				default:
				increment = 15;
			}
			firstTile = new FinalTile(c, increment);
			currTile = new FinalTile(c, increment + 1);
			firstTile.setNextTile(currTile);
			for(int i = 0; i < 4; i++) {
				currTile.setNextTile(new FinalTile(c, increment + i + 1));
				currTile = (FinalTile)currTile.getNextTile();
			}
			finalTiles.put(c, firstTile); //definindo a primeira casa da reta final para a cor
		}
	}
}
