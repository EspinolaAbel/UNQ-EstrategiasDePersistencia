package ar.edu.unq.epers.bichomon.backend.service.impl;


import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.DocumentoDeEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.MapaDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateLugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.mongoDB.MongoDocumentoDeEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.Neo4JMapaDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.neo4j.TipoDeCamino;
import ar.edu.unq.epers.bichomon.backend.model.DocumentoDeEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Evento;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Guarderia;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Pueblo;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;
import ar.edu.unq.epers.bichomon.backend.service.runner.RunnerNeo4J;

public class FeedServiceTest {
	
	private FeedService feedService;
	
	private EntrenadorDAO entrenadorDAO;
	private MapaDAO mapaDAO;
	private DocumentoDeEntrenadorDAO documentoDAO;
	
	private DocumentoDeEntrenador documento;
	private Evento evento;
	@Before
	public void  setUp(){
		
		this.documentoDAO = new  MongoDocumentoDeEntrenadorDAO();
		this.mapaDAO = new Neo4JMapaDAO();
		this.entrenadorDAO = new HibernateEntrenadorDAO();
		feedService=new FeedService(this.documentoDAO,this.mapaDAO,this.entrenadorDAO);
		
		this.documento = new DocumentoDeEntrenador("entrenadorDeTest");
		//doc 1
		this.evento = new Evento("Coronacion", "dojo Test");
		 try
	        {
	            Thread.sleep(1000);
	        }catch(InterruptedException e){}
		this.documento.agregarEvento(evento);
		//doc 2		
		this.evento = new Evento("Depuesto", "dojo Test");
		 try
	        {
	            Thread.sleep(1000);
	        }catch(InterruptedException e){}
		this.documento.agregarEvento(evento);
		//doc 3		
		this.evento = new Evento("Arrivo", "Pueblo Test");
		 try
	        {
	            Thread.sleep(1000);
	        }catch(InterruptedException e){}
		this.documento.agregarEvento(evento);
		//doc 4		
		this.evento = new Evento("Abandono", "Guarderia Test");
		 try
	        {
	            Thread.sleep(1000);
	        }catch(InterruptedException e){}
		this.documento.agregarEvento(evento);
		//doc 5	
		this.evento = new Evento("Abandono", "Guarderia Test desconectada");
		  try
		  	{
			  Thread.sleep(1000);
		  	}catch(InterruptedException e){}
		 this.documento.agregarEvento(evento);
		//guardo el doc con los 5 eventos
		 this.documentoDAO.save(documento);
		
				
	
	 
		}
	
	
	@After 
	public void clean(){
		documentoDAO.deleteCollection();
		
	}
	
	@Test
	public void dadoUnEntrenadorRecuperaTodosSusEventos(){
		
		//solo testeo la cantidad de Eventos que me devuelve, ya que el orden  esta 
		//testeado en el test de MongoDocumentoEnt...  
		List<Evento> eventos= this.feedService.feedEntrenador("entrenadorDeTest");
		assertNotNull(eventos);
		assertEquals(5,eventos.size());
		
	}
	
	@Test
	public void dadoUnEntrenadorRecuperaTodosSusEventosEnUnaListaDeLugares(){
		//el generador de datos test me genera al entrenador y lo uvica en "dojo Test" en la BBDD mySql
		//me genera el mapa de neo4j con las conecciones ("Pueblo Test", "Guarderia Test") 
		generarDatosDeTest();
		//luego FeedUvicacion recuperara las uvicaciones conexas con la actual, y me traera todos 
		//los eventos que tuvo en esas ciudades, para este caso seran 4, dos en la uvicacion  actual, 
		//uno en "pueblo Test" y otra en "Guarderia Test"
		List<Evento> eventos=this.feedService.feedUbicacion("entrenadorDeTest");
		
		System.out.println(eventos);
		assertNotNull(eventos);
		assertEquals(4,eventos.size());
		
		
	}
	
// metodos  para generacion de datoos
	
	
	public void generarDatosDeTest(){
		
		
		
		Lugar uvicacionActual = new Dojo("dojo Test");
		Lugar uvicacion1 = new Pueblo("Pueblo Test");
		Lugar uvicacion2 = new Guarderia("Guarderia Test");
		Lugar uvicacion3= new Pueblo("PuebloDesconectado");
		
		Entrenador entrenador = new Entrenador("entrenadorDeTest");
		entrenador.setUbicacionActual(uvicacionActual);
		//guardo el entrenador en  mySQL para  recupererlo   del servicio feed 
		Runner.runInSession(()->{
			HibernateLugarDAO lDAO = new HibernateLugarDAO();
			lDAO.saveLugar(uvicacionActual);
			this.entrenadorDAO.saveEntrenador(entrenador);
			return null;
		});
		
		// genero un mapa para los test
		RunnerNeo4J.runInSession(()->{
			this.mapaDAO.saveLugar(uvicacionActual);
			this.mapaDAO.saveLugar(uvicacion1);
			this.mapaDAO.saveLugar(uvicacion2);
			this.mapaDAO.saveLugar(uvicacion3);
			
			//Lugar a asertar:
			this.mapaDAO.crearConexion(uvicacionActual.getNombre(), uvicacion1.getNombre(), TipoDeCamino.TERRESTRE);
			this.mapaDAO.crearConexion(uvicacionActual.getNombre(), uvicacion2.getNombre(), TipoDeCamino.TERRESTRE);
			this.mapaDAO.crearConexion(uvicacion2.getNombre(), uvicacion3.getNombre(), TipoDeCamino.TERRESTRE);
			
			return null;
		});
		
	}
	
}
