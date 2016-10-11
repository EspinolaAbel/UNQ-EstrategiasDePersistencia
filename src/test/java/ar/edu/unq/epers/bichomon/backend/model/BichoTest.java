package ar.edu.unq.epers.bichomon.backend.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BichoTest {

	@Before
	public void setUp() throws Exception {
	}

	//TESTS REDEFINICION EQUALS Y HASHCODE
	
		@Test
		public void dadosDosBichosLosComparoConEqualsParaComprobarSiSonIgualesYMeRespondeTrue() {
			Bicho bichoOriginal = this.nuevoBichoOriginal();
			Bicho bichoComparativo = this.nuevoBichoOriginal();
			
			assertEquals(bichoOriginal, bichoComparativo);
		} 
		
		@Test
		public void dadosDosBichosLosComparoConEqualsParaComprobarSiSonIgualesYMeRespondeFalseDebidoAQueTienenDistintoId() {
			Bicho bichoOriginal = this.nuevoBichoOriginal();
			Bicho bichoComparativo = this.nuevoBichoOriginal();
			bichoComparativo.setId(2);
			
			assertNotEquals(bichoOriginal, bichoComparativo);
		}
		
		@Test
		public void dadosDosBichosLosComparoSusHashCodeParaComprobarSiSonIgualesYMeRespondeTrue() {
			Bicho bichoOriginal = this.nuevoBichoOriginal();
			Bicho bichoComparativo = this.nuevoBichoOriginal();
			
			assertEquals(bichoOriginal.hashCode(), bichoComparativo.hashCode());
		} 
		
		@Test
		public void dadosDosBichosLosComparoSusHashCodeParaComprobarSiSonIgualesYMeRespondeFalse() {
			Bicho bichoOriginal = this.nuevoBichoOriginal();
			Bicho bichoComparativo = this.nuevoBichoOriginal();
			bichoComparativo.setId(2);
			
			assertNotEquals(bichoOriginal.hashCode(), bichoComparativo.hashCode());
		}

//		************************************
//		************************************
//		*** MÉTODOS AUXILIARES PARA TEST ***	
//		*** ---------------------------- ***
//		************************************
//		************************************		
		
		private Bicho nuevoBichoOriginal() {
			Bicho b = new Bicho(new Especie("EspecieTest", TipoBicho.AGUA));
			b.setCantidadDeVictorias(999);
			b.setEnergia(999);
			b.setId(1);
			b.setOwner(new Entrenador("EntrenadorTest"));
			b.setTiempoDesdeSuCaptura(999);
			return b;
		} 

}
