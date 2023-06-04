// package Model;

// import static org.junit.Assert.*;

// import org.junit.AfterClass;
// import org.junit.Test;

// public class HasWonTest {

// 	@Test
// 	public void playerHasWon() throws Exception {
// 		ExitTile e1 = new ExitTile(Color.red);
// 		FinalTile f1 = new FinalTile(Color.red);
// 		e1.setNextTile(f1);
// 		FinalTile f2 = new FinalTile(Color.red);
// 		f1.setNextTile(f2);
// 		FinalTile f3 = new FinalTile(Color.red);
// 		f2.setNextTile(f3);
// 		Player p1 = new Player(Color.red, e1);
		
// 		try {
// 			p1.startPawn();
// 			p1.pawns[0].move(3);
// 		} 
// 		catch(PawnInFinalTileException e) {}
// 		catch(Exception e) {throw e;}
		
// 		try {
// 			p1.startPawn();
// 			p1.pawns[1].move(3);
// 		} 
// 		catch(PawnInFinalTileException e) {}
// 		catch(Exception e) {throw e;}
		
// 		try {
// 			p1.startPawn();
// 			p1.pawns[2].move(3);
// 		} 
// 		catch(PawnInFinalTileException e) {}
// 		catch(Exception e) {throw e;}
		
// 		try {
// 			p1.startPawn();
// 			p1.pawns[3].move(3);
// 		} 
// 		catch(PawnInFinalTileException e) {}
// 		catch(Exception e) {throw e;}
		
// 		assertTrue(p1.hasWon());
// 	}
	
// 	@Test
// 	public void playerHasntWon() throws Exception{
// 		ExitTile e1 = new ExitTile(Color.red);
// 		FinalTile f1 = new FinalTile(Color.red);
// 		e1.setNextTile(f1);
// 		FinalTile f2 = new FinalTile(Color.red);
// 		f1.setNextTile(f2);
// 		FinalTile f3 = new FinalTile(Color.red);
// 		f2.setNextTile(f3);
// 		Player p1 = new Player(Color.red, e1);
		
// 		try {
// 			p1.startPawn();
// 			p1.pawns[0].move(2);
// 		} 
// 		catch(PawnInFinalTileException e) {
			
// 		}
// 		catch(Exception e) {throw e;}
		
// 		try {
// 			p1.startPawn();
// 			p1.pawns[1].move(2);
// 		} 
// 		catch(PawnInFinalTileException e) {}
// 		catch(Exception e) {throw e;}
		
// 		try {
// 			p1.startPawn();
// 			p1.pawns[2].move(3);
// 		} 
// 		catch(PawnInFinalTileException e) {}
// 		catch(Exception e) {throw e;}
		
// 		try {
// 			p1.startPawn();
// 			p1.pawns[3].move(3);
// 		} 
// 		catch(PawnInFinalTileException e) {}
// 		catch(Exception e) {throw e;}
		
// 		assertFalse(p1.hasWon());
// 	}

// }
