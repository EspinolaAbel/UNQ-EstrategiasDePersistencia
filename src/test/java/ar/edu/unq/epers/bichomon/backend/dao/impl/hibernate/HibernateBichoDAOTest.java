package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateBichoDAOTest {
	private Especie especie;
	private Bicho bicho;
	private HibernateBichoDAO hibernateBichoDAO;
	private Entrenador entrenador;
	private Lugar lugar;
	private Nivel nivel;
	@Before
	public void setUp(){
		this.especie= new Especie("lagartomon", TipoBicho.AGUA);
		this.bicho = new Bicho(this.especie);
		this.entrenador= new Entrenador("Pedro");
		this.hibernateBichoDAO= new HibernateBichoDAO();
		this.entrenador.agregarBichoCapturado(bicho);
		this.lugar= new Dojo("akura");
		this.bicho.setOwner(entrenador);
		this.entrenador.setUbicacionActual(lugar);
		this.nivel= new Nivel(2,1000,25);
	}
	
	
	@Test
	public void testGuardarUnBicho() {
		
		
		Runner.runInSession(()->{
			this.hibernateBichoDAO.saveBicho(this.bicho);
			
			return null;	
		
	});
	}	


	@Test
	public void testRecuperarUnBicho() {
	
		
		Runner.runInSession(()->{
			this.hibernateBichoDAO.saveBicho(this.bicho);
			Bicho bicho= this.hibernateBichoDAO.getBicho(1);
			assertEquals (bicho.getId(),1);
			assertEquals (bicho.getOwner(),entrenador);
			
			
			return null;	
		
	});
	}	

	
	
}