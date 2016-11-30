package ar.edu.unq.epers.bichomon.backend.model.eventos;

public class Captura extends Evento {
	private String especieCapturada;

	public Captura() {
		// TODO Auto-generated constructor stub
	}

	public Captura( String uvicacion, String especieCapturada) {
		super("Captura", uvicacion);
		this.especieCapturada=especieCapturada;
	}

	public String getEspecieCapturada() {
		return especieCapturada;
	}

	public void setEspecieCapturada(String especieCapturada) {
		this.especieCapturada = especieCapturada;
	}

}
