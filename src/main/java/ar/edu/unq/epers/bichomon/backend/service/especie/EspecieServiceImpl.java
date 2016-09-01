
	/**
	 * 
	 * 
	 * @author Pedro Araoz
	 * */




package ar.edu.unq.epers.bichomon.backend.service.especie;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.dao.impl.especie.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.bicho.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;


/**
 * Esta clase define una API mediante la cual el front-end podrá consultar la tabla Especie de la base de datos.
 * */
public class EspecieServiceImpl implements EspecieService {

	
	private JDBCEspecieDAO especieDAO = new JDBCEspecieDAO();
	
	
	public EspecieServiceImpl(){
		especieDAO = new JDBCEspecieDAO();
		/**
		 *	esto solo establece  la conexion debereia 
		 *  puede lanzar  RuntimeExeption, deberia  manejar la excepcion?
		 **/
		}	
	
	
	/**
	 * Este método será utilizado por una interfaz de administración para crear nuevas
	 * especies de bichos. Recibe por parametro un objeto {@link Especie} previamente
	 * construido y se encarga de persistirlo en la ase de datos.  Tener en cuenta que
	 * el nombre de cada especie debe ser único para toda la aplicación.
	 * 
	 * @param especie - un objeto Especie previamente construido por la gente de frontend
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
	 */
	
		@Override
	public Especie getEspecie(String nombreEspecie) {
			
			// el DAO get especie deberia devolver null si la especie no existe
			Especie especieControl=this.especieDAO.getEspecie(nombreEspecie);
			return especieControl;
				
	}

		

		/**
		 * @return una lista de todas los objetos {@link Especie} existentes ordenados
		 * alfabéticamente por su nombre en forma ascendente
		 */
	@Override
	public List<Especie> getAllEspecies() {

		// el DAO get especie deberia devolver null si la especie no existe
		// y la lista deberia estar ordenada, 
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
	 */

	@Override
	public Bicho crearBicho(String nombreEspecie, String nombreBicho) {
		Especie especieControl=null;
		
		//obtener la especie 
		// el DAO get especie deberia devolver null si la especie no existe
		
		try{
		   especieControl=this.especieDAO.getEspecie(nombreEspecie);
		   especieControl.setCantidadBichos(especieControl.getCantidadBichos()+1);
		   this.especieDAO.updateEspecie(especieControl);
		   return new Bicho(especieControl, nombreBicho);
		   
		} catch(EspecieNoExistente e) {//OJO porque esto no lanza esta exepcion, lanza runtime 
			throw new RuntimeException("La especie del bicho no existe.");
//					{especieControl= new Especie(); //faltaria determinar el tipo de bicho que es
//					 especieControl.setNombre(nombreEspecie);
//					 especieControl.setCantidadBichos(0);// lo inicializo con cero xq despues actualzo 
//					 this.especieDAO.saveEspecie(especieControl);
		}
		   
		   // si no esta , la creo
		//si esta actualizo el contador
		
						// this.especieDAO.updateEspecie(especieControl); //deberia haber algun tipo de apdate
		         //para persistir la modificacion
		
	}

}
