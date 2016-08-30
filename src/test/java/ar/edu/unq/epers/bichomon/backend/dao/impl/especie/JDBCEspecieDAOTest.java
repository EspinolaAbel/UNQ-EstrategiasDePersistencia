/**
 * 
 */
package ar.edu.unq.epers.bichomon.backend.dao.impl.especie;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.data.DataService;
import ar.edu.unq.epers.bichomon.backend.service.data.EspecieDataServiceImpl;

/**
 *	Test de la clase JDBCEspecieDAO
 */
public class JDBCEspecieDAOTest {

	private static Especie especieTest;
	private static DataService especieDS = new EspecieDataServiceImpl();
	private static JDBCEspecieDAO especieDAO = new JDBCEspecieDAO();
	
	/**
	 * Esto se ejecuta antes de correr todos los tests
	 * */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	
	/**
	 * Esto se ejecuta después de correr todos los tests
	 * */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//Borro todos los datos escritos por mis test de esta clase de mi base de datos
		especieDS.eliminarDatos();
	}
	


	
	/**
	 * Test para probar si puedo abrir una conexión con mysql.
	 */
	@Test
	public void testIntento_abrir_una_conexion() {
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
	
	
	/**
	 * Dado una especie, la guardo en mi base de datos usando JDBC.
	 */
	@Test
	public void testDado_una_especie_la_guardo_en_mi_base_de_datos_usando_JDBC() {		
		especieDAO.saveEspecie(especieTest);	
		assertTrue(true);
	}

	/**
	 * Dado un nombre XXXX-XXXX de una especie, intento recuperar dicha especie de mi base de datos a través de JDBC.
	 */
	@Test
	public void testDado_un_nombre_de_especie_la_recupero_de_mi_base_de_datos_usando_JDBC() {
		Especie e = especieDAO.getEspecie("XXXX-XXX1");	
		assertTrue(e.getNombre().contains("XXXX-XXX1"));
	}

	/**
	 * Test method for {@link ar.edu.unq.epers.bichomon.backend.dao.impl.especie.JDBCEspecieDAO#getAllEspecies()}.
	 */
	@Test
	public void testRecupero_todos_los_datos_de_mi_base_de_datos_a_traves_de_JDBC() {
		List<Especie> lsEspecies = new ArrayList<Especie>();
		
		lsEspecies = especieDAO.getAllEspecies();
				
		for(Especie each: lsEspecies) {
			if(each.getNombre().contains("XXXX-XXX")) {
				assertTrue(true);
				return;
			}
		}
		assertTrue(false);
	}
	
	@Test
	public void testDada_una_especie_ya_existente_en_mi_tabla_la_actualizo() {
		Especie e = especieDAO.getEspecie("XXXX-XXX1");
		
		//e.setCantidadBichos( e.getCantidadBichos() - 1 );
		e.setTipo(TipoBicho.ELECTRICIDAD);
		
		especieDAO.updateEspecie(e);
		
		System.out.println(especieDAO.getEspecie("XXXX-XXX1").getCantidadBichos());
		assertEquals(especieDAO.getEspecie("XXXX-XXX1").getTipo(), TipoBicho.ELECTRICIDAD);
	}

}
