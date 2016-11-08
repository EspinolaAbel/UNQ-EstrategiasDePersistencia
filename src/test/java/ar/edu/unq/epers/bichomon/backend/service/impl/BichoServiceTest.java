package ar.edu.unq.epers.bichomon.backend.service.impl;

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
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateNivelDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernatePuntosDeExperienciaDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.Nivel;
import ar.edu.unq.epers.bichomon.backend.model.PuntosDeExperiencia;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.busqueda.FactoresMock;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnEnergia;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnNivel;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnVictorias;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.service.impl.BichoService;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.Truncator;

public class BichoServiceTest {
	
	

	private Entrenador entrenador1, entrenador2,entrenador3,entrenador4;;
	private Lugar guarderia;
	private Dojo dojo;
	private BichoService bichoService;
	private Bicho bicho1,bicho2,bicho3, bicho4, 
		     	  bichoObtenidoDeGuarderia, bichoObtenidoDeDojo, bichoCampeonDeDojo, bichoEvolucionador;
	private Nivel nivel, nivel2;
	
	private EntrenadorDAO entrenadorDAO;
	private LugarDAO lugarDAO;
	private BichoDAO bichoDAO;
	private NivelDAO nivelDAO;
	
	private Especie especieRaiz;
	private Especie especieEvolucion;

	private CondicionBasadaEnVictorias condicionPorVictorias;
	private CondicionBasadaEnEnergia condicionPorEnergia;
	private CondicionBasadaEnNivel condicionPorNivel;
	private HibernateEspecieDAO especieDAO;
	private PuntosDeExperiencia evolucion, duelo, captura;
	
	private HibernatePuntosDeExperienciaDAO puntajesDAO;

	
	
	
	@Before
	public void setUp() {

		this.especieDAO= new HibernateEspecieDAO();
		this.entrenadorDAO = new HibernateEntrenadorDAO();
		this.lugarDAO = new HibernateLugarDAO();
		this.bichoDAO = new HibernateBichoDAO();
		this.nivelDAO = new HibernateNivelDAO();
		this.puntajesDAO= new HibernatePuntosDeExperienciaDAO();
		
		this.entrenador1 = new Entrenador("EntrenadorTest1");
		this.entrenador2 = new Entrenador("EntrenadorTest2");
		this.entrenador3 = new Entrenador("EntrenadorTestDeCampeon");
		this.entrenador4 =new Entrenador("EntrenadorTestEvolucion");
		
		// especies para el test de evolucion
		this.especieRaiz = new Especie("EspecieRaiz", TipoBicho.TIERRA);
		this.especieRaiz.setRaiz(especieRaiz);// especieRaiz es la base de la cadena
		this.especieEvolucion = new Especie("EspecieEvolucion", TipoBicho.TIERRA);
		this.especieRaiz.setEvolucionaA(especieEvolucion);
		
		//condicionnes de evolucion para las especies
		this.condicionPorVictorias= new CondicionBasadaEnVictorias(1);
		this.condicionPorEnergia= new CondicionBasadaEnEnergia(10);
		this.condicionPorNivel= new CondicionBasadaEnNivel(1);
		
		//introduzco las condicoines para que evolucione la especie raiz
		this.especieRaiz.agregarCondicionDeEvolucion(condicionPorVictorias);
		this.especieRaiz.agregarCondicionDeEvolucion(condicionPorEnergia);
		this.especieRaiz.agregarCondicionDeEvolucion(condicionPorNivel);
		
		this.nivel= new Nivel(1,2,5);
		this.nivel2= new Nivel(2,2,5);
		this.nivel.setSiguienteNivel(nivel2);
		this.nivel2.setSiguienteNivel(nivel2);
		
		this.guarderia = new Guarderia("GuarderiaTest");
		this.dojo= new Dojo("DojoTest");
		
		this.bicho1 = new Bicho(new Especie("EspecieTest1", TipoBicho.AGUA)); 
		this.bicho2 = new Bicho(new Especie("EspecieTest2", TipoBicho.AIRE));
		this.bicho3 = new Bicho(new Especie("EspecieTest3", TipoBicho.FUEGO));
		this.bicho4 = new Bicho(new Especie("EspecieTest4", TipoBicho.FUEGO));
		this.bichoCampeonDeDojo = new Bicho(new Especie("EspecieTest5", TipoBicho.FUEGO));
		this.bichoEvolucionador =	new Bicho(especieRaiz);
		
		Runner.runInSession( () -> {
			this.lugarDAO.saveLugar(guarderia);
			this.lugarDAO.saveLugar(dojo);

			this.entrenadorDAO.saveEntrenador(entrenador1);
			this.entrenadorDAO.saveEntrenador(entrenador2);
			this.entrenadorDAO.saveEntrenador(entrenador3);
			this.entrenadorDAO.saveEntrenador(entrenador4);
			
			this.nivelDAO.saveNivel(this.nivel);
			this.nivelDAO.saveNivel(this.nivel2);
			
			this.especieDAO.saveEspecie(especieEvolucion);
			this.especieDAO.saveEspecie(especieRaiz);
			
			this.bichoDAO.saveBicho(bicho1);
			this.bichoDAO.saveBicho(bicho2);
			this.bichoDAO.saveBicho(bicho3);
			this.bichoDAO.saveBicho(bicho4);
			this.bichoDAO.saveBicho(bichoCampeonDeDojo);
			this.bichoDAO.saveBicho(bichoEvolucionador);
			
			this.entrenador1.setNivelActual(nivel);
			this.entrenador2.setNivelActual(nivel);
			this.entrenador3.setNivelActual(nivel);
			
			this.bichoEvolucionador.setCantidadDeVictorias(2);
			this.bichoEvolucionador.setEnergia(20);
			
			this.entrenador4.agregarBichoCapturado(bichoEvolucionador);
			this.entrenador4.setExperiencia(150);
			this.entrenador4.setNivelActual(nivel2);

			this.guarderia.recibirBichoAbandonado(bicho4);
			// al bichoCampeonDeDojo campeon del dojo le vamos a setear 5 energia
			this.dojo.setCampeonActual(bichoCampeonDeDojo);
			this.bichoCampeonDeDojo.setEnergia(5);
			this.bicho3.setEnergia(50);
			
			this.entrenador1.setUbicacionActual(guarderia);
			this.entrenador2.setUbicacionActual(dojo);
			this.entrenador3.setUbicacionActual(dojo);
			
			this.entrenador1.agregarBichoCapturado(bicho1);
			this.entrenador1.agregarBichoCapturado(bicho2);
			this.entrenador2.agregarBichoCapturado(bicho3);
			this.entrenador3.agregarBichoCapturado(bichoCampeonDeDojo);
			
			this.evolucion= new PuntosDeExperiencia("Evolucion",5);
			this.duelo= new PuntosDeExperiencia("Duelo",10);
			this.captura= new PuntosDeExperiencia("CapturarBicho",10);
			
			puntajesDAO.savePuntosDeExperiencia(this.captura);
			puntajesDAO.savePuntosDeExperiencia(this.evolucion);
			puntajesDAO.savePuntosDeExperiencia(this.duelo);
			
			return null;
		});
		//utilizo factores mock que devuelve siempre una  busqueda exitosa
		this.bichoService = new BichoService(entrenadorDAO, bichoDAO,puntajesDAO,new FactoresMock());
	}
	
	@After
	public void cleanUp() {
		Truncator.cleanUpTables();
	}


	@Test
	public void testDadoUnEntrenadorQuebuscaUnBichoEnUnaGuarderiaLoEncuentraYLoGuardaEnSuLista(){
		
		this.bichoObtenidoDeGuarderia=this.bichoService.buscar("EntrenadorTest1");
		
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
			// el tiempo de captura es distindo de cero, fue establecido en la operacion de busqueda
			assertTrue(b.getTiempoDesdeSuCaptura()>0);
			return null;
		});
		
	}
	
	
	@Test
	public void testDadoUnEntrenadorAbandonaUnBichoEnUnaGuarderiaYLoDeja() {
		assertEquals(this.entrenador1.getBichosCapturados().size(),2);// antes de abandonar tiene 2 bichos
		this.bichoService.abandonar(this.entrenador1.getNombre(), 2);
		
		Runner.runInSession(() -> {
				Entrenador entrenadorRecuperado= this.entrenadorDAO.getEntrenador("EntrenadorTest1");
				Guarderia guarderiaRcuperada=(Guarderia) entrenadorRecuperado.getUbicacionActual();

				assertEquals(entrenadorRecuperado.getBichosCapturados().size(),1);
				assertEquals(guarderiaRcuperada.getBichosAbandonados().size(),2);
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
				return null;
			});
		}
	}
	
	
	/**
	 * el test se realiza entre el campeon del dojo con 5 puntos de energia y el bicho de un  
	 * entrenador con 50 puntos de enregia, dad esta circunstancia  y las caracteristicas del combate
	 * el bicho del retador ganara y se consagrara campeon del dojo.
	 */
	
	@Test
	public void testCombateEntreUnBichoDeUnEntrenadorYElCampeonDelDojo(){
		//el entrenador tiene un solo bichocen su lista
		
		ResultadoCombate resultado;
		resultado=	this.bichoService.duelo(this.entrenador2.getNombre(), this.entrenador2.getBichosCapturados().get(0).getId());
		Runner.runInSession(() -> {
				Dojo dojoRec= (Dojo)this.lugarDAO.getLugar("DojoTest");
				
				// el campeon del dojo es el bicho del entrenador 2  que acaba de convatir
				assertEquals(this.entrenador2.getBichosCapturados().get(0).getId(),dojoRec.getCampeonActual().getBichoCampeon().getId());
				assertEquals(resultado.getGanador().getId(),dojoRec.getCampeonActual().getBichoCampeon().getId());				
				
				return null;
			});
		}
	
	/**
	 * Dado un entrenador con un bicho de su lista, se consulta acerca de si el bicho puede evolucionar, y
	 *  como este aprueva las condiciones de  evolucion,devuelve True
	 */
	
	@Test
	public void testUnEntrenadorPreguntasiPuedeEvolucionarUnBicho(){
	
		boolean puedeEvolucionar = this.bichoService.puedeEvolucionar(this.entrenador4.getNombre(),bichoEvolucionador.getId());
		
		// como el bicho estaba en  condiciones de evolucionar debedar verdadero
		assertTrue(puedeEvolucionar);		
	}	
	

	/**
	 * Dado un entrenador con un bicho de su lista, se consulta acerca de si el bicho puede evolucionar, y
	 *  como este aprueva las condiciones de  evolucion,devuelve True
	 * 
	 */
	@Test
	public void testUnEntrenadorYUnBichoQuePuedeEvolucionarLoEvoluciona(){
	
		Bicho bichoEvolucionado= this.bichoService.evolucionar(this.entrenador4.getNombre(),bichoEvolucionador.getId());
		Runner.runInSession(() -> {
			Bicho bichoTest= this.bichoDAO.getBicho(this.bichoEvolucionador.getId());
			Entrenador entrenadorTest= this.entrenadorDAO.getEntrenador(entrenador4.getNombre());
		
			assertEquals(bichoEvolucionado.getEspecie(),this.especieEvolucion);// como el bicho estaba en  condiciones de evolucionar evoluciona a la siguiente especie
			assertEquals(bichoEvolucionado.getId(), bichoTest.getId());// es el mismo bicho  pero evolucionado
			assertEquals(entrenadorTest.getExperiencia(), 155);// el entrenador comenzo con 150 puntos de experiencia
			assertEquals(entrenadorTest.getNivelActual().getNumeroDeNivel(),nivel2.getNumeroDeNivel() );// 
			return null;
		});	
	}

}
