
package ar.edu.unq.epers.bichomon.backend.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Un {@link Bicho} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Especie} en particular.
 * 
 * @author Charly Backend */
@Entity(name="Bichos")
public class Bicho {
	
	@ManyToOne
    private Entrenador owner;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Transient
	private String nombre;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Especie especie; 
	private int energia;
	private long tiempoDesdeSuCaptura;
	private int cantidadDeVictorias;
	
	public Bicho(Especie especie, String nombre) {
		this.especie = especie;
		this.nombre = nombre;
	}

	public Bicho() {
		super();
	}
	
	public Bicho(Especie especie){
		this();
		this.especie=especie;	
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
	 * @return el nombre de un bicho (todos los bichos tienen
	 * nombre). Este NO es el nombre de su especie.
	 */
	public String getNombre() {
		return this.nombre;
	}
	
	/**
	 * @return la especie a la que este bicho pertenece.
	 */
	public Especie getEspecie() {
		return this.especie;
	}
	
	public void setEspecie(Especie especie){
		this.especie= especie;
		
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
	public long getTiempoDesdeSuCaptura() {
		return this.tiempoDesdeSuCaptura;
	}
	
	public int getCantidadDeVictorias() {
		return this.cantidadDeVictorias;
	}
	
	public void setCantidadDeVictorias(Integer victorias) {
		this.cantidadDeVictorias = victorias;
	}
	
	public void setTiempoDesdeSuCaptura(long tiempo) {
		this.tiempoDesdeSuCaptura = tiempo;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int  getId() {
		return this.id;
	}
	
	
	
	public void recuperarEnergia(){
		this.energia += (int) 1+(Math.random()*4);
	}
	
	
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o instanceof Bicho) {
			Bicho otroBicho = (Bicho) o;
			boolean idemId= this.id == otroBicho.getId();
	
			return 	idemId;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int num = 84;
		int result = 11;
		result =	num * result + this.id;
		
		return result;
	}
	
}
