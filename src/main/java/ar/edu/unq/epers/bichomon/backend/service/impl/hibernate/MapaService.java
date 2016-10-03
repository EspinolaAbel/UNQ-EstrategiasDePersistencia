package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class MapaService {

	private LugarDAO lugarDAO;
	private EntrenadorDAO entrenadorDAO;

	public MapaService(EntrenadorDAO entrenadorDAO, LugarDAO lugarDAO) {
		this.entrenadorDAO = entrenadorDAO;
		this.lugarDAO = lugarDAO;
	}

	/** Dado los nombres de un {@link Entrenador} y un {@link Lugar} persistidos en mi BBDD se cambiará
	 * al entrenador desde su ubicación actual a la especificada por parámetro.
	 * @param nombreEntrenador - nombre del entrenador a recuperar de la BBDD
	 * @param nombreLugar - nombre del lugar a recuperar de la BBDD */
	public void mover(String nombreEntrenador, String nombreLugar) {
		Runner.runInSession(() -> {
			Entrenador entrenador = this.entrenadorDAO.getEntrenador(nombreEntrenador);
			Lugar lugar = this.lugarDAO.getLugar(nombreLugar);
			entrenador.setUbicacionActual(lugar);
			return null;
		});
		
	}
	
	/** Dado el nombre de un {@link Lugar} persistido en la BBDD se devuelve la cantidad de entrenadores
	 * que se encuentran actualmente en dicho lugar.
	 * @param nombreLugar - nombre del lugar persistido en la BBDD del cual se obtendrá la cantidad
	 * 						de entrenadores*/
	public Integer cantidadEntrenadores(String nombreLugar) {
		return
		Runner.runInSession(() -> {
			return entrenadorDAO.getCantidadDeEntrenadoresUbicadosEnLugar(nombreLugar);
		});
	}
	
	public Bicho campeon(String nombreDojo) {
		return
		Runner.runInSession(() -> {
			return lugarDAO.getBichoCampeonActualDelDojo(nombreDojo);
		});
	}
	
	public Bicho campeonHistorico(String dojo) {
		//TODO
		return null;
	}
}
