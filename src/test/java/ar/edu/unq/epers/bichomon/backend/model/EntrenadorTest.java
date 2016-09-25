package ar.edu.unq.epers.bichomon.backend.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EntrenadorTest {

	private Entrenador entrenador;
	private Bicho bichoGenerico;

	@Before
	public void setUp() {
		entrenador = new Entrenador();
		bichoGenerico = new Bicho(new Especie(), "Especie generica");
	}

	
	/** Dado un bicho lo agrego a la lista de bichos del entrenador.
	 * @author ae */
	@Test
	public void dadoUnBichoLoAgregoALaListaDeBichosCapturadosDelEntrenadorYLoAgrega() {
		entrenador.agregarBichoCapturado(bichoGenerico);
		
		assertTrue(entrenador.getBichosCapturados().contains(bichoGenerico));
		assertEquals(bichoGenerico.getOwner(), entrenador);
	}
	
	/** Dado un bicho que fue capturado por el entrenador, el entrenador lo descarta y compruebo que
	 *  - el entrenador ya no tenga dicho bicho en su lista de bichos capturados y;
	 *  - el bicho descartado ya no tenga entrenado (entrenador == null).
	 * @author ae **/
	@Test
	public void dadoUnBichoQueFueCapturadoPorElEntrenadorEsDescartado() {
		entrenador.agregarBichoCapturado(bichoGenerico);
		
		entrenador.descartarBichoCapturado(bichoGenerico);
		
		assertFalse(entrenador.getBichosCapturados().contains(bichoGenerico));
		assertNull(bichoGenerico.getOwner());
	}
	
	

}
