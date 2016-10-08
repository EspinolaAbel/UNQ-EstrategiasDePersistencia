package ar.edu.unq.epers.bichomon.backend.model.lugar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateCampeonHistoricoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateCampeonHistoricoDAOTest {

	private HibernateCampeonHistoricoDAO campeonHistoricoDAO;
	private BichoDAO bichoDAO;
	private LugarDAO lugarDAO;
	private Bicho bicho;
	
	@Before
	public void setUp() {
		this.lugarDAO = new HibernateLugarDAO();
		this.bichoDAO = new HibernateBichoDAO();
		this.campeonHistoricoDAO = new HibernateCampeonHistoricoDAO();
	}
	
	@Test
	public void dadoUnCampeonHistoricoLoPersistoEnMiBDYComprueboQueSeHayaPersistidoCorrectamente() {
		Dojo dojo;
		Especie especie = new Especie("EspecieTest", TipoBicho.AGUA);
		this.bicho = new Bicho(especie);
		dojo = new Dojo("DojoTest");
		CampeonHistorico ch = new CampeonHistorico(dojo, bicho);
		
		Runner.runInSession(()->{
			this.bichoDAO.saveBicho(bicho);
			this.lugarDAO.saveLugar(dojo);
			this.campeonHistoricoDAO.saveCampeonHistorico(ch);
			return null;
		});
		
		CampeonHistorico campeonRecuperado = Runner.runInSession(() -> {			
			return this.campeonHistoricoDAO.getUltimoCampeonDelDojo("DojoTest");
		});

		assertEquals(campeonRecuperado.getBichoCampeon(), bicho);
		assertEquals(campeonRecuperado.getLugarDondeEsCampeon().getNombre(), dojo.getNombre());
		assertEquals(campeonRecuperado.getFechaCoronadoCampeon(), ch.getFechaCoronadoCampeon());
	}

}
