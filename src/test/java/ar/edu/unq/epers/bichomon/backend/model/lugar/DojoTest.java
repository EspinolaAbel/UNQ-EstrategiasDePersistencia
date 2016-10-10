package ar.edu.unq.epers.bichomon.backend.model.lugar;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;

public class DojoTest {

	private Dojo dojo;
	
	@Before
	public void setUp() throws Exception {
		dojo = new Dojo("DojoGenerico");
	}
	

	/** Dado un dojo intento abandonar un bicho en él. Como en este lugar no se permite abandonar bichos
	 * se lanza una UbicacionIncorrectaException. */
	@Test
	public void dadoUnDojoIntentoAbandonarUnBichoYSeLanzaUnaUbicacionIncorrectaException() {
		Bicho bicho = new Bicho(new Especie());
		
		try {
			dojo.recibirBichoAbandonado(bicho);
			fail("Nunca se lanzó la exception...");
		}
		catch(UbicacionIncorrectaException e) {
			assertEquals(e.getMessage(), "En el lugar \""+dojo.getNombre()+"\" no se permite abandonar bichos.");
		}
	}

	
	/** Dado un dojo el cual tiene como campeón un bicho de la especie 'EspecieEvolucionada', le 
	 * pido que me de un nuevo bicho del lugar.
	 * Al realizar esta acción me retorna un nuevo bicho de la especie raiz de la especie del bicho
	 * campeón actual.
	 * - Especie del bicho campeon: 'EspecieEvolucionada';
	 * - Especie raiz del campeon: 'EspecieRaiz'. */
	@Test
	public void dadoUnDojoConBichoCampeonDeEspecieEvolucionadaLePidoUnBichoYMeDevuelveUnNuevoBichoDeLaEspecieRaizDelCampeonActual() {
		Especie especieEvolucionada = new Especie("EspecieEvolucionada", TipoBicho.FUEGO);
		Especie especieRaiz = new Especie("EspecieRaiz", TipoBicho.FUEGO);
		
		especieEvolucionada.setRaiz(especieRaiz);		
		Bicho bichoCampeon = new Bicho(especieEvolucionada);		
		dojo.setCampeonActual(bichoCampeon);
		
		Bicho bichoDelLugar = dojo.retornarUnBichoDelLugar();
		
		assertEquals(bichoDelLugar.getEspecie().getNombre(), "EspecieRaiz");
	}
}
