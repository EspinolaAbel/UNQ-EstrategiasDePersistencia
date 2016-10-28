package ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;
import ar.edu.unq.epers.bichomon.backend.service.especie.EspecieNoExistenteException;

/** Clase que define la comunicación entre java y mi base de datos utilizando JDBC. */
public class JDBCEspecieDAO implements EspecieDAO {

	public JDBCEspecieDAO() {
		super();
	}	
	
	
	/** Dado una especie, se la persiste en la base de datos usando JDBC.
	 * @param e - Una especie.*/
	@Override
	public void saveEspecie(Especie e) {
		Runner.executeWithConnection(conn -> {
			String sql = "INSERT INTO "
						+ "Especies (nombre, tipo, altura, peso, cantidadBichos, energiaInicial, urlFoto)"
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

			if (ps.getUpdateCount() != 1)
				throw new RuntimeException("No se inserto la especie " + e);
			ps.close();
			return null;
		});
	}
	
	
	/** Dado el nombre de una especie, se la recupera de la base de datos utilizando JDBC y se la retorna.
	 * @param nombreEspecie - Nombre de la especie a ser buscada.
	 * @return La especie buscada. */
	@Override
	public Especie getEspecie(String nombreEspecie) {
		return Runner.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Especies WHERE nombre = ?");
			ps.setString(1, nombreEspecie);
			
		
			ResultSet resultSet = ps.executeQuery();
	
			Especie especie = null;
			while (resultSet.next()) {
				if (especie != null)
					throw new RuntimeException("Existe mas de una especie con el nombre " + nombreEspecie);
				String nombre = resultSet.getString("nombre");
				TipoBicho tipo = TipoBicho.valueOf(resultSet.getString("tipo"));
				especie = new Especie(nombre, tipo);
				especie.setAltura(resultSet.getInt("altura"));
				especie.setCantidadBichos(resultSet.getInt("cantidadBichos"));
				especie.setPeso(resultSet.getInt("peso"));
				especie.setEnergiaInicial(resultSet.getInt("energiaInicial"));
				especie.setUrlFoto(resultSet.getString("urlFoto"));
			}
			if(especie == null)
				throw new EspecieNoExistenteException(nombreEspecie);
			return especie;
		});

	}
	
	
	/** Se recupera todas las especies de la base de datos usando JDBC y se retornan en un ArrayList.
	 * @return Lista con todas las especies de la base de datos. */
	@Override
	public List<Especie> getAllEspecies() {
		return Runner.executeWithConnection(conn -> {
			List<Especie> lsEspecie = new ArrayList<Especie>();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM Especies");
			
			ResultSet resultSet = ps.executeQuery();
	
			Especie especie = null;
			while (resultSet.next()) {
				String nombre = resultSet.getString("nombre");
				TipoBicho tipo = TipoBicho.valueOf(resultSet.getString("tipo"));
				
				especie = new Especie(nombre, tipo);
				
				especie.setAltura(resultSet.getInt("altura"));
				especie.setCantidadBichos(resultSet.getInt("cantidadBichos"));
				especie.setPeso(resultSet.getInt("peso"));
				especie.setEnergiaInicial(resultSet.getInt("energiaInicial"));
				especie.setUrlFoto(resultSet.getString("urlFoto"));
				
				lsEspecie.add(especie);
			}
			ps.close();
			return lsEspecie;
		});
	}
	
	/** Dada una {@link Especie}, la cual ya fué persistida en mi BD, la actualizo con JDBC .
	 * @param especie - Una especie. */
	public void updateEspecie(Especie especie) {
		Runner.executeWithConnection(conn -> {
			PreparedStatement ps = conn.prepareStatement("UPDATE Especies SET"
					+ "  tipo = ? "
					+ ", altura = ? "
					+ ", peso = ? "
					+ ", cantidadBichos = ? "
					+ ", urlFoto = ? "
					+ ", energiaInicial = ? "
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
	
	@Override
	public Especie getEspecieLider() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Especie> getMasPopulares() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Especie> getMenosPopulares() {
		// TODO Auto-generated method stub
		return null;
	}
	
}