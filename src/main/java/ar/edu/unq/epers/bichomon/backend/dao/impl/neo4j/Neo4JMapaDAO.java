package ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Values;

import ar.edu.unq.epers.bichomon.backend.dao.MapaDAO;
import ar.edu.unq.epers.bichomon.backend.service.runner.RunnerNeo4J;

public class Neo4JMapaDAO implements MapaDAO {

	@Override
	public Integer costoDelViajeMasCorto(String ubicacionPartida, String ubicacionDestino) {
		String query =	"MATCH (i:Pueblo) WHERE i.nombre='{partida}' "
					+ 	"MATCH (f:Pueblo) WHERE f.nombre='{destino}' "
					+ 	"MATCH (i)-[rs*]->(f) "
					+ 	"RETURN REDUCE (total = 0, r IN rs | total + r.costo)";
		return this.calcularCostoDelViaje(ubicacionPartida, ubicacionDestino, query);
	}
	
	@Override
	public Integer costoDelViajeDirecto(String ubicacionPartida, String ubicacionDestino) {
		String query =	"MATCH (i:Pueblo) WHERE i.nombre='{partida}' "
					+ 	"MATCH (f:Pueblo) WHERE f.nombre='{destino}' "
					+ 	"MATCH (i)-[r]->(f) "
					+ 	"RETURN r.costo";
		return this.calcularCostoDelViaje(ubicacionPartida, ubicacionDestino, query);
	}
	
	/** Se calcula el costo de viajar entre dos ubicaciones dadas.
	 * NOTA: Las ubicaciones dadas DEBEN existir en el mapa persistido.*/
	private Integer calcularCostoDelViaje(String ubicacionPartida, String ubicacionDestino, String query) {
		Session session = RunnerNeo4J.getCurrentSession();
		Record result =	session.run(query, 
										Values.parameters("partida", ubicacionPartida, "destino", ubicacionDestino)
									).single();
		
		return result.get(0).asInt();	//TODO: no sé si esto funciona o no.
	}
}
