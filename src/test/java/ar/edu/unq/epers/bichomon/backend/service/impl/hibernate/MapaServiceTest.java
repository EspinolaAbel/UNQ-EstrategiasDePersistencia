package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.driver.v1.StatementResult;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAONeo4j;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.Neo4jLugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.TipoDeCamino;
import ar.edu.unq.epers.bichomon.backend.model.lugar.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.Truncator;

public class MapaServiceTest {

	private Entrenador entrenador;
	private MapaService mapaService;
	private EntrenadorDAO entrenadorDAO;
	private LugarDAO lugarDAO;
	private BichoDAO bichoDAO;

	private LugarDAONeo4j lugarDAONeo4j;
	@Before
	public void setUp() {
		this.entrenador = new Entrenador("EntrenadorTest");
		
		this.entrenadorDAO = new HibernateEntrenadorDAO();
		this.lugarDAO = new HibernateLugarDAO();
		this.bichoDAO = new HibernateBichoDAO();
		this.lugarDAONeo4j = new  Neo4jLugarDAO();

		this.mapaService = new MapaService();
	}
	
	@After
	public void cleanUp() {
		Truncator.cleanUpTables();
	}

	
	@Test
	public void dadoUnEntrenadorSeLoMoveraAUnNuevoLugarYLuegoComprueboQueSeHayaComplatadoLaAccion() {
		Lugar lugarViejo = new Pueblo("LugarViejo");
		Lugar lugarNuevo = new Guarderia("LugarNuevo");

		this.entrenador.setUbicacionActual(lugarViejo);

		Runner.runInSession(() -> {
			this.lugarDAO.saveLugar(lugarViejo);
			this.lugarDAO.saveLugar(lugarNuevo);
			this.entrenadorDAO.saveEntrenador(this.entrenador);
			return null;
		});
		
		this.mapaService.mover(entrenador.getNombre(), lugarNuevo.getNombre());

		Entrenador entrenadorRecuperado =
		Runner.runInSession(() -> {
			return this.entrenadorDAO.getEntrenador(entrenador.getNombre());
		});
		
		assertEquals(entrenadorRecuperado.getUbicacionActual().getNombre(), lugarNuevo.getNombre());
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
	public void dadoUnNuevoLoPersiteEnLaBBDDHibernateYNeo4j(){
		Lugar lugar = new Pueblo("unPueblo");
		this.mapaService.crearUvicacion(lugar);
		
		
		
		Runner.runInSession(()->{
			Pueblo puebloRecuperado	= (Pueblo) this.lugarDAO.getLugar(lugar.getNombre());
			assertEquals("unPueblo", puebloRecuperado.getNombre());
			return null;
		});
		// debo recuperar el pueblo desde la base de datos de neo
		String lugarRecuperadoNeo4j = this.lugarDAONeo4j.recuperarLugar(lugar);
		assertEquals("unPueblo",lugarRecuperadoNeo4j);
		this.lugarDAONeo4j.eliminarLugar("unPueblo");
		
	}

	@Test 
	public void dadoDosLugaresCreaUnaConeccionYLaPersisteEnNeo4j(){
		Pueblo puebloTest 		= new Pueblo("FarTown");
		Dojo dojoTest 			= new Dojo("Akido");
		Guarderia guarderiaTest = new Guarderia("BichoBaby");		
		
		
		TipoDeCamino caminoAereo		= new TipoDeCamino(5,"AEREO");
		TipoDeCamino caminoMaritimo		= new TipoDeCamino(2,"MARITIMO");
		TipoDeCamino caminoTerrestre	= new TipoDeCamino(1,"TERRESTRE");
		
		this.lugarDAONeo4j.saveLugar(puebloTest);
		this.lugarDAONeo4j.saveLugar(dojoTest);
		this.lugarDAONeo4j.saveLugar(guarderiaTest);
		
		this.lugarDAONeo4j.crearConeccion(dojoTest.getNombre(), puebloTest.getNombre(), caminoAereo);
		//Aca tengo redefinido el mensaje crear coneccion para de distintaas maneras 
		this.lugarDAONeo4j.crearConeccion(dojoTest.getNombre(), guarderiaTest.getNombre(), caminoTerrestre);
				
		this.lugarDAONeo4j.crearConeccion(puebloTest.getNombre(), dojoTest.getNombre(), caminoMaritimo); 
		this.lugarDAONeo4j.crearConeccion(puebloTest.getNombre(), guarderiaTest.getNombre(), caminoTerrestre);
		
		this.lugarDAONeo4j.crearConeccion(guarderiaTest.getNombre(), puebloTest.getNombre(), caminoAereo); 
		this.lugarDAONeo4j.crearConeccion(guarderiaTest.getNombre(), guarderiaTest.getNombre(), caminoMaritimo);
		
		// debo recuperar el pueblo desde la base de datos de neo
		List<String> coneccionesRecuperadas = this.lugarDAONeo4j.recuperarRelacion(puebloTest.getNombre(),dojoTest.getNombre());
		assertNotNull(coneccionesRecuperadas);
		
	}
	@Test
	public void dadoUnPuebloLePidoLasConeccionesTerrestresYMeDevuelveDosPueblos(){
		//String lugar = "unPueblo";
		
		Pueblo puebloTest 		= new Pueblo("FarTown");
		Dojo dojoTest 			= new Dojo("Akido");
		Guarderia guarderiaTest = new Guarderia("BichoBaby");		
		
		
		TipoDeCamino caminoAereo		= new TipoDeCamino(5,"AEREO");
		TipoDeCamino caminoMaritimo		= new TipoDeCamino(2,"MARITIMO");
		TipoDeCamino caminoTerrestre	= new TipoDeCamino(1,"TERRESTRE");
		
		this.lugarDAONeo4j.saveLugar(puebloTest);
		this.lugarDAONeo4j.saveLugar(dojoTest);
		this.lugarDAONeo4j.saveLugar(guarderiaTest);
		
		this.lugarDAONeo4j.crearConeccion(dojoTest.getNombre(), puebloTest.getNombre(), caminoAereo);
		//Aca tengo redefinido el mensaje crear coneccion para de distintaas maneras 
		this.lugarDAONeo4j.crearConeccion(dojoTest.getNombre(), guarderiaTest.getNombre(), caminoTerrestre);
				
		this.lugarDAONeo4j.crearConeccion(puebloTest.getNombre(), dojoTest.getNombre(), caminoMaritimo); 
		this.lugarDAONeo4j.crearConeccion(puebloTest.getNombre(), guarderiaTest.getNombre(), caminoTerrestre);
		
		this.lugarDAONeo4j.crearConeccion(guarderiaTest.getNombre(), puebloTest.getNombre(), caminoMaritimo); 
		this.lugarDAONeo4j.crearConeccion(guarderiaTest.getNombre(), dojoTest.getNombre(), caminoMaritimo);
		
	// testeo las conecciones del pueblo	
		List<String> lugaresConectados= this.lugarDAONeo4j.conectados(puebloTest.getNombre(), "TERRESTRE");
		assertEquals (1,lugaresConectados.size());
		assertEquals ("BichoBaby",lugaresConectados.get(0));
	
	// testeo las conecciones del dojo				
		lugaresConectados= this.lugarDAONeo4j.conectados(dojoTest.getNombre(), "MARITIMO");
		assertEquals (0,lugaresConectados.size());
		

	// testeo las conecciones del pueblo	
		lugaresConectados= this.lugarDAONeo4j.conectados(guarderiaTest.getNombre(), "MARITIMO");
		assertEquals (2,lugaresConectados.size());
		assertTrue (lugaresConectados.contains("FarTown"));
		assertTrue (lugaresConectados.contains("Akido"));

	this.lugarDAONeo4j.eliminarLugar("FarTown");
	this.lugarDAONeo4j.eliminarLugar("Akido");
	this.lugarDAONeo4j.eliminarLugar("BichoBaby");
		
	}
	
	
		
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

}
