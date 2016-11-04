package ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j;

import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Values;

import ar.edu.unq.epers.bichomon.backend.dao.MapaDAO;
import ar.edu.unq.epers.bichomon.backend.service.runner.RunnerNeo4J;

public class Neo4JMapaDAO implements MapaDAO {

	@Override
	public Integer calcularCostoDelViaje(String ubicacionPartida, String ubicacionDestino) {
		Session session = RunnerNeo4J.getCurrentSession();
		
		String query =	"MATCH (i:Pueblo) WHERE i nombre='{partida}' "
					+ 	"MATCH (F:Pueblo) WHERE f nombre='{destino}' "
					+ 	"MATCH (i)-[rs*]->(f) "
					+ 	"RETURN REDUCE (total = 0, r IN rs | total + r.costo)";
		StatementResult result =
				session.run(query, 
							Values.parameters("partida", ubicacionPartida, "destino", ubicacionDestino)
						);
		
		return //TODO: no sé qué devolver;
	}

}
