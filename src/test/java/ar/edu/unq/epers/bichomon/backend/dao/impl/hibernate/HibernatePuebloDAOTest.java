package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.lugar.EspecieConProbabilidad;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;

public class HibernatePuebloDAOTest  {

	private List<EspecieConProbabilidad> especies= new ArrayList<EspecieConProbabilidad>();
	private Pueblo pueblo;
	private HibernatePuebloDAO puebloDAO;
	private EspecieConProbabilidad esp; 
	@Before
	public void setUp() {
		
		Especie e = new Especie("EspecieTest1", TipoBicho.AGUA);
		esp= new EspecieConProbabilidad(e,13);
		especies.add(esp);
		e = new Especie("EspecieTest2", TipoBicho.AIRE);
		esp= new EspecieConProbabilidad(e,1);
		especies.add(esp);
		
		this.pueblo = new Pueblo("PuebloTest");
		this.pueblo.setEspecies(especies);//agrego las especies  con  probabilidad
		
		this.puebloDAO = new HibernatePuebloDAO();
		
		
		
	}
	
	@After
	public void reiniciarBD() {
		SessionFactoryProvider.destroy();
	}

	@Test
	public void dadoUnPuebloLoGuardoEnLaBBDD() {
		Runner.runInSession(() -> {
			this.puebloDAO.savePueblo(this.pueblo);
			return null;
		});
	}


	
	
	
	

}
