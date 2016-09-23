package ar.edu.unq.epers.bichomon.backend.model.busqueda;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;

/** clase para manipular lña busqueda de bichos 
 * 
 * @author Peter
 *
 */

public class Busqueda {
	private IFactores factores;
	

	public Busqueda(IFactores factores) {
		this.factores=factores;
		
	}

	public Boolean busquedaExitosa(Entrenador entrenador){
		
		return ( (this.factores.factorNivel(entrenador)
				* this.factores.factorTiempo(entrenador)
				*this.factores.factorPoblacion (entrenador.getUbicacionActual()) 
				* Math.random())
				   >0.5);
	}
	
	/** me devuellve un bicho de un lugar segun las eglas  establecidas
	 * 
	 * @return
	 */
	public Bicho obtenerBicho(Lugar lugar){
		return lugar.retornarUnBichoDelLugar();
	
		}
		
		
	}

