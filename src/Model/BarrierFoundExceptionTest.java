package Model;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

public class BarrierFoundExceptionTest {

	@Test
	public void test() {
		
		// Criando um conjunto de Peões
		Set<Pawn> pawns = new HashSet<Pawn>();
        pawns.add(new Pawn(Color.red, new RegularTile())); // Adicionando um Peão vermelho ao conjunto
        pawns.add(new Pawn(Color.red, new RegularTile())); // Adicionando outro Peão vermelho ao conjunto
        
        // Definindo quantas casas foram movidas antes de encontrar a barreira
        int tilesMoved = 2;
        
        // Criando uma instância da exceção BarrierFoundException, passando o conjunto de Peões e as casas movidas como argumentos
        BarrierFoundException ex = new BarrierFoundException(pawns, tilesMoved);
        
        // Verificando se a mensagem de erro gerada pela exceção está correta
        assertEquals("Você tirou 2 e tem uma barreira", ex.getMessage());
        
        // Verificando se o número de Peões retornado pela exceção está correto
        assertEquals(2, ex.getNumPawns());
        
        // Verificando se o conjunto de Peões retornado pela exceção é igual ao conjunto de Peões criado anteriormente
        assertTrue(pawns.equals(ex.getPawnsInBarrier()));
	}
}
