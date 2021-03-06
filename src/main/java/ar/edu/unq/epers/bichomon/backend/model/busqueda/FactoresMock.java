package ar.edu.unq.epers.bichomon.backend.model.busqueda;

import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;

public class FactoresMock implements IFactores {
/**
 * Esta clase se utiliza para dar una implementacion mock a la interface IFactores de busqueda,
 *  todos  factores devolveran "1" y la busqueda exitosa devolvera  true.
 * 
 */
	public FactoresMock() {
	super();
	}

	@Override
	public double factorTiempo(Entrenador entrenador) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public double factorPoblacion(Lugar pueblo) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public double factorNivel(Entrenador entreneador) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean busquedaExitosa() {
		
		return true;
	}

}
