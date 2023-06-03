package Model;
import static org.junit.Assert.*;

import org.junit.Test;

public class PawnInFinalTileExceptionTest {

    @Test
    public void testConstructor() {
        Color color = Color.red;
        PawnInFinalTileException exception = new PawnInFinalTileException(color);
        assertEquals(String.format("Peão do jogador %s atingiu a casa final", color), exception.getMessage());
    }
}
