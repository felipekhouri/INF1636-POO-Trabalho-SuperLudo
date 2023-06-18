package Model;
import java.util.*;
import Swing.Observed;

public class Facade implements Observed {
	
	private Facade() {
		int i = 0;
		players = new Player[Color.values().length];
		makeNewBoard();
		for(Color c: Color.values()) {
			players[i] = new Player(c, exitTiles.get(c));
			i++;
		}
		currPlayer = players[0];
		observers = new ArrayList<>();

	}
	
	//Singleton
	private static Facade instance = null;
	public static Facade getInstance() {
		if (instance == null)
			instance = new Facade();
		return instance;
	}
	//não queremos usar o singleton em testes
	protected static Facade getTestInstance() {
		return new Facade();
	}
	//implementacao de observable
	private List<Swing.Observer> observers;
	
	//gerenciamento do jogador atual
	private Player currPlayer;
	private Player[] players;
	//casas de saída
	private Map<Color, ExitTile> exitTiles;
	//número tirado no dado
	protected int lastDiceRoll;
	protected int nTiles;
	//último peão que o jogador moveu
	private Pawn lastPlayedPawn;
	//classe random para a rolagem do dado
	private Random rand = new Random();
	private Scanner scanner = new Scanner(System.in);
	//anchor é uma referência para a casa de saída vermelha. No sistema de coordenadas, é a "casa número 0" 
	private Tile anchor;
	//peoes que podem ser movidos
	private Set<Pawn> availablePawns = new HashSet<Pawn>();
	public double tileSize = 51.3;
	//indica se o jogador atual precisa jogar novamente antes de selecionar um peao
	private boolean canPlayAgain = false;
	//indica se o jogador ja rolou o dado antes de jogar
	private boolean hasRolledDice = false;
	//indica se houve captura na ultima jogada
	private boolean dontRollDiceAfterSecondPlay = false;
	//todas as tiles em que há peoes
	private Set<Tile> occupiedTiles = new HashSet<Tile>();
	
	//metodos auxiliares
	/*
	 * makeNewBoard cria todas as tiles de um tabuleiro, instancia a variável exitTiles e
	 * instancia a variável anchor
	 */

	private void makeNewBoard() {
		Tile currTile;
		exitTiles = new HashMap<Color, ExitTile>();
		Color color = Color.red;
		currTile = new ExitTile(color, 0, false);
		anchor = currTile;
		exitTiles.put(color, (ExitTile)currTile);
		for(int position = 1; position < 52; position++) {
			if (position % 13 == 0) {
				switch(position) {
					case 13:
					color = Color.green;
					break;
					case 26:
					color = Color.yellow;
					break;
					default:
					color = Color.blue;
					break;
				}
				currTile.setNextTile(new ExitTile(color, position, false));
				exitTiles.put(color, (ExitTile)currTile.getNextTile());
			} else if (position % 13 == 9) {
				currTile.setNextTile(new ShelterTile(position));
			} else {
				currTile.setNextTile(new RegularTile(position, false));
			}
			currTile = currTile.getNextTile();
			if(position == 51) {
				currTile.setNextTile(anchor);
			}
		}
	}

	/*
	 * getAvaliablePawnPosition retorna a posição do peão selecionado atualmente no sistema de coordenadas
	 * do tabuleiro. Este método não será private para que possa ser testado.
	 */
	PawnPosition getPawnPosition(Pawn p) 
	{
		int position = 0;
		Tile auxTile;
		if(p.getTile() instanceof FinalTile ) {
			auxTile = FinalTile.finalTiles.get(p.getColor());
			while (auxTile != p.getTile()) {
				auxTile = auxTile.getNextTile();
				position ++;
			}
			switch(p.getColor()) {
			case green:
				position += 5;
				break;
			case yellow:
				position += 10;
				break;
			case blue:
				position += 15;
			}
			return new PawnPosition(position, true);
		}
		
		auxTile = anchor;
		do {
			if (auxTile.contains(p)) {
				return new PawnPosition(position, false);
			}
			auxTile = auxTile.getNextTile();
			position++;
		} while (auxTile != anchor);
		return new PawnPosition(-1, false);
	}

	public PawnPosition getPosition(double x, double y) {
		double tileX, tileY, result[];
		PawnPosition position;
		for (int i = 0; i < 52; i++) {
			position = new PawnPosition(i, false);
			result = getXY(position);
			tileX = result[0]; tileY = result[1];
			if ( (y >= tileY) && (y <= tileY + tileSize) && (x >= tileX) && (x <= tileX + tileSize) )
				return position;
		}
		for(int i = 0; i < 20; i++) {
			position = new PawnPosition(i, true);
			result = getXY(position);
			tileX = result[0]; tileY = result[1];
			if ( (y >= tileY) && (y <= tileY + tileSize) && (x >= tileX) && (x <= tileX + tileSize) )
				return position;
		}
		return null;
	}
	
	protected void updateOccupiedTiles() {
		for(Player player : players) {
			for(Pawn pawn : player.pawns) {
				if(!pawn.getIsInInitialTile() && !occupiedTiles.contains(pawn.getTile())) {
					occupiedTiles.add(pawn.getTile());
				}
			}
		}

	}
	
	public double[] getXY(PawnPosition pawnPosition) {
		double xAnchor, yAnchor;
		xAnchor = tileSize;
		yAnchor = tileSize * 6;
		int position = pawnPosition.getNumber();
		if (!pawnPosition.getIsInFinalTiles()) {
			if(position < 5)
				return new double[] {xAnchor + position*tileSize, yAnchor};
			else if(position < 11) 
				return new double[] {xAnchor + 5*tileSize, yAnchor - (position - 4)*tileSize };
			else if(position == 11)
				return new double[] {xAnchor + 6*tileSize, 0 };
			else if(position < 18)
				return new double[] {xAnchor + 7*tileSize, yAnchor - 6*tileSize + (position-12)*tileSize};
			else if(position < 24)
				return new double[] {xAnchor + 7*tileSize + (position - 17)*tileSize, yAnchor};
			else if(position == 24)
				return new double[] {xAnchor + 13*tileSize, yAnchor + tileSize};
			else if(position < 31)
				return new double[] {xAnchor + 13*tileSize - (position-25)*tileSize, yAnchor + 2*tileSize};
			else if(position < 37)
				return new double[] {xAnchor + 7*tileSize, yAnchor + 2*tileSize + (position-30)*tileSize};
			else if(position == 37)
				return new double[] {xAnchor + 6*tileSize, yAnchor + 8*tileSize};
			else if(position < 44)
				return new double[] {xAnchor + 5*tileSize, yAnchor + 8*tileSize - (position-38)*tileSize};
			else if (position < 50)
				return new double[] {xAnchor + 4*tileSize - (position-44)*tileSize, yAnchor + 2*tileSize};
			else if (position == 50)
				return new double[] {xAnchor - tileSize, yAnchor + tileSize};
			else if (position == 51)
				return new double[] {xAnchor - tileSize, yAnchor};
		} else {
			if(position < 6) {
				return new double[] {xAnchor + tileSize*position, yAnchor + tileSize};
			} else if (position < 12) {
				return new double[] {xAnchor + 6*tileSize, yAnchor - 5*tileSize + (position-5)*tileSize};
			} else if (position < 18) {
				return new double[] {xAnchor + 12*tileSize - tileSize*(position-10), yAnchor + tileSize};
			} else {
				return new double[] {xAnchor + 6 * tileSize, yAnchor + 7*tileSize - (position-15)*tileSize};
			}
		}
		return new double[] {xAnchor, yAnchor};
	}
	
	public Color getCurrPlayerColor() {
		return currPlayer.getColor();
	}

	public int getNTiles() {
		return nTiles;
	}

	/*
	 * nextPlayer atualiza os valores das variáveis para o próximo jogador
	 * Não vamos testar esta função, pois é muito simples.
	 */
	void updateCurrPlayer() {
		int index = -1;
		for(int i = 0; i < players.length; i++) {
			if (players[i] == currPlayer) index = i; 
		}
		if(index == -1) return;
		index = (index + 1) % players.length;
		currPlayer = players[index];
	}
	
	void nextPlayer() {
		currPlayer.endPlay();
		lastPlayedPawn = null;
		availablePawns = new HashSet<Pawn>();
		canPlayAgain = false;
		hasRolledDice = false;
		dontRollDiceAfterSecondPlay = false;
		updateCurrPlayer();
	}
	
	//setters
	/*
	 * Este setter é usado pelos testes, mas será protected para que o 
	 * cliente do módulo Model não tenha acesso
	 */
	void setNTiles(int n) {
		lastDiceRoll = n;
	}
	
	void setNStraight6(int n) {
		currPlayer.setNStraight6(n);;
	}
	
	void setLastPlayedPawn(Pawn p) {
		lastPlayedPawn = p;
	}
	
	//getters
	//Não vamos testar os getters, pois são muito simples
	
	//este getter é usado pelos testes
	Player getCurrPlayer() {
		return currPlayer;
	}
	
	Map<Color, ExitTile> getExitTiles() {
		return exitTiles;
	}
	
	Tile getAnchor() {
		return this.anchor;
	}
	
	public Set<Tile> getOccupiedTiles() {
		return occupiedTiles;
	}
	
	//metodos publicos, na ordem em que sao usado pelo jogador
	/*
	 * rollDice corresponde à rolagem do dado. Essa função retorna o valor da rolagem, além de atualizar
	 * a variável local que armazena o valor obtido no dado
	 */
	public int rollDice() 
	{
		System.out.println("estou rodando");
		System.out.println("Ha " + occupiedTiles.size() + " tiles ocupadas");
		if(canPlayAgain || hasRolledDice) {
			//TODO: subsituir por um aviso para o jogador
			return nTiles;
		}
		System.out.printf("Digite o dado: ");
		nTiles = scanner.nextInt();
		if(nTiles < 0) {
			nTiles = (rand.nextInt(6) + 1);
		}
		System.out.println("O jogador ");
		printColor(currPlayer.getColor());
		System.out.println("tirou %d no dado" + nTiles);
		boolean wasAbleToStartPawn = false;
		
		if (nTiles == 5) {
			try {
				wasAbleToStartPawn = currPlayer.startPawn();
			} catch (PawnCapturedException e) {
				canPlayAgain = true;
				hasRolledDice = true;
				dontRollDiceAfterSecondPlay = true;
				nTiles = 6;
				notifyObservers();
				return 5;
			} finally {
				if (wasAbleToStartPawn) {
					nextPlayer();
					notifyObservers();
					return 5;
				}
			}
		}
		
		if(nTiles == 6) {
			System.out.printf("O jogador ");
			printColor(currPlayer.getColor());
			System.out.println(" tirou 6\n");
			currPlayer.rollSixOnDice();
			if (currPlayer.getNStraight6() == 3) {
				if(!lastPlayedPawn.getIsInLastTile())
					lastPlayedPawn.sendToInitial();
				nextPlayer();
				notifyObservers();
				return nTiles;
			} else {
				System.out.println("Pode jogar novamente");
			}
		}
		
		try {
			availablePawns = currPlayer.evaluateMoves(nTiles);
		} catch (NoMovesAvailableException e) {
			nextPlayer();
			notifyObservers();
			return nTiles;
		} catch (BarrierFoundException e) {
			availablePawns = e.getPawnsInBarrier();
			System.out.println("Number of available pawns: " + availablePawns.size());
			if (availablePawns.size() == 2) {
				for(Pawn p : availablePawns) {
					hasRolledDice = true;
					play(p.currTile.getPosition());
					return nTiles;
				}
			} else {
				PawnPosition firstPosition = null;
				PawnPosition secondPosition = null;
				for(Pawn p : availablePawns) {
					if(firstPosition == null) firstPosition = p.currTile.getPosition();
					else if (!firstPosition.equals(p.currTile.getPosition())) {
						secondPosition = p.currTile.getPosition();
						break;
					}
				}
				hasRolledDice = true;
				if(firstPosition.closestToFinal(secondPosition, currPlayer.getColor())) {
					play(firstPosition);
					notifyObservers();
					return nTiles;
				}
				play(secondPosition);
				notifyObservers();
				return nTiles;
			}
		}
		hasRolledDice = true;
		notifyObservers();
		return nTiles;
	}
	
	private void play(PawnPosition selectedPawnPosition) {
		System.out.println("Funcao play da Facade chamada");
		if ((!hasRolledDice) && (!canPlayAgain)) {
			//TODO: mudar isso por uma mensagem na tela
			return;
		}
		if (canPlayAgain) {
			try {
			availablePawns = currPlayer.evaluateMoves(nTiles);
		} catch (NoMovesAvailableException e) {
			nextPlayer();
			notifyObservers();
		} catch (BarrierFoundException e) {
			availablePawns = e.getPawnsInBarrier();
		}
		}
		PawnPosition tempPawnPosition;
		Pawn selectedPawn = null; 
		System.out.printf("%d\n", selectedPawnPosition.getNumber());
		for(Pawn pawn : availablePawns) {
			System.out.println("entro aqui na play papai");
			tempPawnPosition = getPawnPosition(pawn);
			if (tempPawnPosition.equals(selectedPawnPosition)) {
				selectedPawn = pawn;
			}
		}
		if (selectedPawn == null) {
			System.out.println("selected pawn nao existe");
			return;
		}
		try {
			selectedPawn.move(nTiles);
		} catch (MoveImpossibleException e) {
			return;
		} catch (PawnCapturedException e) {
			System.out.println("A capture ocurred!");
			canPlayAgain = true;
			dontRollDiceAfterSecondPlay = true;
			nTiles = 6;
			notifyObservers();
			return;
		} catch (PawnInFinalTileException e) {
			if(currPlayer.hasWon()) {
				System.out.println("O jogador ghanhou!!");
				return;
			}
			canPlayAgain = true;
			dontRollDiceAfterSecondPlay = true;
			nTiles = 6;
			notifyObservers();
			return;
		}
		lastPlayedPawn = selectedPawn;
		if(nTiles != 6) {
			nextPlayer();
		} else {
			if(dontRollDiceAfterSecondPlay) {
				nextPlayer();
			} else {
				hasRolledDice = false;
				canPlayAgain = false;
			}
		}
		notifyObservers();
		return;
	}

	public void play(double x, double y) {
		PawnPosition position = getPosition(x, y);
		if (position == null) {
			return;
		}
		play(position);
	}

	public void printColor(Color color) {
		switch(color) {
			case red:
			System.out.printf("red ");
			break;
			case green:
			System.out.printf("green ");
			break;
			case blue:
			System.out.printf("blue ");
			break;
			case yellow:
			System.out.printf("yellow ");
		}
	}

	public void printAllPawnsOnBoard() {
		for(Player p : players) {
			printColor(p.getColor());
			for (Pawn pawn : p.getPawns()) {
				System.out.printf("Peao na posicao %d\n", pawn.getTile().getPosition().getNumber());
			}
		}
	}

	public Player[] getPlayers() {
		return players;
	}

	//implementacao de observable
	@Override
	public void addObserver(Swing.Observer observer) {
	    observers.add(observer);
	}

	@Override
	public void removeObserver(Swing.Observer observer) {
	    observers.remove(observer);
	}

	public void notifyObservers() {
		updateOccupiedTiles();
		for (Swing.Observer observer : observers) {
			observer.notify(this);
		}
	}
}
