package ar.edu.unq.epers.bichomon.backend.model.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

/** Esta condición será superada solo cuando el {@link Bicho} evaluado tiene una cantidad de energía
 * superior al que especifica en está condición.
 * @author ae */
public class CondicionBasadaEnEnergia extends CondicionDeEvolucion {

	@Override
	public Integer magnitudDeCondicionDelBicho(Bicho bicho) {
		return bicho.getEnergia();
	}

}
