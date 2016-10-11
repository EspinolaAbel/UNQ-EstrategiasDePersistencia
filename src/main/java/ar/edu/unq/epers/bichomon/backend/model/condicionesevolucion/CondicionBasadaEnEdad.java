package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import javax.persistence.Entity;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

/** Esta condición será superada solo si el {@link Bicho} evaluado supera una determinada cantidad
 * de tiempo desde que fue capturado, que es especificado en está condición. */
@Entity
public class CondicionBasadaEnEdad extends CondicionDeEvolucion {

	public CondicionBasadaEnEdad() {
		super();
	}
	
	/** @param segundos - un tiempo expresado en segundos */
	public CondicionBasadaEnEdad(Integer segundos) {
		super(segundos);
	}

	/** Dado un bicho obtengo el tiempo desde que fue capturado por su entrenador actual.
	 * El tiempo obtenido del bicho está representado en nano-segundos. Para un mejor manejo del tiempo,
	 * dicha magnitud será convertida a segundos.
	 * PREC.: El bicho DEBE tener actualmente un entrenador como su dueño.*/
	@Override
	public Integer magnitudDeCondicionDelBicho(Bicho bicho) {
		Integer tiempoEnSegundos = (int) (bicho.getTiempoDesdeSuCaptura() / Math.pow(1000, 3));
		return tiempoEnSegundos;
	}

}
