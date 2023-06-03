package Model;

import static org.junit.Assert.*;

import org.junit.Test;

public class StartPawnTest {

	@Test (expected = PawnCapturedException.class)
	public void startPawnPossibleWithCapture() throws Exception {
		ExitTile t1 = new ExitTile(Color.red);
		Tile t2 = new RegularTile();
		t1.setNextTile(t2);
		Tile t3 = new RegularTile();
		t2.setNextTile(t3);
		Tile t4 = new RegularTile();
		t3.setNextTile(t4);
		Player p1 = new Player(Color.red, t1);
		assertEquals(p1.startPawn(), true);
		try {
			p1.pawns[0].move(3);
		} catch (Exception e) {
			throw e;
		}
		System.out.println(t1.numPawns);
		assertEquals(p1.startPawn(), true);
		System.out.println(t1.numPawns);
		assertFalse(p1.pawns[0].getIsInInitialTile());
		try {
			p1.pawns[1].move(3);
			System.out.println(t1.numPawns);
			t1.addPawn(new Pawn(Color.blue, t1));
			System.out.println(t1.numPawns);
		} catch (Exception e) {
			throw e;
		}
		p1.startPawn();
	}
	
	@Test
	public void startPawnImpossibleExitTileFull() throws Exception{
		ExitTile t1 = new ExitTile(Color.red);
		Tile t2 = new RegularTile();
		t1.setNextTile(t2);
		Player p1 = new Player(Color.red, t1);
		p1.startPawn();
		assertFalse(p1.startPawn());
		try {
			p1.pawns[0].move(1);
		} catch (Exception e) {
			throw e;
		}
		assertTrue(p1.startPawn());
	}
	
	@Test
	public void startPawnImpossibleNoPawnsToStart() throws Exception {
		ExitTile t1 = new ExitTile(Color.red);
		Tile t2 = new RegularTile();
		t1.setNextTile(t2);
		Tile t3 = new RegularTile();
		t2.setNextTile(t3);
		Tile t4 = new RegularTile();
		t3.setNextTile(t4);
		Player p1 = new Player(Color.red, t1);
		try {
			p1.startPawn();
			p1.pawns[0].move(3);
			p1.startPawn();
			p1.pawns[1].move(3);
			p1.startPawn();
			p1.pawns[2].move(2);
			p1.startPawn();
			p1.pawns[3].move(2);
		} catch (Exception e) {
			throw e;
		}
		assertFalse(p1.startPawn());
	}

}
