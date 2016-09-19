package ar.edu.unq.epers.bichomon.backend.model.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

/** Esta condición será superada solo si el {@link Bicho} evaluado supera una determinada cantidad
 * de tiempo desde que fue capturado, que es especificado en está condición.
 * @author ae */
public class CondicionBasadaEnEdad extends CondicionDeEvolucion {

	@Override
	public Integer magnitudDeCondicionDelBicho(Bicho bicho) {
		return bicho.getTiempoDesdeSuCaptura();
	}

}