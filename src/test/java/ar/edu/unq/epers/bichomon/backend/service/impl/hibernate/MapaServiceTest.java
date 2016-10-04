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
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.lugar.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;

public class MapaServiceTest {

	private Entrenador entrenador;
	private Lugar lugarViejo, lugarNuevo;
	private Dojo dojo;
	private MapaService mapaService;
	private EntrenadorDAO entrenadorDAO;
	private LugarDAO lugarDAO;
	private Bicho bicho;
	private BichoDAO bichoDAO;

	@Before
	public void setUp() {
		this.entrenador = new Entrenador("EntrenadorTest");
		this.lugarViejo = new Pueblo("LugarViejo");
		this.lugarNuevo = new Guarderia("LugarNuevo");
		this.dojo = new Dojo("DojoTest");
		this.bicho = new Bicho(new Especie("EspecieTest", TipoBicho.AGUA)); 
		
		this.entrenadorDAO = new HibernateEntrenadorDAO();
		this.lugarDAO = new HibernateLugarDAO();
		this.bichoDAO = new HibernateBichoDAO();

		this.mapaService = new MapaService(entrenadorDAO, lugarDAO);
		
		this.entrenador.setUbicacionActual(lugarViejo);
		this.dojo.setBichoCampeonActual(bicho);
		
		Runner.runInSession(() -> {
			this.bichoDAO.saveBicho(bicho);
			this.lugarDAO.saveLugar(dojo);
			this.lugarDAO.saveLugar(lugarViejo);
			this.lugarDAO.saveLugar(lugarNuevo);
			this.entrenadorDAO.saveEntrenador(entrenador);
			return null;
		});
	}
	
	@After
	public void reiniciarBD() {
		SessionFactoryProvider.destroy();
	}

	@Test
	public void dadoUnEntrenadorSeLoMoveraAUnNuevoLugarYLuegoComprueboQueSeHayaComplatadoLaAccion() {
		this.mapaService.mover(entrenador.getNombre(), lugarNuevo.getNombre());

		Runner.runInSession(() -> {
			Entrenador entrenadorRecuperado = entrenadorDAO.getEntrenador("EntrenadorTest");
			
			assertEquals(entrenadorRecuperado.getUbicacionActual().getNombre(), lugarNuevo.getNombre());
			return null;
		});
	}
	
	@Test
	public void dadoDiezEntrenadoresConsultoCuantosEstanUbicadosEnUnLugarDado() {
		this.cargarEntrenadoresEnElLugar(5, lugarNuevo);
		this.cargarEntrenadoresEnElLugar(5, lugarViejo);
		
		Integer cantidadObtenida = this.mapaService.cantidadEntrenadores(this.lugarNuevo.getNombre());
		
		assertEquals(cantidadObtenida, (Integer) 5);
	}
	
	@Test
	public void dadoElNombreDeUnDojoConsultoCualEsElCampeonActualDeEsaUbicacionYLaRetorno() {
		Bicho bichoRecuperado = this.mapaService.campeon(dojo.getNombre());
		
		assertEquals(bichoRecuperado, bicho);
	}
	
	@Test
	public void dadoUnDojoSeBuscaEnLaBBDDElBichoQueHayaSidoCampeonDuranteMasTiempoEnEstaUbicacion() {
		CampeonHistorico c1, c2, c3, c4, c5;
		Bicho b1, b2, b3, b4, b5;
		
		b1 = new Bicho();
		b2 = new Bicho();
		b3 = new Bicho();
		b4 = new Bicho();
		b5 = new Bicho();
		
		c1 = new CampeonHistorico(this.dojo, b1);	c1.setFechaCoronadoCampeon(1); c1.setFechaDepuesto(2);
		c2 = new CampeonHistorico(this.dojo, b2);	c2.setFechaCoronadoCampeon(2); c2.setFechaDepuesto(4);
		c3 = new CampeonHistorico(this.dojo, b3);	c3.setFechaCoronadoCampeon(4); c3.setFechaDepuesto(8);
		c4 = new CampeonHistorico(this.dojo, b4);	c4.setFechaCoronadoCampeon(8); c4.setFechaDepuesto(16);
		c5 = new CampeonHistorico(this.dojo, b5);	c5.setFechaCoronadoCampeon(16); c5.setFechaDepuesto(32);
		
		Runner.runInSession(() -> {
			Session session = Runner.getCurrentSession();
			
			session.save(b1);	session.save(b2);	session.save(b3);
			session.save(b4);	session.save(b5);
			
			session.save(c1);	session.save(c2);	session.save(c3);
			session.save(c4);	session.save(c5);
			
			return null;
		});
		Bicho bichoRecuperado = this.mapaService.campeonHistorico(this.dojo.getNombre());
		
		assertEquals(bichoRecuperado, b5);
	}

	
//MÉTODOS AUXILIARES PARA TESTS	
	
	private void cargarEntrenadoresEnElLugar(Integer cantidadDeEntrenadores, Lugar lugar) {
		Runner.runInSession(() -> {
			Integer nombreEntrenador = lugar.hashCode();
			Entrenador e;
			for(int i=0; i<cantidadDeEntrenadores; i++) {
				nombreEntrenador += 1; 
				e = new Entrenador(nombreEntrenador.toString());
				e.setUbicacionActual(lugar);
				this.entrenadorDAO.saveEntrenador(e);
			}
			return null;
		});
	}

}
