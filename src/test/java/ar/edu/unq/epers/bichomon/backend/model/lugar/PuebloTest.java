package ar.edu.unq.epers.bichomon.backend.model.lugar;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;

public class PuebloTest {

	private Pueblo pueblo;

	@Before
	public void setUp() throws Exception {
		//el pueblp se  crea con la lista de speciesConProbabilidad vacia
		this.pueblo = new Pueblo("PuebloGenerico"); 
	}

	
	/** Dado un pueblo intento abandonar un bicho en él. Como en este lugar no se permite abandonar
	 * bichos se lanza una UbicacionIncorrectaException. */
	@Test
	public void dadoUnPuebloIntentoAbandonarUnBichoYSeLanzaUnaUbicacionIncorrectaException() {
		Bicho bicho = new Bicho(new Especie());
		
		try {
			pueblo.recibirBichoAbandonado(bicho);
			fail("Nunca se lanzó la exception...");
		}
		catch(UbicacionIncorrectaException e) {
			assertEquals(e.getMessage(), "En el lugar \""+pueblo.getNombre()+"\" no se permite abandonar bichos.");
		}
	}
	
	/**
	 * retornarBichoDelLugarDevuelve nul si la lista de especie esta vacia mo en su defecto devuelve un bicho 
	 * segun sea sorteado
	 */
	@Test 
	public void dadoUnPuebloSinEspeciesConProbabilidadNoDevuelveBichos(){
		assertNull(this.pueblo.retornarUnBichoDelLugar(null));// no le paso ningun entrenador  xq no lo presisisa
	}
	
	
//TESTS REDEFINICION EQUALS Y HASHCODE

	@Test
	public void dadosDosEntrenadoresLosComparoConEqualsParaComprobarSiSonIgualesYMeRespondeTrue() {
		Pueblo puebloOriginal = this.nuevoPuebloOriginal();
		Pueblo puebloComparativo = this.nuevoPuebloOriginal();
		
		assertEquals(puebloOriginal, puebloComparativo);
	} 
	
	@Test
	public void dadosDosEntrenadoresLosComparoConEqualsParaComprobarSiSonIgualesYMeRespondeFalseDebidoAQueTienenDistintoId() {
		Pueblo puebloOriginal = this.nuevoPuebloOriginal();
		Pueblo puebloComparativo = this.nuevoPuebloOriginal();
		puebloComparativo.setNombre("PuebloComparativo");
		
		assertNotEquals(puebloOriginal, puebloComparativo);
	}
	
	@Test
	public void dadosDosEntrenadoresLosComparoSusHashCodeParaComprobarSiSonIgualesYMeRespondeTrue() {
		Pueblo puebloOriginal = this.nuevoPuebloOriginal();
		Pueblo puebloComparativo = this.nuevoPuebloOriginal();
		
		assertEquals(puebloOriginal.hashCode(), puebloComparativo.hashCode());
	} 
	
	@Test
	public void dadosDosEntrenadoresLosComparoSusHashCodeParaComprobarSiSonIgualesYMeRespondeFalse() {
		Pueblo puebloOriginal = this.nuevoPuebloOriginal();
		Pueblo puebloComparativo = this.nuevoPuebloOriginal();
		puebloComparativo.setNombre("PuebloComparativo");
		
		assertNotEquals(puebloOriginal.hashCode(), puebloComparativo.hashCode());
	}
	
//		************************************
//		************************************
//		*** MÉTODOS AUXILIARES PARA TEST ***	
//		*** ---------------------------- ***
//		************************************
//		************************************		
	
	private Pueblo nuevoPuebloOriginal() {
		Pueblo e = new Pueblo();
		e.setNombre("PuebloOriginal");
		return e;
	} 
}
