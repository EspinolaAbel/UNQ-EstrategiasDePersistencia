package ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;

import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;

/** {@link CondicionDeEvolucion} es una clase abstracta que representa una condición que un
 * {@link Bicho} debe cumplir para evolucionar a la siguiente {@link Especie} de su arbol de evolución.
 * Las subclases que hereden de esta clase, deben implementar una condición especifica.
 * @author ae */
@Entity
@Table(name = "Condiciones_de_evolucion")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)				//@IdClass(CondicionDeEvolucionPK.class)
public abstract class CondicionDeEvolucion {
	

//	--Guarda la secuencia de id en la misma tabla de la entidad.	
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	--Guarda la secuencia de id en una tabla separada la cual tiene una fila por cada id de cada tabla que usen esta estrategia.	
//	@GeneratedValue(strategy=GenerationType.TABLE)
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private Integer magnitudASuperar;
	
	@ManyToOne
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Especie especie;
	
	
	public CondicionDeEvolucion() {}
	
	public CondicionDeEvolucion(Integer magnitud) {
		this.magnitudASuperar = magnitud;
	}
	
	
	private Integer getMagnitudASuperar() {
		return this.magnitudASuperar;
	}
	
	public void setMagnitudASuperar(Integer magnitudASuperar) {
		this.magnitudASuperar = magnitudASuperar;
	}
	
	
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

	
	/** En este método se debe definir cuál es el campo del {@link Bicho} que será evaluado 
	 * en esta {@link CondicionDeEvalucion}.
	 * @return El valor númerico a evaluar del {@link Bicho}.
	 * @author ae */
	public abstract Integer magnitudDeCondicionDelBicho(Bicho bicho);


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
