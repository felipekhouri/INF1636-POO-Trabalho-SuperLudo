// package Model;

// import static org.junit.Assert.*;

// import org.junit.Test;
// import java.util.Set;

// public class EvaluateMovesTest {

// 	@Test (expected = BarrierFoundException.class)
// 	public void barrierFoundException() throws Exception{
// 		ExitTile t1 = new ExitTile(Color.red);
// 		Tile t2 = new RegularTile();
// 		t1.setNextTile(t2);
// 		Tile t3 = new RegularTile();
// 		t2.setNextTile(t3);
// 		Tile t4 = new RegularTile();
// 		t3.setNextTile(t4);
// 		Tile t5 = new RegularTile();
// 		t4.setNextTile(t5);
// 		Tile t6 = new RegularTile();
// 		t5.setNextTile(t6);
// 		Tile t7 = new RegularTile();
// 		t6.setNextTile(t7);
// 		Tile t8 = new RegularTile();
// 		t7.setNextTile(t8);
// 		Player p1 = new Player(Color.red, t1);
// 		try {
// 			p1.startPawn();
// 			p1.pawns[0].move(1);
// 			p1.startPawn();
// 			p1.pawns[1].move(1);
// 			p1.evaluateMoves(6);
// 		} catch (BarrierFoundException e) {
// 			assertTrue(e.getPawnsInBarrier().contains(p1.pawns[0]));
// 			assertTrue(e.getPawnsInBarrier().contains(p1.pawns[1]));
// 			assertEquals(e.getPawnsInBarrier().size(), 2);
// 			throw e;
// 		}
// 		catch (Exception e) {
// 			throw e;
// 		}
// 	}
	
// 	@Test
// 	public void thereAreBarriersButNoBarrierFoundException() throws Exception{
// 		ExitTile t1 = new ExitTile(Color.red);
// 		Tile t2 = new RegularTile();
// 		t1.setNextTile(t2);
// 		Tile t3 = new RegularTile();
// 		t2.setNextTile(t3);
// 		Tile t4 = new RegularTile();
// 		t3.setNextTile(t4);
// 		Tile t5 = new RegularTile();
// 		t4.setNextTile(t5);
// 		Tile t6 = new RegularTile();
// 		t5.setNextTile(t6);
// 		Tile t7 = new RegularTile();
// 		t6.setNextTile(t7);
// 		Tile t8 = new RegularTile();
// 		t7.setNextTile(t8);
// 		Player p1 = new Player(Color.red, t1);
// 		try {
// 			p1.startPawn();
// 			p1.pawns[0].move(1);
// 			p1.startPawn();
// 			p1.pawns[1].move(1);
// 			p1.evaluateMoves(5);
// 		}
// 		catch (Exception e) {
// 			throw e;
// 		}
// 	}
	
// 	@Test (expected = NoMovesAvaliableException.class)
// 	public void thereAreBarriersButNoMovesAvaliable() throws Exception{
// 		ExitTile t1 = new ExitTile(Color.red);
// 		Tile t2 = new RegularTile();
// 		t1.setNextTile(t2);
// 		Tile t3 = new RegularTile();
// 		t2.setNextTile(t3);
// 		Tile t4 = new RegularTile();
// 		t3.setNextTile(t4);
// 		Tile t5 = new RegularTile();
// 		t4.setNextTile(t5);
// 		Tile t6 = new RegularTile();
// 		t5.setNextTile(t6);
// 		Tile t7 = new RegularTile();
// 		t6.setNextTile(t7);
// 		Tile t8 = new RegularTile();
// 		t7.setNextTile(t8);
// 		Player p1 = new Player(Color.red, t1);
// 		try {
// 			p1.startPawn();
// 			p1.pawns[0].move(7);
// 			p1.startPawn();
// 			p1.pawns[1].move(7);
// 			p1.startPawn();
// 			p1.pawns[2].move(1);
// 			p1.startPawn();
// 			p1.pawns[3].move(1);
// 			p1.evaluateMoves(6);
// 		}
// 		catch (Exception e) {
// 			throw e;
// 		}
// 	}
	
// 	@Test (expected = NoMovesAvaliableException.class)
// 	public void noMovesAvaliableException() throws Exception {
// 		ExitTile t1 = new ExitTile(Color.red);
// 		Tile t2 = new RegularTile();
// 		t1.setNextTile(t2);
// 		Tile t3 = new RegularTile();
// 		t2.setNextTile(t3);
// 		Tile t4 = new RegularTile();
// 		t3.setNextTile(t4);
// 		Player p1 = new Player(Color.red, t1);
// 		Pawn pwn1 = new Pawn(Color.blue, t3);
// 		Pawn pwn2 = new Pawn(Color.blue, t3);
// 		pwn1.setIsInInitialTile(false);
// 		pwn2.setIsInInitialTile(false);
// 		t3.addPawn(pwn1);
// 		t3.addPawn(pwn2);
// 		try {
// 			p1.startPawn();
// 			p1.pawns[0].move(1);
// 			p1.startPawn();
// 			p1.pawns[1].move(1);
// 			p1.startPawn();
// 			p1.evaluateMoves(1);
// 		} catch (NoMovesAvaliableException e) {
// 			throw e;
// 		}
// 		catch (Exception e) {
// 			throw e;
// 		}
// 	}

// 	@Test (expected = NoMovesAvaliableException.class)
// 	public void noMovesAvaliableNullInPath() 
// 	throws Exception
// 	{
// 		ExitTile t1 = new ExitTile(Color.red);
// 		Tile t2 = new RegularTile();
// 		t1.setNextTile(t2);
// 		Tile t3 = new RegularTile();
// 		t2.setNextTile(t3);
// 		Tile t4 = new RegularTile();
// 		t3.setNextTile(t4);
// 		Player p1 = new Player(Color.red, t1);
// 		p1.startPawn();
// 		try {
// 			p1.evaluateMoves(5);
// 		} catch (Exception e) {
// 			throw e;
// 		}
// 	}
	
// 	@Test
// 	public void movesAvaliable() throws Exception
// 	{
// 		ExitTile t1 = new ExitTile(Color.red);
// 		Tile t2 = new RegularTile();
// 		t1.setNextTile(t2);
// 		Tile t3 = new RegularTile();
// 		t2.setNextTile(t3);
// 		Tile t4 = new RegularTile();
// 		t3.setNextTile(t4);
// 		Player p1 = new Player(Color.red, t1);
// 		p1.startPawn();
// 		try {
// 			Set<Pawn> pawns = p1.evaluateMoves(2);
// 			assertEquals(pawns.size(), 1);
// 			assertTrue(pawns.contains(p1.pawns[0]));
// 		} catch (Exception e) {
// 			throw e;
// 		}
// 	}
// }
