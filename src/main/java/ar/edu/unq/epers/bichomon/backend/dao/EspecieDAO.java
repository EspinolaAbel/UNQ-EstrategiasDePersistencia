package ar.edu.unq.epers.bichomon.backend.dao;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.Especie;

public interface EspecieDAO {
	
	public void saveEspecie(Especie e);
	
	public Especie getEspecie(String nombre);
	
	public List<Especie> getAllEspecies();

	public Especie getEspecieLider();

	public List<Especie> getMasPopulares();

	public List<Especie> getMenosPopulares();
}