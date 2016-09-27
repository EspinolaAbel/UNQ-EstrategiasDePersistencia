package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;

/**
	 * Tiene la responsabilidad de guardar y recuperar items desde
	 * el medio persistente
	 */
public interface BichoDAO {

		void guardar(Bicho bicho);
		
		Bicho recuperar(int  idBicho);
		
	}

