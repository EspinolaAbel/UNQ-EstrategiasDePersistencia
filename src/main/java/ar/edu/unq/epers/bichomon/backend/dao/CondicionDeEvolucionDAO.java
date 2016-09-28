package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionDeEvolucion;

public interface CondicionDeEvolucionDAO {
	
	public void guardar(CondicionDeEvolucion condicion);
	
	public CondicionDeEvolucion recuperar();
}
