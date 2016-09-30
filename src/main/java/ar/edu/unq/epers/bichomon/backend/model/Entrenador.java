package ar.edu.unq.epers.bichomon.backend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;

@Entity
public class Entrenador {
	
	@Id
	private String  nombre;
	private Integer experiencia;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Lugar ubicacion;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Nivel nivelActual;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="owner", fetch=FetchType.EAGER)
	private List<Bicho> bichosCapturados;

	public Entrenador(String nombre) {
		this();
		this.nombre = nombre;
	}
	
	public Entrenador() {
		this.bichosCapturados = new ArrayList<Bicho>();
		this.experiencia = 0;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(Integer experiencia) {
		this.experiencia = experiencia;
	}

	public Lugar getUbicacionActual() {
		return ubicacion;
	}

	public void setUbicacionActual(Lugar ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Nivel getNivelActual() {
		return this.nivelActual;
	}

	public void setNivelActual(Nivel nivelActual) {
		this.nivelActual = nivelActual;
	} 
	
	public List<Bicho> getBichosCapturados() {
		return this.bichosCapturados;
	}

	
	/** Dado un {@link Bicho} se lo agrega a la lista de bichos capturados del entrenador.
     * Este método también agrega a este entrenador como el owner del bicho.
	 * @param bichoCapturado - {@link Bicho} capturado a agregar a la lista de bichos capturados.
	 * @author ae */
    public  void agregarBichoCapturado(Bicho bichoCapturado){
    	this.bichosCapturados.add(bichoCapturado);
    	bichoCapturado.setOwner(this);
    }
    
    /** Dado un {@link Bicho} se lo elimina de la lista de bichos capturados del entrenador.
     * Este método también elimina a este entrenador como el owner del bicho.
     * @param bicho - El {@link int }establece que bichos de la coleccion sera eliminado
     * @author ae */
    public void descartarBichoCapturado(Bicho bichoADescartar) {
		this.bichosCapturados.remove(bichoADescartar);
		bichoADescartar.setOwner(null);
    }	
	
}
