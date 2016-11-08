package ar.edu.unq.epers.bichomon.backend.service.runner;

import java.util.function.Supplier;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Driver;

public class RunnerNeo4J {
	
	private static final ThreadLocal<Session> CONTEXTO = new ThreadLocal<>();
	private static Driver driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
	
	public static <T> T runInSession(Supplier<T> bloque) {

		if (CONTEXTO.get() != null)
			return bloque.get();
		
		Session session = null;
		
		try {
			session = driver.session();
			CONTEXTO.set(session);
			T resultado = bloque.get();
			
			return resultado;
		} finally {
			if (session != null) {
				CONTEXTO.set(null);
				session.close();
			}
		}
	}

	public static Session getCurrentSession() {
		Session session = CONTEXTO.get();
		if (session == null) {
			throw new RuntimeException("No hay ninguna session en el contexto");
		}
		return session;
	}

}
