package ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnVictorias;

public class HibernateCondicionBasadaEnVictoriasDAOTest extends HibernateCondicionDeEvolucionDAOTest {

	@Before
	public void setUp() {
		super.setUp();
		this.condicionOriginal = new CondicionBasadaEnVictorias(100);
	}

}
