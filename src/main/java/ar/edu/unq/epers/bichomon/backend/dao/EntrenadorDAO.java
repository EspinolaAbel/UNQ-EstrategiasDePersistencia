package ar.edu.unq.epers.bichomon.backend.dao;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.Entrenador;

public interface EntrenadorDAO {

	public void saveEntrenador(Entrenador entrenador);
	
	public Entrenador getEntrenador(String nombre);
	
	public List<Entrenador> getAllEntrenadores();

	public int getCantidadDeEntrenadoresUbicadosEnLugar(String nombreLugar);
	
}
