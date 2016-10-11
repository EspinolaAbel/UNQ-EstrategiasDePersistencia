package ar.edu.unq.epers.bichomon.backend.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EntrenadorTest {

	private Entrenador entrenador;
	private Bicho bichoGenerico;

	@Before
	public void setUp() {
		entrenador = new Entrenador();
		bichoGenerico = new Bicho(new Especie(), "Especie generica");
	}

	
	/** Dado un bicho lo agrego a la lista de bichos del entrenador.
	 * @author ae */
	@Test
	public void dadoUnBichoLoAgregoALaListaDeBichosCapturadosDelEntrenadorYLoAgrega() {
		entrenador.agregarBichoCapturado(bichoGenerico);
		
		assertTrue(entrenador.getBichosCapturados().contains(bichoGenerico));
		assertEquals(bichoGenerico.getOwner(), entrenador);
	}
	
	/** Dado un bicho que fue capturado por el entrenador, el entrenador lo descarta y compruebo que
	 *  - el entrenador ya no tenga dicho bicho en su lista de bichos capturados y;
	 *  - el bicho descartado ya no tenga entrenado (entrenador == null).
	 * @author ae **/
	@Test
	public void dadoUnBichoQueFueCapturadoPorElEntrenadorEsDescartado() {
		entrenador.agregarBichoCapturado(bichoGenerico);
		
		entrenador.descartarBichoCapturado(bichoGenerico);
		
		assertFalse(entrenador.getBichosCapturados().contains(bichoGenerico));
		assertNull(bichoGenerico.getOwner());
	}
	
//TESTS REDEFINICION EQUALS Y HASHCODE
	
	@Test
	public void dadosDosBichosLosComparoConEqualsParaComprobarSiSonIgualesYMeRespondeTrue() {
		Entrenador entrenadorOriginal = this.nuevoEntrenadorOriginal();
		Entrenador entrenadorComparativo = this.nuevoEntrenadorOriginal();
		
		assertEquals(entrenadorOriginal, entrenadorComparativo);
	} 
	
	@Test
	public void dadosDosBichosLosComparoConEqualsParaComprobarSiSonIgualesYMeRespondeFalseDebidoAQueTienenDistintoId() {
		Entrenador entrenadorOriginal = this.nuevoEntrenadorOriginal();
		Entrenador entrenadorComparativo = this.nuevoEntrenadorOriginal();
		entrenadorComparativo.setNombre("EntrenadorComparativo");
		
		assertNotEquals(entrenadorOriginal, entrenadorComparativo);
	}
	
	@Test
	public void dadosDosBichosLosComparoSusHashCodeParaComprobarSiSonIgualesYMeRespondeTrue() {
		Entrenador entrenadorOriginal = this.nuevoEntrenadorOriginal();
		Entrenador entrenadorComparativo = this.nuevoEntrenadorOriginal();
		
		assertEquals(entrenadorOriginal.hashCode(), entrenadorComparativo.hashCode());
	} 
	
	@Test
	public void dadosDosBichosLosComparoSusHashCodeParaComprobarSiSonIgualesYMeRespondeFalse() {
		Entrenador entrenadorOriginal = this.nuevoEntrenadorOriginal();
		Entrenador entrenadorComparativo = this.nuevoEntrenadorOriginal();
		entrenadorComparativo.setNombre("EntrenadorComparativo");
		
		assertNotEquals(entrenadorOriginal.hashCode(), entrenadorComparativo.hashCode());
	}

	
	
	@Test
	public void dadosUnEntrenadorQueEstaEnCondicionesDeCambiarDeNivelLoHace(){
		
		// genero dos niveles de experiencia  para test
		Nivel nivel1 = new Nivel(1,100, 20);
		Nivel nivel2 = new Nivel(2,200, 10);
		nivel1.setSiguienteNivel(nivel2);
		nivel2.setSiguienteNivel(nivel2);

		this.entrenador.setNivelActual(nivel1);
		
		//inicialmente no lo puede hacer, pero luego adquiere experiencia
		this.entrenador.setExperiencia(90);
		
		
		assertEquals(this.entrenador.getNivelActual(), nivel1);
		
		this.entrenador.aumentarExperiencia(20);
		this.entrenador.aumentarDeNivelSiTieneExperiencia();
		
		assertEquals(nivel2, this.entrenador.getNivelActual());
	}

//MÉTODOS AUXILIARES PARA TEST		
	
	private Entrenador nuevoEntrenadorOriginal() {
		Entrenador e = new Entrenador();
		e.setNombre("EntrenadorOriginal");
		return e;
	} 
}
