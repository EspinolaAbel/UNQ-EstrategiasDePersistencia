package ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j;

import java.util.List;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;

import ar.edu.unq.epers.bichomon.backend.dao.MapaDAO;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.service.runner.RunnerNeo4J;

public class Neo4JMapaDAO implements MapaDAO {

	
	@Override
	public Integer costoDelViajeMasCorto(String ubicacionPartida, String ubicacionDestino) {
		String query =	"MATCH (partida:Lugar { nombre:{partida} }) "
					+ 	"MATCH (destino:Lugar { nombre:{destino} }) "
					+ 	"MATCH shortestPath((partida)-[caminos*]->(destino)) "
					+ 	"RETURN REDUCE (total = 0, c IN caminos | total + c.costo)";
		return this.calcularCostoDelViaje(ubicacionPartida, ubicacionDestino, query);
	}
	
	
	@Override
	public Integer costoDelViajeDirecto(String ubicacionPartida, String ubicacionDestino) {
		String query =	"MATCH (partida:Lugar { nombre:{partida} }) "
					+ 	"MATCH (destino:Lugar { nombre:{destino} }) "
					+ 	"MATCH (partida)-[camino]->(destino) "
					+ 	"RETURN camino.costo ORDER BY camino.costo ASC LIMIT 1";
		try{
			return this.calcularCostoDelViaje(ubicacionPartida, ubicacionDestino, query);
		}
		catch(DestinoInalcanzableException e){
			throw new UbicacionMuyLejanaException(ubicacionPartida, ubicacionDestino);
		}
	}
	
	
	/** Se calcula el costo de viajar desde la ubicacion de partida hacia la ubicación de destino.
	 * @param ubicacionPartida - nombre de la ubicación actual.
	 * @param ubicacionDestino - nombre de la ubicación destino.
	 * @throws DestinoInalcanzableException si no se puede alcanzar el destino dado. */
	private Integer calcularCostoDelViaje(String ubicacionPartida, String ubicacionDestino, String query) {
		Session session = RunnerNeo4J.getCurrentSession();
		List<Record> result = session.run(query, Values.parameters("partida", ubicacionPartida, "destino", ubicacionDestino) ).list();
		if(result.isEmpty())
			throw new DestinoInalcanzableException(ubicacionPartida, ubicacionDestino);
		else
			return result.get(0).get(0).asInt();
	}
	
	
	@Override
	public void saveLugar(Lugar lugar) {
		Session session = RunnerNeo4J.getCurrentSession();
		String query = "MERGE (l:Lugar:"+lugar.tipoDeLugar()+" {nombre:{elNombre}}) " ;
		session.run(query, Values.parameters("elNombre", lugar.getNombre()));
	}
	
	
	@Override
	public boolean existeLugar(String nombreLugar) {
		Session session = RunnerNeo4J.getCurrentSession();
		String query = 	"MATCH (l:Lugar { nombre:{unNombreDeLugar} }) "
					+	"RETURN l";
		return session.run(query, Values.parameters("unNombreDeLugar", nombreLugar) ).hasNext();
	}
	
	
	@Override
	public boolean existeCamino(String partida, String destino) {
		Session session = RunnerNeo4J.getCurrentSession();
		String query = 	"MATCH (partida:Lugar { nombre:{partida} }) "
					+ 	"MATCH (destino:Lugar { nombre:{destino} }) "
					+	"MATCH (partida)-[caminos*]-(destino) "
					+	"RETURN (count(caminos) > 0) as hayResultados";
		List<Record> result = session.run(query, Values.parameters("partida", partida, "destino", destino) ).list();
		return result.get(0).get(0).asBoolean();
	}
	
	
	@Override
	public void crearConexion(String ubicacion1, String ubicacion2, TipoDeCamino tipoCamino){
		Session session = RunnerNeo4J.getCurrentSession();
		String query= "MATCH (lugar1 {nombre: {unLugar} }	) "
					+ "MATCH (lugar2 {nombre: {otroLugar} }	) "
					+ "MERGE (lugar1)-[camino:"+tipoCamino.toString()+"]->(lugar2) "
					+ "ON CREATE SET camino.costo = {unCosto}";
		session.run(query, Values.parameters("unLugar",ubicacion1, "otroLugar", ubicacion2,"unCosto",tipoCamino.getCosto()));
	}
	
	
	@Override
	public List<String> lugaresAdyacentes(String ubicacion, TipoDeCamino tipoCamino){
		Session session = RunnerNeo4J.getCurrentSession();
		String query= 	"MATCH (puntoDePartida:Lugar {nombre:{nombrePartida}})-[r:"+tipoCamino.toString()+" ]->(adyacentes) " +
						"return adyacentes";
		StatementResult result=	session.run(query, Values.parameters("nombrePartida",ubicacion));
		return result.list(record -> {
				Value lugar = record.get(0);
				String nombreDeLugar = lugar.get("nombre").asString();
				return nombreDeLugar;
		});
	}
	
	@Override
	public List<String> lugaresAdyacentesDeCualquierTipo(String ubicacion){
		Session session = RunnerNeo4J.getCurrentSession();
		String query= 	"MATCH (puntoDePartida:Lugar {nombre:{nombrePartida}})-[ ]-(adyacentes) " +
						"return adyacentes";
		StatementResult result=	session.run(query, Values.parameters("nombrePartida",ubicacion));
		return result.list(record -> {
				Value lugar = record.get(0);
				String nombreDeLugar = lugar.get("nombre").asString();
				return nombreDeLugar;
		});
	}
	
}