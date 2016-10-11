package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.lugar.EspecieConProbabilidad;

public interface EspecieConProbabilidadDAO {
	
	public void saveEspecieConProbabilidad(EspecieConProbabilidad e);
	
	public EspecieConProbabilidad getEspecieConProbabilidad(int id);

}
