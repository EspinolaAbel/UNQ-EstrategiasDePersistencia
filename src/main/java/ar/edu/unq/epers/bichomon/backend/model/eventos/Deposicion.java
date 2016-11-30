package ar.edu.unq.epers.bichomon.backend.model.eventos;

public class Deposicion extends Evento {

	private String entrenadorRival;

	public Deposicion() {
		// TODO Auto-generated constructor stub
	}
	public Deposicion( String uvicacion, String entrenadorRival) {
		super("Deposicion", uvicacion);
		this.entrenadorRival=entrenadorRival;
	}
	public String getEntrenadorRival() {
		return this.entrenadorRival;
	}

	public void setEntrenadorRival(String entrenadorRival) {
		entrenadorRival = entrenadorRival;
	}


}
