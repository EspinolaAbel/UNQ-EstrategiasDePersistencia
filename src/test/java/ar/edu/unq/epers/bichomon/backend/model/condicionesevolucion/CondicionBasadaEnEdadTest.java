package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import static org.junit.Assert.*;

import org.junit.Before;

public class CondicionBasadaEnEdadTest extends CondicionDeEvolucionTest {

	private CondicionBasadaEnEdad condicionEdad;

	@Before
	public void setUp() {
		super.setUp();
		this.condicionOriginal = new CondicionBasadaEnEdad();
		this.condicionComparativa = new CondicionBasadaEnEdad();
	}

	/** Dado un bicho consulto si el tiempo que pasó desde su captura es superior a la condición.
	 * Como el tiempo que pasó desde su captura es superior a la condición se responde true.
	 * 	- El bicho dado tiene 2 segundos (2x10^9 nano-segundos) desde su captura y;
	 *  - la condición dice que debe tener más de 1 segundo desde su captura. */
	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeTrue() {
		long dosSegundosEnNano = (long) (2 * (Math.pow(10, 9)));
		bichoGenerico.setTiempoDesdeSuCaptura(dosSegundosEnNano);
		condicionEdad = new CondicionBasadaEnEdad(1);
		
		assertTrue(condicionEdad.apruebaLaCondicion(bichoGenerico));
	}

	/** Dado un bicho consulto si el tiempo que pasó desde su captura es superior a la condición.
	 * Como el tiempo que pasó desde su captura no supera la condición se responde false.
	 * 	- El bicho dado tiene 1 segundos (1x10^9 nano-segundos) desde su captura y;
	 *  - la condición dice que debe tener más de 1 segundo desde su captura. */
	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeFalse() {
		long unSegundoEnNano = (long) (1 * (Math.pow(10, 9)));
		bichoGenerico.setTiempoDesdeSuCaptura(unSegundoEnNano);
		condicionEdad = new CondicionBasadaEnEdad(1);
		
		assertFalse(condicionEdad.apruebaLaCondicion(bichoGenerico));
	}

}
