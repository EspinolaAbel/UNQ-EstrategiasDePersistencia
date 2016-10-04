package ar.edu.unq.epers.bichomon.backend.dao;

import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;

public interface LugarDAO {
	
	public void saveLugar(Lugar lugar);
	
	public Lugar getLugar(String nombre);

	public Bicho getBichoCampeonActualDelDojo(String nombreDojo);

	public Bicho getCampeonHistoricoDelDojo(String dojoNombre);

}
