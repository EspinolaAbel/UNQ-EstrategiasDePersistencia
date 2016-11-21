package ar.edu.unq.epers.bichomon.backend.model;

public class Evento {

	private String tipo;
	private String uvicacion;
	
	
	public Evento(){};
	

	public Evento(String tipo, String uvicacion) {
		super();
		this.tipo = tipo;
		this.uvicacion = uvicacion;
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
	
	
	
	
	
}
