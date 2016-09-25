package ar.edu.unq.epers.bichomon.backend.model;

/**
 * Un {@link Bicho} existente en el sistema, el mismo tiene un nombre
 * y pertenece a una {@link Especie} en particular.
 * 
 * @author Charly Backend
 */
public class Bicho {
	
	
    private Entrenador owner;
	private String nombre;// vuela
	private Especie especie;
	//energia >=0 siempre, 
	private int energia;
	private Integer tiempoDesdeSuCaptura;
	private Integer cantidadDeVictorias;
	
	public Bicho(Especie especie, String nombre) {
		this.especie = especie;
		this.nombre = nombre;
	}
	public Bicho(Especie especie){
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
