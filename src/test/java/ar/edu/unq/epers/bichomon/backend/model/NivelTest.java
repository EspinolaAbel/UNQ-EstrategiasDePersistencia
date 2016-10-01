package ar.edu.unq.epers.bichomon.backend.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class NivelTest {
	
//REDEFINICINES DE EQUALS Y HASHCODE 
	
	@Test
	public void dadosDosNivelesLosComparoConEqualsParaComprobarSiSonNivelesIgualesYMeRespondeTrue() {
		Nivel nivelOriginal = this.nuevoNivelOriginal();
		Nivel nivelComparativo = this.nuevoNivelOriginal();

		assertEquals(nivelOriginal, nivelComparativo);
	}


	@Test
	public void dadosDosNivelesSimilaresPeroConDistintoNumeroDeNivelLosComparoPorEqualsYMeRespondeFalse() {	
		Nivel nivelOriginal = this.nuevoNivelOriginal();
		Nivel nivelComparativo = this.nuevoNivelOriginal();
		
		//Distinto nivel
		nivelComparativo.setNumeroDeNivel(999);
		assertNotEquals(nivelOriginal, nivelComparativo);
	}
	
	@Test
	public void dadosDosNivelesSimilaresPeroConDistintoPuntosParaSubirDeNivelLosComparoPorEqualsYMeRespondeFalse() {	
		Nivel nivelOriginal = this.nuevoNivelOriginal();
		Nivel nivelComparativo = this.nuevoNivelOriginal();
		
		//Distinto puntaje para subir de nivel
		nivelComparativo.setPuntosParaSubirDeNivel(999);
		assertNotEquals(nivelOriginal, nivelComparativo);
	}
		
	@Test
	public void dadosDosNivelesSimilaresPeroConDistintaMaxCantBichosLosComparoPorEqualsYMeRespondeFalse() {	
		Nivel nivelOriginal = this.nuevoNivelOriginal();
		Nivel nivelComparativo = this.nuevoNivelOriginal();
		
		//Distinta máxima cantidad de bichos
		nivelComparativo.setMaxCantidadDeBichos(999);
		assertNotEquals(nivelOriginal, nivelComparativo);
	}
	
	@Test
	public void dadosDosNivelesSimilaresPeroConDistintoSiguienteNivelLosComparoPorEqualsYMeRespondeFalse() {	
		Nivel nivelOriginal = this.nuevoNivelOriginal();
		Nivel nivelComparativo = this.nuevoNivelOriginal();
		
		//Distinto siguiente nivel
		nivelComparativo.setSiguienteNivel(new Nivel());
		assertNotEquals(nivelOriginal, nivelComparativo);
	}
	
	
	@Test
	public void dadosDosNivelesComparoSusHashCodeYResultanSerIguales() {
		Nivel nivelOriginal = this.nuevoNivelOriginal();
		Nivel nivelComparativo = this.nuevoNivelOriginal();
		
		assertEquals(nivelOriginal.hashCode(), nivelComparativo.hashCode());
	}
	
	
	@Test
	public void dadosDosNivelesComparoSusHashCodeYResultanSerDistintos() {
		Nivel nivelOriginal = this.nuevoNivelOriginal();
		Nivel nivelComparativo = this.nuevoNivelOriginal();
		
		nivelComparativo.setNumeroDeNivel(999);
		assertNotEquals(nivelOriginal.hashCode(), nivelComparativo.hashCode());
	}
	
	
//TESTS AUXILIARES PARA TESTS
	
	private Nivel nuevoNivelOriginal() {
		Nivel siguienteNivel = new Nivel(2, 200, 20, null);
		return new Nivel(1, 100, 10, siguienteNivel);
	}
	
}
