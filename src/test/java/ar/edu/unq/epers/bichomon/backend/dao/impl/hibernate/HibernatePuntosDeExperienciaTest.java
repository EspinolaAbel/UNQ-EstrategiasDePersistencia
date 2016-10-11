package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.PuntosDeExperiencia;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.Truncator;

public class HibernatePuntosDeExperienciaTest {

	private PuntosDeExperiencia evolucion, duelo,captura;
	
	
	@Before
	public void setUp(){
		
		this.evolucion= new PuntosDeExperiencia("Evolucion",5);
		this.duelo= new PuntosDeExperiencia("Duelo",10);
		this.captura= new PuntosDeExperiencia("CapturarBicho",10);
		
	}
	
	

	@After
	public void cleanUp() {
		Truncator.cleanUpTables();
	}
	
	/**
	 * Test para persiistir las tres clases de prueba y ver que se  genero la tabala en la BBDD
	 * 
	 */
	@Test
	public void testPersistirPuntajes() {
		HibernatePuntosDeExperienciaDAO puntajesDAO= new HibernatePuntosDeExperienciaDAO();
		
		Runner.runInSession(()->{
				puntajesDAO.savePuntosDeExperiencia(this.captura);
				puntajesDAO.savePuntosDeExperiencia(this.evolucion);
				puntajesDAO.savePuntosDeExperiencia(this.duelo);
			return null;
		});
	
		
		Runner.runInSession(()->{
			PuntosDeExperiencia	evolucionRecuperada =puntajesDAO.getPuntosDeExperiencia("Evolucion");
			PuntosDeExperiencia capturaRecuperada=puntajesDAO.getPuntosDeExperiencia("CapturarBicho");
			PuntosDeExperiencia dueloRecuperado=puntajesDAO.getPuntosDeExperiencia("Duelo");
			assertEquals(5,evolucionRecuperada.getPuntaje());
			assertEquals(10,capturaRecuperada.getPuntaje());
			assertEquals(10,dueloRecuperado.getPuntaje());
			return null;
		});
		
		
	}

}
