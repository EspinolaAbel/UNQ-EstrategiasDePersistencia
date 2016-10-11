package ar.edu.unq.epers.bichomon.backend.model.lugar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;

public class GuarderiaTest {
	
	Guarderia guarderia;

	@Before
	public void setUp() throws Exception {
		this.guarderia = new Guarderia("GuarderiaGenerica");
	}

	/** Dados una guarderia y un bicho que será abandonado en esta ubicación, se abandona dicho bicho en 
	 * la guardería y luego queda guardado en la lista de bichos abandonados. */
	@Test
	public void dadoUnaGuarderiaAbandonoUnBichoEnEsteLugarYLaGuarderiaLoGuardaEnSuListaDeBichosAbandonados() {
		Bicho bichoAAbandonar = new Bicho(new Especie());
		this.guarderia.recibirBichoAbandonado(bichoAAbandonar);
		
		assertTrue(this.guarderia.getBichosAbandonados().contains(bichoAAbandonar));
	}

	
	/** Dada una guarderia con bichos abandonados, obtengo un bicho abandonado al azar y compruebo que
	 * este ya no pertenezca a la lista de bichos abandonados del lugar. */
	@Test
	public void dadaUnaGuarderiaObtengoUnBichoQueHayaSidoAbandonadoEnEsteLugarYCheckeoQueHayaSidoEliminadoDeLaListaDeBichosAbandonados(){
		int i;
		for(i=1; i<=5; i++) {
			Bicho b = new Bicho(new Especie());
			b.setId(i);
			guarderia.recibirBichoAbandonado(b);
		}
			
		Bicho bichoObtenido = guarderia.retornarUnBichoDelLugar();
		
		assertFalse(guarderia.getBichosAbandonados().contains(bichoObtenido));
	}
	
}
