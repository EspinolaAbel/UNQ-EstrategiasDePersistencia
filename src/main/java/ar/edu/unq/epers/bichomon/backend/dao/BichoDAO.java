package ar.edu.unq.epers.bichomon.backend.dao;

import java.util.Collection;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

public interface BichoDAO {
	
	public void saveBicho(Bicho bicho);
	
	public Bicho getBicho(int id );
	
	public Collection<Bicho> getAllBichos();
	

}