package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;

public interface PuebloDAO {
	
	public Pueblo getPueblo(String pueblo);
	public void savePueblo(Pueblo pueblo);

}
