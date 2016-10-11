package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.Nivel;

public interface NivelDAO {
	
	public void saveNivel(Nivel nivel);
	
	public Nivel getNivel(Integer nroNivel);
	
}
