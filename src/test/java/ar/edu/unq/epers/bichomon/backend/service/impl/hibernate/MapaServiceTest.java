package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.lugar.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;

public class MapaServiceTest {

	private Integer cont;
	private Entrenador entrenador;
	private Lugar lugarViejo, lugarNuevo;
	private MapaService mapaService;
	private EntrenadorDAO entrenadorDAO;
	private LugarDAO lugarDAO;

	@Before
	public void setUp() {
		this.entrenador = new Entrenador("EntrenadorTest");
		this.entrenadorDAO = new HibernateEntrenadorDAO();
		this.lugarDAO = new HibernateLugarDAO();
		this.mapaService = new MapaService(entrenadorDAO, lugarDAO);
		this.lugarViejo = new Pueblo("LugarViejo");
		this.lugarNuevo = new Guarderia("LugarNuevo");
		
		this.entrenador.setUbicacionActual(lugarViejo);
		
		Runner.runInSession(() -> {
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
	public void dado5EntrenadoresConsultoLaCuanotosEstanUbicadosEnUnLugarDado() {
		this.cargarEntrenadoresEnElLugar(5, lugarNuevo);
		this.cargarEntrenadoresEnElLugar(5, lugarViejo);
		
		Integer cantidadObtenida = this.mapaService.cantidadEntrenadores(this.lugarNuevo.getNombre());
		
		assertEquals(cantidadObtenida, (Integer) 5);
	}

	
//MÉTODOS AUXILIARES PARA TESTS	
	
	private void cargarEntrenadoresEnElLugar(Integer cantidadDeEntrenadores, Lugar lugar) {
		Runner.runInSession(() -> {
			Integer nombreEntrenador = lugar.hashCode();
			Entrenador e;
			for(int i=0; i<cantidadDeEntrenadores; i++) {
				nombreEntrenador += i; 
				e = new Entrenador(nombreEntrenador.toString());
				this.entrenadorDAO.saveEntrenador(e);
			}
			return null;
		});
	}

}
