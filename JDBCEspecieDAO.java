package ar.edu.unq.epers.bichomon.backend.dao.especie.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.especie.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;
import ar.edu.unq.unidad1.wop.dao.impl.ConnectionBlock;

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
		this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO personaje (nombre, pesoMaximo, xp, vida) VALUES (?,?,?,?)");
			ps.setString(1, personaje.getNombre());
			ps.setInt(2, personaje.getPesoMaximo());
			ps.setInt(3, personaje.getXp());
			ps.setInt(4, personaje.getVida());
			//ojo, no estamos guardando el inventario!
			ps.execute();

			if (ps.getUpdateCount() != 1) {
				throw new RuntimeException("No se inserto el personaje " + personaje);
			}
			ps.close();

			return null;
	}

	@Override
	public Especie getEspecie(String nombre) {
		// TODO Auto-generated method stub
		return null;
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
		Connection connection = this.openConnection("jdbc:mysql://localhost:8889/epers-1?user=root&password=root");
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
			//La url de conexion no deberia estar harcodeada aca
			return DriverManager.getConnection("jdbc:mysql://localhost:3307/epers_ejemplo_jdbc?user=root&password=root");
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