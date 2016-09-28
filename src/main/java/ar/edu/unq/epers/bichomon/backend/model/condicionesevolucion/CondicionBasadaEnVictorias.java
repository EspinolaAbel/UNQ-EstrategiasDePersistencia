package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import javax.persistence.Entity;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

/** Esta condición será superada solo si el {@link Bicho} evaluado supera la cantidad de victorias que
 * se especifica en está condición.
 * @author ae */
@Entity
public class CondicionBasadaEnVictorias extends CondicionDeEvolucion {

	public CondicionBasadaEnVictorias() {
		super();
	}
	
	public CondicionBasadaEnVictorias(Integer magnitud) {
		super(magnitud);
	}

	@Override
	public Integer magnitudDeCondicionDelBicho(Bicho bicho) {
		return bicho.getCantidadDeVictorias();
	}

}
