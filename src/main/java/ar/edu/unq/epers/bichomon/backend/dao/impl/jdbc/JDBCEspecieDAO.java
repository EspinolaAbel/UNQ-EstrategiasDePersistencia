package ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;

/**
 * Clase que define la comunicación entre java y mi base de datos utilizando JDBC.
 * */
public class JDBCEspecieDAO implements EspecieDAO {

	/**
	 * Constructor de JDBCEspecieDAO. Al instanciar esta clase se registra el driver MySql en el DriverManager.*/
	public JDBCEspecieDAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("No se puede encontrar la clase del driver", e);
		}
	}
	
	
	
	/**
	 * Dado una especie, se la persiste en la base de datos usando JDBC.
	 * 
	 * @param e - Una especie.
	 * @author Abel Espínola*/
	@Override
	public void saveEspecie(Especie e) {
		this.executeWithConnection(conn -> {
			String sql = "INSERT INTO "
						+ "Especie (nombre, tipo, altura, peso, cantidad_de_bichos, energia_inicial, url_foto)"
						+ "VALUES(?,?,?,?,?,?,?)";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, e.getNombre());
			ps.setString(2, e.getTipo().toString() );
			ps.setInt(3, e.getAltura());
			ps.setInt(4, e.getPeso());
			ps.setInt(5, e.getCantidadBichos());
			ps.setInt(6, e.getEnergiaInicial());
			ps.setString(7, e.getUrlFoto());
			
			ps.execute();

			if (ps.getUpdateCount() != 1) {
				throw new RuntimeException("No se inserto la especie " + e);
			}
			ps.close();

			return null;
		});
		
	}	
	
	
	
	/**
	 * Dado el nombre de una especie, se la recupera de la base de datos utilizando JDBC y se la retorna.
	 * 
	 * @param nombreEspecie - Nombre de la especie a ser buscada.
	 * @return La especie buscada.
	 * @author Abel Espínola*/
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
				especie.setEnergiaInicial(resultSet.getInt("energia_inicial"));
				especie.setUrlFoto(resultSet.getString("url_foto"));
			}
	
			ps.close();
			return especie;
		});

	}	
	
	
	
	/**
	 * Se recupera todas las especies de la base de datos usando JDBC y se retornan en un ArrayList.
	 * 
	 * @return Lista con todas las especies de la base de datos.
	 * @author Abel Espínola*/
	@Override
	public List<Especie> getAllEspecies() {
		return this.executeWithConnection(conn -> {
			List<Especie> lsEspecie = new ArrayList<Especie>();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Especie");
			
			ResultSet resultSet = ps.executeQuery();
	
			Especie especie = null;
			while (resultSet.next()) {
				String nombre = resultSet.getString("nombre");
				TipoBicho tipo = TipoBicho.valueOf(resultSet.getString("tipo"));
				
				especie = new Especie(nombre, tipo);
				
				especie.setAltura(resultSet.getInt("altura"));
				especie.setCantidadBichos(resultSet.getInt("cantidad_de_bichos"));
				especie.setPeso(resultSet.getInt("peso"));
				especie.setEnergiaInicial(resultSet.getInt("energia_inicial"));
				especie.setUrlFoto(resultSet.getString("url_foto"));
				
				lsEspecie.add(especie);
			}
	
			ps.close();
			return lsEspecie;
		});
	}
	
	/**
	 * Dada una especie obtenida de mi base de datos, aumento en 1 la cantidad de bichos que esta posee 
	 * y la persisto en mi BD con JDBC.
	 * @param especie - Una especie.
	 * @author Abel Espínola
	 * */
	public void updateEspecie(Especie especie) {
		this.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement("UPDATE Especie SET"
					+ "  tipo = ? "
					+ ", altura = ? "
					+ ", peso = ? "
					+ ", cantidad_de_bichos = ? "
					+ ", url_foto = ? "
					+ ", energia_inicial = ? "
					+ " WHERE nombre= ? ");
			
			ps.setString(1, especie.getTipo().toString());
			ps.setInt(2, especie.getAltura());
			ps.setInt(3, especie.getPeso());
			ps.setInt(4, especie.getCantidadBichos());
			ps.setString(5, especie.getUrlFoto());
			ps.setInt(6, especie.getEnergiaInicial());
			ps.setString(7, especie.getNombre());
			
			
			ps.execute();
			
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