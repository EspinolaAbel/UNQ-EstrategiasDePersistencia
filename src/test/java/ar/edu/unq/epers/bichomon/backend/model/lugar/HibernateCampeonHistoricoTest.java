package ar.edu.unq.epers.bichomon.backend.model.lugar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateBichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateCampeonHistoricoTest {

	private HibernateCampeonHistoricoDAO campeonHistoricoDAO;
	private BichoDAO bichoDAO;
	private LugarDAO lugarDAO;
	
	@Before
	public void setUp() {
		this.lugarDAO = new HibernateLugarDAO();
		this.bichoDAO = new HibernateBichoDAO();
		this.campeonHistoricoDAO = new HibernateCampeonHistoricoDAO();
	}

	@Test
	public void dados10BichosCampeonesHistoricosLosPersistoEnLaBBDDYComprueboQueSeHayaPersistidoDeManeraCorrecta() {

		Runner.runInSession(() -> {
			Dojo dojo;
			Bicho bicho;
			Especie especie = new Especie("EspecieTest", TipoBicho.AGUA);
			CampeonHistorico ch;
			dojo = new Dojo("DojoTest");
			this.lugarDAO.saveLugar(dojo);
			int fechaDondeFueCoronadoElCampeonAnterior = -1;
			
			for(int i=0; i<10; i++) {
				bicho = new Bicho(especie);
				this.bichoDAO.saveBicho(bicho);
				
				ch = new CampeonHistorico(dojo, bicho);
				this.campeonHistoricoDAO.saveCampeonHistorico(ch);
				
				assertEquals(ch.getBichoCampeon(), bicho);
				assertEquals(ch.getLugarDondeFueCoronadoCampeon(), dojo);
				assertTrue(ch.getFechaCoronadoCampeon() > fechaDondeFueCoronadoElCampeonAnterior);
				
				fechaDondeFueCoronadoElCampeonAnterior = ch.getFechaCoronadoCampeon();
			}
			return null;
		});
	}

}
