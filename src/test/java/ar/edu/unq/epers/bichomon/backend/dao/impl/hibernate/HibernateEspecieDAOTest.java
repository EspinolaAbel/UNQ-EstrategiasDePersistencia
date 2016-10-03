package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.CondicionDeEvolucionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;

public class HibernateEspecieDAOTest {
	
	private EspecieDAO especieDAO;
	private CondicionDeEvolucionDAO condicionDAO;
	private Especie especieOriginal;
	
	private CondicionDeEvolucion condicionEnergia, condicionEdad;

	@Before
	public void setUp() {
		especieDAO = new HibernateEspecieDAO();
		condicionDAO = new HibernateCondicionDeEvolucionDAO();
		
		this.especieOriginal = new Especie("EspecieTest", TipoBicho.AGUA);
		
		this.condicionEnergia = new CondicionBasadaEnEnergia(999);
		this.condicionEdad = new CondicionBasadaEnEdad(888);
		
		this.especieOriginal.agregarCondicionDeEvolucion(condicionEnergia);
		this.especieOriginal.agregarCondicionDeEvolucion(condicionEdad);
		
		this.especieOriginal.setRaiz(new Especie("RaizTest", TipoBicho.AGUA));
		this.especieOriginal.setEvolucionaA(new Especie("EvolucionTest", TipoBicho.AGUA));
	}
	
	@After
	public void reiniciarBD() {
		SessionFactoryProvider.destroy();
	}

	@Test
	public void dadaUnaEspecieLaPersistoEnMiBBDDUtilizandoHibernateYLuegoLaRecuperoYComprueboQueSeHayaPersistidoDeManeraCorrecta() {

		Runner.runInSession(() -> {
			this.condicionDAO.saveCondicion(condicionEdad);
			this.condicionDAO.saveCondicion(condicionEnergia);
			this.especieDAO.saveEspecie(this.especieOriginal);
			return null;
		});
		
		Especie especieRecuperada = Runner.runInSession(() -> {
			Especie especie = especieDAO.getEspecie("EspecieTest");
			return especie;
		});
		
		assertEquals(especieRecuperada.getNombre(), "EspecieTest");
		assertEquals(especieRecuperada.getTipo(), TipoBicho.AGUA);
		assertTrue(especieRecuperada.getCondicionesDeEvolucion().contains(this.condicionEnergia));
		assertTrue(especieRecuperada.getCondicionesDeEvolucion().contains(this.condicionEdad));
		assertEquals(especieRecuperada.getEvolucionaA().getNombre(), "EvolucionTest");
		assertEquals(especieRecuperada.getRaiz().getNombre(), "RaizTest");
	}
	
	/** Dada una especie, la persisto en mi base de datos. Luego modifico dicha especie y la guardo nuevamente.
	 * Este test fallará en caso que la segunda vez que intento guardarla hibernate intenta ejecutar un INSERT en vez de un UPDATE.
	 * Si se intenta INSERT dos veces sobre la misma especie se lanzará una excepción debido a que intento guardar una especie que ya existe
	 * en mi BBDD.*/
	@Test
	public void dadaUnaEspecieLaPersistoEnMiBBDDYLuegoLaModificoEIntentoPersistirlaNuevamenteParaActualizarla() {
		Runner.runInSession(() -> {
			this.condicionDAO.saveCondicion(condicionEdad);
			this.condicionDAO.saveCondicion(condicionEnergia);
			this.especieDAO.saveEspecie(this.especieOriginal);
			return null;
		});
		
		this.especieOriginal.setCantidadBichos(999);
		
		Runner.runInSession(() -> {
			this.especieDAO.saveEspecie(this.especieOriginal);
			return null;
		});
	}

}
