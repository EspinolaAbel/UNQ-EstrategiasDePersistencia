package ar.edu.unq.epers.bichomon.backend.dao;


import ar.edu.unq.epers.bichomon.backend.model.PuntosDeExperiencia;


public interface PuntosDAO {
	
	public void savePuntosDeExperiencia (PuntosDeExperiencia p);
	public PuntosDeExperiencia getPuntosDeExperiencia(String tarea);

}
