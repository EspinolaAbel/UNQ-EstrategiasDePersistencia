package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.CondicionDeEvolucionDAO;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnEdad;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnEnergia;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnNivel;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnVictorias;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionDeEvolucion;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionQueFalla;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateCondicionDeEvolucionDAOTest {

	@Before
	public void setUp(){
	}

	@Test
	public void test() {
		CondicionDeEvolucionDAO condicionDAO = new HibernateCondicionDeEvolucionDAO();
		CondicionDeEvolucion condicionEner = new CondicionBasadaEnEnergia(100);
		Runner.runInSession(() -> {
			condicionDAO.guardar(condicionEner);
			return null;
		});
		
		CondicionDeEvolucion condicionVic = new CondicionBasadaEnVictorias(15);
		Runner.runInSession(() -> {
			condicionDAO.guardar(condicionVic);
			return null;
		});
		
		CondicionDeEvolucion condicionNiv = new CondicionBasadaEnNivel(5);
		Runner.runInSession(() -> {
			condicionDAO.guardar(condicionNiv);
			return null;
		});
		
		CondicionDeEvolucion condicionEda = new CondicionBasadaEnEdad(3);
		Runner.runInSession(() -> {
			condicionDAO.guardar(condicionEda);
			return null;
		});
		
		CondicionDeEvolucion condicionFal = new CondicionQueFalla();
		Runner.runInSession(() -> {
			condicionDAO.guardar(condicionFal);
			return null;
		});
		
	}

}
