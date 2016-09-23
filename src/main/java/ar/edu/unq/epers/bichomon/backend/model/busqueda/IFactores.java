/**
 * 
 */
package ar.edu.unq.epers.bichomon.backend.model.busqueda;

import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;

/**interface para calcular la probabilidadd de que la busqueda  sea exitosa
 * @author Peter
 *
 */
public interface IFactores {

	public double factorTiempo (Entrenador entrenador);
	public double factorPoblacion (Lugar pueblo);
	public double factorNivel (Entrenador entreneador);
	
	
}
