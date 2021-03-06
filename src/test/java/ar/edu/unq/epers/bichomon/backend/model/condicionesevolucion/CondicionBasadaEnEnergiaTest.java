package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import static org.junit.Assert.*;
import org.junit.Before;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

public class CondicionBasadaEnEnergiaTest extends CondicionDeEvolucionTest {
	
	private CondicionDeEvolucion condicionEvolucion;
	
	@Before
	public void setUp() {
		super.setUp();
		especieGenerica.agregarCondicionDeEvolucion(condicionEvolucion);
		bichoGenerico = new Bicho(especieGenerica);
		this.condicionOriginal = new CondicionBasadaEnEnergia();
		this.condicionComparativa = new CondicionBasadaEnEnergia();
	}
	
	
	
	/** Dado un Bicho consulto supera la una condición de evolución definida para su Especie.
	 * Como el bicho supera dicha condición se responderá true.
	 * - El bicho dado tiene una energía de 101 y
	 * - la condición especifica que la energía del bicho debe ser superioir a 100. */
	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeTrue() {
		condicionEvolucion = new CondicionBasadaEnEnergia(100);
		bichoGenerico.setEnergia(101);
		
		assertTrue(condicionEvolucion.apruebaLaCondicion(bichoGenerico));
	}

	
	/** Dado un Bicho consulto supera la una condición de evolución definida para su Especie.
	 * Como el bicho no supera dicha condición se responderá false.
	 * - El bicho dado tiene una energía de 100 y
	 * - la condición especifica que la energía del bicho debe ser superior a 100. */
	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeFalse() {
		condicionEvolucion = new CondicionBasadaEnEnergia(100);
		bichoGenerico.setEnergia(100);
		
		assertFalse(condicionEvolucion.apruebaLaCondicion(bichoGenerico));
	} 
}