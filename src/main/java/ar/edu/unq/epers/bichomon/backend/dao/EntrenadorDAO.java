package ar.edu.unq.epers.bichomon.backend.dao;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.Entrenador;

public interface EntrenadorDAO {

	public void saveEntrenador(Entrenador entrenador);
	
	public Entrenador getEntrenador(String nombre);

	public int getCantidadDeEntrenadoresUbicadosEnLugar(String nombreLugar);

	public List<Entrenador> getEntrenadoresConBichosCampeones();

	public List<Entrenador> getLideres();
	
}
