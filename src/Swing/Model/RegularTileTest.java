// package Model;

// import org.junit.Test;
// import static org.junit.Assert.*;
// import java.util.*;

// public class RegularTileTest {
	
// 	//test canAddPawn
// 	@Test
// 	public void testCanAddPawnTrue() {
// 		Tile t = new RegularTile();
// 		t.numPawns = 0;
//         Pawn p = new Pawn(Color.blue, t);
// 		t.canAddPawn(p);
// 		assertTrue(t.canAddPawn(p));
		
// 	}
	
// 	@Test
// 	public void testCanAddPawnFalse() {
// 		Tile t = new RegularTile();
// 		t.numPawns = 2;
//         Pawn p = new Pawn(Color.blue, t);
// 		t.canAddPawn(p);
// 		assertFalse(t.canAddPawn(p));
		
// 	}
	
// 	@Test
// 	public void testAddPawnEmpty() {
// 		Tile t = new RegularTile();
// 		t.numPawns = 0;
// 		Set<Pawn> currPawns = new HashSet<>();
//         Pawn p = new Pawn(Color.blue, t);
//         t.currPawns = currPawns;
        
// 		try {
// 			t.addPawn(p);
			
			
// 		}
// 		catch (Exception e){
// 			fail("Unexpected exception: " + e.getMessage());
// 		}
		
// 		assertTrue(t.currPawns.contains(p));
// 		assertEquals(1,currPawns.size());
// 	}
	
// 	@Test
// 	public void testAddPawnCaptured() {
// 		Tile t = new RegularTile();
// 		t.numPawns = 1;
// 		Set<Pawn> currPawns = new HashSet<>();
//         Pawn p1 = new Pawn(Color.blue, t);
//         Pawn p2 = new Pawn(Color.red, t);
//         t.currPawns = currPawns;

//         t.currPawns.add(p1);
        
// 		try {
// 			t.addPawn(p2);
// 		}
// 		catch (Exception e){
// 			assertTrue(e instanceof PawnCapturedException);
// 		}
		
// 		assertFalse(t.currPawns.contains(p1));
// 		assertTrue(t.currPawns.contains(p2));
// 		assertEquals(1,currPawns.size());
// 		assertFalse(t.isBarrier);
		
// 	}
	
// 	@Test
// 	public void testAddPawnIsBarrier() {
// 		Tile t = new RegularTile();
// 		Set<Pawn> currPawns = new HashSet<>();
// 		t.isBarrier = true;
// 		t.numPawns = 2;
//         t.currPawns = currPawns;
//         Pawn p3 = new Pawn(Color.blue, t);
	
// 		try {
// 			t.addPawn(p3);
// 		}
		
// 		catch (Exception e){
// 			assertTrue(e instanceof MoveImpossibleException);
// 		}
		
// 	}
	
// 	@Test
// 	public void testAddPawnBecomeBarrier() {
		
// 		Tile t = new RegularTile();
// 		t.numPawns = 1;
// 		Set<Pawn> currPawns = new HashSet<>();
//         Pawn p1 = new Pawn(Color.blue, t);
//         Pawn p2 = new Pawn(Color.blue, t);
//         currPawns.add(p1);
//         t.currPawns = currPawns;
        
//         try {
// 			t.addPawn(p2);
// 		}
		
// 		catch (Exception e){
// 			fail("Unexpected exception: " + e.getMessage());
// 		}
        
        
// 		assertTrue(t.isBarrier);
// 		assertTrue(t.currPawns.contains(p2));
// 		assertEquals(2,t.currPawns.size());


// 	}
	
// }
