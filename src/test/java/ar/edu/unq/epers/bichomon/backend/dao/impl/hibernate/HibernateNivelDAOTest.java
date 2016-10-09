package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.NivelDAO;
import ar.edu.unq.epers.bichomon.backend.model.Nivel;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.SessionFactoryProvider;
import ar.edu.unq.epers.bichomon.backend.service.runner.Truncator;

public class HibernateNivelDAOTest {

	private NivelDAO nivelDao;
	
	
	@Before
	public void setUp() {
		this.nivelDao = new HibernateNivelDAO();
	}
	
	@After
	public void cleanUp() {
		Truncator.cleanUpTables();
	}
	
	
	@Test
	public void dadoUnNivelLoPersistoEnLaBaseDeDatosBichomonYLuegoLoRecuperoDeLaMismaParaComprobarQueSeHayaPersistidoDeManeraCorrecta(){
		Nivel nivelSiguienteDeNivelOriginal = new Nivel(2, 200, 20, null);
		Nivel nivelOriginal = new Nivel(1, 100, 10, nivelSiguienteDeNivelOriginal);		
		
		Runner.runInSession( () -> {
			this.nivelDao.saveNivel(nivelOriginal);
			return null;
		});
			
		
		Nivel nivelRecuperado = Runner.runInSession( () -> {
									return this.nivelDao.getNivel(1);
								});
		
		assertEquals(nivelOriginal, nivelRecuperado);
	}

}
