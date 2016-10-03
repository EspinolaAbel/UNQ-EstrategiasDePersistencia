package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

	/**
	 * Tiene la responsabilidad de guardar y recuperar items desde
	 * el medio persistente
	 */

public interface BichoDAO {
	
	public void saveBicho(Bicho bicho);
	public Bicho getBicho(int id );
		
	}

