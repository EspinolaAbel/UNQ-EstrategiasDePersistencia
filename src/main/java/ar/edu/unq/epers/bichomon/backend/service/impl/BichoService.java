package ar.edu.unq.epers.bichomon.backend.service.impl;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.PuntosDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.busqueda.IFactores;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class BichoService {


	private EntrenadorDAO entrenadorDAO ;
	private BichoDAO bichoDAO;
	private PuntosDAO puntosDAO;
	private IFactores busqueda;
	
	
	public BichoService (EntrenadorDAO entrenadorDAO, BichoDAO bichoDAO, PuntosDAO puntosDAO, IFactores factresDeBusqueda ){
		this.entrenadorDAO=entrenadorDAO;
		this.bichoDAO=bichoDAO;
		this.puntosDAO=puntosDAO;// cree la interface y la utilizo como parametro
		this.busqueda= factresDeBusqueda;
		
	}
	
	/**
	 * buscar(String entrenador):Bicho el entrenador deberá buscar un nuevo bicho en la 
	 * localización actual en la que se encuentre. Si la captura es exitosa el bicho será 
	 * agregado al inventario del entrenador (ver sección Busquedas) y devuelto por el 
	 * servicio.
	 */

	public Bicho buscar(String nombreDelEntrenador) {
		
		Bicho bichoRetornado= null;
		
		// aca deberia crear una nueva busqueda  y ver la probabilidad de que se recupere un   bicho
		if (this.busqueda.busquedaExitosa()){
			bichoRetornado =
					Runner.runInSession(() -> {
 
						int expPorBusqueda = this.puntosDAO.getPuntosDeExperiencia("CapturarBicho").getPuntaje();
						Bicho bichoRecuperado=null;
						Entrenador entrenador = this.entrenadorDAO.getEntrenador(nombreDelEntrenador);
						bichoRecuperado= entrenador.buscarBicho(expPorBusqueda);
						return bichoRecuperado;
					});
			}
		return bichoRetornado;
	}
	
	
	/**
	 * abandonar(  String entrenador, int bicho):void el entrenador abandonará el bicho especificado 
	 * en la  localización actual.Si la ubicación no es una Guarderia se arrojará 
	 * UbicacionIncorrectaException.
	 * 
	 * @param entrenador - es el nombre del entrenador
	 * @param bicho - es el identificador del bicho
	 */
	public void abandonar(String nombreDelEntrenador, int idBicho) {
		
		Runner.runInSession(() -> {
			Entrenador entrenador= this.entrenadorDAO.getEntrenador(nombreDelEntrenador);
			Bicho bicho= this.bichoDAO.getBicho(idBicho)	;	
			entrenador.abandonarBicho(bicho);
			
		return null;
		});
	}

			
	/**
	 * duelo(String entrenador, int bicho):ResultadoCombate el entrenador desafiará
	 *  al actual campeon del dojo a duelo. Si la ubicación no es un Dojo se arrojará
	 *  UbicacionIncorrectaException. El objeto resultante ResultadoCombate informará 
	 *  no solo quién fue el ganador del combate sino el resultado de cada uno de 
	 *  los ataques realizados.
	 * @param entrenador
	 * @param bicho
	 * @return
	 */
	public ResultadoCombate duelo(String nombreDelEntrenador, int idBicho) {
			
		return	Runner.runInSession(() -> {
			
 
					int expPorCombate =this.puntosDAO.getPuntosDeExperiencia("Duelo").getPuntaje();
					
					Entrenador entrenador= this.entrenadorDAO.getEntrenador(nombreDelEntrenador);
					Bicho bichoRetador= this.bichoDAO.getBicho(idBicho)	;	

					ResultadoCombate resultadoDeCombate =entrenador.combatir(bichoRetador,expPorCombate);

					return resultadoDeCombate;
			
				});		
		}
	
	/**
	 * devuelve true o false si el bicho en cestion  esta en condiciones de evolucionar
	 * @param entrenador
	 * @param bicho
	 * @return boolean que indica si el bicho puede evolucionar. */
	public boolean puedeEvolucionar(String entrenador, int bicho) {
		//no utilizo el entrenador para nada, ya que la consulta al nivel del  entrenador
		//lo obtengo del bicho q conoce a su entrenador
		return Runner.runInSession(() -> {
					Bicho b= this.bichoDAO.getBicho(bicho)	;	
					return b.puedeEvolucionar();
		});
	}
	
	/**
	 * evolucionar(String entrenador, int bicho):Bicho evoluciona el bicho especificado 
	 * (si cumple con las codiciones para evolucionar)
	 * 
	 * @param entrenador
	 * @param bicho
	 * @return	 */
	public Bicho evolucionar(String nombreDelEntrenador, int idBicho) {

		Bicho bichoQueEvoluciono;
		if (puedeEvolucionar(nombreDelEntrenador, idBicho)){
			bichoQueEvoluciono= Runner.runInSession(() -> {
					
						int expPorEvolucionar = this.puntosDAO.getPuntosDeExperiencia("Evolucion").getPuntaje();
						Entrenador entrenador= this.entrenadorDAO.getEntrenador(nombreDelEntrenador);
						Bicho bicho= this.bichoDAO.getBicho(idBicho)	;	
						// voy a  asumir que la responsabilidad de evolucionar un bicho es del entrenador el 
						// cual le pide al bicho que evolucione.
						entrenador.evolucionarBicho(expPorEvolucionar, bicho);
						return bicho;
						});
			return bichoQueEvoluciono;
		}
		else
			return null;
	}
}
