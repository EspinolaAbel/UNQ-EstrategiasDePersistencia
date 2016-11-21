package fruta;

public enum Fruta {
	BANANA(10), 
	PERA(15),
	MANZANA(20);
	
	private Integer costo;
	
	private Fruta(Integer costo) {
		this.setCosto(costo);
	}

	public Integer getCosto() {
		return costo;
	}

	private void setCosto(Integer costo) {
		this.costo = costo;
	}
}
