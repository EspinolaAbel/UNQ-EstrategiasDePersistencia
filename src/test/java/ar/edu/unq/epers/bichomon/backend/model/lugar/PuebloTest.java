package ar.edu.unq.epers.bichomon.backend.model.lugar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;

public class PuebloTest {

	private Pueblo pueblo;

	@Before
	public void setUp() throws Exception {
		this.pueblo = new Pueblo("PuebloGenerico"); 
	}

	
	/** Dado un pueblo intento abandonar un bicho en él. Como en este lugar no se permite abandonar
	 * bichos se lanza una UbicacionIncorrectaException. */
	@Test
	public void dadoUnPuebloIntentoAbandonarUnBichoYSeLanzaUnaUbicacionIncorrectaException() {
		Bicho bicho = new Bicho(new Especie());
		
		try {
			pueblo.recibirBichoAbandonado(bicho);
			fail("Nunca se lanzó la exception...");
		}
		catch(UbicacionIncorrectaException e) {
			assertEquals(e.getMessage(), "En el lugar \""+pueblo.getNombre()+"\" no se permite abandonar bichos.");
		}
	}
}
