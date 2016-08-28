package ar.edu.unq.epers.bichomon.backend.dao.impl.especie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.especie.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.epers.bichomon.backend.model.especie.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.dao.impl.ConnectionBlock;


public class JDBCEspecieDAO implements EspecieDAO {

	public JDBCEspecieDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("No se puede encontrar la clase del driver", e);
		}
	}
	
	@Override
	public void saveEspecie(Especie e) {
		return this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO "
					+ "Especie (nombre, tipo, altura, peso, cantidad_de_bichos, url_foto)"
					+ "");
		});
		
	}

	@Override
	public Especie getEspecie(String nombreEspecie) {
		return this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Especie WHERE nombre = ?");
			ps.setString(1, nombreEspecie);
			
			ResultSet resultSet = ps.executeQuery();
	
			Especie especie = null;
			while (resultSet.next()) {

				if (especie != null) {
					throw new RuntimeException("Existe mas de una especie con el nombre " + nombreEspecie);
				}
				
				String nombre = resultSet.getString("nombre");
				TipoBicho tipo = TipoBicho.valueOf(resultSet.getString("tipo"));
				especie = new Especie(nombre, tipo);
				especie.setAltura(resultSet.getInt("altura"));
				especie.setCantidadBichos(resultSet.getInt("cantidad_de_bichos"));
				especie.setPeso(resultSet.getInt("peso"));
				especie.setUrlFoto(resultSet.getString("url_foto"));
			}
	
			ps.close();
			return especie;
		});

	}

	@Override
	public List<Especie> getAllEspecies() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	/**
	 * Ejecuta un bloque de codigo contra una conexion.
	 */
	private <T> T executeWithConnection(ConnectionBlock<T> bloque) {
		Connection connection = this.openConnection("jdbc:mysql://localhost:3306/Bichomon?user=root&password=root");
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