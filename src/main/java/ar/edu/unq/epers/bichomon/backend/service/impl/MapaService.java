package ar.edu.unq.epers.bichomon.backend.service.impl;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.MapaDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.FondosInsuficientesException;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.RunnerNeo4J;

public class MapaService {

	private LugarDAO lugarDAO;
	private EntrenadorDAO entrenadorDAO;
	private MapaDAO mapaDAO;
	
	public MapaService(EntrenadorDAO entrenadorDAO, LugarDAO lugarDAO){
		this.lugarDAO = lugarDAO;
		this.entrenadorDAO = entrenadorDAO;
	}

	public MapaService(EntrenadorDAO entrenadorDAO, LugarDAO lugarDAO, MapaDAO mapaDAO) {
		this(entrenadorDAO, lugarDAO);
		this.mapaDAO = mapaDAO;
	}
	
	

	/** Dado los nombres de un {@link Entrenador} y un {@link Lugar} persistidos en mi BBDD, se cambiará
	 * al entrenador desde su ubicación actual al lugar especificado por el parámetro.
	 * @param nombreEntrenador - nombre del entrenador a recuperar de la BBDD
	 * @param nombreLugar - nombre del lugar a recuperar de la BBDD * /
	public void mover(String nombreEntrenador, String nombreLugar) {
		LugarDAO lugarDAO = new HibernateLugarDAO();
		EntrenadorDAO entrenadorDAO = new HibernateEntrenadorDAO();
		
		Runner.runInSession(() -> {
			Entrenador entrenador = entrenadorDAO.getEntrenador(nombreEntrenador);
			Lugar lugar = lugarDAO.getLugar(nombreLugar);
			entrenador.setUbicacionActual(lugar);
			return null;
		});	
	}*/
	
	/** Dado el nombre de un {@link Lugar} persistido en la BBDD se devuelve la cantidad de entrenadores
	 * que se encuentran actualmente en dicho lugar.
	 * @param nombreLugar - nombre del lugar persistido en la BBDD del cual se obtendrá la cantidad
	 * 						de entrenadores*/
	public Integer cantidadEntrenadores(String nombreLugar) {
		EntrenadorDAO entrenadorDAO = new HibernateEntrenadorDAO();
		return
		Runner.runInSession(() -> {
			return entrenadorDAO.getCantidadDeEntrenadoresUbicadosEnLugar(nombreLugar);
		});
	}
	
	/** Dado el nombre de un {@link Dojo} persistido en la BBDD se devuelve el {@link Bicho} que
	 * actualmente es campeón en dicho lugar.
	 * @param nombreDojo - nombre del lugar persistido en la BBDD del cual buscará el bicho campeón.*/
	public Bicho campeon(String nombreDojo) {
		LugarDAO lugarDAO = new HibernateLugarDAO();
		return
		Runner.runInSession(() -> {
			return lugarDAO.getBichoCampeonActualDelDojo(nombreDojo);
		});
	}
	
	/** Dado el nombre de un {@link Dojo} persistido en la BBDD se devuelve el {@link Bicho} que
	 * es campeón durante más tiempo en dicho lugar.
	 * @param dojoNombre - nombre del lugar persistido en la BBDD del cual buscará el campeón más
	 * 					   longevo.*/
	public Bicho campeonHistorico(String nombreDojo) {
		LugarDAO lugarDAO = new HibernateLugarDAO();
		return Runner.runInSession(() -> {
			return lugarDAO.getCampeonHistoricoDelDojo(nombreDojo);
		});
	}
	

	public void mover(String nombreEntrenador, String nombreDestino) {
		Runner.runInSession(()->{
			Entrenador entrenador = this.entrenadorDAO.getEntrenador(nombreEntrenador);
			Lugar partida = entrenador.getUbicacionActual();
			Lugar destino = this.lugarDAO.getLugar(nombreDestino);
			
			Integer costoDelViaje =
					RunnerNeo4J.runInSession(() -> {
						return mapaDAO.costoDelViajeDirecto(partida.getNombre(), destino.getNombre());
					});
			try{
				entrenador.viajarALugar(destino, costoDelViaje);
			}
			catch(FondosInsuficientesException e) {
				throw new CaminoMuyCostosoException(entrenador, destino, costoDelViaje);
			}
			return null;
		});
	}
	
	public void moverMasCorto(String nombreEntrenador, String nombreDestino) {
		Runner.runInSession(()->{
			Entrenador entrenador = this.entrenadorDAO.getEntrenador(nombreEntrenador);
			Lugar partida = entrenador.getUbicacionActual();
			Lugar destino = this.lugarDAO.getLugar(nombreDestino);
			
			Integer costoDelViaje =
					RunnerNeo4J.runInSession(() -> {
						return mapaDAO.costoDelViajeMasCorto(partida.getNombre(), destino.getNombre());
					});
			
			try{
				entrenador.viajarALugar(destino, costoDelViaje);
			}
			catch(FondosInsuficientesException e) {
				throw new CaminoMuyCostosoException(entrenador, destino, costoDelViaje);
			}
			return null;
		});
	}
	
	/**
	 * Persiste la ubicacion  pasada por parametro en la base de datos de hibernate y neo4j
	 * @param lugar */
	public void crearUbicacion(Lugar lugar){
		Runner.runInSession(()->{
			lugarDAO.saveLugar(lugar);
			return null;
		});
		RunnerNeo4J.runInSession(()->{
			mapaDAO.saveLugar(lugar);
			return null;
		});
	}
	
	
	/**
	 * Conecta dos ubicaciones preexistentes por medio de un tipo de camino.
	 * @param */
	public void conectar(String ubicacion1, String ubicacion2, String tipoCamino){
		RunnerNeo4J.runInSession(()->{
			this.mapaDAO.crearConexion(ubicacion1, ubicacion2, tipoCamino);
			return null;
		});
	}
	

	/**
	 * Responde con una lista de los lugares adyacentes al lugar dado por un tipo de camino.
	 * @param */
	public List<String> conectados(String ubicacion, String tipoCamino){
		return
		RunnerNeo4J.runInSession(()->{
			return this.mapaDAO.lugaresAdyacentes(ubicacion, tipoCamino);
		});
	}
}
