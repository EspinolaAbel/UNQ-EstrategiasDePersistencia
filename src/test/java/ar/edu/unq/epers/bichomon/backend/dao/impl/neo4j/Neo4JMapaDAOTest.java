package ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j;

import static org.junit.Assert.*;

import java.util.List;

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
	public void dadasUnaUbicacionDePartidaYUnaDeDestinoCalculoElCostoDelViajeConRutaDirecta() {
		this.crearMapaTestMover();
		
		RunnerNeo4J.runInSession(()->{
			assertEquals(mapaDAO.costoDelViajeDirecto("Partida", "Destino"), Integer.valueOf(1));
			return null;
		});
	}
	
	
	@Test
	public void dadasUnaUbicacionDePartidaYConDestinoIndirectoIntentoCalcularElCostoDelViajeConRutaDirectaPeroSeLanzaUbicacionMuyLejanaException() {
		this.crearMapaTestMover();
		
		RunnerNeo4J.runInSession(()->{
			try {
				mapaDAO.costoDelViajeDirecto("Partida", "DestinoIndirecto");
				fail("No se lanzó UbicacionMuyLejanaException");
			}
			catch(UbicacionMuyLejanaException e) {
				assertTrue(true);
			}
			return null;
		});
	}
	

	@Test
	public void dadasUnaUbicacionDePartidaYUnaDeDestinoAlCualPuedoLlegarConUnaRutaCortaYOtraLargaCalculoElCostoDelViajeDeLaRutaMasCorta() {
		this.crearMapaTestMover();
		
		RunnerNeo4J.runInSession(()->{
			assertEquals(mapaDAO.costoDelViajeMasCorto("Partida", "DestinoIndirecto"), Integer.valueOf(2));
			return null;
		});
	}
	
	
	@Test
	public void dadasUnaUbicacionDePartidaYUnDestinoInalcanzableIntentoCalcularElCostoDelViajePeroSeLanzaDestinoInalcanzableException() {
		this.crearMapaTestMover();
		
		RunnerNeo4J.runInSession(()->{
			try {
				mapaDAO.costoDelViajeMasCorto("Partida", "DestinoInalcanzable");
				fail("No se lanzó DestinoInalcanzableException");
			}
			catch(DestinoInalcanzableException e) {
				assertTrue(true);
			}
			return null;
		});
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
	
	
	@Test
	public void dadoDosLugaresCreoUnaRutaEntreEllosYComprueboQueSeHayanConectado() {
		Lugar partida = new Pueblo("Partida");
		Lugar destino = new Pueblo("Destino");
		
		RunnerNeo4J.runInSession(()->{
			mapaDAO.saveLugar(partida);
			mapaDAO.saveLugar(destino);
			
			mapaDAO.crearConexion("Partida", "Destino", TipoDeCamino.AEREO);
			
			assertTrue(mapaDAO.existeCamino("Partida", "Destino"));
			return null;
		});
	}
	
	
	@Test
	public void dadoDosLugaresSinRutasQueLosConectenComprueboQueNoEstenConectados() {
		Lugar partida = new Pueblo("Partida");
		Lugar destino = new Pueblo("Destino");
		
		RunnerNeo4J.runInSession(()->{
			mapaDAO.saveLugar(partida);
			mapaDAO.saveLugar(destino);
			
			assertFalse(mapaDAO.existeCamino("Partida", "Destino"));
			return null;
		});
	}
	
	
	@Test
	public void dadoUnLugarObtengoUnaListaDeAquellosLugaresQueEstanConectadosDirectamentePorUnTipoDeCamino() {
		Lugar lugarAlcanzable = crearMapaTestConUnLugarAlcanzablePorRutaDirecta();
			
		RunnerNeo4J.runInSession(()-> {
			List<String> resultado = mapaDAO.lugaresAdyacentes("PARTIDA", TipoDeCamino.TERRESTRE);
			
			assertEquals(resultado.size(), 1);
			assertTrue(resultado.contains(lugarAlcanzable.getNombre()));
			return null;
		});
	}
	
	

	@Test
	public void dadoUnLugarObtengoUnaListaDeAquellosLugaresQueEstanConectadosDirectamentePorCualquieripoDeCamino() {
		Lugar lugarAlcanzable = crearMapaTestConUnLugarAlcanzablePorRutaDirecta();
			
		RunnerNeo4J.runInSession(()-> {
			List<String> resultado = mapaDAO.lugaresAdyacentesDeCualquierTipo("PARTIDA");
			
			assertEquals(resultado.size(), 2);
			assertTrue(resultado.contains(lugarAlcanzable.getNombre()));
			//aca veulvo a devolver "DIRECTO-Por ruta terrestre", y  el nodo no esta conectado consigo mismo
			resultado = mapaDAO.lugaresAdyacentesDeCualquierTipo("DIRECTO-Por ruta terrestre");
			
			assertEquals(resultado.size(), 3);
			assertFalse(resultado.contains(lugarAlcanzable.getNombre()));
			
			return null;
		});
	}
	
//	METODOS AUXILIARES DE TEST
	
	private Lugar crearMapaTestConUnLugarAlcanzablePorRutaDirecta() {
		Lugar partida = new Pueblo("PARTIDA");
		Lugar rutaDirecta1 = new Pueblo("DIRECTO-Por ruta terrestre");
		Lugar rutaDirecta2 = new Pueblo("DIRECTO-Por ruta maritima");
		Lugar indirecto1_1 = new Pueblo("INDIRECTO-1.1");
		Lugar indirecto1_2 = new Pueblo("INDIRECTO-1.2");
		Lugar indirecto2_1 = new Pueblo("INDIRECTO-2.1");
		Lugar indirecto2_2 = new Pueblo("INDIRECTO-2.2");
		RunnerNeo4J.runInSession(()->{
			this.mapaDAO.saveLugar(partida);
			this.mapaDAO.saveLugar(rutaDirecta1);
			this.mapaDAO.saveLugar(rutaDirecta2);
			this.mapaDAO.saveLugar(indirecto1_1);
			this.mapaDAO.saveLugar(indirecto1_2);
			this.mapaDAO.saveLugar(indirecto2_1);
			this.mapaDAO.saveLugar(indirecto2_2);
			//Lugar a asertar:
			this.mapaDAO.crearConexion("PARTIDA", "DIRECTO-Por ruta terrestre", TipoDeCamino.TERRESTRE);//"terrestre");
			//Lugares de control. No deben aparecer entre los resultados:
			this.mapaDAO.crearConexion("PARTIDA", "DIRECTO-Por ruta maritima",TipoDeCamino.MARITIMO);// "maritimo");
			this.mapaDAO.crearConexion("DIRECTO-Por ruta terrestre", "INDIRECTO-1.1",TipoDeCamino.TERRESTRE);// "terrestre");
			this.mapaDAO.crearConexion("DIRECTO-Por ruta terrestre", "INDIRECTO-1.2",TipoDeCamino.MARITIMO);// "maritimo");
			this.mapaDAO.crearConexion("DIRECTO-Por ruta maritima", "INDIRECTO-2.1", TipoDeCamino.TERRESTRE);//"terrestre");
			this.mapaDAO.crearConexion("DIRECTO-Por ruta maritima", "INDIRECTO-2.2",TipoDeCamino.AEREO);// "aereo");
			
			return null;
		});
		return rutaDirecta1;
	}
	
	
	/**El mapa creado crea un lugar de partida, un destino1 al cual puede llegarse con una ruta directa y destino2 el cual solo puede
	 * alcanzanse a través de rutas indirectas. También se crear un destinoInalzable al cual no puede llegarse a través de ninguna ruta.
	 * 
	 * Formas de llegar a los destinos alcanzables:
	 * ------------------------------------------
	 * Hay dos formas de llegar a destino1:
	 * - (corto) Tomando una ruta directa: Partida -> Destino;
	 * - (largo) Tomando una ruta indirecta: Partida -> Intermedio -> Destino
	 * 
	 * Hay dos formas de llegar a destino2:
	 * - (corto) Tomando la ruta indirecta: Partida -> Destino -> DestinoIndirecto;
	 * - (largo) Tomando la ruta indirecta: Partida -> Intermedio -> Destino -> DestinoIndirecto.
	 * */
	private void crearMapaTestMover() {
		Lugar partida = new Pueblo("Partida");
		Lugar intermedio = new Pueblo("Intermedio");
		Lugar destino = new Pueblo("Destino");
		Lugar destinoIndirecto = new Pueblo("DestinoIndirecto");
		Lugar destinoInalcanzable = new Pueblo("DestinoInalcanzable");
		
		RunnerNeo4J.runInSession(()->{
			this.mapaDAO.saveLugar(partida);
			this.mapaDAO.saveLugar(intermedio);
			this.mapaDAO.saveLugar(destino);
			this.mapaDAO.saveLugar(destinoIndirecto);
			this.mapaDAO.saveLugar(destinoInalcanzable);
			
			this.mapaDAO.crearConexion("Partida", "Intermedio",TipoDeCamino.TERRESTRE);// "terrestre");
			this.mapaDAO.crearConexion("Intermedio", "Destino",TipoDeCamino.TERRESTRE);// "terrestre");
			this.mapaDAO.crearConexion("Partida", "Destino",TipoDeCamino.TERRESTRE);// "terrestre");
			this.mapaDAO.crearConexion("Destino", "DestinoIndirecto",TipoDeCamino.TERRESTRE);// "terrestre");

			return null;
		});
		
	}
	
}
