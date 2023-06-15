// package Model;

// import org.junit.Test;
// import static org.junit.Assert.*;
// import java.util.*;

// public class TileTest {
	
	
// 	//testing getCurrPawnsAsArray()
// 	@Test 
// 	public void testGetCurrPawnsAsArray() {
// 		Tile t = new RegularTile(); //chose Regular randomly because Tile is abstract
// 		Set<Pawn> currPawns = new HashSet<>();
//         Pawn pawn1 = new Pawn(Color.blue, t);
//         Pawn pawn2 = new Pawn(Color.red, t);
//         Pawn pawn3 = new Pawn(Color.yellow, t);
//         currPawns.add(pawn1);
//         currPawns.add(pawn2);
//         currPawns.add(pawn3);
//         t.numPawns = 3;
//         t.currPawns = currPawns;
        
//         //testing the return of array
//         Pawn p[] = t.getCurrPawnsAsArray();
        
//         assertEquals(3,p.length);
        
// 	}
	
	
// 	//testing RemovePawn()
// 	@Test
// 	public void testRemovePawn() {
// 			Tile t = new RegularTile(); //chose Regular randomly because Tile is abstract
// 			Set<Pawn> currPawns = new HashSet<>();
// 	        Pawn pawn1 = new Pawn(Color.red, t);
// 	        Pawn pawn2 = new Pawn(Color.red, t);
// 	        Pawn pawn3 = new Pawn(Color.red, t);
// 	        currPawns.add(pawn1);
// 	        currPawns.add(pawn2);
// 	        currPawns.add(pawn3);
// 			t.currPawns = currPawns;
// 			t.numPawns = currPawns.size();
// 			//test function
// 	        t.removePawn(pawn2);

// 	        //see if it was removed
// 	        assertFalse(currPawns.contains(pawn2));
	        
// 	        //test set size
// 	        assertEquals(2, t.numPawns);
// 	        //test other elements are in
// 	        assertTrue(currPawns.contains(pawn1));
// 	        assertTrue(currPawns.contains(pawn3));
// 	}
	
// 	//testing isBarrier()
	
// 	@Test
// 	public void testIsBarrierTrue() {
// 		 Tile t = new RegularTile(); //chose Regular randomly because Tile is abstract

// 		Set<Pawn> currPawns = new HashSet<>();
//         Pawn pawn1 = new Pawn(Color.blue, t);
//         Pawn pawn2 = new Pawn(Color.red, t);
//         Pawn pawn3 = new Pawn(Color.blue, t);
//         currPawns.add(pawn1);
//         currPawns.add(pawn2);
//         currPawns.add(pawn3);
//         t.numPawns = currPawns.size();
// 		t.currPawns = currPawns;
// 		assertTrue(t.isBarrier());
		
// 	}
	
// 	public void testIsBarrierFalse() {
// 		 Tile t = new RegularTile(); //chose Regular randomly because Tile is abstract

// 		Set<Pawn> currPawns = new HashSet<>();
//        Pawn pawn1 = new Pawn(Color.blue, t);
//        Pawn pawn2 = new Pawn(Color.red, t);
//        Pawn pawn3 = new Pawn(Color.yellow, t);
//        currPawns.add(pawn1);
//        currPawns.add(pawn2);
//        currPawns.add(pawn3);
//        t.numPawns = currPawns.size();
//        t.currPawns = currPawns;
// 	   assertFalse(t.isBarrier());
		
// 	}
	
// 	//testing getDuplicates()
// 	@Test
// 	public void testGetDuplicatesTrue() {
// 	    Tile t = new RegularTile(); //chose Regular randomly because Tile is abstract

// 		// create an array with duplicate elements
//         Object[] array = {2, 2, 3, 4, 5};


//         // getDuplicates() should return true
//         assertTrue(t.getDuplicates(array));
// 	}
	
// 	@Test
// 	public void testGetDuplicatesFalse() {
// 	    Tile t = new RegularTile(); //chose Regular randomly because Tile is abstract

// 		// create an array without duplicate elements
//         Object[] array2 = {1, 8, 3, 4, 5};

//         // getDuplicates() should return false
//         assertFalse(t.getDuplicates(array2));
// 	}
	
	
// }
