// package Model;

// import static org.junit.Assert.*;

// import org.junit.Test;

// public class PawnCapturedExceptionTest {

//     @Test
//     public void testGetCapturedPawn() {
//         Pawn p = new Pawn(Color.red, new RegularTile());
//         PawnCapturedException ex = new PawnCapturedException(p);
//         assertEquals(p, ex.getCapturedPawn());
//     }

//     @Test
//     public void testExceptionMessage() {
//         Pawn p = new Pawn(Color.blue, new RegularTile());
//         PawnCapturedException ex = new PawnCapturedException(p);
//         String expectedMsg = String.format("Peão do jogador %s capturado", p.getColor());
//         assertEquals(expectedMsg, ex.getMessage());
//     }
// }