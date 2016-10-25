package ar.edu.unq.epers.bichomon.backend.model.lugar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
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
			//pongo un nombre de ultimo dueño
			b.setIdUltimoDueño("Ninguno");
			b.setId(i);
			guarderia.recibirBichoAbandonado(b);
		}
		Entrenador entrenadorTest = new Entrenador("entrenadorDeTest");	
		Bicho bichoObtenido = guarderia.retornarUnBichoDelLugar(entrenadorTest);
		
		assertFalse(guarderia.getBichosAbandonados().contains(bichoObtenido));
	}
	
	/** Dada una un Entrenador y una guarderia con bichos abandonados,los cuales fuero suyos con anterioridad
	 *  pidoUnbichoDelLugar y compruebo que no me devuelve ninguno. */
	@Test
	public void dadaUnaGuarderiaYUnEntrenadorNoObtengoUnBichoAbandonadoEnEsteLugarPorQiueTodosLePertenecieronAnteriormente(){
		int i;
		for(i=1; i<=5; i++) {
			Bicho b = new Bicho(new Especie());
			//pongo un nombre de ultimo dueño
			b.setIdUltimoDueño("entrenadorDeTest");
			b.setId(i);
			guarderia.recibirBichoAbandonado(b);
		}
		Entrenador entrenadorTest = new Entrenador("entrenadorDeTest");	
		Bicho bichoObtenido = guarderia.retornarUnBichoDelLugar(entrenadorTest);
		
		assertNull(bichoObtenido);
	}

	/** Dada una un Entrenador y una guarderia con bichos abandonados,los cuales fuero suyos con anterioridad, 
	 * salvo el ultimo, pidoUnbichoDelLugar y compruebo que me devuelve el ultimo con id 5. */
	@Test
	public void dadaUnaGuarderiaYUnEntrenadorObtengoElUnicoBichoAbandonadoEnEsteLugarPorQiueElRestoLePertenecieronAnteriormente(){
		int i;
		for(i=1; i<=5; i++) {
			Bicho b = new Bicho(new Especie());
			//pongo un nombre de ultimo dueño
			b.setIdUltimoDueño("entrenadorDeTest");
			b.setId(i);
			if (i==5)
				{b.setIdUltimoDueño("ninguno");}
			guarderia.recibirBichoAbandonado(b);
		}
		Entrenador entrenadorTest = new Entrenador("entrenadorDeTest");	
		Bicho bichoObtenido = guarderia.retornarUnBichoDelLugar(entrenadorTest);
		
		assertNotNull(bichoObtenido);
		assertEquals(bichoObtenido.getId(),5);
	}

	
}
