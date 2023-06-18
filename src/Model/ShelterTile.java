package Model;

class ShelterTile extends Tile{

	public ShelterTile() {
	}

	public ShelterTile(int n) {
		super(n, false);
	}

	//Abrigo: em um abrigo, dois piões (no máximo) de cores distintas poderão estar abrigados
	@Override
	public boolean canAddPawn(Pawn p) {
		if (numPawns < 2){
			return true;
		}
		return false;
	}
	
	
	//Adiciona na lista de pioes
	public void addPawn(Pawn p) throws MoveImpossibleException{
		if (canAddPawn(p)){					
			currPawns.add(p);
			numPawns ++;
		}
		else {
			throw new MoveImpossibleException(/*"Cannot add pawn to shelter"*/);
		}
			
	}
		
	
}
