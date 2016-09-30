package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEspecieDAOTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		EspecieDAO especieDAO = new HibernateEspecieDAO();
		
		Especie esp = new Especie("EspecieTest", TipoBicho.AGUA);
		esp.agregarCondicionDeEvolucion(new CondicionBasadaEnEnergia(5));
		esp.agregarCondicionDeEvolucion(new CondicionBasadaEnEdad(4));
		esp.agregarCondicionDeEvolucion(new CondicionBasadaEnVictorias(15));
		esp.agregarCondicionDeEvolucion(new CondicionBasadaEnNivel(8));
		
		Runner.runInSession(() -> {
			especieDAO.saveEspecie(esp);
			return null;
		});
	}

}
