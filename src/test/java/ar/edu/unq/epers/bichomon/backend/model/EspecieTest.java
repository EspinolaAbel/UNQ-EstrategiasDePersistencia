package ar.edu.unq.epers.bichomon.backend.model;

import static org.junit.Assert.*;

import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnEdad;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnEnergia;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnNivel;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionBasadaEnVictorias;
import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionDeEvolucion;

public class EspecieTest {

	/** Dado un bicho consulto si este puede evolucionar a la siguiente especie. Como el bicho
	 * esta en condiciones de evolucionar se responde true.
	 * - El bicho dado pertenece a una especie cuyas condiciones de evolucion son de: energía, edad,
	 *   nivel y victorias. */
	@Test
	public void dadoUnBichoConsultoSiEstePuedeEvolucionarALaSiguienteEspecieYRespondeTrue() {
		Especie especie = this.especieConCondicionesDeEnergiaNivelEdadYVictorias(1,1,1,1);
		Bicho bicho = new Bicho(especie);
		Entrenador entrenadorDeNivel2 = this.generarEntrenadorConNivel(2);
		this.setBichoConEnergiaNivelEdadYVictoriasAUnEntrenador(bicho, 2, 2, entrenadorDeNivel2, 2);
		
		assertTrue(especie.puedeEvolucionar(bicho));
	}

	/** Dado un bicho consulto si este puede evolucionar a la siguiente especie. Como el bicho
	 * no esta en condiciones de evolucionar se responde false.
	 * - El bicho dado pertenece a una especie cuyas condiciones de evolucion son de: energía, edad,
	 *   nivel y victorias. */
	@Test
	public void dadoUnBichoConsultoSiEstePuedeEvolucionarALaSiguienteEspecieYRespondeFalse() {
		Especie especie = this.especieConCondicionesDeEnergiaNivelEdadYVictorias(1,1,1,1);
		Bicho bicho = new Bicho(especie);
		Entrenador entrenador = this.generarEntrenadorConNivel(2);
		
		//bicho no supera las condiciones de energia, edad y victorias, pero si la del nivel del entrenador
		this.setBichoConEnergiaNivelEdadYVictoriasAUnEntrenador(bicho, 1, 1, entrenador, 1);
		
		assertFalse(especie.puedeEvolucionar(bicho));
	}
	
	
//TESTS REDEFINICION EQUALS Y HASHCODE
//------------------------------------
	
	@Test
	public void dadasDosEspeciesLosComparoConEqualsParaComprobarSiSonIgualesYMeRespondeTrue() {
		Especie especieOriginal = this.nuevaEspecieOriginal();
		Especie especieComparativa = this.nuevaEspecieOriginal();
		
		assertEquals(especieOriginal, especieComparativa);
	} 
	
	/** El campo "nombre" de la clase Especie es el único que necesito comparar para saber si dos Especies son iguales 
	 * (ver contrato de {@link Especie}). */
	@Test
	public void dadasDosEspeciesLosComparoConEqualsParaComprobarSiSonIgualesYMeRespondeFalseDebidoAQueTienenDistintoNombre() {
		Especie especieOriginal = this.nuevaEspecieOriginal();
		Especie especieComparativa = this.nuevaEspecieOriginal();
		especieComparativa.setNombre("EspecieDistintaALaOriginal");
		
		assertNotEquals(especieOriginal, especieComparativa);
	}
	
	@Test
	public void dadasDosEspeciesComparoSusHashParaComprobarQueSeanIgualesYMeRespondeTrue() {
		Especie especieOriginal = this.nuevaEspecieOriginal();
		Especie especieComparativa = this.nuevaEspecieOriginal();
		
		assertEquals(especieOriginal.hashCode(), especieComparativa.hashCode());
	}
	
	@Test
	public void dadasDosEspeciesComparoSusHashParaComprobarQueSeanIgualesYMeRespondeFalse() {
		Especie especieOriginal = this.nuevaEspecieOriginal();
		Especie especieComparativa = this.nuevaEspecieOriginal();
		especieComparativa.setNombre("EspecieDistintaALaOriginal");
		
		assertNotEquals(especieOriginal.hashCode(), especieComparativa.hashCode());
	}

//	************************************
//	************************************
//	*** MÉTODOS AUXILIARES PARA TEST ***	
//	*** ---------------------------- ***
//	************************************
//	************************************
	
	private Especie nuevaEspecieOriginal() {
		Especie e = this.especieConCondicionesDeEnergiaNivelEdadYVictorias(1, 2, 3, 4);
		e.setNombre("EspecieOriginal");
		e.setAltura(9999);
		e.setCantidadBichos(9999);
		e.setEnergiaInicial(9999);
		e.setEvolucionaA(new Especie("EspecieEvolucion", TipoBicho.AGUA));
		e.setPeso(9999);
		e.setRaiz(new Especie("EspecieRaiz", TipoBicho.AGUA));
		e.setUrlFoto("--URL-FOTO--");
		return e;
	}
	
	private Entrenador generarEntrenadorConNivel(int i) {
		Nivel n = new Nivel();	n.setNumeroDeNivel(i);
		Entrenador e = new Entrenador();	e.setNivelActual(n);
		return e;
	}

	private Especie especieConCondicionesDeEnergiaNivelEdadYVictorias(int energia,int nroNivel,int edad,int victorias) {
		Especie especie;
		
		especie = new Especie("EspecieGenerica", TipoBicho.FUEGO);
		CondicionDeEvolucion condicion = new CondicionBasadaEnEnergia(energia);
		
		especie.agregarCondicionDeEvolucion(condicion);
		condicion =  new CondicionBasadaEnEdad(edad);
		
		especie.agregarCondicionDeEvolucion(condicion);
		condicion = new CondicionBasadaEnNivel(nroNivel);
		
		especie.agregarCondicionDeEvolucion(condicion);
		condicion = new CondicionBasadaEnVictorias(victorias);
		
		return especie;
	}
	
	private Bicho setBichoConEnergiaNivelEdadYVictoriasAUnEntrenador(Bicho bicho, int victorias, int energia, Entrenador owner, int tiempoDeCaptura) {
		bicho.setCantidadDeVictorias(victorias);
		bicho.setEnergia(energia);
		bicho.setOwner(owner);
		long tiempoDeCapturaEnNanoSegundos = tiempoDeCaptura * (long) Math.pow(10, 9);
		bicho.setTiempoDesdeSuCaptura(tiempoDeCapturaEnNanoSegundos);
		return bicho;
	}

}
