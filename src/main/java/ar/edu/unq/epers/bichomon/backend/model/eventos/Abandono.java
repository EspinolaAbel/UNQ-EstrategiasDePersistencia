package ar.edu.unq.epers.bichomon.backend.model.eventos;

public class Abandono extends Evento {

	private String especieAbandonada;

	public Abandono() {
		// TODO Auto-generated constructor stub
	}

	public Abandono( String uvicacion, String especieAbandonada) {
		super("Abandono", uvicacion);
		this.especieAbandonada= especieAbandonada;
	}

	public String getEspecieAbandonada() {
		return especieAbandonada;
	}

	public void setEspecieAbandonada(String especieAbandonada) {
		this.especieAbandonada = especieAbandonada;
	}

}
