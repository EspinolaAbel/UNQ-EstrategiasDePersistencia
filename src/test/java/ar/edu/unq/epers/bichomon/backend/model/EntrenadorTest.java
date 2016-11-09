package ar.edu.unq.epers.bichomon.backend.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;

public class EntrenadorTest {

	private Entrenador entrenador;
	private Bicho bichoGenerico;

	@Before
	public void setUp() {
		entrenador = new Entrenador();
		bichoGenerico = new Bicho(new Especie(), "Especie generica");
	}

	
	/** Dado un bicho lo agrego a la lista de bichos del entrenador. */
	@Test
	public void dadoUnBichoLoAgregoALaListaDeBichosCapturadosDelEntrenadorYLoAgrega() {
		entrenador.agregarBichoCapturado(bichoGenerico);
		
		assertTrue(entrenador.getBichosCapturados().contains(bichoGenerico));
		assertEquals(bichoGenerico.getOwner(), entrenador);
	}
	
	/** Dado un bicho que fue capturado por el entrenador, el entrenador lo descarta y compruebo que
	 *  - el entrenador ya no tenga dicho bicho en su lista de bichos capturados y;
	 *  - el bicho descartado ya no tenga entrenado (entrenador == null).*/
	@Test
	public void dadoUnBichoQueFueCapturadoPorElEntrenadorEsDescartado() {
		entrenador.agregarBichoCapturado(bichoGenerico);
		
		entrenador.descartarBichoCapturado(bichoGenerico);
		
		assertFalse(entrenador.getBichosCapturados().contains(bichoGenerico));
		assertNull(bichoGenerico.getOwner());
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
		
		//this.entrenador.aumentarExperiencia(20);
		//adquiere 20 puntos de experiencia y luego aumenta el nivel
		this.entrenador.aumentarDeNivelSiTieneExperiencia(20);
		
		assertEquals(nivel2, this.entrenador.getNivelActual());
	}
	
	@Test
	public void dadoUnEntrenadorCon10MonedasViajaAUnLugarYPagaElCostoDeDichoViaje() {
		Lugar destino = new Pueblo("Destino");
		this.entrenador.setMonedas(10);
		
		this.entrenador.viajarALugar(destino, 10);
		
		assertEquals(this.entrenador.getUbicacionActual(), destino);
	}
	
	@Test
	public void dadoUnEntrenadorCon10MonedasIntentaViajarAUnLugarYFallaDebidoAQueNoPuedePagarElViaje() {
		Lugar destino = new Pueblo("Destino");
		this.entrenador.setMonedas(10);
		
		try {
			this.entrenador.viajarALugar(destino, 11);
			fail("El entrenador viajo al lugar pero no debía debido a falta de monedas.");
		}
		catch(FondosInsuficientesException e) {
			assertNotEquals(this.entrenador.getUbicacionActual(), destino);			
		}
	}
	
//TESTS REDEFINICION EQUALS Y HASHCODE

	@Test
	public void dadosDosEntrenadoresLosComparoConEqualsParaComprobarSiSonIgualesYMeRespondeTrue() {
		Entrenador entrenadorOriginal = this.nuevoEntrenadorOriginal();
		Entrenador entrenadorComparativo = this.nuevoEntrenadorOriginal();
		
		assertEquals(entrenadorOriginal, entrenadorComparativo);
	} 
	
	@Test
	public void dadosDosEntrenadoresLosComparoConEqualsParaComprobarSiSonIgualesYMeRespondeFalseDebidoAQueTienenDistintoId() {
		Entrenador entrenadorOriginal = this.nuevoEntrenadorOriginal();
		Entrenador entrenadorComparativo = this.nuevoEntrenadorOriginal();
		entrenadorComparativo.setNombre("EntrenadorComparativo");
		
		assertNotEquals(entrenadorOriginal, entrenadorComparativo);
	}
	
	@Test
	public void dadosDosEntrenadoresLosComparoSusHashCodeParaComprobarSiSonIgualesYMeRespondeTrue() {
		Entrenador entrenadorOriginal = this.nuevoEntrenadorOriginal();
		Entrenador entrenadorComparativo = this.nuevoEntrenadorOriginal();
		
		assertEquals(entrenadorOriginal.hashCode(), entrenadorComparativo.hashCode());
	} 
	
	@Test
	public void dadosDosEntrenadoresLosComparoSusHashCodeParaComprobarSiSonIgualesYMeRespondeFalse() {
		Entrenador entrenadorOriginal = this.nuevoEntrenadorOriginal();
		Entrenador entrenadorComparativo = this.nuevoEntrenadorOriginal();
		entrenadorComparativo.setNombre("EntrenadorComparativo");
		
		assertNotEquals(entrenadorOriginal.hashCode(), entrenadorComparativo.hashCode());
	}
	


//	************************************
//	************************************
//	*** MÉTODOS AUXILIARES PARA TEST ***	
//	*** ---------------------------- ***
//	************************************
//	************************************		
	
	private Entrenador nuevoEntrenadorOriginal() {
		Entrenador e = new Entrenador();
		e.setNombre("EntrenadorOriginal");
		return e;
	} 
}
