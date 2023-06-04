// package Model;
// import org.junit.Test;
// import static org.junit.Assert.*;
// import java.util.*;

// public class ExitTileTest {

// 	@Test
// 	public void testCanAddPawnTrueEmpty() {
// 		ExitTile t = new ExitTile(Color.blue);
// 		t.numPawns = 0;
//         Pawn p = new Pawn(Color.blue, t);
// 		t.canAddPawn(p);
// 		assertTrue(t.canAddPawn(p));
		
// 	}
	
// 	@Test
// 	public void testCanAddPawnTrueSameHouseColor() {
// 		ExitTile t = new ExitTile(Color.blue);
// 		Set<Pawn> currPawns = new HashSet<>();
//         t.currPawns = currPawns;
// 		t.numPawns = 1;
//         Pawn p1 = new Pawn(Color.blue, t);
//         currPawns.add(p1);
        
//         Pawn p2 = new Pawn(Color.red, t);
//         Pawn p3 = new Pawn(Color.blue, t);

//         //can add a pawn of different color, but cannot add a pawn of same color
//         assertTrue(t.canAddPawn(p2));
//         assertFalse(t.canAddPawn(p3));

        
// 	}
	
// 	@Test
// 	public void testCanAddPawnTrueDifferentHouseColor() {
// 		ExitTile t = new ExitTile(Color.red);
// 		Set<Pawn> currPawns = new HashSet<>();
//         t.currPawns = currPawns;
// 		t.numPawns = 1;
//         Pawn p1 = new Pawn(Color.blue, t);
//         currPawns.add(p1);
        
//         Pawn p2 = new Pawn(Color.red, t);
//         Pawn p3 = new Pawn(Color.blue, t);

//         //can add a pawn with the same color as house and different color as current pawn
//         assertTrue(t.canAddPawn(p2));
//         //cannot add pawn with same color as current pawn and different than house color.
//         assertFalse(t.canAddPawn(p3));
 
// 	}
	
// 	@Test
// 	public void testAddPawn() {
// 		ExitTile t = new ExitTile(Color.red);
// 		Set<Pawn> currPawns = new HashSet<>();
//         Pawn p1 = new Pawn(Color.blue, t);
//         currPawns.add(p1);
//         Pawn p2 = new Pawn(Color.red, t);

// 		try {
// 			t.addPawn(p2);
// 		}
// 		catch (Exception e) {
// 			fail("Unexpected exception");
// 		}
// 		try {
// 			t.addPawn(p1);
// 		}
// 		catch (Exception e) {
// 			fail("Unexpected exception");
// 		}
		
// 		assertEquals(2,t.currPawns.size());
// 		assertTrue(t.currPawns.contains(p2));
		
// 	}
	
// 	@Test
// 	public void testAddPawnThrowing() {
// 		ExitTile t = new ExitTile(Color.red);
// 		Set<Pawn> currPawns = new HashSet<>();
//         t.currPawns = currPawns;
// 		t.numPawns = 2;
//         Pawn p1 = new Pawn(Color.blue, t);
//         currPawns.add(p1);
        
//         Pawn p2 = new Pawn(Color.red, t);
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
