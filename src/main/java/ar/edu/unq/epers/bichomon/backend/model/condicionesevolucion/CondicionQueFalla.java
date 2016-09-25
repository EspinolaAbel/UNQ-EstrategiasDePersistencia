package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

/** Esta condición nunca va a ser aprobada por ningún {@link Bicho}. Siempre que evalue alguno
 * esta condición fallará.
 *  Esta condición solo debe ser utilizada por la {@link Especie} que no evoluciona a ninguna otra
 *  especie, es decir, aquellas especies que terminan con el arbol de evoluciones.*/
public class CondicionQueFalla extends CondicionDeEvolucion {

	public CondicionQueFalla(){
		super(0);
	}
	
	private CondicionQueFalla(Integer magnitud) {
		super(magnitud);
	}

	@Override
	public Integer magnitudDeCondicionDelBicho(Bicho bicho) {
		return null;
	}
	
	@Override
	public Boolean apruebaLaCondicion(Bicho bicho) {
		return false;
	}

}
