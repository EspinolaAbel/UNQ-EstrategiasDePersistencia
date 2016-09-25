package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import static org.junit.Assert.*;

import org.junit.Before;

public class CondicionBasadaEnEdadTest extends CondicionDeEvolucionTest {

	private CondicionBasadaEnEdad condicionEdad;

	@Before
	public void setUp() {
		super.setUp();
	}

	/** Dado un bicho consulto si el tiempo que pasó desde su captura es superior a la condición.
	 * Como el tiempo que pasó desde su captura es superior a la condición se responde true.
	 * 	- El bicho dado tiene 2 días desde su captura y;
	 *  - la condición dice que debe tener más de 1 día desde su captura.
	 *  @author ae*/
	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeTrue() {
		bichoGenerico.setTiempoDesdeSuCaptura(2);
		condicionEdad = new CondicionBasadaEnEdad(1);
		
		assertTrue(condicionEdad.apruebaLaCondicion(bichoGenerico));
	}

	/** Dado un bicho consulto si el tiempo que pasó desde su captura es superior a la condición.
	 * Como el tiempo que pasó desde su captura no supera la condición se responde false.
	 * 	- El bicho dado tiene 1 día desde su captura y;
	 *  - la condición dice que debe tener más de 1 día desde su captura.
	 *  @author ae*/
	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeFalse() {
		bichoGenerico.setTiempoDesdeSuCaptura(1);
		condicionEdad = new CondicionBasadaEnEdad(1);
		
		assertFalse(condicionEdad.apruebaLaCondicion(bichoGenerico));
	}

}
