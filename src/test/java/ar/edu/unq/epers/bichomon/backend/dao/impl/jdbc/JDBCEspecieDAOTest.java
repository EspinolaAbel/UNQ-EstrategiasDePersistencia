package ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.EspecieDataServiceImpl;

/** Test de la clase JDBCEspecieDAO */
public class JDBCEspecieDAOTest {

	private Especie especieTest;
	private DataService especieDS = new EspecieDataServiceImpl();
	private JDBCEspecieDAO especieDAO = new JDBCEspecieDAO();
	
	@Before
	public void setUp() {
		//Creo en la base de datos un conjunto de datos iniciales para correr tests
		especieDS.crearSetDatosIniciales();
		
		//especieDS se ocupa de borrar especieTest de la BD al finalizar los test
		especieTest = new Especie("XXXX-XXXX", TipoBicho.FUEGO);
		especieTest.setAltura(9999);
		especieTest.setCantidadBichos(9999);
		especieTest.setEnergiaInicial(9999);
		especieTest.setPeso(9999);
		especieTest.setUrlFoto("URL NO EXISTENTE");
	}
	
	@After
	public void tearDown() {
		//Borro todos los datos escritos por mis test de esta clase de mi base de datos
		especieDS.eliminarDatos();
	}
	
	/** Test para probar si puedo abrir una conexión con mysql. */
	@Test
	public void testIntentoAbrirUnaConexion() {
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/bichomon?useSSL=false","root","root");
			conn.close();
			assertTrue(conn.isClosed());
		}
		catch(Exception e) {
			assertTrue(false);
			throw new RuntimeException(e);
		}
	}
	
	
	/** Dada una {@link Especie} la guardo en mi base de datos usando JDBC, recupero
	 * dicha especie y compruebo que se haya persistido correctamente. */
	@Test
	public void testDadoUnaEspecieLaGuardoEnMiBaseDeDatosUsandoJDBCYLuegoLaRecuperoParaComprobarQueSeHayaPersistidoDeManeraCorrecta() {		
		especieDAO.saveEspecie(especieTest);	
		String nombreEspecie = especieTest.getNombre();
		
		Especie e = especieDAO.getEspecie(nombreEspecie);	
		assertEquals(e.getNombre(), especieTest.getNombre());
		assertEquals(e.getAltura(), especieTest.getAltura());
		assertEquals(e.getCantidadBichos(), especieTest.getCantidadBichos());
		assertEquals(e.getEnergiaInicial(), especieTest.getEnergiaInicial());
		assertEquals(e.getPeso(), especieTest.getPeso());
		assertEquals(e.getTipo(), e.getTipo());
		assertEquals(e.getUrlFoto(), especieTest.getUrlFoto());
	}

	/** Intento recuperar de mi base de datos todas las especies. */
	@Test
	public void testRecuperoTodosLosDatosDeMiBaseDeDatosATravesDeJDBC() {
		List<Especie> lsEspecies = new ArrayList<Especie>();
		
		lsEspecies = especieDAO.getAllEspecies();
		
		assertTrue(lsEspecies.size() > 1);
		
		for(Especie each: lsEspecies)
			if(each.getNombre().contains("XXXX-XXX")) {
				assertTrue(true);
				return;
			}
		assertTrue(false);
	}
	
	
	/** Dado el nombre de una especie existente en mi base de datos, obtengo la especie de dicho nombre, la modifico, la guardo nuevamente en mi base
	 * de datos y compruebo si los cambios se persistieron.  */
	@Test
	public void testDada_una_especie_ya_existente_en_mi_tabla_la_actualizo() {
		
		//recupero la especie de mi bd
		Especie e = especieDAO.getEspecie("XXXX-XXX1");
		
		//modifico la especie recuperada de mi bd
		e.setTipo(TipoBicho.ELECTRICIDAD);
		e.setAltura(8999);
		e.setPeso(8999);
		e.setCantidadBichos( 8999 );
		e.setUrlFoto("--- URL NO EXISTENTE 2 ---");
		e.setEnergiaInicial(8999);
		
		//la actualizo en mi base de datos
		especieDAO.updateEspecie(e);
		
		//vuelvo a recuperar esta especie de mi bd para comprobar que se realizaron los cambios
		e = especieDAO.getEspecie("XXXX-XXX1");
		
		assertEquals(e.getTipo(), TipoBicho.ELECTRICIDAD);
		assertEquals(e.getAltura(), (Integer) 8999);
		assertEquals(e.getPeso(), (Integer) 8999);
		assertEquals(e.getCantidadBichos(), (Integer) 8999 );
		assertEquals(e.getUrlFoto(), "--- URL NO EXISTENTE 2 ---");
		assertEquals(e.getEnergiaInicial(), (Integer) 8999);
	}

}
