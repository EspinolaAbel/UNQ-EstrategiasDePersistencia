package ar.edu.unq.epers.bichomon.backend.model.lugar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;

public class SorteoEspeciesTest {

	@Before
	public void setUp() {
		
	}

	@Test
	public void test() {
	}
	
	
//METODOS AUXILIARES PARA TEST
	
	public EspecieConProbabilidad especieConProbabilidad(Integer prob) {
		Especie e = new Especie("nn", TipoBicho.FUEGO);
		EspecieConProbabilidad ep = new EspecieConProbabilidad(e, prob);
		return ep;
	}
}
