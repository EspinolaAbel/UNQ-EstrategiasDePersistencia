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
	
	//PEDRO---------------------------------------------------------------------------
	
	@Override
	public void saveLugar(Lugar lugar) {
		Session session = RunnerNeo4J.getCurrentSession();
		String query = "MERGE (l :Lugar:"+lugar.tipoDeLugar()+" {nombre:{elNombre}}) " ;
		session.run(query, Values.parameters("elNombre", lugar.getNombre()));
	}
	
	/** Se responde si el lugar con el nombre dado se encuentra en el mapa, es decir, si está 
	 * persistido en la base de datos.*/
	public boolean existeLugar(String nombreLugar) {
		Session session = RunnerNeo4J.getCurrentSession();
		String query = 	"MATCH (l:Lugar) WHERE l.nombre={unNombreDeLugar} "
					+	"RETURN l";
		return session.run(query, Values.parameters("unNombreDeLugar", nombreLugar) ).hasNext();
	}
	
	@Override
	public void crearConeccion(String ubicacion1, String ubicacion2, String tipoCamino){
		Session session = RunnerNeo4J.getCurrentSession();
		int costo=0;
		switch (tipoCamino.toUpperCase()){
			case "AEREO"	: costo=5; break;
			case "MARITIMO"	: costo=2; break;
			case "TERRESTRE": costo=1;
		}

		String query= "MATCH (lugar1 {nombre: {unLugar} }	) "
					+ "MATCH (lugar2 {nombre: {otroLugar} }	) "
					+ "MERGE (lugar1)-[r:"+tipoCamino.toUpperCase()+"]->(lugar2) "
					+ "ON CREATE SET r.costo = {unCosto}";
		
		session.run(query, Values.parameters("unLugar",ubicacion1, "otroLugar", ubicacion2,"unCosto",costo));
	}
	
	@Override
	public List<String> lugaresAdyacentes(String ubicacion, String tipoCamino){
		Session session = RunnerNeo4J.getCurrentSession();
		String query= 	"MATCH (lugar1 {nombre: {unLugar}})-[r:" + tipoCamino + "]->(lugar2) " +
						"return lugar2";
		StatementResult result=	session.run(query, Values.parameters("unLugar",ubicacion));
		return result.list(record -> {
				Value lugar = record.get(0);
				String nombreDeLugar = lugar.get("nombre").asString();
				return nombreDeLugar;
		});
	}
	
}