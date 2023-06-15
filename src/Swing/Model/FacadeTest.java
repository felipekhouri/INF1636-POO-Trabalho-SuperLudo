// package Model;

// import static org.junit.Assert.*;

// import org.junit.AfterClass;
// import org.junit.Test;

// public class FacadeTest {
	
// 	@Test
// 	public void testMakeNewBoard() {
// 		Facade facade = Facade.getTestInstance();
// 		Tile currTile = facade.getAnchor();
// 		for(Color c: Color.values()) {
// 			assertTrue(currTile instanceof ExitTile);
// 			for(int i = 0; i < 8; i++) {
// 				currTile = currTile.getNextTile();
// 				assertTrue(currTile instanceof RegularTile);
// 			}
// 			currTile = currTile.getNextTile();
// 			assertTrue(currTile instanceof ShelterTile);
// 			for(int i = 0; i < 3; i++) {
// 				currTile = currTile.getNextTile();
// 				assertTrue(currTile instanceof RegularTile);
// 			}
// 			currTile = currTile.getNextTile();
// 		}
// 		assertEquals(currTile, facade.getAnchor());
// 	}
	
// 	//testando a função getAvaliablePawnPosition
// 	@Test
// 	public void testPawnPositionInInitialTile() {
// 		Facade facade = Facade.getTestInstance();
// 		Pawn p1 = new Pawn(Color.red, facade.getAnchor());
// 		try {
// 			facade.getAnchor().addPawn(p1);	
// 		} catch (Exception e) {
			
// 		}
// 		assertEquals(facade.getAvaliablePawnPosition(p1).getNumber(), 0);
// 		assertFalse(facade.getAvaliablePawnPosition(p1).getIsInFinalTiles());
// 	}
	
// 	@Test
// 	public void testPawnPositionInFinalTile() {
// 		Facade facade = Facade.getTestInstance();
// 		Pawn p1 = new Pawn(Color.red, facade.getAnchor());
// 		try {
// 			facade.getAnchor().addPawn(p1);	
// 			p1.setIsInInitialTile(false);
// 			p1.move(51);
// 		} catch (Exception e) {
			
// 		}
// 		assertEquals(facade.getAvaliablePawnPosition(p1).getNumber(), 0);
// 		assertTrue(facade.getAvaliablePawnPosition(p1).getIsInFinalTiles());
// 	}
	
// 	@Test
// 	public void testPawnPositionInMidBoard() {
// 		Facade facade = Facade.getTestInstance();
// 		Pawn p1 = new Pawn(Color.red, facade.getAnchor());
// 		try {
// 			facade.getAnchor().addPawn(p1);	
// 			p1.setIsInInitialTile(false);
// 			p1.move(10);
// 		} catch (Exception e) {
			
// 		}
// 		assertEquals(facade.getAvaliablePawnPosition(p1).getNumber(), 10);
// 		assertFalse(facade.getAvaliablePawnPosition(p1).getIsInFinalTiles());
// 	}

// 	//testando a função rollDice
// 	@Test
// 	public void testRollDice() {
// 		Facade facade = Facade.getTestInstance();
// 		assertTrue(facade.rollDice() > 0);
// 		assertTrue(facade.rollDice() < 7);
// 	}
	
// 	@Test
// 	public void testCanStartPlayReturnsFalseThirdSixInARow() {
// 		Facade facade = Facade.getTestInstance();
// 		facade.setNStraight6(2);
// 		facade.lastDiceRoll = 6;
// 		Player p1 = facade.getCurrPlayer();
// 		p1.updateFirstPlay();
// 		facade.setLastPlayedPawn(p1.pawns[0]);
// 		try {
// 			assertFalse(facade.canStartPlay());
// 		} catch (PawnCapturedException e) {
// 			fail("Excecao inesperada");
// 		}
// 		assertTrue(p1.pawns[0].getIsInInitialTile());
// 	}

// 	@Test
// 	public void testCanStartPlayReturnsFalseNoMovesAvaliable() {
// 		Facade facade = Facade.getTestInstance();
// 		try {
// 			assertFalse(facade.canStartPlay());
// 		} catch (PawnCapturedException e) {
			
// 		}
// 	}
	
// 	@Test
// 	public void testCanStartPlayReturnsTrueMovesAvaliable() {
// 		Facade facade = Facade.getTestInstance();
// 		try {
// 			facade.getCurrPlayer().startPawn();
// 		} catch (Exception e) {
			
// 		}
// 		facade.rollDice();
// 		try {
// 			assertTrue(facade.canStartPlay());
// 		} catch (PawnCapturedException e) {
			
// 		}
// 	}
	
// 	@Test
// 	public void testPlayReturnTrueWithPawnCaptured() {
// 		Facade facade = Facade.getTestInstance();
// 		try {
// 			facade.getCurrPlayer().startPawn();
// 		} catch (Exception e) {
			
// 		}
// 		facade.setNTiles(5);
// 	}
	
// 	@Test
// 	public void testPlayLast6() {
// 		Facade facade = Facade.getTestInstance();
// 		facade.nTiles = 6;
// 		facade.lastDiceRoll = 6;
// 		try {
// 			facade.canStartPlay();	
// 		} catch(Exception e) {
// 			fail("Unexpected exception");
// 		}
// 		facade.getNextAvaliablePawnPosition();
// 		boolean result = false;
// 		try {
// 			result = facade.play();
// 		}
// 		catch(Exception e) {
// 			System.out.println(e.getLocalizedMessage());
// 			fail("Unexpected exception");
// 		}
// 		assertTrue(result);
		
// 	}
// }
