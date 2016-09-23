package ar.edu.unq.epers.bichomon.backend.service.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc.ConnectionBlock;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;

/**
 * EspecieDataServiceImpl tiene como función cargar datos a mi base de datos en la tabla Especies para poder realizar testeos sobre ella.
 * */
public class EspecieDataServiceImpl implements DataService {

	/**
	 * Se eliminan todos las filas de la tabla Especie de mi base de datos que hayan sido cargados por el método crearSetDatosIniciales
	 * de la clase EspecieDataServiceImpl.
	 * 
	 * @author Abel Espínola*/
	@Override
	public void eliminarDatos() {
		this.executeWithConnection(conn -> {
			String sql = "DELETE FROM Especie WHERE nombre LIKE 'XXXX-XXX%'";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			
			ps.close();
			return null;
		});
	}

	/**
	 * Este método tiene como función cargar un set filas en mi tabla Especie de mi base de datos con el único fin de poder
	 * realizar testeos sobre ella.
	 * Todos los nombres de Especies en mis filas ingresadas empiezan con el patrón 'XXXX-XXX' y terminan con un número del 0-9.
	 * Todos los campos numéricos están cargados con el número 9999.
	 * El TipoBicho elegido para todas las Especies ingresadas por este método es AGUA.
	 * El campo url_foto esta seteado con el String '--URL NO EXISTENTE--'
	 * 
	 * @author Abel Espínola*/
	@Override
	public void crearSetDatosIniciales() {
		this.executeWithConnection(conn -> {
			Integer i;
			String sql = "INSERT INTO "
					+ "Especie (nombre, tipo, altura, peso, cantidad_de_bichos, energia_inicial, url_foto)"
					+ "VALUES(?,?,?,?,?,?,?)";
			PreparedStatement ps = null;
			
			for(i=0; i<10; i++) { 
				ps = conn.prepareStatement(sql);
				ps.setString(1, "XXXX-XXX"+i);
				ps.setString(2, TipoBicho.AGUA.toString());
				ps.setInt(3, 9999);
				ps.setInt(4, 9999);
				ps.setInt(5, 9999);
				ps.setInt(6, 9999);
				ps.setString(7, "--URL NO EXISTENTE--");
				
				ps.execute();
				ps.close();
			}
			
			return null;
		});
		
	}
	
	
	
	
	/**
	 * Ejecuta un bloque de código contra una conexión.
	 */
	private <T> T executeWithConnection(ConnectionBlock<T> bloque) {
		Connection connection = this.openConnection("jdbc:mysql://localhost:3307/Bichomon?user=root&password=root&useSSL=false");
		try {
			return bloque.executeWith(connection);
		} catch (SQLException e) {
			throw new RuntimeException("Error no esperado", e);
		} finally {
			this.closeConnection(connection);
		}
	}
	
	
	/**
	 * Establece una conexión a la url especificada
	 * @param url - la url de conexión a la base de datos
	 * @return la conexión establecida
	 */
	private Connection openConnection(String url) {
		try {
			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new RuntimeException("No se puede establecer una conexion", e);
		}
	}

	/**
	 * Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
	 * @param connection - la conexion a cerrar.
	 */
	private void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException("Error al cerrar la conexion", e);
		}
	}
	
}
