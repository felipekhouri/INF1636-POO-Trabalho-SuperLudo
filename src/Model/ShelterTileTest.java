// package Model;
// import org.junit.Test;

// import static org.junit.Assert.*;
// import java.util.*;

// public class ShelterTileTest {
// 	@Test
// 	public void testCanAddPawnTrueEmpty() {
// 		Tile t = new ShelterTile();
// 		t.numPawns = 0;
//         Pawn p = new Pawn(Color.blue, t);
// 		t.canAddPawn(p);
// 		assertTrue(t.canAddPawn(p));
		
// 	}
// 	@Test

// 	public void testCanAddPawnTrue() {
// 		Tile t = new ShelterTile();
// 		t.numPawns = 1;
//         Pawn p = new Pawn(Color.blue, t);
// 		t.canAddPawn(p);
// 		assertTrue(t.canAddPawn(p));
		
// 	}
	
// 	@Test
// 	public void testCanAddPawnFalse() {
// 		Tile t = new ShelterTile();
// 		t.numPawns = 2;
//         Pawn p = new Pawn(Color.blue, t);
// 		t.canAddPawn(p);
// 		assertFalse(t.canAddPawn(p));
		
// 	}
	
// 	@Test
// 	public void testAddPawn() {
// 		Tile t = new ShelterTile();
// 		Set<Pawn> currPawns = new HashSet<>();
//         t.currPawns = currPawns;
// 		t.numPawns = 1;
//         Pawn p1 = new Pawn(Color.blue, t);
//         currPawns.add(p1);
        
//         Pawn p2 = new Pawn(Color.blue, t);

// 		try {
// 			t.addPawn(p2);
// 		}
// 		catch (Exception e) {
// 			fail("Unexpected exception");
// 		}
		
// 		assertEquals(2,t.currPawns.size());
// 		assertTrue(t.currPawns.contains(p2));
		
// 	}
	
// 	@Test
// 	public void testAddPawnThrowing() {
// 		Tile t = new ShelterTile();
// 		Set<Pawn> currPawns = new HashSet<>();
//         t.currPawns = currPawns;
// 		t.numPawns = 2;
//         Pawn p1 = new Pawn(Color.blue, t);
//         currPawns.add(p1);
        
//         Pawn p2 = new Pawn(Color.blue, t);
//         currPawns.add(p2);
        
//         Pawn p3 = new Pawn(Color.blue, t);


// 		try {
// 			t.addPawn(p3);
// 		}
// 		catch (Exception e){
// 			assertTrue(e instanceof MoveImpossibleException);
// 		}
		
// 		assertEquals(2,t.currPawns.size());
// 		assertFalse(t.currPawns.contains(p3));
		
// 	}
	
// }
