package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

/** Esta condición será superada solo si el {@link Bicho} evaluado supera la cantidad de victorias que
 * se especifica en está condición.
 * @author ae */
public class CondicionBasadaEnVictorias extends CondicionDeEvolucion {

	@Override
	public Integer magnitudDeCondicionDelBicho(Bicho bicho) {
		return bicho.getCantidadDeVictorias();
	}

}
