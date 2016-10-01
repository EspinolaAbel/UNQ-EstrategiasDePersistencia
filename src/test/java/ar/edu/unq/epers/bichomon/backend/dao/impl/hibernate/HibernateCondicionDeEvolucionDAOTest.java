package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.CondicionDeEvolucionDAO;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionDeEvolucion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public abstract class HibernateCondicionDeEvolucionDAOTest {

	private CondicionDeEvolucionDAO condicionDAO;
	protected CondicionDeEvolucion condicionOriginal;
	
	@Before
	public void setUp(){
		this.condicionDAO = new HibernateCondicionDeEvolucionDAO();
		this.condicionOriginal = null;
	}

	@Test
	public void dadaUnaCondicionDeEvolucionLaPersistoEnLaBBDDBichomonLuegoLaRecuperoYComprueboSiFuePersistidaDeManeraCorrecta() {
		
		Runner.runInSession(() -> {
			condicionDAO.saveCondicion(this.condicionOriginal);
			return null;
		});
		
		List<CondicionDeEvolucion> condicionesRecuperadas = (List<CondicionDeEvolucion>) Runner.runInSession(() -> {
			return condicionDAO.getAllCondiciones();
		});
		
		assertTrue(condicionesRecuperadas.contains(condicionOriginal));
		System.out.println(condicionesRecuperadas.size());
	}

}
