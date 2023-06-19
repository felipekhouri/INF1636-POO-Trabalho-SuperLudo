package Model;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;


public class Player implements Comparable<Player> {
	Pawn[] pawns;
	private Color color;
	private ExitTile exitTile;
	private boolean isFirstPlay = true;
	private int nStraight6 = 0;
	
	public Player(Color color, ExitTile initialTile) {
		this.color = color;
		pawns = new Pawn[] {new Pawn(color, initialTile), //criando peões do jogador
							new Pawn(color, initialTile),
							new Pawn(color, initialTile),
							new Pawn(color, initialTile)
						   };
		exitTile = initialTile;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean getIsFirstPlay() {
		return isFirstPlay;
	}
	
	void setNStraight6(int n) {
		nStraight6 = n;
	}
	
	int getNStraight6() {
		return nStraight6;
	}

	public Pawn[] getPawns(){
		return pawns;
	}

	public int getDistanceToEnd() {
		int distance = 0;
		for(Pawn p : pawns) {
			distance += p.getTile().getPosition().getDistanceToFinal(color);
		}
		return distance;
	}
	
	public Set<Pawn> evaluateMoves(int nTiles)
	throws NoMovesAvailableException, BarrierFoundException
	{
		boolean pawnCanMove, movesAvailable = false;
		Set<Pawn> avaliablePawns = new HashSet<Pawn>();
		Set<Pawn> pawnsInBarrier = new HashSet<Pawn>();
		
		for(Pawn p: pawns) { //vamos analisar cada peao para verificar se ha movimentos disponiveis e se esta em barreira
			pawnCanMove = p.canMove(nTiles);
			movesAvailable = movesAvailable || pawnCanMove;
			if (pawnCanMove) {
				avaliablePawns.add(p);
				if ( p.getTile().isBarrier() ) {
					if (!pawnsInBarrier.contains(p)) {
						for (Pawn pawnInTile: p.getTile().getCurrPawnsAsArray())
							pawnsInBarrier.add(pawnInTile);
					}
				}
			}
		}
		
		if (!movesAvailable)
			throw new NoMovesAvailableException();
		if (pawnsInBarrier.size() > 0 && nTiles == 6)
			throw new BarrierFoundException(pawnsInBarrier, nTiles);
		return avaliablePawns;
	}
	
	public boolean startPawn()
	throws PawnCapturedException
	{
		System.out.println("startPawn chamada");
		Pawn pawnInInitialTile = null;
		for (Pawn p: pawns) { //analisamos cada peão para encontrar um que esteja na casa inicial
			if (p.getIsInInitialTile()) {
				pawnInInitialTile = p; //no primeiro peão que encontramos, paramos de procurar.
				break;
			}
		}
		
		if (pawnInInitialTile == null) { //nenhum peão está na casa inicial
			return false;
		}
		
		if (exitTile.canAddPawn(pawnInInitialTile)) { //aqui, verificamos se é possível posicionar o peão na casa de saída
			try {
				exitTile.addPawn(pawnInInitialTile); //addPawn, neste caso, não irá jogar exceções de peão capturado, apenas de movimento impossível. Aqui, posicionamos o peão na casa de saída 
				pawnInInitialTile.setIsInInitialTile(false); //aqui, removemos o peão da casa inicial
			}
			catch (MoveImpossibleException e) {
				return false; //caso addPawn jogue alguma exceção, o movimento não é possível
			}
			catch (PawnCapturedException e) {
				System.out.println("Capturado!");
				pawnInInitialTile.setIsInInitialTile(false);
				throw e;
			}
			System.out.println("Peao adicionado na casa " + pawnInInitialTile.getTile().getPosition().getNumber());
			return true; //o movimento foi possível.
		}
		return false;
	}

	public boolean hasWon() {
		boolean hasWon = true;
		for(Pawn p: pawns)
			hasWon = hasWon && p.getIsInLastTile();
		return hasWon;
	}
	
	public void updateFirstPlay() {
		if (isFirstPlay)
			isFirstPlay = false;
	}
	
	public boolean rollSixOnDice() {
		nStraight6 += 1;
		if (nStraight6 >= 3) return true;
		return false;
	}
	
	public void endPlay() {
		nStraight6 = 0;
	}

	public int nPawnsInInitialTile() {
		int i = 0;
		for(Pawn p : pawns) {
			if (p.getIsInInitialTile()) i++;
		}
		return i;
	}

	//conforming to Comparable
	@Override
	public int compareTo(Player p) {
		int d1, d2;
		d1 = this.getDistanceToEnd(); d2 = p.getDistanceToEnd();
		if (d1 == d2) return 0;
		if (d1 > d2) return 1;
		return -1;
	}
}
