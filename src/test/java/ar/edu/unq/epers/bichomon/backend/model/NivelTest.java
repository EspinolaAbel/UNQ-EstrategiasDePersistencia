package ar.edu.unq.epers.bichomon.backend.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NivelTest {
	
	private Nivel nivelOriginal;
	private Nivel nivelComparativo;
	
	@Test
	public void dadosDosNivelesLosComparoConEqualsParaComprobarSiSonNivelesIgualesYMeRespondeTrue() {

		
		this.igualarNiveles(nivelOriginal, nivelComparativo);
		
		assertEquals(nivelOriginal, nivelCopia);
	}

	private void igualarNiveles(Nivel nivelOriginal, Nivel nivelComparativo) {
		Nivel siguienteNivel = new Nivel(2, 200, 20);
		nivelOriginal = new Nivel(1, 100, 10, siguienteNivel);
		nivelComparativo = new Nivel(1, 100, 10, siguienteNivel);
	}

	@Test
	public void dadosDosNivelesLosComparoConEqualsParaComprobarSiSonNivelesIgualesYMeRespondeFalse() {
		Nivel siguienteNivel = new Nivel(2, 200, 20); 
		
		Nivel nivelOriginal = new Nivel(1, 100, 10, siguienteNivel);
		Nivel nivelComparativo = new Nivel(1, 100, 10, siguienteNivel);
		
		//Distinto nivel
		nivelOriginal.setNumeroDeNivel(2);
		
		assertNotEquals(nivelOriginal, nivelComparativo);
	}
	
}
