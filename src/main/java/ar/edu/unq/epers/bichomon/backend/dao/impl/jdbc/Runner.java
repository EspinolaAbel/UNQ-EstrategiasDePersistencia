package ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Runner  {

	/** Ejecuta un bloque de código contra una conexión. */
	public static <T> T executeWithConnection(ConnectionBlock<T> bloque) {
		Connection connection = openConnection("jdbc:mysql://localhost:3307/bichomon?user=root&password=root&useSSL=false");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			return bloque.executeWith(connection);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("No se puede encontrar la clase del driver", e);
		} catch (SQLException e) {
			throw new RuntimeException("Error no esperado", e);
		} finally {
			closeConnection(connection);
		}
	}
	
	
	/** Establece una conexión a la url especificada
	 * @param url - la url de conexión a la base de datos
	 * @return la conexión establecida */
	private static Connection openConnection(String url) {
		try {
			return DriverManager.getConnection(url);
		} catch (SQLException e) {
			throw new RuntimeException("No se puede establecer una conexion", e);
		}
	}

	/** Cierra una conexion con la base de datos (libera los recursos utilizados por la misma)
	 * @param connection - la conexion a cerrar. */
	private static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException("Error al cerrar la conexion", e);
		}
	}

}
