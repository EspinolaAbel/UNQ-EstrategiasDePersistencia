package ar.edu.unq.epers.bichomon.backend.model.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

/** Esta condición será superada solo si el {@link Entrenador} del {@link Bicho} evaluado está
 * en un {@link Nivel} superior al que especifica en está condición.
 * @author ae */
public class CondicionBasadaEnNivel extends CondicionDeEvolucion {

	@Override
	public Integer magnitudDeCondicionDelBicho(Bicho bicho) {
		return bicho.getOwner().getNivelActual().getNumeroDeNivel();
	}

}
