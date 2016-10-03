package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.CondicionDeEvolucionDAO;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionDeEvolucion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;

public abstract class HibernateCondicionDeEvolucionDAOTest {

	private CondicionDeEvolucionDAO condicionDAO;
	protected CondicionDeEvolucion condicionOriginal;
	
	@Before
	public void setUp(){
		this.condicionDAO = new HibernateCondicionDeEvolucionDAO();
		this.condicionOriginal = null;
	}
	
	@After
	public void reiniciarBD() {
		SessionFactoryProvider.destroy();
	}

	@Test
	public void dadaUnaCondicionDeEvolucionLaPersistoEnLaBBDDBichomonLuegoLaRecuperoYComprueboSiFuePersistidaDeManeraCorrecta() {
		Integer idCondicion =	Runner.runInSession(() -> {
									condicionDAO.saveCondicion(this.condicionOriginal);
									return condicionOriginal.getId();
								});
		
		CondicionDeEvolucion condicionesRecuperada =
				Runner.runInSession(() -> {
					return condicionDAO.getCondicion(idCondicion);
				});
		
		assertEquals(this.condicionOriginal, condicionesRecuperada);
	}

}
