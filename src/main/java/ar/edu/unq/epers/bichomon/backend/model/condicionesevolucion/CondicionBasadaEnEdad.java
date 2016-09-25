package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

/** Esta condición será superada solo si el {@link Bicho} evaluado supera una determinada cantidad
 * de tiempo desde que fue capturado, que es especificado en está condición.
 * @author ae */
public class CondicionBasadaEnEdad extends CondicionDeEvolucion {

	public CondicionBasadaEnEdad(Integer magnitud) {
		super(magnitud);
	}

	@Override
	public Integer magnitudDeCondicionDelBicho(Bicho bicho) {
		return bicho.getTiempoDesdeSuCaptura();
	}

}
