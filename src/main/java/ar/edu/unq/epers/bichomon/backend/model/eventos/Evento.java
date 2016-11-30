package ar.edu.unq.epers.bichomon.backend.model.eventos;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.CLASS ,property="_class")
public class Evento {

	private String tipo;
	private String uvicacion;
	//se implementa con el tiempo actual en milisegundo
	private Long fecha; 
	
	
	public Evento(){};
	

	public Evento(String tipo, String uvicacion) {
		super();
		this.tipo = tipo;
		this.uvicacion = uvicacion;
		this.fecha= System.currentTimeMillis();
	
	}	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getUvicacion() {
		return uvicacion;
	}
	public void setUvicacion(String uvicacion) {
		this.uvicacion = uvicacion;
	}
	
	@Override
	public String toString() {
		return "{"+ tipo + ", " + uvicacion + ", "+ fecha+"}";
	}


	public Long getFecha() {
		// TODO Auto-generated method stub
		return this.fecha;
	}
	
	
	
}
