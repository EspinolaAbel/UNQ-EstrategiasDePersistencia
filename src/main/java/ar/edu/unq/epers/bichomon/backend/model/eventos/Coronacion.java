package ar.edu.unq.epers.bichomon.backend.model.eventos;

public class Coronacion extends Evento {
	private String entrenadorRival;
	public Coronacion() {
		// TODO Auto-generated constructor stub
	}

	public Coronacion( String uvicacion, String entrenadorRival) {
		super("Coronacion", uvicacion);
		this.entrenadorRival=entrenadorRival;
	}
	public String getEntrenadorRival() {
		return this.entrenadorRival;
	}

	public void setEntrenadorRival(String entrenadorRival) {
		this.entrenadorRival = entrenadorRival;
	}


}
