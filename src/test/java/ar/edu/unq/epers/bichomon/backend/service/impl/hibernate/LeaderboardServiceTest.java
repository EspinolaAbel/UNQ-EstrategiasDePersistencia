package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import static org.junit.Assert.*;

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
import ar.edu.unq.epers.bichomon.backend.model.lugar.Dojo;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;

public class LeaderboardServiceTest {

	private LeaderboardService leaderboardService;

	@Before
	public void setUp() {
		this.leaderboardService = new LeaderboardService();
	}
	
	@After
	public void reiniciarBD() {
		SessionFactoryProvider.destroy();
	}
	
	@Test
	public void recuperoTodosLosEntrenadoresQueTenganAlgunBichoQueSeaCampeonActualmenteEnAlgunDojoOrdenadosPorAquellosBichosQueSonCampeonesHaceMasTiempo() {
		Entrenador ent1, ent2, ent3;
		Bicho campeon1, campeon2, campeon3, bic4, bic5, bic6, bic7, bic8, bic9;
		Especie esp1, esp2, esp3;
		Dojo doj1, doj2, doj3;
		
		ent1 = new Entrenador("ent1");	ent2 = new Entrenador("ent2");	ent3 = new Entrenador("ent3");
		
		esp1 = new Especie("esp1", TipoBicho.AGUA); esp2 = new Especie("esp2", TipoBicho.AIRE); esp3 = new Especie("esp3", TipoBicho.CHOCOLATE);
		
		campeon1 = new Bicho(esp1); campeon2 = new Bicho(esp2); campeon3 = new Bicho(esp3);
		bic4 = new Bicho(esp1); bic5 = new Bicho(esp2); bic6 = new Bicho(esp3);
		bic7 = new Bicho(esp1); bic8 = new Bicho(esp2); bic9 = new Bicho(esp3);
		
		doj1 = new Dojo("doj1"); doj2 = new Dojo("doj2"); doj3 = new Dojo("doj3");
		
		ent1.agregarBichoCapturado(bic4);
		ent1.agregarBichoCapturado(bic5);
		ent1.agregarBichoCapturado(bic6);
		
		ent2.agregarBichoCapturado(campeon1);
		ent2.agregarBichoCapturado(bic7);
		ent2.agregarBichoCapturado(bic8);
		
		ent3.agregarBichoCapturado(campeon2);
		ent3.agregarBichoCapturado(campeon3);
		ent3.agregarBichoCapturado(bic9);
		
		doj1.setCampeonActual(campeon1);
		doj2.setCampeonActual(campeon2);
		doj3.setCampeonActual(campeon3);
		
		EntrenadorDAO entDAO = new HibernateEntrenadorDAO();
		BichoDAO bicDAO = new HibernateBichoDAO();
		EspecieDAO espDAO = new HibernateEspecieDAO();
		LugarDAO lugDAO = new HibernateLugarDAO();
		
		Runner.runInSession(() -> {
			espDAO.saveEspecie(esp1);	espDAO.saveEspecie(esp2);	espDAO.saveEspecie(esp3);
			
			bicDAO.saveBicho(campeon1);	bicDAO.saveBicho(campeon2);	bicDAO.saveBicho(campeon3);
			bicDAO.saveBicho(bic4);	bicDAO.saveBicho(bic5);	bicDAO.saveBicho(bic6);	
			bicDAO.saveBicho(bic7);	bicDAO.saveBicho(bic8);	bicDAO.saveBicho(bic9);	
			
			entDAO.saveEntrenador(ent1);	entDAO.saveEntrenador(ent2);	entDAO.saveEntrenador(ent3);	
			
			lugDAO.saveLugar(doj1);	lugDAO.saveLugar(doj2);	lugDAO.saveLugar(doj3);
			
			return null;
		});
	
		List<Entrenador> entrenadoresRecuperados = this.leaderboardService.campeones();
		
		assertEquals(entrenadoresRecuperados.size(), 2);
		assertEquals(entrenadoresRecuperados.get(0), ent1);
		assertEquals(entrenadoresRecuperados.get(0), ent2);
	}

}
