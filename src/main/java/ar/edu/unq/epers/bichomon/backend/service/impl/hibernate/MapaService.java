package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAONeo4j;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.Neo4jLugarDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class MapaService {

	public MapaService() {
		super();
	}

	/** Dado los nombres de un {@link Entrenador} y un {@link Lugar} persistidos en mi BBDD, se cambiará
	 * al entrenador desde su ubicación actual al lugar especificado por el parámetro.
	 * @param nombreEntrenador - nombre del entrenador a recuperar de la BBDD
	 * @param nombreLugar - nombre del lugar a recuperar de la BBDD */
	public void mover(String nombreEntrenador, String nombreLugar) {
		LugarDAO lugarDAO = new HibernateLugarDAO();
		EntrenadorDAO entrenadorDAO = new HibernateEntrenadorDAO();
		
		Runner.runInSession(() -> {
			Entrenador entrenador = entrenadorDAO.getEntrenador(nombreEntrenador);
			Lugar lugar = lugarDAO.getLugar(nombreLugar);
			entrenador.setUbicacionActual(lugar);
			return null;
		});	
	}
	
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
	 * es campeón duranta más tiempo en dicho lugar.
	 * @param dojoNombre - nombre del lugar persistido en la BBDD del cual buscará el campeón más
	 * 					   longevo.*/
	public Bicho campeonHistorico(String nombreDojo) {
		LugarDAO lugarDAO = new HibernateLugarDAO();
		return Runner.runInSession(() -> {
			return lugarDAO.getCampeonHistoricoDelDojo(nombreDojo);
		});
	}
	
	
	/**
	 * Persiste la uvicacion  pasada por parametro en la base de datos de hibernate y neo4j
	 * 
	 * @param lugar
	 */
	
	public void crearUvicacion(Lugar lugar){
		
		LugarDAO lugarDAOHibernate= new HibernateLugarDAO();
		LugarDAONeo4j lugarDAONeo4j= new Neo4jLugarDAO();
		
		Runner.runInSession(()->{
			lugarDAOHibernate.saveLugar(lugar);
			return null;
		});
		lugarDAONeo4j.saveLugar(lugar);
		
	}
	
	
/**
 * conectar(String ubicacion1, String ubicacion2, String tipoCamino)  conecta dos ubicaciones 
 * (se asumen preexistentes) por medio de un tipo de camino.
 * @param 
 */
	public void conectar(String ubicacion1, String ubicacion2, String tipoCamino){
		
		LugarDAONeo4j lugarDAONeo4j= new Neo4jLugarDAO();
		lugarDAONeo4j.crearConeccion(ubicacion1, ubicacion2, tipoCamino);
		
		
		
	}

}
