package Model;

import java.util.HashMap;
import java.util.Map;

class FinalTile extends Tile{
	
	protected Color color;
	
	public FinalTile(Color c) {
		color = c;
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
		FinalTile firstTile, currTile;
		for(Color c: Color.values()) { //vamos criar uma "reta final" para cada cor
			firstTile = new FinalTile(c);
			currTile = new FinalTile(c);
			firstTile.setNextTile(currTile);
			for(int i = 0; i < 4; i++) {
				currTile.setNextTile(new FinalTile(c));
				currTile = (FinalTile)currTile.getNextTile();
			}
			finalTiles.put(c, firstTile); //definindo a primeira casa da reta final para a cor
		}
	}
}
