package un.paquete.a.borrar.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.*;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEspecieDAOTest {

	@Test
	public void testGuardar() {
		EspecieDAO especieDAO = new HibernateEspecieDAO();
		Especie e = new Especie("EspecieDeTesteo", TipoBicho.TIERRA);
		
		e.agregarCondicionDeEvolucion(new CondicionBasadaEnEnergia(21));
		e.agregarCondicionDeEvolucion(new CondicionBasadaEnNivel(2));
		e.agregarCondicionDeEvolucion(new CondicionBasadaEnEdad(5));
		e.agregarCondicionDeEvolucion(new CondicionBasadaEnVictorias(15));
		
		Runner.runInSession(() -> {
			especieDAO.saveEspecie(e);			
			return null;
		});


		Especie esp =		
		Runner.runInSession(() -> {
			return (Especie) especieDAO.getEspecie("EspecieDeTesteo");
		});
		
		assertEquals(esp.getNombre(), "EspecieDeTesteo");
		assertEquals(esp.getCondicionesDeEvolucion().size(), 4);
	}

}
