package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import javax.persistence.Entity;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Nivel;

/** Esta condición será superada solo si el {@link Entrenador} del {@link Bicho} evaluado está
 * en un {@link Nivel} superior al que especifica en está condición.
 * @author ae */
@Entity
public class CondicionBasadaEnNivel extends CondicionDeEvolucion {

	public CondicionBasadaEnNivel() {
		super();
	}
	
	public CondicionBasadaEnNivel(Integer magnitud) {
		super(magnitud);
	}
	

	@Override
	public Integer magnitudDeCondicionDelBicho(Bicho bicho) {
		return bicho.getOwner().getNivelActual().getNumeroDeNivel();
	}

}
