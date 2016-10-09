package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import static org.junit.Assert.*;

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
