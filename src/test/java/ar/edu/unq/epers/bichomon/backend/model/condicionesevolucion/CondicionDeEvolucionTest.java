package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;

public abstract class CondicionDeEvolucionTest {

	protected Especie especieGenerica;
	protected Bicho bichoGenerico;
	
	
	@Before
	public void setUp() {
		especieGenerica = new Especie("Generico", TipoBicho.FUEGO);
		bichoGenerico = new Bicho(especieGenerica);
	}
	
	@Test
	public abstract void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeTrue();
	@Test
	public abstract void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeFalse();
}
