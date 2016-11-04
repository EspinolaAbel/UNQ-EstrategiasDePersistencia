package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.lugar.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.Truncator;

public class MapaServiceTest {

	private Entrenador entrenador;
	private MapaService mapaService;
	private EntrenadorDAO entrenadorDAO;
	private LugarDAO lugarDAO;
	private BichoDAO bichoDAO;

	@Before
	public void setUp() {
		this.entrenador = new Entrenador("EntrenadorTest");
		
		this.entrenadorDAO = new HibernateEntrenadorDAO();
		this.lugarDAO = new HibernateLugarDAO();
		this.bichoDAO = new HibernateBichoDAO();

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
	public void intentoMoverUnEntrenadorAUnaUbicacionQuePuedePagarLoMuevoYComprueboQueSeHayaMovidoCorrectamente() {
		//SETEO EL ENTORNO DEL TEST
		LugarDAO lugarDAO = new LugarFakeDAO();
		EntrenadorDAO entrenadorDAO = new EntrenadorDummyDAO();
		Lugar partida = new Dojo("Tibet Dojo");
		Lugar destFinal = new Pueblo("Lagartolandia");
		lugarDAO.saveLugar(destFinal);
		this.crearGrafoEnBaseDeDatos();//TODO: crear grafo dedicado para este test.
		
		//TEST
		MapaService mapaService = new MapaService(entrenadorDAO, lugarDAO);
		Entrenador entrenador = new Entrenador("EntrenadorTest");
		entrenador.agregarMonedas(100);//TODO: setear las monedas en el costo real de viaje. 
		
		mapaService.moverMasCorto("EntrenadorTest", "Lagartolandia");
		
		assertEquals(entrenador.getUbicacionActual(), destFinal);
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
	
	private void crearGrafoEnBaseDeDatos() {
		MapaService mapaSvc = new MapaService(null, new Neo4jLugarDAO());
		
		Lugar stBlah = new Guarderia("St. Blah");
		Lugar plantalandia = new Pueblo("Plantalandia");
		Lugar agualandia = new Pueblo("Agualandia");
		Lugar lagartolandia = new Pueblo("Lagartolandia");
		Lugar tibetDojo = new Dojo("Tibet Dojo");
		Lugar bicholandia = new Pueblo("bicholandia");
		
		mapaSvc.crearUbicacion(stBlah);
		mapaSvc.crearUbicacion(plantalandia);
		mapaSvc.crearUbicacion(agualandia);
		mapaSvc.crearUbicacion(lagartolandia);
		mapaSvc.crearUbicacion(tibetDojo);
		mapaSvc.crearUbicacion(bicholandia);
		
		mapaSvc.conectar("St. Blah", "Agualandia", "terrestre");
		mapaSvc.conectar("St. Blah", "Plantalandia", "aereo");
		
		mapaSvc.conectar("Plantalandia", "Agualandia", "maritimo");
		
		mapaSvc.conectar("Agualandia", "St. Blah", "terrestre");
		mapaSvc.conectar("Agualandia", "Plantalandia", "maritimo");
		mapaSvc.conectar("Agualandia", "Bicholandia", "maritimo");
		mapaSvc.conectar("Agualandia", "Lagartolandia", "maritimo");
		
		mapaSvc.conectar("Lagartolandia", "Agualandia", "maritimo");
		mapaSvc.conectar("Lagartolandia", "Bicholandia", "terrestre");
		
		mapaSvc.conectar("Tibet Dojo", "Plantalandia", "terrestre");
		mapaSvc.conectar("Tibet Dojo", "Bicholandia", "aereo");
		
		mapaSvc.conectar("Bicholandia", "Tibet Dojo", "aereo");
		mapaSvc.conectar("Bicholandia", "Lagartolandia", "terrestre");
	}

}

//TEST DOUBLES:

/** Finge ser un dao con conexión a una base de datos.*/
class LugarFakeDAO implements LugarDAO {
	
	List<Lugar> repoLugares = new ArrayList<Lugar>();
	
	@Override
	public void saveLugar(Lugar lugar) {
		this.repoLugares.add(lugar);
	}

	@Override
	public Lugar getLugar(String nombre) {
		for(Lugar each: this.repoLugares) {
			if(each.getNombre().equals(nombre))
				return each;
		}
		return null;
	}

	@Override
	public Bicho getBichoCampeonActualDelDojo(String nombreDojo) {return null;}
	@Override
	public Bicho getCampeonHistoricoDelDojo(String dojoNombre) {return null;}	
}

class EntrenadorDummyDAO implements EntrenadorDAO {
	
	Entrenador entrenador;

	@Override
	public void saveEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}

	@Override
	public Entrenador getEntrenador(String nombre) {
		return this.entrenador;
	}

	@Override
	public int getCantidadDeEntrenadoresUbicadosEnLugar(String nombreLugar) {return 0;}
	@Override
	public List<Entrenador> getEntrenadoresConBichosCampeones() {return null;}
	@Override
	public List<Entrenador> getLideres() {return null;}
	
}
