package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.Truncator;

public class HibernateBichoDAOTest {
	private Especie especie;
	private Bicho bichoOriginal;
	private HibernateBichoDAO bichoDAO;
	private Entrenador entrenador;
	
	@Before
	public void setUp(){
		this.especie= new Especie("EspecieTest", TipoBicho.AGUA);
		this.bichoOriginal = new Bicho(this.especie);
		this.entrenador= new Entrenador("EntrenadorTest");
		this.bichoDAO= new HibernateBichoDAO();
		this.entrenador.agregarBichoCapturado(bichoOriginal);
		this.bichoOriginal.setOwner(entrenador);
	}
	
	@After
	public void cleanUp() {
		Truncator.cleanUpTables();
	}
	
	@Test
	public void dadoUnBichoLoPersistoEnMiBBDDYLuegoLoRecuperoParaComprobarQueSeHallaPersistidoDeManeraCorrecto() {
		EntrenadorDAO entrenadorDAO = new HibernateEntrenadorDAO();
		
		Integer idBicho = 	Runner.runInSession(()->{
								entrenadorDAO.saveEntrenador(this.entrenador);
								this.bichoDAO.saveBicho(this.bichoOriginal);
								return bichoOriginal.getId();
							});
		
		Bicho bichoRecuperado =	Runner.runInSession(()->{
									return this.bichoDAO.getBicho(idBicho);
								});
		
		assertEquals(bichoRecuperado.getEspecie(), this.bichoOriginal.getEspecie());
		assertEquals(bichoRecuperado.getOwner().getNombre(), this.bichoOriginal.getOwner().getNombre());
		assertEquals(bichoRecuperado.getEnergia(), this.bichoOriginal.getEnergia());
		assertEquals(bichoRecuperado.getTiempoDesdeSuCaptura(), this.bichoOriginal.getTiempoDesdeSuCaptura());
	}
	
}