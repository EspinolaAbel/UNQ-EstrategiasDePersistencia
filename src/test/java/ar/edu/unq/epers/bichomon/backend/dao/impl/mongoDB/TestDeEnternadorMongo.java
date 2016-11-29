package ar.edu.unq.epers.bichomon.backend.dao.impl.mongoDB;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Evento;
import ar.edu.unq.epers.bichomon.backend.model.DocumentoDeEntrenador;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;

public class TestDeEnternadorMongo {

	private MongoDocumentoDeEntrenadorDAO entrenadorDAO ;
	private DocumentoDeEntrenador entrenador;
	private Evento evento;
	
	@Before
	public void iniciar(){
		this.entrenador= new DocumentoDeEntrenador("Pedro ARAOZ");
		
		this.evento = new Evento("Coronacion","Dojo Cobrakai");this.entrenador.agregarEvento(evento);
		try
        {
            Thread.sleep(1000);
        }catch(InterruptedException e){}
		
		this.evento = new Evento("Captura","Pueblo chico");this.entrenador.agregarEvento(evento);
		try
        {
            Thread.sleep(1000);
        }catch(InterruptedException e){}
		
		this.evento = new Evento("Coronacion","Dojo Cobrakai");this.entrenador.agregarEvento(evento);
		try
        {
            Thread.sleep(1000);
        }catch(InterruptedException e){}
		this.evento = new Evento("Abandono", "Gauderia Los Seibos");this.entrenador.agregarEvento(evento);
		
		this.entrenadorDAO = new MongoDocumentoDeEntrenadorDAO();
		this.entrenadorDAO.save(entrenador);
	}
	@After
	public void dropAll(){
		this.entrenadorDAO.deleteAll();
	}
	
	@Test
	public void testSaveRegistroEntrenador() {
		DocumentoDeEntrenador entrenador = new DocumentoDeEntrenador("EntrenadorTest"); 
		
		this.entrenadorDAO.save(entrenador);
		DocumentoDeEntrenador entrenadorRecuperado = this.entrenadorDAO.buscarPorNombre("EntrenadorTest");
		assertEquals("EntrenadorTest",entrenadorRecuperado.getNombre());
		}

	@Test
	public void testBuscaunEntrenadorPorNombre(){
		DocumentoDeEntrenador jugadorRecuperado = this.entrenadorDAO.buscarPorNombre("Pedro ARAOZ");
		assertEquals(jugadorRecuperado.getEvetos().size(),4);	
		assertEquals("Pedro ARAOZ", jugadorRecuperado.getNombre());
	}
	
	
	@Test
	public void testBucarEventosDelEntrenador(){
		
		/**
		 *primero busco  todos los eventos del entrenadorque se generaron en el set up,
		 *luego unserto u nueveo evento  yn verifiico nuevamente la insercion  y  q el ultimo  
		 *sigue siendoel evento mas lejano
		 */
		Evento nuevoEvento= new Evento("Abandono", "Nueva Uvicacion");
		List<Evento> eventosRecuperados = this.entrenadorDAO.buscarEventosDelEntrenador("Pedro ARAOZ");
		assertEquals(4, eventosRecuperados.size());
		assertTrue(eventosRecuperados.get(0).getFecha()>eventosRecuperados.get(1).getFecha());
		assertTrue(eventosRecuperados.get(1).getFecha()>eventosRecuperados.get(2).getFecha());
		assertTrue(eventosRecuperados.get(2).getFecha()>eventosRecuperados.get(3).getFecha());
		
		this.entrenadorDAO.insertarEvento("Pedro ARAOZ", nuevoEvento);
		eventosRecuperados = this.entrenadorDAO.buscarEventosDelEntrenador("Pedro ARAOZ");
		assertEquals(5, eventosRecuperados.size());
		assertTrue(eventosRecuperados.get(3).getFecha()>eventosRecuperados.get(4).getFecha());
		System.out.println(eventosRecuperados);
	
	}
	
	@Test
	public void testBucarEventosDelEntrenadorEnUnaListaDeLugares(){
		
		/**
		 *primero busco  todos los eventos del entrenadorque que ocurrieron en "Pueblo chico" y 
		 *"Dojo Cobrakai"  luego inserto un nueveo evento en "Dojo Cobrakai" y verifiico
		 * nuevamente la insercion  y  q el ultimo sigue siendo el evento mas lejano
		 */
		Evento nuevoEvento= new Evento("Captura", "Gauderia Los Seibos");
		List<Evento> eventosRecuperados = this.entrenadorDAO.buscarEventosDelEntrenadorEnLugares("Pedro ARAOZ", Arrays.asList("Gauderia Los Seibos","Pueblo chico"));
		assertEquals(2, eventosRecuperados.size());
		assertTrue(eventosRecuperados.get(0).getFecha()>eventosRecuperados.get(1).getFecha());
		assertEquals(eventosRecuperados.get(0).getTipo(),"Abandono"); // el ultimo evento en alguno de estos dos lugares es la cporonacion
		assertEquals(eventosRecuperados.get(1).getTipo(), "Captura");
		
		this.entrenadorDAO.insertarEvento("Pedro ARAOZ", nuevoEvento);
		eventosRecuperados = this.entrenadorDAO.buscarEventosDelEntrenadorEnLugares("Pedro ARAOZ", Arrays.asList("Gauderia Los Seibos","Pueblo chico"));
		assertEquals(3, eventosRecuperados.size());
		assertTrue(eventosRecuperados.get(1).getFecha()>eventosRecuperados.get(2).getFecha());
		
	
	}
	
	
	
	
	
	/**
	 * 
	 */
	@Test
	public void testReemplazar(){
		DocumentoDeEntrenador entrenadorAReemplazar= new DocumentoDeEntrenador("Juan Perez");
		DocumentoDeEntrenador EntrenadorReemplazo = new DocumentoDeEntrenador("Peter2");
		this.entrenadorDAO.save(entrenadorAReemplazar);
		//me fijo que existe el entrenador guardado
		DocumentoDeEntrenador entrenadorRecuperado=this.entrenadorDAO.buscarPorNombre("Juan Perez");
		 assertEquals("Juan Perez", entrenadorRecuperado.getNombre());
		
		 // reemplazop el entrenador
		 this.entrenadorDAO.reemplazar("Juan Perez", EntrenadorReemplazo);
		DocumentoDeEntrenador jugadorRecuperado= this.entrenadorDAO.buscarPorNombre("Peter2");
		assertEquals("Peter2",jugadorRecuperado.getNombre());
	}
	
	
	
	/**
	 * inserto un evento en un entrenador que no existe y uno en otro  que si existe
	 * en el primenro se genera y se inserta  el evento y em el segundo se incrementa la cantidad
	 * de eventos que tenia.
	 */
	@Test
	public void testInsertarEvento(){
		
		Evento evento = new Evento("Coronacion", "dojo Akido");
		 evento = new Evento("Depuesto", "dojo Akido");
		this.entrenadorDAO.insertarEvento("Pepe Argento", evento);
		evento = new Evento("Depuesto", "dojo Akido");
		this.entrenadorDAO.insertarEvento("Pedro ARAOZ", evento);
		DocumentoDeEntrenador jugadorRecuperado= this.entrenadorDAO.buscarPorNombre("Pepe Argento");
		assertEquals(1,jugadorRecuperado.getEvetos().size());
		jugadorRecuperado= this.entrenadorDAO.buscarPorNombre("Pedro ARAOZ");
		assertEquals(5,jugadorRecuperado.getEvetos().size());
		
		
	}
	

}
