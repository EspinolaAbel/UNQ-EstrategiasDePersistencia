package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import static org.junit.Assert.*;

import org.junit.Before;

public class CondicionQueFallaTest extends CondicionDeEvolucionTest {

	CondicionQueFalla condicionQueFalla;
	
	@Before
	public void setUp() {
		super.setUp();
		this.condicionOriginal = new CondicionQueFalla();
		this.condicionComparativa = new CondicionQueFalla();
	}


	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeTrue() {
		//Este caso nunca sucederá con esta condición.
	}

	/** Dado un bicho generico, consulto si aprueba la condicion de evolución y me responde false.
	 * @author ae */
	@Override
	public void dadoUnBichoDeUnaEspecieConsultoSiSuperaLaCondicionDeEvolucionYMeRespondeFalse() {
		condicionQueFalla = new CondicionQueFalla();
		
		assertFalse(condicionQueFalla.apruebaLaCondicion(bichoGenerico));
	}

}
