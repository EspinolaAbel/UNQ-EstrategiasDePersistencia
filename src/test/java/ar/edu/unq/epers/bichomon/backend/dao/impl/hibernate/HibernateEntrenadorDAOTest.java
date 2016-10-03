package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.NivelDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;

public class HibernateEntrenadorDAOTest {
	private Entrenador entrenadorOriginal;
	private HibernateEntrenadorDAO entrenadorDAO;
	private Nivel nivel;
	private Lugar lugar;
	private LugarDAO lugarDAO;
	private NivelDAO nivelDAO;
	
	@Before
	public void setUp(){
		this.entrenadorOriginal = new Entrenador("EntrenadorTest");
		this.entrenadorOriginal.setExperiencia(999);
		this.nivel = new Nivel(1, 999, 999);
		this.entrenadorOriginal.setNivelActual(this.nivel);
		this.lugar = new Guarderia("GuarderiaTest"); 
		this.entrenadorOriginal.setUbicacionActual(lugar);
		this.entrenadorOriginal.agregarBichoCapturado(new Bicho());
		this.entrenadorOriginal.agregarBichoCapturado(new Bicho());
		this.entrenadorDAO= new  HibernateEntrenadorDAO();
		this.lugarDAO = new HibernateLugarDAO();
		this.nivelDAO = new HibernateNivelDAO();
	}
	
	@After
	public void reiniciarBD() {
		SessionFactoryProvider.destroy();
	}
	
	/**
	 * Test para Entrenador dao, los tres metodos se testeean juntos,
	 * save entrenador
	 * getEntrenador
	 * getAllEntrenadores
	 */
	@Test
	public void dadoUnEntrenadorLoPersistoEnLaBBDDYLuegoLoRecuperoParaComprobarQueSeHayaPersistidoDeManeraCorrecta() {
		Runner.runInSession(() -> {
			this.lugarDAO.saveLugar(this.lugar);
			this.nivelDAO.saveNivel(nivel);
			this.entrenadorDAO.saveEntrenador(entrenadorOriginal);
			return null;
		});
		
		Entrenador entrenadorRecuperado =
					Runner.runInSession(() -> {
						return this.entrenadorDAO.getEntrenador("EntrenadorTest");
					});
		
		assertEquals(this.entrenadorOriginal.getNombre(), entrenadorRecuperado.getNombre());
		assertTrue(this.entrenadorOriginal.getBichosCapturados().containsAll( entrenadorRecuperado.getBichosCapturados() ));
		assertEquals(this.entrenadorOriginal.getExperiencia(), entrenadorRecuperado.getExperiencia());
		assertEquals(	this.entrenadorOriginal.getNivelActual().getNumeroDeNivel(),
						entrenadorRecuperado.getNivelActual().getNumeroDeNivel());
		assertEquals(	this.entrenadorOriginal.getUbicacionActual().getNombre(),
						entrenadorRecuperado.getUbicacionActual().getNombre());
	}

}
