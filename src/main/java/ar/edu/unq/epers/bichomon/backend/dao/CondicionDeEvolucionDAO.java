package ar.edu.unq.epers.bichomon.backend.dao;

import java.util.Collection;

import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionDeEvolucion;

public interface CondicionDeEvolucionDAO {
	
	public void saveCondicion(CondicionDeEvolucion condicion);
	
	public CondicionDeEvolucion getCondicion(Integer id);

	public Collection<CondicionDeEvolucion> getAllCondiciones();
}
