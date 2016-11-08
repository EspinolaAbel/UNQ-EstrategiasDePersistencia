package ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j;

import java.util.List;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Value;
import org.neo4j.driver.v1.Values;

import ar.edu.unq.epers.bichomon.backend.dao.LugarDAONeo4j;
import ar.edu.unq.epers.bichomon.backend.model.TipoDeCamino;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;

public class Neo4jLugarDAO implements LugarDAONeo4j {

	private Driver driver;
	
	
	public Neo4jLugarDAO() {
		this.driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "root" ) );
	}

	@Override
	public void saveLugar(Lugar lugar) {
			Session session = this.driver.session();
			try {
				String query = "MERGE (l:"+ lugar.tipoDeLugar() +"{nombre:{elNombre}}) " ;
				session.run(query, Values.parameters("elLugar",lugar.getNombre(),"elNombre", lugar.getNombre()));
			} finally {
				session.close();
			}
		}

	@Override
	public StatementResult recuperarLugar(Lugar lugarARecuperar) {
		
		Session session = this.driver.session();

		try {

			String query = "MATCH (l {nombre: {unNombreDeLugar}}) "+
			"RETURN l";
			StatementResult result = session.run(query, Values.parameters("unNombreDeLugar", lugarARecuperar.getNombre()));
				return  result;	
		} finally {
			session.close();
		}
	}
	
	
	
	public void crearConeccion(String ubicacion1, String ubicacion2, String tipoCamino){
		
		Session session = this.driver.session();
		int costo=0;
		switch (tipoCamino){
			case "AEREO"	: costo=5; break;
			case "MARITIMO"	: costo=2; break;
			case "TERRESTRE": costo=1;
			}
		
		
		try {
			String query= "MATCH (lugar1 {nombre: {unLugar}}) " +
					"MATCH (lugar2  {nombre: {otroLugar}}) " +
					"MERGE (lugar1)-[r:" + tipoCamino + "]->(lugar2) "+
					"ON CREATE SET r.costo = {unCosto}";//CREATE
					session.run(query, Values.parameters("unLugar",ubicacion1, "otroLugar", ubicacion2,"unCosto",costo));

		} finally {
			session.close();
		}
			
		}

public void crearConeccion(String ubicacion1, String ubicacion2, TipoDeCamino tipoCamino){
		
		Session session = this.driver.session();
		
		
		try {
			String query= "MATCH (lugar1 {nombre: {unLugar}}) " +
					"MATCH (lugar2  {nombre: {otroLugar}}) " +
					"MERGE (lugar1)-[r:" + tipoCamino.getTipo() + "]->(lugar2) "+
					"ON CREATE SET r.costo = {unCosto}";//CREATE
					session.run(query, Values.parameters("unLugar",ubicacion1, "otroLugar", ubicacion2,"unCosto",tipoCamino.getCosto()));

		} finally {
			session.close();
			}
			
	}


	@Override
	public StatementResult recuperarRelacion(String ubicacion1, String ubicacion2) {
		Session session= this.driver.session();
		
		try{
			String query= 	"MATCH (lugar1 {nombre: {unLugar}})-[r]->(lugar2 {nombre: {otroLugar}}) " +
							"return r";
			StatementResult result=	session.run(query, Values.parameters("unLugar",ubicacion1, "otroLugar", ubicacion2));
		 return result;
		}finally {
			session.close();
		}
		
	}
	
	
	public List<String> conectados(String ubicacion, String tipoCamino){
		Session session= this.driver.session();
		
		try{
			String query= 	"MATCH (lugar1 {nombre: {unLugar}})-[r:" + tipoCamino + "]->(lugar2) " +
							"return lugar2";
			StatementResult result=	session.run(query, Values.parameters("unLugar",ubicacion));
		return result.list(record -> {
				Value lugar = record.get(0);
				String nombreDeLugar = lugar.get("nombre").asString();
				return nombreDeLugar;
			});
		}finally {
			session.close();
		}
		
	}
	
	//Funciones auxiliares

	/**
	 * elimina las conecciones de entrada y salida del nodo en cuestion
	 * @param nombreDelLugar
	 */
	public void eliminarConecciones(String nombreDelLugar){
		Session session= this.driver.session();
		
		try{
			String query= 	"match ( n {nombre: {unNombreDeLugar}})<-[r]-() delete r" ;
			session.run(query, Values.parameters("unNombreDeLugar",nombreDelLugar));
	
			query= 	"match ( n {nombre: {unNombreDeLugar}})-[r]->() delete r" ;
			session.run(query, Values.parameters("unNombreDeLugar",nombreDelLugar));
			
		
		}finally {
			session.close();
			}
	}


	/**
	 * elimina el nodo pasado por parametrosegun la propiead nombre que debe ser identificador
	 * el nodo no debe tener conecciones
	 * 	 * @param nombreDelLugar
	 */
	public void eliminarNodo(String identificador){
		Session session= this.driver.session();
		
		try{
			String query= 	"match ( n {nombre: {unIdentificador}}) delete n" ;
			session.run(query, Values.parameters("unIdentificador",identificador));
		
		}finally {
			session.close();
			}
	}
	
	public void eliminarLugar(String nombreDelLugar){
		eliminarConecciones(nombreDelLugar);
		eliminarNodo(nombreDelLugar);
	}




}			
	






