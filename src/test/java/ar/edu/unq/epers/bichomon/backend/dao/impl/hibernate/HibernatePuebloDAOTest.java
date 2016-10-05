package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieConProbabilidadDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.PuebloDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.lugar.EspecieConProbabilidad;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;

public class HibernatePuebloDAOTest  {


private Pueblo pueblo;
private PuebloDAO puebloDAO;
private EspecieConProbabilidadDAO especieConProbabilidadDAO;

private List <EspecieConProbabilidad> especies;
private EspecieConProbabilidad esp;
private EspecieDAO especieDAO;
 private Especie e ;

@Before
public void setUp() {
	
	especieConProbabilidadDAO= new HibernateEspecieConProbabilidadDAO();
	especieDAO= new HibernateEspecieDAO();
	especies= new ArrayList<EspecieConProbabilidad>();
	

	e = new Especie("EspecieTest1", TipoBicho.AGUA);
	esp= new EspecieConProbabilidad(e,13);
	
	
	
	especies.add(esp);
	Runner.runInSession( () ->{
		this.especieDAO.saveEspecie(e);
		this.especieConProbabilidadDAO.saveEspecieConProbabilidad(esp);
		return null;
		});
	
	e = new Especie("EspecieTest2", TipoBicho.AIRE);
	esp= new EspecieConProbabilidad(e,1);
	especies.add(esp);
	Runner.runInSession( () ->{
		this.especieDAO.saveEspecie(e);
		this.especieConProbabilidadDAO.saveEspecieConProbabilidad(esp);
		return null;
		});

	
	
	this.pueblo = new Pueblo("PuebloTest");
	this.pueblo.setEspecies(this.especies);
	this.puebloDAO = new HibernatePuebloDAO();
	}

@After
public void reiniciarBD() {
	SessionFactoryProvider.destroy();
}

@Test
public void dadoUnLugarLoPersisto() {
	Runner.runInSession(() -> {
		this.puebloDAO.savePueblo(this.pueblo);
		return null;
	});
	
	/**
	 * 	Lugar lugarRecuperado =
	 *					return this.lugarDAO.getLugar("LugarTest");
	 *				});
	 *	
	 *	assertEquals(this.lugarOriginal.getNombre(), lugarRecuperado.getNombre());
	 *	}
	 **/
}
}
