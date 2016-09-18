package ar.edu.unq.epers.bichomon.backend.model.bicho;

import ar.edu.unq.epers.bichomon.backend.model.entrenador.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

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
	
	public Bicho(Especie especie, String nombre) {
		this.especie = especie;
		this.nombre = nombre;
	}
	public Bicho(Especie especie){
		this.especie=especie;
		
	}

	public void setOwner(Entrenador owner){
		this.owner=owner;
		
	}
	
	public Entrenador gerOwner(){
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

}
