package ar.edu.unq.epers.bichomon.backend.model.especie;

import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;

/** {@link CondicionDeEvolucion} es una clase abstracta que representa una condición que un
 * {@link Bicho} debe cumplir para evolucionar a la siguiente {@link Especie} de su arbol de evolución.
 * Las subclases que hereden de esta clase, deben implementar una condición especifica.
 * @author ae */
public abstract class CondicionDeEvolucion {
	
	private Integer magnitudASuperar;
	
	/** Se evalua si el {@link Bicho} aprueba la condición.
	 * @param bicho - {@link Bicho} que será evaluado para la condición.
	 * @return {@ Boolean} que indica si el {@link Bicho} dado aprueba la condición.
	 * @author ae */
	public Boolean apruebaLaCondicion(Bicho bicho) {
		if(this.magnitudDeCondicionDelBicho(bicho) > this.getMagnitudASuperar()) {
			return true;
		}
		else {
			return false;
		}
	}

	/**@return El valor numérico que el {@link Bicho} debe superar en alguno de sus campos para
	 * aprobar la condición.
	 * @author ae */
	private Integer getMagnitudASuperar() {
		return this.magnitudASuperar;
	}

	/** En este método se debe definir cuál es el campo del {@link Bicho} que será evaluado 
	 * en esta {@link CondicionDeEvalucion}.
	 * @return El valor númerico a evaluar del {@link Bicho}.
	 * @author ae */
	public abstract Integer magnitudDeCondicionDelBicho(Bicho bicho);
}
