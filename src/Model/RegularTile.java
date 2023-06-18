package Model;

class RegularTile extends Tile {
	//repensar 
	
	public RegularTile(){}
	
	public RegularTile(int position, boolean isInFinalTiles){
		super(position, isInFinalTiles);
	}

	@Override
	public boolean canAddPawn(Pawn p) {
		if (numPawns == 0 || numPawns == 1) {
			return true;
		}
		return false;
	}

	@Override
	public void addPawn(Pawn p) throws MoveImpossibleException, PawnCapturedException {
		if (isBarrier == true) {
				throw new MoveImpossibleException(/*"Barrier, cannot move"*/);
			}
		else if (numPawns == 0) {
			currPawns.add(p);
			numPawns ++;
		}
		else if (numPawns == 1) {
			//checar se vai ser comido 
			if (currPawns.iterator().next().getColor() != p.getColor()){
//				removePawn(currPawns.iterator().next());
				currPawns.iterator().next().sendToInitial();
				currPawns.add(p);
				numPawns ++;
				throw new PawnCapturedException(/*"Pawn captured"*/);
				
			}
			else {
				//se forem da mesma cor forma barreira
				currPawns.add(p);
				numPawns ++;
				isBarrier = true;
			}
			
		}
		

	}
	
}
