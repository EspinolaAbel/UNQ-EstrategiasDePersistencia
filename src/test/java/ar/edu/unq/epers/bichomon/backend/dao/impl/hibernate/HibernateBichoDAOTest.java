package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateBichoDAOTest {
	private Especie especie;
	private Bicho bicho;
	private HibernateBichoDAO hibernateBichoDAO;
	@Before
	public void setUp(){
		this.especie= new Especie("lagartomon", TipoBicho.AGUA);
		this.bicho = new Bicho(this.especie);
		this.hibernateBichoDAO= new HibernateBichoDAO();
		
		
	}
	
	
	@Test
	public void testGuardarUnBicho() {
		
		
		Runner.runInSession(()->{
			this.hibernateBichoDAO.saveBicho(this.bicho);
			return null;	
		
	});
	}	
		
}