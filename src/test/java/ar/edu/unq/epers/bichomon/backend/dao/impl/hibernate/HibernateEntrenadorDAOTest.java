package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEntrenadorDAOTest {
	private Entrenador entrenador;
	
	private Entrenador entrenador2;
	private Entrenador entrenador3;
	private HibernateEntrenadorDAO entrenadorDao;
	
	@Before
	public void prepare(){
		this.entrenador= new Entrenador("Juan");
		this.entrenadorDao= new  HibernateEntrenadorDAO();
		this.entrenador2= new Entrenador("Pedro");
		this.entrenador3= new Entrenador("van");
	}
	
	/**
	 * Test para Entrenador dao, los tres metodos se testeean juntos,
	 * save entrenador
	 * getEntrenador
	 * getAllEntrenadores
	 */
//	 * deberia limpiarse un poco y reorganizarlo	
	@Test
	public void testSaveEntreneador() {
		Runner.runInSession(() -> {
			entrenadorDao.saveEntrenador(entrenador);
			entrenadorDao.saveEntrenador(entrenador3);
			entrenadorDao.saveEntrenador(entrenador2);
			
			entrenador2= entrenadorDao.getEntrenador("Juan");
			assertEquals(entrenador2, entrenador);
			assertNotEquals(entrenador2, entrenador3);
			
			List <Entrenador> e= entrenadorDao.getAllEntrenadores();
	
			assertEquals (e.size(),3);
			
			return null;
		});
	//este test solo fue para probar si funciona el Entrenador dao, los tres metodos
		//corrieron  bien y me   trajo todos  los entrenadores
		// le metodo  list() se queja, deprecated , pero igual me trae los elementos
	}

}
