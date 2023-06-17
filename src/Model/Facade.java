package Model;

import java.util.*;
import Swing.Observed;

public class Facade implements Observed {

	private Facade() {
		int i = 0;
		players = new Player[Color.values().length];
		makeNewBoard();
		for (Color c : Color.values()) {
			players[i] = new Player(c, exitTiles.get(c));
			System.out.printf("tile de saids do jogador: %d\n", exitTiles.get(c).getPosition().getNumber());
			i++;
		}
		currPlayer = players[0];
		observers = new ArrayList<>();

	}

	// Singleton
	private static Facade instance = null;

	public static Facade getInstance() {
		if (instance == null)
			instance = new Facade();
		return instance;
	}

	// não queremos usar o singleton em testes
	protected static Facade getTestInstance() {
		return new Facade();
	}

	// implementacao de observable
	private List<Swing.Observer> observers;

	// gerenciamento do jogador atual
	private Player currPlayer;
	private Player[] players;
	// casas de saída
	private Map<Color, ExitTile> exitTiles;
	// número tirado no dado
	protected int lastDiceRoll;
	protected int nTiles;
	// último peão que o jogador moveu
	private Pawn lastPlayedPawn;
	// classe random para a rolagem do dado
	private Random rand = new Random();
	// anchor é uma referência para a casa de saída vermelha. No sistema de
	// coordenadas, é a "casa número 0"
	private Tile anchor;
	// peoes que podem ser movidos
	private Set<Pawn> availablePawns = new HashSet<Pawn>();
	public double tileSize = 51.3;
	// indica se o jogador atual precisa jogar novamente antes de selecionar um peao
	private boolean canPlayAgain = false;
	// indica se o jogador ja rolou o dado antes de jogar
	private boolean hasRolledDice = false;
	// todas as tiles em que há peoes
	private Set<Tile> occupiedTiles = new HashSet<Tile>();

	// metodos auxiliares
	/*
	 * makeNewBoard cria todas as tiles de um tabuleiro, instancia a variável
	 * exitTiles e
	 * instancia a variável anchor
	 */
	private void makeNewBoard() {
		Tile currTile, firstTile = null, finalTile = null;
		boolean isFirstRun = true;
		exitTiles = new HashMap<Color, ExitTile>();
		double[] array;
		int count = 0;
		for (Color c : Color.values()) {
			currTile = new ExitTile(c);
			currTile.position = new PawnPosition(count, false);
			exitTiles.put(c, (ExitTile) currTile);
			if (finalTile != null)
				finalTile.setNextTile(currTile);

			if (c == Color.red)
				anchor = currTile;

			if (isFirstRun) {
				firstTile = currTile;
				isFirstRun = false;
			}

			for (int i = 0; i < 8; i++) {
				currTile.setNextTile(new RegularTile());
				currTile.position = new PawnPosition(count, false);
				currTile = currTile.getNextTile();
			}
			currTile.setNextTile(new ShelterTile());
			currTile = currTile.getNextTile();
			for (int i = 0; i < 3; i++) {
				currTile.setNextTile(new RegularTile());
				currTile.position = new PawnPosition(count, false);
				currTile = currTile.getNextTile();
			}
			finalTile = currTile;
		}
		finalTile.setNextTile(firstTile); // aqui, garantimos que a lista encadeada é circular
	}

	/*
	 * getAvaliablePawnPosition retorna a posição do peão selecionado atualmente no
	 * sistema de coordenadas
	 * do tabuleiro. Este método não será private para que possa ser testado.
	 */
	public PawnPosition getPawnPosition(Pawn p) {
		int position = 0;
		Tile auxTile;
		if (p.getTile() instanceof FinalTile) {
			auxTile = FinalTile.finalTiles.get(p.getColor());
			while (auxTile != p.getTile()) {
				auxTile = auxTile.getNextTile();
				position++;
			}
			switch (p.getColor()) {
				case green:
					position += 5;
					break;
				case yellow:
					position += 10;
				case blue:
					position += 15;
			}
			return new PawnPosition(position, true);
		}

		auxTile = anchor;
		do {
			if (auxTile.contains(p)) {
				if (position == 0) {
					System.out.println("nem saiu da primeira casa");
				}
				return new PawnPosition(position, false);
			}
			auxTile = auxTile.getNextTile();
			position++;
		} while (auxTile != anchor);
		return new PawnPosition(-1, false);
	}

	public PawnPosition getPosition(double x, double y) {
		System.out.printf("recebi um toque nas posicoes %f e %f.\n", x, y);
		double tileX, tileY, result[];
		PawnPosition position;
		for (int i = 0; i < 52; i++) {
			position = new PawnPosition(i, false);
			result = getXY(position);
			tileX = result[0];
			tileY = result[1];
			if ((y >= tileY) && (y <= tileY + tileSize) && (x >= tileX) && (x <= tileX + tileSize))
				return position;
		}
		for (int i = 0; i < 20; i++) {
			position = new PawnPosition(i, true);
			result = getXY(position);
			tileX = result[0];
			tileY = result[1];
			if ((y >= tileY) && (y <= tileY + tileSize) && (x >= tileX) && (x <= tileX + tileSize))
				return position;
		}
		return null;
	}

	protected void updateOccupiedTiles() {
		for (Player player : players) {
			for (Pawn pawn : player.pawns) {
				if (!pawn.getIsInInitialTile() && !occupiedTiles.contains(pawn.getTile())) {
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
			if (position < 5)
				return new double[] { xAnchor + position * tileSize, yAnchor };
			else if (position < 11)
				return new double[] { xAnchor + 5 * tileSize, yAnchor - (position - 4) * tileSize };
			else if (position == 11)
				return new double[] { xAnchor + 6 * tileSize, 0 };
			else if (position < 18)
				return new double[] { xAnchor + 7 * tileSize, yAnchor - 6 * tileSize + (position - 12) * tileSize };
			else if (position < 24)
				return new double[] { xAnchor + 7 * tileSize + (position - 17) * tileSize, yAnchor };
			else if (position == 24)
				return new double[] { xAnchor + 13 * tileSize, yAnchor + tileSize };
			else if (position < 31)
				return new double[] { xAnchor + 13 * tileSize - (position - 25) * tileSize, yAnchor + 2 * tileSize };
			else if (position < 37)
				return new double[] { xAnchor + 7 * tileSize, yAnchor + 2 * tileSize + (position - 30) * tileSize };
			else if (position == 37)
				return new double[] { xAnchor + 6 * tileSize, yAnchor + 8 * tileSize };
			else if (position < 44)
				return new double[] { xAnchor + 5 * tileSize, yAnchor + 8 * tileSize - (position - 38) * tileSize };
			else if (position < 50)
				return new double[] { xAnchor + 4 * tileSize - (position - 44) * tileSize, yAnchor + 2 * tileSize };
			else if (position == 50)
				return new double[] { xAnchor - tileSize, yAnchor + tileSize };
			else if (position == 51)
				return new double[] { xAnchor - tileSize, yAnchor };
		} else {
			if (position < 5) {
				return new double[] { xAnchor + tileSize * position, yAnchor + tileSize };
			} else if (position < 10) {
				return new double[] { xAnchor + 6 * tileSize, yAnchor - 5 * tileSize + (position - 5) * tileSize };
			} else if (position < 15) {
				return new double[] { xAnchor + 12 * tileSize - tileSize * (position - 10), yAnchor + tileSize };
			} else {
				return new double[] { xAnchor + 6 * tileSize, yAnchor + 7 * tileSize - (position - 15) * tileSize };
			}
		}
		return new double[] { xAnchor, yAnchor };
	}

	public Player[] getPlayers(){
		return players;
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
		for (int i = 0; i < players.length; i++) {
			if (players[i] == currPlayer)
				index = i;
		}
		if (index == -1)
			return;
		index = (index + 1) % players.length;
		currPlayer = players[index];
	}

	void nextPlayer() {
		currPlayer.endPlay();
		lastPlayedPawn = null;
		availablePawns = new HashSet<Pawn>();
		canPlayAgain = false;
		hasRolledDice = false;
		updateCurrPlayer();
	}

	// setters
	/*
	 * Este setter é usado pelos testes, mas será protected para que o
	 * cliente do módulo Model não tenha acesso
	 */
	void setNTiles(int n) {
		lastDiceRoll = n;
	}

	void setNStraight6(int n) {
		currPlayer.setNStraight6(n);
		;
	}

	void setLastPlayedPawn(Pawn p) {
		lastPlayedPawn = p;
	}

	// getters
	// Não vamos testar os getters, pois são muito simples

	// este getter é usado pelos testes
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

	// metodos publicos, na ordem em que sao usado pelo jogador
	/*
	 * rollDice corresponde à rolagem do dado. Essa função retorna o valor da
	 * rolagem, além de atualizar
	 * a variável local que armazena o valor obtido no dado
	 */
	public int rollDice() {
		System.out.printf("dado atual: %d\n", lastDiceRoll);
		System.out.printf("pode rolar o dado: %s\n", hasRolledDice ? "nao" : "sim");
		if (canPlayAgain || hasRolledDice) {
			// TODO: subsituir por um aviso para o jogador
			System.out.println("Voce precisa selecionar um peao para jogar antes de rolar o dado novamente");
			return nTiles;
		}
		lastDiceRoll = (rand.nextInt(6) + 1);
		nTiles = lastDiceRoll;
		boolean wasAbleToStartPawn = false;

		if (nTiles == 5) {
			try {
				wasAbleToStartPawn = currPlayer.startPawn();
			} catch (PawnCapturedException e) {
				canPlayAgain = true;
				hasRolledDice = true;
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

		if (nTiles == 6) {
			currPlayer.rollSixOnDice();
			if (currPlayer.getNStraight6() == 3) {
				lastPlayedPawn.sendToInitial();
				nextPlayer();
				notifyObservers();
				return nTiles;
			} else {
				canPlayAgain = true;
			}
		}

		try {
			currPlayer.evaluateMoves(nTiles);
		} catch (NoMovesAvailableException e) {
			System.out.println("nenhum movimento disponivel");
			nextPlayer();
			notifyObservers();
			return nTiles;
		} catch (BarrierFoundException e) {
			availablePawns = e.getPawnsInBarrier();
		}
		hasRolledDice = true;
		return nTiles;
	}

	private void play(PawnPosition selectedPawnPosition) {
		System.out.printf("jogou na posicao %d%s\n", selectedPawnPosition.getNumber(),
				selectedPawnPosition.getIsInFinalTiles() ? ", final" : "");
		if ((!hasRolledDice) && (!canPlayAgain)) {
			// TODO: mudar isso por uma mensagem na tela
			System.out.println("voce precisa rolar o dado antes de jogar");
			return;
		}
		PawnPosition tempPawnPosition;
		Pawn selectedPawn = null;
		for (Pawn pawn : availablePawns) {
			tempPawnPosition = getPawnPosition(pawn);
			if (tempPawnPosition.equals(selectedPawnPosition)) {
				selectedPawn = pawn;
			}
		}
		if (selectedPawn == null) {
			return;
		}
		try {
			selectedPawn.move(nTiles);
		} catch (MoveImpossibleException e) {
			return;
		} catch (PawnCapturedException e) {
			canPlayAgain = true;
			nTiles = 6;
			return;
		} catch (PawnInFinalTileException e) {
			if (currPlayer.hasWon()) {
				// TODO: substituir isso por false
				System.out.println("somebody won the game!!!");
			}
		}
		nextPlayer();
		return;
	}

	public void play(double x, double y) {
		PawnPosition position = getPosition(x, y);
		if (position == null) {
			System.out.println("O toque nao gerou uma posicao valida.");
			return;
		}
		play(position);
	}

	// implementacao de observable
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
