package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import javax.persistence.Entity;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

/** Esta condición será superada solo cuando el {@link Bicho} evaluado tiene una cantidad de energía
 * superior al que especifica en está condición. */
@Entity
public class CondicionBasadaEnEnergia extends CondicionDeEvolucion {

	public CondicionBasadaEnEnergia() {
		super();
	}
	
	public CondicionBasadaEnEnergia(Integer energia) {
		super(energia);
	}

	@Override
	public Integer magnitudDeCondicionDelBicho(Bicho bicho) {
		return bicho.getEnergia();
	}

}
