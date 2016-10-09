package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.service.runner.Truncator;

public class HibernateLugarDAOTest {

	private Lugar lugarOriginal;
	private LugarDAO lugarDAO;

	@Before
	public void setUp() {
		this.lugarOriginal = new Pueblo("LugarTest");
		this.lugarDAO = new HibernateLugarDAO();
	}
	
	@After
	public void cleanUp() {
		Truncator.cleanUpTables();
	}

	@Test
	public void dadoUnLugarLoPersistoEnLaBBDDYLuegoLoRecuperoParaComprobarQueSeHayaPersistidoDeManeraCorrecta() {
		Runner.runInSession(() -> {
			this.lugarDAO.saveLugar(this.lugarOriginal);
			return null;
		});
		
		Lugar lugarRecuperado =
					Runner.runInSession(() -> {
						return this.lugarDAO.getLugar("LugarTest");
					});
		
		assertEquals(this.lugarOriginal.getNombre(), lugarRecuperado.getNombre());
	}

}
