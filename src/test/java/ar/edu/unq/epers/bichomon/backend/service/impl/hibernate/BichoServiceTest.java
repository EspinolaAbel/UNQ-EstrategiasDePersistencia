package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.NivelDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateNivelDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;
import ar.edu.unq.epers.bichomon.backend.model.lugar.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;

public class BichoServiceTest {
	
	

	private Entrenador entrenador, entrenador2;
	private Lugar lugar;
	private Dojo lugar2;
	private BichoService bichoService;
	private Bicho bicho,bicho2,bicho3, bicho4, 
		     	  bichoObtenidoDeGuarderia, bichoObtenidoDeDojo;
	private Nivel nivel;
	
	private EntrenadorDAO entrenadorDAO;
	private LugarDAO lugarDAO;
	private BichoDAO bichoDAO;
	private NivelDAO nivelDAO;
	
	
	@Before
	public void setUp() {
		this.entrenador = new Entrenador("EntrenadorTest");
		this.entrenador2 = new Entrenador("EntrenadorTest2");
		
		this.nivel= new Nivel(1,2,5);
		
		this.lugar = new Guarderia("GuarderiaTest");
		this.lugar2= new Dojo("DojoTest");
				
		this.bicho = new Bicho(new Especie("EspecieTest", TipoBicho.AGUA)); 
		this.bicho2 = new Bicho(new Especie("EspecieTest2", TipoBicho.AIRE));
		this.bicho3 = new Bicho(new Especie("EspecieTest3", TipoBicho.FUEGO));
		this.bicho4 = new Bicho(new Especie("EspecieTest4", TipoBicho.FUEGO));
		
		this.entrenadorDAO = new HibernateEntrenadorDAO();
		this.lugarDAO = new HibernateLugarDAO();
		this.bichoDAO = new HibernateBichoDAO();
		this.nivelDAO = new HibernateNivelDAO();
		this.bichoService = new BichoService(entrenadorDAO, bichoDAO);
		
		
		this.lugar.recibirBichoAbandonado(bicho4);
		this.lugar2.setBichoCampeonActual(bicho3);
		
		this.entrenador.setUbicacionActual(lugar);
		this.entrenador2.setUbicacionActual(lugar2);
		
		this.entrenador.setNivelActual(nivel);
		this.entrenador2.setNivelActual(nivel);
		
		this.entrenador.agregarBichoCapturado(bicho);this.entrenador.agregarBichoCapturado(bicho2);
		this.entrenador2.agregarBichoCapturado(bicho3);
		
		Runner.runInSession(() -> {
			
			this.nivelDAO.saveNivel(this.nivel);
			this.bichoDAO.saveBicho(bicho);this.bichoDAO.saveBicho(bicho2);
			this.bichoDAO.saveBicho(bicho3);
			this.lugarDAO.saveLugar(lugar);
			this.lugarDAO.saveLugar(lugar2);
			this.entrenadorDAO.saveEntrenador(entrenador);
			this.entrenadorDAO.saveEntrenador(entrenador2);
			return null;
		});
	}
	
	@After
	public void reiniciarBD() {
		SessionFactoryProvider.destroy();
	}


	@Test
	public void testDadoUnEntrenadorQuebuscaUnBichoEnUnaGuarderiaLoEncuentraYLoGuardaEnSuLista(){
		
		this.bichoObtenidoDeGuarderia=this.bichoService.buscar("EntrenadorTest");
		
		Runner.runInSession(() -> {
			Bicho b= this.bichoDAO.getBicho(this.bichoObtenidoDeGuarderia.getId());
			assertEquals(b.getId(),bichoObtenidoDeGuarderia.getId());
			assertEquals(3, b.getOwner().getBichosCapturados().size());
			return null;
		});
	}

	@Test
	public void testDadoUnEntrenadorQuebuscaUnBichoEnUnDojoLoEncuentraYLoGuardaEnSuLista(){
		
		this.bichoObtenidoDeDojo=this.bichoService.buscar("EntrenadorTest2");
		
		Runner.runInSession(() -> {
			Bicho b= this.bichoDAO.getBicho(this.bichoObtenidoDeDojo.getId());
			assertEquals(b.getId(),bichoObtenidoDeDojo.getId());
			
			assertEquals(2, b.getOwner().getBichosCapturados().size());
			return null;
		});
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	@Test
	public void testDadoUnEntrenadorAbandonaUnBichoEnUnaGuarderiaYLoDeja() {
		this.bichoService.abandonar(this.entrenador.getNombre(), 2);
		
		Runner.runInSession(() -> {

				Entrenador entrenadorRecuperado= this.entrenadorDAO.getEntrenador("EntrenadorTest");
				Guarderia guarderiaRcuperada=(Guarderia) entrenadorRecuperado.getUbicacionActual();
				assertEquals(entrenadorRecuperado.getBichosCapturados().size(),1);
				assertEquals(guarderiaRcuperada.getBichosAbandonados().size(),1);
				//la guarderia debe tener el bicho con  id = 2
				assertEquals(guarderiaRcuperada.getBichosAbandonados().iterator().next().getId(), 2);
			return null;
		});
		
		
	}
	

	@Test
	public void testDadoUnEntrenadorAbandonaUnBichoEnUnDojoYNoLoDeja() {
		
		try{
		this.bichoService.abandonar(this.entrenador2.getNombre(), 3);
		} catch (UbicacionIncorrectaException uIE){
		Runner.runInSession(() -> {

				Entrenador entrenadorRecuperado= this.entrenadorDAO.getEntrenador("EntrenadorTest2");
				assertEquals(1,entrenadorRecuperado.getBichosCapturados().size());
				//la guarderia debe tener el bicho con  id = 2
				
			return null;
		});
		}
		
	}

}
