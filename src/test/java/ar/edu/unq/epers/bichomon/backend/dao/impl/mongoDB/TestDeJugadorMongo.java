package ar.edu.unq.epers.bichomon.backend.dao.impl.mongoDB;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.Evento;
import ar.edu.unq.epers.bichomon.backend.model.DocumentoDeJugador;

public class TestDeJugadorMongo {

	private DocumentoDeJugadorDAO jugadorDAO ;
	private DocumentoDeJugador jugador;
	private Evento evento;
	
	@Before
	public void iniciar(){
		this.jugador= new DocumentoDeJugador("Pedro ARAOZ");
		this.evento = new Evento("Coronacion","Dojo Cobrakai");
		
		this.jugador.agregarEvento(evento);
		this.evento = new Evento("Captura","Pueblo chico");this.jugador.agregarEvento(evento);
		this.evento = new Evento("Coronacion","Dojo Cobrakai");this.jugador.agregarEvento(evento);
		this.evento = new Evento("abandono", "Gauderia Los Seibos");this.jugador.agregarEvento(evento);
		
		this.jugadorDAO = new DocumentoDeJugadorDAO();
		
	}
	//@After
	public void dropAll(){
		this.jugadorDAO.deleteAll();
	}
	
	@Test
	public void testSaveRegistroJugador() {
		this.jugadorDAO.save(this.jugador);
		}

	@Test
	public void testBuscaunJugadorPorNombre(){
		DocumentoDeJugador jugadorRecuperado = this.jugadorDAO.buscarPorNombre("Pedro ARAOZ");
		assertEquals(jugadorRecuperado.getEvetos().size(),4);	
	}
	
	/**
	 * 
	 */
	@Test
	public void testReemplazar(){
		DocumentoDeJugador jugadorReemplazo = new DocumentoDeJugador("Peter2");
		this.jugadorDAO.reemplazar("Juan Perez", jugadorReemplazo);
		DocumentoDeJugador jugadorRecuperado= this.jugadorDAO.buscarPorNombre("Peter2");
		assertEquals("Peter2",jugadorRecuperado.getNombre());
	}
	
	
	
	/**
	 * 
	 */
	@Test
	public void testInsertarEvento(){
		
		Evento evento = new Evento("Coronacion", "dojo Akido");
		this.jugadorDAO.insertarEvento("Pepe Argento", evento);
		DocumentoDeJugador jugadorRecuperado= this.jugadorDAO.buscarPorNombre("Pepe Argento");
		assertEquals(1,jugadorRecuperado.getEvetos().size());
		
		
	}
	

}
