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
	 *   nivel y victorias.
	 * @author ae */
	@Test
	public void dadoUnBichoConsultoSiEstePuedeEvolucionarALaSiguienteEspecieYRespondeTrue() {
		Especie especie = this.especieConCondicionesDeEnergiaNivelEdadYVictorias(1,1,1,1);
		Bicho bicho = new Bicho(especie);
		Entrenador entrenadorDeNivel2 = this.entrenadorConNivel(2);
		this.setBichoConEnergiaNivelEdadYVictorias(bicho, 2, 2, entrenadorDeNivel2, 2);
		
		assertTrue(especie.puedeEvolucionar(bicho));
	}

	/** Dado un bicho consulto si este puede evolucionar a la siguiente especie. Como el bicho
	 * no esta en condiciones de evolucionar se responde false.
	 * - El bicho dado pertenece a una especie cuyas condiciones de evolucion son de: energía, edad,
	 *   nivel y victorias.
	 * @author ae */
	@Test
	public void dadoUnBichoConsultoSiEstePuedeEvolucionarALaSiguienteEspecieYRespondeFalse() {
		Especie especie = this.especieConCondicionesDeEnergiaNivelEdadYVictorias(1,1,1,1);
		Bicho bicho = new Bicho(especie);
		Entrenador entrenador = this.entrenadorConNivel(2);
		
		//bicho no supera las condiciones de energia, edad y victorias, pero si la del nivel del entrenador
		this.setBichoConEnergiaNivelEdadYVictorias(bicho, 1, 1, entrenador, 1);
		
		assertFalse(especie.puedeEvolucionar(bicho));
	}
	
	
	
//ALGUNOS MÉTODOS AUXILIARES PARA TEST	
	
	private Entrenador entrenadorConNivel(int i) {
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
	
	private Bicho setBichoConEnergiaNivelEdadYVictorias(Bicho bicho, int victorias, int energia, Entrenador owner, int tiempoDeCaptura) {
		bicho.setCantidadDeVictorias(victorias);
		bicho.setEnergia(energia);
		bicho.setOwner(owner);
		bicho.setTiempoDesdeSuCaptura(tiempoDeCaptura);
		return bicho;
	}

}
