package Model;
import java.util.*;

class ExitTile extends Tile{
	protected Color color;
	
	public ExitTile(Color c) {
		super();
		color = c;
	}

	public ExitTile(Color c, int position, boolean isInFinalTiles) {
		super(position, isInFinalTiles);
		color = c;
	}
	
	public Color getColor() {
		return color;
	}

	@Override
	public boolean canAddPawn(Pawn p) {
		// se tiver dois pioes com cores distintas, desde que um deles seja da cor da tile, vira abrigo
		// se nao movimento impossivel
		if (numPawns == 1){
			if (currPawns.iterator().next().getColor() == color){
				if (color != p.getColor()){					
					return true;
				}
			}
			else if(color == p.getColor()){
				if(currPawns.iterator().next().getColor() != color) {
					return true;
				}
			}
		}
		
		else if (numPawns == 0){
			return true;
		}
	
		return false;
	}

	@Override
	public void addPawn(Pawn p) throws MoveImpossibleException, PawnCapturedException {
		
		if (canAddPawn(p)) {
			
			if (numPawns == 1 && (currPawns.iterator().next().getColor() != color) && p.getColor() == color) {
				currPawns.iterator().next().sendToInitial(); //peao que estava na casa eh capturada
				currPawns.add(p);
				numPawns = currPawns.size();
				throw new PawnCapturedException();
			}
			
			currPawns.add(p);
			numPawns = currPawns.size();
		}
		else {
			throw new MoveImpossibleException(/*"Cannot add pawn to exit tile."*/);
		}
	}
}