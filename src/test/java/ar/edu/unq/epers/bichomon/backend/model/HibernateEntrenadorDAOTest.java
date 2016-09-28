package ar.edu.unq.epers.bichomon.backend.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class HibernateEntrenadorDAOTest {

	@Test
	public void test() {
		EntrenadorDAO entrenadorDAO = new HibernateEntrenadorDAO();
		
		Entrenador entrenador = new Entrenador("EntrenadorTest");
		
		entrenador.agregarBichoCapturado(new Bicho());
		entrenador.agregarBichoCapturado(new Bicho());
		entrenador.agregarBichoCapturado(new Bicho());
		entrenador.agregarBichoCapturado(new Bicho());
		entrenador.agregarBichoCapturado(new Bicho());
	
		entrenador.setExperiencia(10);
		Nivel nivel = new Nivel(1,10,10);
		entrenador.setNivelActual(nivel);
		
		Pueblo pueblo = new Pueblo("PuebloTest");
		entrenador.setUbicacionActual(pueblo);
		
		Runner.runInSession(() -> {
			entrenadorDAO.saveEntrenador(entrenador);
			return null;
		});
		
//		Entrenador entrenadorRecuperado =
//		Runner.runInSession(() -> {
//			return entrenadorDAO.getEntrenador("EntrenadorTest");
//		});
//		
//		assertEquals(entrenadorRecuperado.getBichosCapturados().size(), 5);
//		assertEquals(entrenadorRecuperado.getExperiencia(), 10);
//		assertEquals(entrenadorRecuperado.getNombre(), "EntrenadorTest");
	}

}
