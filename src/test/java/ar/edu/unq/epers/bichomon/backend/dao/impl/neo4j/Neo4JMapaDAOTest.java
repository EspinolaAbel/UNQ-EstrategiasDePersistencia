package ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.MapaDAO;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.runner.GraphCleaner;
import ar.edu.unq.epers.bichomon.backend.service.runner.RunnerNeo4J;

public class Neo4JMapaDAOTest {

	private MapaDAO mapaDAO;
	
	@Before
	public void setUp() throws Exception {
		this.mapaDAO = new Neo4JMapaDAO();
	}

	@After
	public void tearDown() throws Exception {
		GraphCleaner.cleanUpGraph();
	}

	@Test
	public void dadoUnLugarLoPersistoEnLaBDNeo4JYComprueboQueSeHayaPersistidoDeManeraCorrecta() {
		Lugar lugar = new Pueblo("PuebloTest");
		RunnerNeo4J.runInSession(()->{
			mapaDAO.saveLugar(lugar);
			return null;
		});
		RunnerNeo4J.runInSession(()->{
			assertTrue(mapaDAO.existeLugar("PuebloTest"));
			return null;
		});
	}

	@Test
	public void dadoElNombreDeUnLugarNoPersistidoConsultoSiExisteDichoLugarYMeRespondeQueNo(){
		RunnerNeo4J.runInSession(()->{
			assertFalse(mapaDAO.existeLugar("LugarInexistente"));
			return null;
		});
	}
}
