package ar.edu.unq.epers.bichomon.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Esta clase va a representar los puntos de exéroencia que adquiere un entrenador
 *  por las tareas que realiza
 * 
 * @author pa
 *
 */


@Entity (name= "Puntajes")
public class PuntosDeExperiencia {
	@Id 
	@Column (name ="Tarea")
	private String tareaRealizada;
	private  int puntaje;
	
	
	public PuntosDeExperiencia() {
		
	}
	public PuntosDeExperiencia(String tarea, int puntaje) {
		this.tareaRealizada=tarea;
		this.puntaje=puntaje;
	}
	
	
	public void setTareaRealizada(String  tarea){
		 this.tareaRealizada=tarea;
	 }
	
	public String getTareatRealizada(){
		return this.tareaRealizada;
	 }
	
	public  int getPuntaje(){
		return this.puntaje;
	}
	
	public void setPuntaje(int puntaje){
		this.puntaje=puntaje;
	}
	

}