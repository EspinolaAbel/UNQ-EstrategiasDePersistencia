package ar.edu.unq.epers.bichomon.backend.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NivelTest {

	Nivel nivel1, nivel2;

	/** Dados un nivel le pido me de su siguiente nivel y me lo responde.
	 * - El nivel 1 tiene como siguiente nivel el nivel 2*/
	@Test
	public void dadoUnNivelLePidoMeDeSuSiguienteNivel() {
		nivel1 = new Nivel();
		nivel1.setNumeroDeNivel(1);
		nivel2 = new Nivel();
		nivel2.setNumeroDeNivel(2);
		
		nivel1.setSiguienteNivel(nivel2);
		
		assertEquals(nivel1.getSiguienteNivel(), nivel2);
	}

}
