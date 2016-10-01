package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import org.junit.Before;

import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnNivel;

public class HibernateCondicionBasadaEnNivelDAOTest extends HibernateCondicionDeEvolucionDAOTest {

	@Before
	public void setUp() {
		super.setUp();
		this.condicionOriginal = new CondicionBasadaEnNivel(5);
	}

}
