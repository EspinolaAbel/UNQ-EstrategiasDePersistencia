package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.CondicionDeEvolucionDAO;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnEnergia;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnVictorias;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionDeEvolucion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateCondicionDeEvolucionDAOTest {

	@Before
	public void setUp(){
	}

	@Test
	public void test() {
		CondicionDeEvolucionDAO condicionEnerDAO = new HibernateCondicionDeEvolucionDAO();
		CondicionDeEvolucion condicionEner = new CondicionBasadaEnEnergia(100);
		Runner.runInSession(() -> {
			condicionEnerDAO.guardar(condicionEner);
			return null;
		});
		
		CondicionDeEvolucionDAO condicionVicDAO = new HibernateCondicionDeEvolucionDAO();
		CondicionDeEvolucion condicionVic = new CondicionBasadaEnVictorias(15);
		Runner.runInSession(() -> {
			condicionVicDAO.guardar(condicionVic);
			return null;
		});
	}

}
