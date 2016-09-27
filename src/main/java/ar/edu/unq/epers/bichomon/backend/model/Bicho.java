package ar.edu.unq.epers.bichomon.backend.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Id;

/**
 * Un {@link Bicho} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Especie} en particular.
 * 
 * @author Charly Backend
 */


@Entity
public class Bicho implements Serializable{
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -731129257068449225L;
	@Transient
	private Entrenador owner;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
    private String nombre;
    @Transient
	private Especie especie;
	//energia >=0 siempre, 
	private int energia;
	private Integer tiempoDesdeSuCaptura;
	private Integer cantidadDeVictorias;
	
	
	public Bicho(){
		
	}
	
	public Bicho(Especie especie){
		this.especie=especie;
		
	}


	public Bicho(Especie especie, String nombre){
		this.especie=especie;
		this.nombre= nombre;
		
		
	}

	
	/** Se setea el {@link Entrenador} propietario del {@link Bicho}.
	 * @param owner - {@link Entrenador} que será el nuevo propietario.*/
	public void setOwner(Entrenador owner){
		this.owner=owner;
		
	}
	
	/** Se responde con el {@link Entrenador} propietario del {@link Bicho}.
	 * @return owner - {@link Entrenador} propietario.*/
	public Entrenador getOwner(){
		return this.owner;
		
	}
	
	/**
	 * @return la especie a la que este bicho pertenece.
	 */
	public Especie getEspecie() {
		return this.especie;
	}
	
	/**
	 * @return la cantidad de puntos de energia de este bicho en
	 * particular. Dicha cantidad crecerá (o decrecerá) conforme
	 * a este bicho participe en combates contra otros bichomones.
	 */
	public int getEnergia() {
		return this.energia;
	}
	public void setEnergia(int energia) {
		this.energia = energia;
	}
	
	
	/** Se responde con el tiempo transcurrido desde la captura del {@link Bicho}.
	 * NOTA: El tiempo transcurrido es representado por un {@link Integer}.
	 * 
	 * @author ae */
	public Integer getTiempoDesdeSuCaptura() {
		return this.tiempoDesdeSuCaptura;
	}
	
	public Integer getCantidadDeVictorias() {
		return this.cantidadDeVictorias;
	}
	
	public void setCantidadDeVictorias(Integer victorias) {
		this.cantidadDeVictorias = victorias;
	}
	
	public void setTiempoDesdeSuCaptura(Integer tiempo) {
		this.tiempoDesdeSuCaptura = tiempo;
	}
	
	
}
