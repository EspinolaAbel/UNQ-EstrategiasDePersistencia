package ar.edu.unq.epers.bichomon.backend.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.MapaDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.Neo4JMapaDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.TipoDeCamino;
import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.UbicacionMuyLejanaException;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.lugar.CampeonHistorico;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.impl.CaminoMuyCostosoException;
import ar.edu.unq.epers.bichomon.backend.service.runner.GraphCleaner;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.RunnerNeo4J;
import ar.edu.unq.epers.bichomon.backend.service.runner.Truncator;

public class MapaServiceTest {

	private MapaService mapaService;
	private EntrenadorDAO entrenadorDAO;
	private LugarDAO lugarDAO;
	private BichoDAO bichoDAO;
	private MapaDAO mapaDAO;
	
	@Before
	public void setUp() {		
		this.entrenadorDAO = new HibernateEntrenadorDAO();
		this.lugarDAO = new HibernateLugarDAO();
		this.bichoDAO = new HibernateBichoDAO();
		this.mapaDAO = new Neo4JMapaDAO();
		this.mapaService = new MapaService(entrenadorDAO,lugarDAO, mapaDAO);
	}
	
	@After
	public void cleanUp() {
		Truncator.cleanUpTables();
		GraphCleaner.cleanUpGraph();
	}
	
	
	@Test
	public void dadoDiezEntrenadoresConsultoCuantosEstanUbicadosEnUnLugarDado() {
		Lugar lugarABuscar = new Pueblo("LugarABuscar");
		Lugar lugarDeControl = new Guarderia("LugarControl");
		
		Runner.runInSession(() -> {
			this.lugarDAO.saveLugar(lugarDeControl);
			this.lugarDAO.saveLugar(lugarABuscar);
			this.cargarEntrenadoresEnElLugar(8, lugarABuscar);
			this.cargarEntrenadoresEnElLugar(5, lugarDeControl);
			return null;
		});
			
		Integer cantidadObtenida = this.mapaService.cantidadEntrenadores(lugarABuscar.getNombre());
		
		assertEquals(cantidadObtenida, (Integer) 8);
	}
	
	
	@Test
	public void dadoElNombreDeUnDojoConsultoCualEsElCampeonActualDeEsaUbicacionYLaRetorno() {
		Dojo dojo = new Dojo("DojoConActualCampeonTest");
		Bicho bichoCampeon = new Bicho();
		
		Runner.runInSession(() -> {
			this.bichoDAO.saveBicho(bichoCampeon);
			this.lugarDAO.saveLugar(dojo);
			this.cargarBichosCampeonesEnDojoYRetornarElCampeonMasLongevo(5, dojo);
			dojo.setCampeonActual(bichoCampeon);
			return null;
		});
		
		Bicho bichoRecuperado = this.mapaService.campeon(dojo.getNombre());
		
		assertEquals(bichoRecuperado, bichoCampeon);
	}
	
	@Test
	public void dadoUnDojoSeBuscaEnLaBBDDElBichoQueHayaSidoCampeonDuranteMasTiempoEnEstaUbicacion() {
		Dojo dojoDeControl = new Dojo("DojoControlTest");
		Dojo dojoABuscar = new Dojo("DojoABuscarTest");
		
		Bicho bichoCampeonHistorico = 
		Runner.runInSession(() -> {
			this.lugarDAO.saveLugar(dojoABuscar);
			this.lugarDAO.saveLugar(dojoDeControl);
			
			this.cargarBichosCampeonesEnDojoYRetornarElCampeonMasLongevo(10, dojoDeControl);
			return this.cargarBichosCampeonesEnDojoYRetornarElCampeonMasLongevo(10, dojoABuscar).getBichoCampeon();
		});
		
		Bicho bichoRecuperado = this.mapaService.campeonHistorico(dojoABuscar.getNombre());
		
		assertEquals(bichoRecuperado, bichoCampeonHistorico);
	}


	@Test
	public void muevoUnEntrenadorAUnaUbicacionAdyacenteQuePuedePagarYComprueboQueSeHayaMovidoCorrectamente() {
		Lugar destino = new Pueblo("Destino");
		Lugar partida = new Pueblo("Partida");
		this.mapaService.crearUbicacion(partida);
		this.mapaService.crearUbicacion(destino);
		this.crearMapaTestMover();
		Entrenador entrenador = new Entrenador("EntrenadorTest");
		
		Runner.runInSession(()->{
			entrenador.setMonedas(1); 
			entrenador.setUbicacionActual(partida);
			this.entrenadorDAO.saveEntrenador(entrenador);
		
			this.mapaService.mover("EntrenadorTest", "Destino");

			return null;
		});
		
		assertEquals(entrenador.getUbicacionActual(), destino);
		assertEquals(entrenador.getMonedas(), 0);
	}
	
	
	@Test
	public void intentoMoverUnEntrenadorAUnaUbicacionAdyacenteQueNoPuedePagarYSeLanzaCaminoMuyCostosoException() {
		Lugar destino = new Pueblo("Destino");
		Lugar partida = new Pueblo("Partida");
		this.mapaService.crearUbicacion(partida);
		this.mapaService.crearUbicacion(destino);
		this.crearMapaTestMover();
		Entrenador entrenador = new Entrenador("EntrenadorTest");

		Runner.runInSession(()->{
			entrenador.setMonedas(0);
			entrenador.setUbicacionActual(partida);
			entrenadorDAO.saveEntrenador(entrenador);
		
			try {
				this.mapaService.mover("EntrenadorTest", "Destino");
				fail("El entrenador se movio de lugar y no debia porque no podia pagar el viaje");
			}
			catch(CaminoMuyCostosoException e){
				assertEquals(entrenador.getUbicacionActual(), partida);
				assertEquals(entrenador.getMonedas(), 0);
			}
			return null;
		});
	}
	
	
	@Test
	public void intentoMoverUnEntrenadorAUnaUbicacionLejanaYSeLanzaUbicacionMuyLejanaException() {
		Lugar destino = new Pueblo("DestinoIndirecto");
		Lugar partida = new Pueblo("Partida");
		this.mapaService.crearUbicacion(partida);
		this.mapaService.crearUbicacion(destino);
		this.crearMapaTestMover();
		Entrenador entrenador = new Entrenador("EntrenadorTest");

		Runner.runInSession(()->{
			entrenador.setMonedas(100);
			entrenador.setUbicacionActual(partida);
			entrenadorDAO.saveEntrenador(entrenador);
		
			try {
				this.mapaService.mover("EntrenadorTest", "DestinoIndirecto");
				fail("El entrenador se movio de lugar y no debia por ser una ubicación lejana");
			}
			catch(UbicacionMuyLejanaException e){
				assertEquals(entrenador.getUbicacionActual(), partida);
				assertEquals(entrenador.getMonedas(), 100);
			}
			return null;
		});
	}

	
	@Test
	public void muevoUnEntrenadorAUnaUbicacionTomandoElCaminoMasCortoComprueboQueSeHayaMovidoCorrectamente() {
		Lugar destino = new Pueblo("DestinoIndirecto");
		Lugar partida = new Pueblo("Partida");
		this.mapaService.crearUbicacion(partida);
		this.mapaService.crearUbicacion(destino);
		this.crearMapaTestMover();
		Entrenador entrenador = new Entrenador("EntrenadorTest");
		
		Runner.runInSession(()->{
			entrenador.setMonedas(2);	//La ruta más corta al destino cuesta 2 monedas. 
			entrenador.setUbicacionActual(partida);
			entrenadorDAO.saveEntrenador(entrenador);
		
			this.mapaService.moverMasCorto("EntrenadorTest", "DestinoIndirecto");
			
			assertEquals(entrenador.getUbicacionActual(), destino);
			assertEquals(entrenador.getMonedas(), 0);
			return null;
		});
	}
	
	
	@Test 
	public void dadoUnNuevoLugarLoPersitoEnLaBBDD(){
		Lugar lugar = new Pueblo("unPueblo");
		this.mapaService.crearUbicacion(lugar);

		Runner.runInSession(()->{
			Lugar puebloRecuperado = this.lugarDAO.getLugar(lugar.getNombre());
			assertEquals("unPueblo", puebloRecuperado.getNombre());
			return null;
		});
		RunnerNeo4J.runInSession(()->{
			assertTrue(this.mapaDAO.existeLugar(lugar.getNombre()));
			return null;
		});
	}

//ACLARACIÓN:
//	Los métodos "conectar" y "conectados" del MapaService lo único que hacen es delegar en su
//	mapaDAO sus tareas. Por ese motivo, y para no duplicar la lógica de los tests, los tests 
//	correspondientes a la funcionalidad de esos métodos están implementados en Neo4JMapaDAOTest
	
	
//MÉTODOS AUXILIARES PARA TESTS	
	
	private CampeonHistorico cargarBichosCampeonesEnDojoYRetornarElCampeonMasLongevo(int cantCampeones, Dojo dojo) {
		Session session = Runner.getCurrentSession();
		
		CampeonHistorico campeon;
		Bicho bicho = null;
		CampeonHistorico campeonMasLongevo = null;
		
		for(int i=0; i<cantCampeones; i++) {
			bicho = new Bicho();
			campeon = new CampeonHistorico(dojo, bicho);
			
			session.save(bicho);
			session.save(campeon);
			
			if(i==0)
				campeonMasLongevo = campeon;
		}
		return campeonMasLongevo;
	}
	
	private void cargarEntrenadoresEnElLugar(Integer cantidadDeEntrenadores, Lugar lugar) {
	
		Integer nombreEntrenador = lugar.hashCode();
		Entrenador e;
		for(int i=0; i<cantidadDeEntrenadores; i++) {
			nombreEntrenador += 1; 
			e = new Entrenador(nombreEntrenador.toString());
			e.setUbicacionActual(lugar);
			this.entrenadorDAO.saveEntrenador(e);
		}
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
			
			this.mapaDAO.crearConexion("Partida", "Intermedio", TipoDeCamino.TERRESTRE);//"terrestre");
			this.mapaDAO.crearConexion("Intermedio", "Destino", TipoDeCamino.TERRESTRE);//"terrestre");
			this.mapaDAO.crearConexion("Partida", "Destino", TipoDeCamino.TERRESTRE);//"terrestre");
			this.mapaDAO.crearConexion("Destino", "DestinoIndirecto", TipoDeCamino.TERRESTRE);//"terrestre");

			return null;
		});
		
	}
	
//	private void crearGrafoEnBaseDeDatos() {
//		MapaService mapaSvc = new MapaService(null, new HibernateLugarDAO(), new Neo4JMapaDAO());
//		
//		Lugar stBlah = new Guarderia("St. Blah");
//		Lugar plantalandia = new Pueblo("Plantalandia");
//		Lugar agualandia = new Pueblo("Agualandia");
//		Lugar lagartolandia = new Pueblo("Lagartolandia");
//		Lugar tibetDojo = new Dojo("Tibet Dojo");
//		Lugar bicholandia = new Pueblo("bicholandia");
//		
//		mapaSvc.crearUbicacion(stBlah);
//		mapaSvc.crearUbicacion(plantalandia);
//		mapaSvc.crearUbicacion(agualandia);
//		mapaSvc.crearUbicacion(lagartolandia);
//		mapaSvc.crearUbicacion(tibetDojo);
//		mapaSvc.crearUbicacion(bicholandia);
//		
//		mapaSvc.conectar("St. Blah", "Agualandia", "terrestre");
//		mapaSvc.conectar("St. Blah", "Plantalandia", "aereo");
//		
//		mapaSvc.conectar("Plantalandia", "Agualandia", "maritimo");
//		
//		mapaSvc.conectar("Agualandia", "St. Blah", "terrestre");
//		mapaSvc.conectar("Agualandia", "Plantalandia", "maritimo");
//		mapaSvc.conectar("Agualandia", "Bicholandia", "maritimo");
//		mapaSvc.conectar("Agualandia", "Lagartolandia", "maritimo");
//		
//		mapaSvc.conectar("Lagartolandia", "Agualandia", "maritimo");
//		mapaSvc.conectar("Lagartolandia", "Bicholandia", "terrestre");
//		
//		mapaSvc.conectar("Tibet Dojo", "Plantalandia", "terrestre");
//		mapaSvc.conectar("Tibet Dojo", "Bicholandia", "aereo");
//		
//		mapaSvc.conectar("Bicholandia", "Tibet Dojo", "aereo");
//		mapaSvc.conectar("Bicholandia", "Lagartolandia", "terrestre");
//	}

}

