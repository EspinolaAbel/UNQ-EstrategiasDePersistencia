package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.TipoDeCamino;

public interface TipoDeCaminoDAO {
	
	public void saveTipoDeCamino(TipoDeCamino tipoDeCamino);
	public TipoDeCamino getTipoDeCamino(String tipoDeCamino);

}
