package ar.edu.unq.epers.bichomon.backend.service.runner;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class Truncator {
	
	/** Este método de clase se encarga de limpiar todas las tablas de la  base de datos.*/
	public static void cleanUpTables() {
		Runner.runInSession(() -> {
			Session session = Runner.getCurrentSession();
			@SuppressWarnings("unchecked")
			Query<String> query = session.createNativeQuery("SHOW TABLES");
			List<String> todasLasTablas = query.getResultList();
			
			try {
				session.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
				
				for(String tabla: todasLasTablas)
					session.createNativeQuery("TRUNCATE TABLE "+ tabla).executeUpdate();
				
			}
//			catch(Exception e) {
//				throw e;
//			}
			finally{
				session.createNativeQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
			}
			return null;
		});
	}

}