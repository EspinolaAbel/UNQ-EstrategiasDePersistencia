package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import static org.junit.Assert.*;

import org.junit.Before;

public class CondicionBasadaEnVictoriasTest extends CondicionDeEvolucionTest {

	private CondicionBasadaEnVictorias condicionDeVictorias ;

	@Before
	public void setUp() {
		super.setUp();
		this.condicionOriginal = new CondicionBasadaEnVictorias();
		this.condicionComparativa = new CondicionBasadaEnVictorias();
	}

	/** Dado un bicho consulto si su cantidad de victorias es superior a la de la condición.
	 * Como la cantidad de victorias es superior a la condición se responde true.
	 * 	- El bicho dado tiene 2 victorias y;
	 *  - la condición dice que debe tener más de 1 victoria. */
	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeTrue() {
		condicionDeVictorias = new CondicionBasadaEnVictorias(1);
		bichoGenerico.setCantidadDeVictorias(2);
		
		assertTrue(condicionDeVictorias.apruebaLaCondicion(bichoGenerico));
	}

	/** Dado un bicho consulto si su cantidad de victorias es superior a la de la condición.
	 * Como la cantidad de victorias no supera a la condición se responde false.
	 * 	- El bicho dado tiene 1 victoria y;
	 *  - la condición dice que debe tener más de 1 victoria. */
	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeFalse() {
		condicionDeVictorias = new CondicionBasadaEnVictorias(1);
		bichoGenerico.setCantidadDeVictorias(1);
		
		assertFalse(condicionDeVictorias.apruebaLaCondicion(bichoGenerico));
	}

}
