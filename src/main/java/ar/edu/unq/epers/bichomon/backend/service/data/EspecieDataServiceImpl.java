package ar.edu.unq.epers.bichomon.backend.service.data;

import java.sql.PreparedStatement;

import ar.edu.unq.epers.bichomon.backend.dao.EspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc.Runner;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.TipoBicho;

/**
 * EspecieDataServiceImpl tiene como función cargar datos a mi base de datos en la tabla Especies para poder realizar testeos sobre ella.
 * */
public class EspecieDataServiceImpl implements DataService {

	/** Se eliminan todas las filas de la tabla "Especies" de la base de datos ejecutando una
	 * query JDBC. */
	@Override
	public void eliminarDatos() {
		Runner.executeWithConnection(conn -> {
			String sql = "DELETE FROM Especies";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.execute();
			
			ps.close();
			return null;
		});
	}

	/**
	 * Este método tiene como función cargar un set filas en mi tabla Especies de mi base de datos con el único fin de poder
	 * realizar testeos sobre ella.
	 * - Todos los nombres de Especies en mis filas ingresadas empiezan con el patrón 'XXXX-XXX' y terminan con un número del 0-9.
	 * - Todos los campos numéricos están cargados con el número 9999.
	 * - El TipoBicho elegido para todas las Especies ingresadas por este método es AGUA.
	 * - El campo urlFoto esta seteado con el String '--URL NO EXISTENTE--' */
	@Override
	public void crearSetDatosIniciales() {
		EspecieDAO especieDAO = new JDBCEspecieDAO();
		Especie especieTest;
		for(int i=0; i<10; i++) { 
			especieTest = new Especie("XXXX-XXX"+i, TipoBicho.AGUA);
			especieTest.setAltura(9999);
			especieTest.setCantidadBichos(9999);
			especieTest.setEnergiaInicial(9999);
			especieTest.setPeso(9999);
			especieTest.setUrlFoto("--URL NO EXISTENTE--");
			
			especieDAO.saveEspecie(especieTest);
		}
	}

	
}
