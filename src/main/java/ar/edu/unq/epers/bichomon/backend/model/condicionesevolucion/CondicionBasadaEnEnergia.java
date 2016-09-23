package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

/** Esta condición será superada solo cuando el {@link Bicho} evaluado tiene una cantidad de energía
 * superior al que especifica en está condición.
 * @author ae */
public class CondicionBasadaEnEnergia extends CondicionDeEvolucion {

	public CondicionBasadaEnEnergia(Integer magnitud) {
		super(magnitud);
	}

	@Override
	public Integer magnitudDeCondicionDelBicho(Bicho bicho) {
		return bicho.getEnergia();
	}

}
