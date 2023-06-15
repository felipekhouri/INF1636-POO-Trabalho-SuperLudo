// package Model;

// import org.junit.Test;
// import static org.junit.Assert.*;

// public class PawnTest {

// 	@Test (expected = MoveImpossibleException.class)
// 	public void moveImpossibleBarrierInPath() throws MoveImpossibleException, PawnInFinalTileException, PawnCapturedException {
// 		Tile t3 = new RegularTile();
// 		Tile t2 = new RegularTile();
// 		t2.setNextTile(t3);
// 		Tile t1 = new RegularTile();
// 		t1.setNextTile(t2);
// 		Pawn p1 = new Pawn(Color.red, t1);
// 		Pawn p2 = new Pawn(Color.red, t3);
// 		Pawn p3 = new Pawn(Color.red, t3);
// 		p1.setIsInInitialTile(false);
// 		try {
// 			t1.addPawn(p1);
// 			t3.addPawn(p2);
// 			t3.addPawn(p3);
// 			p1.move(2);
// 		} catch (Exception e) {
// 			throw e;
// 		}
// 	}
	
// 	@Test (expected = PawnCapturedException.class)
// 	public void movePossibleAndPawnCaptured() throws MoveImpossibleException, PawnInFinalTileException, PawnCapturedException {
// 		Tile t3 = new RegularTile();
// 		Tile t2 = new RegularTile();
// 		t2.setNextTile(t3);
// 		Tile t1 = new RegularTile();
// 		t1.setNextTile(t2);
// 		Pawn p1 = new Pawn(Color.red, t1);
// 		Pawn p2 = new Pawn(Color.blue, t3);
// 		p1.setIsInInitialTile(false);
// 		try {
// 			t1.addPawn(p1);
// 			t3.addPawn(p2);
// 			p1.move(2);
// 		} catch (Exception e) {
// 			throw e;
// 		}
// 	}
	
// 	@Test
// 	public void moveCorrectlyChangesPawnTile() throws MoveImpossibleException, PawnInFinalTileException, PawnCapturedException {
// 		Tile t3 = new RegularTile();
// 		Tile t2 = new RegularTile();
// 		t2.setNextTile(t3);
// 		Tile t1 = new RegularTile();
// 		t1.setNextTile(t2);
// 		Pawn p1 = new Pawn(Color.red, t1);
// 		p1.setIsInInitialTile(false);
// 		try {
// 			t1.addPawn(p1);
// 			p1.move(2);
// 		} catch (Exception e) {
// 			throw e;
// 		}
// 		assertEquals(p1.currTile, t3);
// 		assertEquals(t1.currPawns.size(), 0);
// 	}
	
// 	@Test (expected = PawnInFinalTileException.class) //(expected = PawnInFinalTileException.class)
// 	public void moveInsideFinalTiles() throws MoveImpossibleException, PawnInFinalTileException, PawnCapturedException {
// 		FinalTile t1 = new FinalTile(Color.red);
// 		FinalTile t2 = new FinalTile(Color.red);
// 		t1.setNextTile(t2);
// 		Pawn p1 = new Pawn(Color.red, t1);
// 		p1.setIsInInitialTile(false);
// 		t1.addPawn(p1);
// 		try {
// 			p1.move(1);
// 		} catch(PawnMoveException e) {
// 			throw e;
// 		}
// 	}
	
// 	@Test
// 	public void pawnGetsIntoFinalTilesCorrectly() {
// 		Facade facade = Facade.getTestInstance();
// 		Pawn p1 = new Pawn(Color.red, facade.getAnchor());
// 		try {
// 			p1.setIsInInitialTile(false);
// 			p1.move(51);
// 		} catch (Exception e) {
			
// 		}
// 		assertTrue(p1.currTile instanceof FinalTile);
// 		assertEquals(p1.currTile.getPreviousTile(), null);
// 	}
	
// 	@Test
// 	public void sendPawnToInitialTile() {
// 		Tile t1 = new RegularTile();
// 		Pawn p1 = new Pawn(Color.red, t1);
// 		p1.sendToInitial();
// 		assertEquals(p1.currTile, Facade.getInstance().getExitTiles().get(Color.red));
// 		assertEquals(p1.getIsInInitialTile(), true);
// 	}

// 	@Test
// 	public void sendPawnAlreadyInInitialTileThere() {
// 		Tile t1 = new RegularTile();
// 		Pawn p1 = new Pawn(Color.red, Facade.getInstance().getExitTiles().get(Color.red));
// 		p1.sendToInitial();
// 		assertEquals(p1.currTile, Facade.getInstance().getExitTiles().get(Color.red));
// 		assertEquals(p1.getIsInInitialTile(), true);
// 	}



// 	@Test
// 	public void testCanMove() throws MoveImpossibleException, PawnCapturedException {
// 	   // Criar o tabuleiro com algumas casas e adicionar peões em posições específicas
// 	   Tile t1 = new RegularTile();
// 	   Tile t2 = new RegularTile();
// 	   Tile t3 = new RegularTile();
// 	   Tile t4 = new RegularTile();
// 	   Tile t5 = new RegularTile();
// 	   t1.setNextTile(t2);
// 	   t2.setNextTile(t3);
// 	   t3.setNextTile(t4);
// 	   t4.setNextTile(t5);

// 	   Pawn p1 = new Pawn(Color.red, t1);
// 	   Pawn p2 = new Pawn(Color.red, t2);

// 	   t2.addPawn(p1);
// 	   t3.addPawn(p2);
// 	   p1.setIsInInitialTile(false);
// 	   p2.setIsInInitialTile(false);
	   
// 	   // Tenta mover um peão em 2 casas
// 	   boolean result = p1.canMove(2);

// 	   // Verifica se o resultado é o esperado
// 	   assertTrue(result);

// 	   // Tenta mover um peão em 6 casas
// 	   result = p1.canMove(6);

// 	   // Verifica se o resultado é o esperado
// 	   assertFalse(result);

// 	   // Adiciona um peão do mesmo jogador em t2, criando uma barreira
// 	   Pawn p4 = new Pawn(Color.red, t2);
// 	   t2.addPawn(p4);

// 	   // Tenta mover o peão em t2
// 	   result = p1.canMove(1);

// 	   // Verifica se o resultado é o esperado
// 	   assertFalse(result);

// 	   // Move o peão para fora do initial tile
// 	   p1.setIsInInitialTile(false);

// 	   // Tenta mover o peão quando ele está no initial tile
// 	   result = p1.canMove(1);

// 	   // Verifica se o resultado é o esperado
// 	   assertFalse(result);
// 	}


// 	@Test
// 	public void testCanMoveSuccess() throws MoveImpossibleException, PawnCapturedException {
// 	    // Criar o tabuleiro com algumas casas e adicionar peões em posições específicas
// 	    Tile t1 = new RegularTile();
// 	    Tile t2 = new RegularTile();
// 	    Tile t3 = new RegularTile();
// 	    Tile t4 = new RegularTile();
// 	    Tile t5 = new RegularTile();
// 	    t1.setNextTile(t2);
// 	    t2.setNextTile(t3);
// 	    t3.setNextTile(t4);
// 	    t4.setNextTile(t5);

// 	    Pawn p1 = new Pawn(Color.red, t1);
// 	    Pawn p2 = new Pawn(Color.red, t2);
// 	    p1.setIsInInitialTile(false);
// 	    p2.setIsInInitialTile(false);
	    
	    
// 	    t2.addPawn(p1);
// 	    t3.addPawn(p2);

// 	    // Tenta mover um peão em 2 casas
// 	    boolean result = p1.canMove(2);

// 	    // Verifica se o resultado é o esperado
// 	    assertTrue(result);
// 	}

// 	@Test
// 	public void testCanMoveFailDistance() throws MoveImpossibleException, PawnCapturedException {
// 	    // Criar o tabuleiro com algumas casas e adicionar peões em posições específicas
// 	    Tile t1 = new RegularTile();
// 	    Tile t2 = new RegularTile();
// 	    Tile t3 = new RegularTile();
// 	    Tile t4 = new RegularTile();
// 	    Tile t5 = new RegularTile();
// 	    t1.setNextTile(t2);
// 	    t2.setNextTile(t3);
// 	    t3.setNextTile(t4);
// 	    t4.setNextTile(t5);

// 	    Pawn p1 = new Pawn(Color.red, t1);
// 	    Pawn p2 = new Pawn(Color.red, t2);

// 	    t2.addPawn(p1);
// 	    t3.addPawn(p2);

// 	    // Tenta mover um peão em 3 casas
// 	    boolean result = p1.canMove(3);

// 	    // Verifica se o resultado é o esperado
// 	    assertFalse(result);
// 	}

// 	@Test
// 	public void testCanMoveFailBarrier() throws MoveImpossibleException, PawnCapturedException {
// 	    // Criar o tabuleiro com algumas casas e adicionar peões em posições específicas
// 	    Tile t1 = new RegularTile();
// 	    Tile t2 = new RegularTile();
// 	    Tile t3 = new RegularTile();
// 	    Tile t4 = new RegularTile();
// 	    Tile t5 = new RegularTile();
// 	    t1.setNextTile(t2);
// 	    t2.setNextTile(t3);
// 	    t3.setNextTile(t4);
// 	    t4.setNextTile(t5);

// 	    Pawn p1 = new Pawn(Color.red, t1);
// 	    Pawn p2 = new Pawn(Color.red, t2);
// 	    Pawn p3 = new Pawn(Color.blue, t3);
// 	    Pawn p4 = new Pawn(Color.red, t2);

// 	    t2.addPawn(p1);
// 	    t2.addPawn(p4);
// 	    t1.addPawn(p2);
// 	    t3.addPawn(p3);

// 	    // Tenta mover o peão em t2
// 	    boolean result = p1.canMove(1);

// 	    // Verifica se o resultado é o esperado
// 	    assertFalse(result);
// 	}

// 	@Test
// 	public void testCanMoveFailInitialTile() throws MoveImpossibleException, PawnCapturedException {
// 	    // Cria o tabuleiro com algumas casas e adiciona peões em posições específicas
// 	    Tile t1 = new RegularTile();
// 	    Tile t2 = new RegularTile();
// 	    Tile t3 = new RegularTile();
// 	    Tile t4 = new RegularTile();
// 	    Tile t5 = new RegularTile();
// 	    t1.setNextTile(t2);
// 	    t2.setNextTile(t3);
// 	    t3.setNextTile(t4);
// 	    t4.setNextTile(t5);

// 	    Pawn p1 = new Pawn(Color.red, t1);

// 	    // Tenta mover o peão em 1 casa quando ele está no initial tile
// 	    boolean result = p1.canMove(1);

// 	    // Verifica se o resultado é o esperado
// 	    assertFalse(result);
// 	}
// }


