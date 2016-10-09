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
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateCampeonHistoricoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.lugar.CampeonHistorico;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Dojo;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.service.runner.Truncator;

public class LeaderboardServiceTest {

	private LeaderboardService leaderboardService;
	private Entrenador ent1, campXMasTiempo,ent2;
	private Bicho bic5, bic6, bic7, bic8, bic9;
	private Especie esp1, esp2, especieLider;
	private Bicho campeon1, campeon2, campeon3, campeon4;
	private Dojo doj1, doj2, doj3;
	private EntrenadorDAO entDAO;
	private BichoDAO bicDAO;
	private EspecieDAO espDAO;
	private LugarDAO lugDAO;

	@Before
	public void setUp() {
		this.leaderboardService = new LeaderboardService();
		
		//Datos iniciales
		ent1 = new Entrenador("ent1");	ent2 = new Entrenador("ent2");	campXMasTiempo = new Entrenador("Campeon x mas tiempo");
		
		esp1 = new Especie("esp1", TipoBicho.AGUA); esp2 = new Especie("esp2", TipoBicho.AIRE); especieLider = new Especie("EspecieLider", TipoBicho.CHOCOLATE);
		
		campeon1 = new Bicho(especieLider); campeon2 = new Bicho(especieLider); campeon3 = new Bicho(esp1);
		campeon4 = new Bicho(esp2); bic5 = new Bicho(esp1); bic6 = new Bicho(esp2);
		bic7 = new Bicho(esp1); bic8 = new Bicho(esp2); bic9 = new Bicho(esp1);
		
		doj1 = new Dojo("doj1"); doj2 = new Dojo("doj2"); doj3 = new Dojo("doj3");
		
		Runner.runInSession(() -> {
			this.entDAO = new HibernateEntrenadorDAO();
			this.bicDAO = new HibernateBichoDAO();
			this.espDAO = new HibernateEspecieDAO();
			this.lugDAO = new HibernateLugarDAO();
			
			espDAO.saveEspecie(esp1);	espDAO.saveEspecie(esp2);	espDAO.saveEspecie(especieLider);
			
			bicDAO.saveBicho(campeon1);	bicDAO.saveBicho(campeon2);	bicDAO.saveBicho(campeon3);
			bicDAO.saveBicho(campeon4);	bicDAO.saveBicho(bic5);	bicDAO.saveBicho(bic6);	
			bicDAO.saveBicho(bic7);	bicDAO.saveBicho(bic8);	bicDAO.saveBicho(bic9);	
			
			entDAO.saveEntrenador(ent1);	entDAO.saveEntrenador(campXMasTiempo);	entDAO.saveEntrenador(ent2);	
			
			lugDAO.saveLugar(doj1);	lugDAO.saveLugar(doj2);	lugDAO.saveLugar(doj3);

			//Entrenador sin campeones
			ent1.agregarBichoCapturado(bic5);
			ent1.agregarBichoCapturado(bic6);
			ent1.agregarBichoCapturado(bic7);
			
			//Entrenador con 1 campeon -- Este entrenador tiene el bicho campeon hace más tiempo
			campXMasTiempo.agregarBichoCapturado(campeon1);
			campXMasTiempo.agregarBichoCapturado(bic8);
			campXMasTiempo.agregarBichoCapturado(bic9);
			
			//Entrenador con 2 campeones
			ent2.agregarBichoCapturado(campeon2);
			ent2.agregarBichoCapturado(campeon3);
			ent2.agregarBichoCapturado(campeon4);

			doj1.setCampeonActual(campeon1);
			doj2.setCampeonActual(campeon2);
			doj3.setCampeonActual(campeon3);
			
			return null;
		});
	}
	
	@After
	public void cleanUp() {
		Truncator.cleanUpTables();
	}
	
	@Test
	public void recuperoTodosLosEntrenadoresQueTenganAlgunBichoQueSeaCampeonActualmenteEnAlgunDojoOrdenadosPorAquellosBichosQueSonCampeonesHaceMasTiempo() {
	
		List<Entrenador> entrenadoresRecuperados = this.leaderboardService.campeones();
		
		assertEquals(entrenadoresRecuperados.size(), 2);
		assertEquals(entrenadoresRecuperados.get(0), campXMasTiempo);
		assertEquals(entrenadoresRecuperados.get(1), ent2);
	}
	
	@Test
	public void consultoPorLaEspecieQueTengaMasBichosQueHayanSidoCampeonesAlgunaVezIgnorandoSiUnMismoBichoSalioCampeonMasDeUnaVez() {
		HibernateCampeonHistoricoDAO chDAO = new HibernateCampeonHistoricoDAO();
		Runner.runInSession(()->{
			for(int i=0; i<5; i++)
				chDAO.saveCampeonHistorico(new CampeonHistorico(doj3, campeon3));
			return null;
		});
		Especie especieRecuperada = this.leaderboardService.especieLider();
		
		assertEquals(especieRecuperada, this.especieLider);
	}
	
	
	@Test
	public void consultoPorLaListaDe10PrimerosEntrenadoresOrdenadosDeMayorAMenorSegunElPoderCombinadoDeTodosSusBichos(){
		this.cargarEnBBDD20EntrenadoresConTresBichosDondeCadaEntrenadorEsSuperiorEnPoderDeCombateEsSuperiorAlEntrenadorAnterior();
		
		List<Entrenador> entrenadoresLideres = this.leaderboardService.lideres();
		
		for (int i=0; i<10; i++)
			assertEquals(entrenadoresLideres.get(i).getNombre(), "LiderTest"+(19-i));
	}
	
//MÉTODOS AUXILIARES PARA TESTS
	
	public void cargarEnBBDD20EntrenadoresConTresBichosDondeCadaEntrenadorEsSuperiorEnPoderDeCombateEsSuperiorAlEntrenadorAnterior() {
		Runner.runInSession(() -> {
			Entrenador ent;
			Bicho b1, b2, b3;
			for (int i=0; i<20; i++) {
				ent = new Entrenador("LiderTest"+i);
				b1 = new Bicho(esp1);	b1.setEnergia(3*i + 1);	ent.agregarBichoCapturado(b1);
				b2 = new Bicho(esp1);	b2.setEnergia(3*i + 2);	ent.agregarBichoCapturado(b2);
				b3 = new Bicho(esp1);	b3.setEnergia(3*i + 3);	ent.agregarBichoCapturado(b3);
				this.bicDAO.saveBicho(b1);
				this.bicDAO.saveBicho(b2);
				this.bicDAO.saveBicho(b3);
				this.entDAO.saveEntrenador(ent);
			}
			return null;
		});
	}

}
