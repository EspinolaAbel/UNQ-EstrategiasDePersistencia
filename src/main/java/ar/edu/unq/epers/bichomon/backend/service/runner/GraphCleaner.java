package ar.edu.unq.epers.bichomon.backend.service.runner;

import org.neo4j.driver.v1.Session;

public class GraphCleaner {

	/**Borra todo el grafo.*/
	public static void cleanUpGraph() {
		RunnerNeo4J.runInSession(()->{
			Session session = RunnerNeo4J.getCurrentSession();
			String query = 	"MATCH (all) "
						+ 	"DETACH DELETE all";
			session.run(query);
			return null;
		});
	}
}
