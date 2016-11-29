package ar.edu.unq.epers.bichomon.backend.service.cache;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.service.runner.CacheProvider;

public class CantidadEntrenadoresCacheTest {

	private CantidadEntrenadoresCache cache;

	@Before
	public void setUp() {
		this.cache = CacheProvider.getInstance().getCantidadEntrenadoresCache();
		
		this.cache.put("LugarXTest", 10);
		this.cache.put("LugarYTest", 5);
		this.cache.put("LugarZTest", 0);
	}
	
	@After
	public void cleanUp() {
		this.cache.clear();
	}

	
	@Test
	public void ingresoUnNuevoMapeoEnLaCacheYComprueboQueSeHayaPersistidoDeManeraCorrecta() {
		Integer cantRecuperada = this.cache.get("LugarXTest");
		
		assertEquals( (Integer)10, cantRecuperada);
	}
	
	@Test
	public void notificoALaCacheQuePoseeDatosInconsistentesYEsteLosBorra(){
		assertEquals((Integer)this.cache.size() , (Integer)3);
		
		this.cache.datosInconsistentes("LugarZTest");
		
		assertEquals((Integer)this.cache.size() , (Integer)2);
		assertNull(this.cache.get("LugarZTest"));
	}

}
