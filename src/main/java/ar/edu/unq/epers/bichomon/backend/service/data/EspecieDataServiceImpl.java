package ar.edu.unq.epers.bichomon.backend.service.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ar.edu.unq.epers.bichomon.backend.dao.impl.ConnectionBlock;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;

public class EspecieDataServiceImpl implements DataService {

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
	 * Ejecuta un bloque de codigo contra una conexion.
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
	 * Establece una conexion a la url especificada
	 * @param url - la url de conexion a la base de datos
	 * @return la conexion establecida
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
