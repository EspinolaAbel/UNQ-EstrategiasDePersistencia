package ar.edu.unq.epers.bichomon.backend.service.especie;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.impl.jdbc.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Especie;


/**
 * Esta clase define una API mediante la cual el front-end podrá consultar la tabla Especie de la base de datos.
 * 
 * @author Pedro Araoz
 * */
public class EspecieServiceImpl implements EspecieService {

	
	private JDBCEspecieDAO especieDAO = new JDBCEspecieDAO();
	
	
	public EspecieServiceImpl(){
		especieDAO = new JDBCEspecieDAO();
	}	
	
	
	/**
	 * Este método será utilizado por una interfaz de administración para crear nuevas
	 * especies de bichos. Recibe por parametro un objeto {@link Especie} previamente
	 * construido y se encarga de persistirlo en la ase de datos.  Tener en cuenta que
	 * el nombre de cada especie debe ser único para toda la aplicación.
	 * 
	 * @param especie - un objeto Especie previamente construido por la gente de frontend
	 * @author Pedro Araoz
	 *
	 *El metodo verifica que la Especie no exista, 
	 */
	@Override
	public void crearEspecie(Especie especie) {
	 	this.especieDAO.saveEspecie(especie);
	}
	

	/**
	 * Este método devolverá la {@link Especie} cuyo nombre sea igual al provisto por
	 * parámetro.
	 * 
	 * Se espera que este método devuelva, a lo sumo, un solo resultado.
	 * 
	 * @param nombreEspecie - el nombre de la especie que se busca
	 * @return la especie encontrada
	 * @throws la excepción {@link EspecieNoExistente} (no chequeada)
	 * @author Pedro Araoz */
	@Override
	public Especie getEspecie(String nombreEspecie) {
		return this.especieDAO.getEspecie(nombreEspecie);
	}

		

	/**@return una lista de todas los objetos {@link Especie} existentes ordenados
	 * @author Pedro Araoz
	 * 
	 * alfabéticamente por su nombre en forma ascendente */
	@Override
	public List<Especie> getAllEspecies() {
		List<Especie> especiesControl=this.especieDAO.getAllEspecies();
		return especiesControl;
	}

	
	
	/**
	 * Crea un nuevo Bicho perteneciente a la especie especificada. El nuevo objeto Bicho no es
	 * persistido (de momento), solo devuelto.
	 * 
	 * Para llevar una mejor estadística de los bichos que han sido creados cada objeto
	 * {@link Especie} cuenta con un contador cantidadBichos. El mismo deberá ser incrementado
	 * en 1.
	 * 
	 * @param nombreEspecie - el nombre de la especie del bicho a crear
	 * @param nombreBicho - el nombre del bicho a ser creado
	 * @return un objeto {@link Bicho} instanciado
	 * @author Pedro Araoz */
	@Override
	public Bicho crearBicho(String nombreEspecie, String nombreBicho) {
		Especie especie = this.especieDAO.getEspecie(nombreEspecie);
		Bicho nuevoBicho = especie.crearBichoConNombre(nombreBicho);
		this.especieDAO.updateEspecie(especie);
		return nuevoBicho;
	}

}
