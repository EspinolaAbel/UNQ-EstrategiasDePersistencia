package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import org.junit.Assert;
import org.junit.Before;

import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Nivel;

public class CondicionBasadaEnNivelTest extends CondicionDeEvolucionTest {

	private CondicionDeEvolucion condicionDeEvolucion;
	
	private Entrenador entrenador;
	private Nivel nivel;

	@Before
	public void setUp() {
		super.setUp();
		entrenador = new Entrenador();
		nivel = new Nivel();				
	}

	/** Dado un Bicho consulto si su Entrenador esta en un nivel superior al definido en la condición de la Especie.
	 * Como el bicho supera dicha condición se responderá true.
	 * - El bicho dado tiene tine un entrenador con nivel 2 y
	 * - la condición especifica que el entrenador debe estar en nivel superior a 1.
	 * @author ae */
	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeTrue() {
		condicionDeEvolucion = new CondicionBasadaEnNivel(1);
		nivel.setNumeroDeNivel(2);
		entrenador.setNivelActual(nivel);
		bichoGenerico.setOwner(entrenador);
		
		Assert.assertTrue(condicionDeEvolucion.apruebaLaCondicion(bichoGenerico));
	}

	/** Dado un Bicho consulto si su Entrenador esta en un nivel superior al definido en la condición de la Especie.
	 * Como el bicho no supera dicha condición se responderá false.
	 * - El bicho dado tiene tine un entrenador con nivel 1 y
	 * - la condición especifica que el entrenador debe estar en nivel superior a 1.
	 * @author ae */
	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeFalse() {
		condicionDeEvolucion = new CondicionBasadaEnNivel(1);
		nivel.setNumeroDeNivel(1);
		entrenador.setNivelActual(nivel);
		bichoGenerico.setOwner(entrenador);
		
		Assert.assertFalse(condicionDeEvolucion.apruebaLaCondicion(bichoGenerico));
	}

}
