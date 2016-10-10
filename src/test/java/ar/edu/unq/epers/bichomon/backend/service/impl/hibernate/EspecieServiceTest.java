package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Guarderia;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.Truncator;

public class EspecieServiceTest {

	private EspecieDAO especieDAO;
	private EntrenadorDAO entrenadorDAO;
	private LugarDAO guarderiaDAO;
	private BichoDAO bichoDAO;
	private List<Especie> populares;
	private List<Especie> impopulares;
	private EspecieService especieService;
	
	@Before
	public void setUp() {
		this.entrenadorDAO = new HibernateEntrenadorDAO();
		this.especieDAO = new HibernateEspecieDAO();
		this.guarderiaDAO = new HibernateLugarDAO();
		this.bichoDAO = new HibernateBichoDAO();
		this.cargarTodasLasEspeciesPopularesEImpopularesEnLaBD();
		this.especieService = new EspecieService();
	}

	@After
	public void cleanUp() {
		Truncator.cleanUpTables();
	}

	/** Dado una lista de especies recuperadas, las comparo con el orden de la lista de populares,
	 *  la cual esta cargada y ordenada según la popularidad de las especies entre los entrenadores.*/
	@Test
	public void consultoLasEspeciesMasPopularesEntreTodosLosEntrenadores(){
		List<Especie> especiesRecuperadas = this.especieService.populares();
		
		for(int i=0; i<10; i++)
			assertEquals(especiesRecuperadas.get(i), populares.get( 10-i ));
	}
	
	/** Dado una lista de especies recuperadas, las comparo con el orden de la lista de impopulares,
	 *  la cual esta cargada y ordenada según la popularidad de las especies ubicadas en las 
	 *  guarderias.*/
	@Test
	public void consultoLasEspeciesMenosPopularesEntreTodasLasGuarderias(){
		List<Especie> especiesRecuperadas = this.especieService.impopulares();
		
		for(int i=0; i<10; i++)
			assertEquals(especiesRecuperadas.get(i), impopulares.get( 10-i ));
	}
	
	
//METODOS AUXILIARES PARA TESTS
	
	public void cargarTodasLasEspeciesPopularesEImpopularesEnLaBD() {
		
		Runner.runInSession( () -> {
			Especie ep1, ep2, ep3, ep4, ep5, ep6, ep7, ep8, ep9, ep10, ep11;
			Especie ei1, ei2, ei3, ei4, ei5, ei6, ei7, ei8, ei9, ei10, ei11;
			
			ep1 = new Especie("popular1", TipoBicho.AGUA);
			ep2 = new Especie("popular2", TipoBicho.AIRE);
			ep3 = new Especie("popular3", TipoBicho.CHOCOLATE);
			ep4 = new Especie("popular4", TipoBicho.ELECTRICIDAD);
			ep5 = new Especie("popular5", TipoBicho.FUEGO);
			ep6 = new Especie("popular6", TipoBicho.PLANTA);
			ep7 = new Especie("popular7", TipoBicho.TIERRA);
			ep8 = new Especie("popular8", TipoBicho.AGUA);
			ep9 = new Especie("popular9", TipoBicho.AIRE);
			ep10 = new Especie("popular10", TipoBicho.CHOCOLATE);
			ep11 = new Especie("popular11", TipoBicho.ELECTRICIDAD);
			
			this.populares = new ArrayList<Especie>(Arrays.asList(ep1, ep2, ep3, ep4, ep5, ep6, ep7, ep8, ep9, ep10, ep11));
			
			ei1 = new Especie("impopular1", TipoBicho.AGUA);
			ei2 = new Especie("impopular2", TipoBicho.AIRE);
			ei3 = new Especie("impopular3", TipoBicho.CHOCOLATE);
			ei4 = new Especie("impopular4", TipoBicho.ELECTRICIDAD);
			ei5 = new Especie("impopular5", TipoBicho.FUEGO);
			ei6 = new Especie("impopular6", TipoBicho.PLANTA);
			ei7 = new Especie("impopular7", TipoBicho.TIERRA);
			ei8 = new Especie("impopular8", TipoBicho.AGUA);
			ei9 = new Especie("impopular9", TipoBicho.AIRE);
			ei10 = new Especie("impopular10", TipoBicho.CHOCOLATE);
			ei11 = new Especie("impopular11", TipoBicho.ELECTRICIDAD);
			
			this.impopulares = new ArrayList<Especie>(Arrays.asList(ei1, ei2, ei3, ei4, ei5, ei6, ei7, ei8, ei9, ei10, ei11));
			
			Entrenador ent1, ent2, ent3, ent4, ent5, ent6, ent7, ent8, ent9, ent10, ent11;
			
			ent1 = new Entrenador("EntrenadorTest1");
			ent2 = new Entrenador("EntrenadorTest2");
			ent3 = new Entrenador("EntrenadorTest3");
			ent4 = new Entrenador("EntrenadorTest4");
			ent5 = new Entrenador("EntrenadorTest5");
			ent6 = new Entrenador("EntrenadorTest6");
			ent7 = new Entrenador("EntrenadorTest7");
			ent8 = new Entrenador("EntrenadorTest8");
			ent9 = new Entrenador("EntrenadorTest9");
			ent10 = new Entrenador("EntrenadorTest10");
			ent11 = new Entrenador("EntrenadorTest11");
			
			List<Entrenador> entrenadores = new ArrayList<Entrenador>(Arrays.asList(ent1, ent2, ent3, ent4, ent5, ent6, ent7, ent8, ent9, ent10, ent11));
			
			//Persisto todas las especies
			for(int i=0; i<11; i++)
				especieDAO.saveEspecie(populares.get(i));
			
			//Persisto todos los bichos populares y además los asgino a los entrenadores
			for(int i=0; i<11; i++) {
				for(int j=0; j<=i; j++) {
					Entrenador entrenador = entrenadores.get(j);
					Especie especie = populares.get(i);
					Bicho bicho = new Bicho(especie);
					
					this.bichoDAO.saveBicho(bicho);
					
					entrenador.agregarBichoCapturado(bicho);
				}
			}
			
			//Persisto todos los entrenadores
			for(int i=0; i<11; i++)
				entrenadorDAO.saveEntrenador(entrenadores.get(i));
			
			
			Guarderia gua1, gua2, gua3, gua4, gua5, gua6, gua7, gua8, gua9, gua10, gua11;
			
			gua1 = new Guarderia("Guarderia1");
			gua2 = new Guarderia("Guarderia2");
			gua3 = new Guarderia("Guarderia3");
			gua4 = new Guarderia("Guarderia4");
			gua5 = new Guarderia("Guarderia5");
			gua6 = new Guarderia("Guarderia6");
			gua7 = new Guarderia("Guarderia7");
			gua8 = new Guarderia("Guarderia8");
			gua9 = new Guarderia("Guarderia9");
			gua10 = new Guarderia("Guarderia10");
			gua11 = new Guarderia("Guarderia11");
			
			List<Guarderia> guarderias = new ArrayList<Guarderia>(Arrays.asList(gua1, gua2, gua3, gua4, gua5, gua6, gua7, gua8, gua9, gua10, gua11));
			
			//Persisto todos los bichos impopulares y además los asgino a las guarderias
			for(int i=0; i<11; i++) {
				for(int j=0; j<=i; j++) {
					Guarderia guarderia = guarderias.get(j);
					Especie especie = impopulares.get(i);
					Bicho bicho = new Bicho(especie);
					
					this.bichoDAO.saveBicho(bicho);
					
					guarderia.recibirBichoAbandonado(bicho);
				}
			}
			
			//Persisto todas los guarderias
			for(int i=0; i<11; i++)
				guarderiaDAO.saveLugar(guarderias.get(i));
			
			return null;
		});
	}

}
