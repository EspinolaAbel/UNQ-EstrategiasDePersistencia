package ar.edu.unq.epers.bichomon.backend.service.cache;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.service.runner.CacheProvider;

public class EntrenadoresCampeonesCacheTest {

	private EntrenadoresCampeonesCache cache;

	@Before
	public void setUp() {
		this.cache = CacheProvider.getInstance().getEntrenadoresCampeonesCache();
		
		this.cache.put(Arrays.asList(new Entrenador("EntrenadorXTest"), new Entrenador("EntrenadorYTest"),
									 new Entrenador("EntrenadorZTest")));
	}
	
	@After
	public void cleanUp() {
		this.cache.clear();
	}

	
	@Test
	public void ingresoUnNuevoMapeoEnLaCacheYComprueboQueSeHayaPersistidoDeManeraCorrecta() {
		List<Entrenador> lsEntrenadores = this.cache.get();
		
		assertEquals( lsEntrenadores.size(), 3);
		assertTrue(lsEntrenadores.contains(new Entrenador("EntrenadorXTest")));
		assertTrue(lsEntrenadores.contains(new Entrenador("EntrenadorYTest")));
		assertTrue(lsEntrenadores.contains(new Entrenador("EntrenadorZTest")));
		assertFalse(lsEntrenadores.contains(new Entrenador("Inexistente")));
	}
	
	@Test
	public void notificoALaCacheQuePoseeDatosInconsistentesYEsteLosBorra(){
		assertEquals(this.cache.size(), 1);
		
		this.cache.datosInconsistentes();
		
		assertEquals(this.cache.size() , 0);
		assertNull(this.cache.get());
	}

}
