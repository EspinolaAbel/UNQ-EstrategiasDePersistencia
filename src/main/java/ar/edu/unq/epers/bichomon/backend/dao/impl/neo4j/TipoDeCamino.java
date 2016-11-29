package ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j;

public enum TipoDeCamino {
	AEREO(5), //el costo del viaje aereo es 5
	MARITIMO(2), //el costo del viaje maritimo es 2
	TERRESTRE(1);// el costo del camino Terrestre es 1
	
	
	private int costo;
	
	private TipoDeCamino(int costo){
		this.setCosto(costo);
	}

	public int getCosto() {
		return costo;
	}

	private void setCosto(int costo) {
		this.costo = costo;
	}
	
}
