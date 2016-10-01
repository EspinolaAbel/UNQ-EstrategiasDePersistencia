package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.CondicionDeEvolucionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEspecieDAOTest {
	
	private EspecieDAO especieDAO;
	private CondicionDeEvolucionDAO condicionDAO;
	private Especie especieOriginal;

	@Before
	public void setUp() {
		especieDAO = new HibernateEspecieDAO();
		condicionDAO = new HibernateCondicionDeEvolucionDAO();
	}

	@Test
	public void test() {
		this.especieOriginal = new Especie("EspecieTest", TipoBicho.AGUA);
		
		CondicionDeEvolucion condicionEnergia, condicionEdad;
		condicionEnergia = new CondicionBasadaEnEnergia(999);
		condicionEdad = new CondicionBasadaEnEdad(888);
		
		this.especieOriginal.agregarCondicionDeEvolucion(condicionEnergia);
		this.especieOriginal.agregarCondicionDeEvolucion(condicionEdad);
		
		this.especieOriginal.setRaiz(new Especie("RaizTest", TipoBicho.AGUA));
		this.especieOriginal.setEvolucionaA(new Especie("EvolucionTest", TipoBicho.AGUA));
		
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
		assertTrue(especieRecuperada.getCondicionesDeEvolucion().contains(condicionEnergia));
		assertTrue(especieRecuperada.getCondicionesDeEvolucion().contains(condicionEdad));
		assertEquals(especieRecuperada.getEvolucionaA().getNombre(), "EvolucionTest");
		assertEquals(especieRecuperada.getRaiz().getNombre(), "RaizTest");
	}

}
