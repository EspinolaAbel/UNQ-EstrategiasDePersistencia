package ar.edu.unq.epers.bichomon.backend.service.impl.hibernate;

import ar.edu.unq.epers.bichomon.backend.dao.BichoDAO;
import ar.edu.unq.epers.bichomon.backend.dao.EntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.LugarDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernateEntrenadorDAO;
import ar.edu.unq.epers.bichomon.backend.dao.impl.hibernate.HibernatePuntosDeExperienciaDAO;
import ar.edu.unq.epers.bichomon.backend.model.Bicho;
import ar.edu.unq.epers.bichomon.backend.model.Entrenador;
import ar.edu.unq.epers.bichomon.backend.model.Especie;
import ar.edu.unq.epers.bichomon.backend.model.ResultadoCombate;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Dojo;
import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;
import ar.edu.unq.epers.bichomon.backend.model.lugar.UbicacionIncorrectaException;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

public class BichoService {


	private EntrenadorDAO entrenadorDAO ;
	private BichoDAO bichoDAO;
	
	
	
	
	public BichoService (EntrenadorDAO entrenadorDAO, BichoDAO bichoDAO ){
		this.entrenadorDAO=entrenadorDAO;
		this.bichoDAO=bichoDAO;
	}
	
	/**
	 * buscar(String entrenador):Bicho el entrenador deberá buscar un nuevo bicho en la 
	 * localización actual en la que se encuentre. Si la captura es exitosa el bicho será 
	 * agregado al inventario del entrenador (ver sección Busquedas) y devuelto por el 
	 * servicio.
	 */

	public Bicho buscar(String entrenador2) {
		// aca deberia crear una nueva busqueda  y ver la probabilidad de que se recupere un   bicho
		Bicho bichoRetornado=Runner.runInSession(() -> {
			HibernatePuntosDeExperienciaDAO puntosDAO= new HibernatePuntosDeExperienciaDAO(); 
			
			int expPorBusqueda = puntosDAO.getPuntosDeExperiencia("CapturarBicho").getPuntaje();
			
			Bicho bichoRecuperado=null;
			Entrenador entrenador = this.entrenadorDAO.getEntrenador(entrenador2);
			if ( (entrenador.getBichosCapturados().size()) <  entrenador.getNivelActual().getMaxCantidadDeBichos() ){
				bichoRecuperado=entrenador.getUbicacionActual().retornarUnBichoDelLugar();
				entrenador.agregarBichoCapturado(bichoRecuperado);
				entrenador.aumentarExperiencia(expPorBusqueda);
				bichoRecuperado.setTiempoDesdeSuCaptura(System.nanoTime());
			}
			
			
			return bichoRecuperado;
			});
		
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
	public void abandonar(String entrenador, int bicho) {
		
		Runner.runInSession(() -> {
			Entrenador e= this.entrenadorDAO.getEntrenador(entrenador);
			Bicho b= this.bichoDAO.getBicho(bicho)	;	
			//solo lo deja si no se queda sin bichos
			if (e.getBichosCapturados().size()>1){
				e.getUbicacionActual().recibirBichoAbandonado(b);
				e.descartarBichoCapturado(b);
				}
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
	
	public ResultadoCombate duelo(String entrenador, int bicho) {
			
		return		Runner.runInSession(() -> {
			
					HibernatePuntosDeExperienciaDAO puntosDAO= new HibernatePuntosDeExperienciaDAO(); 
					int expPorCombate = puntosDAO.getPuntosDeExperiencia("Duelo").getPuntaje();
			
					
					Entrenador e= this.entrenadorDAO.getEntrenador(entrenador);
					Bicho bichoRetador= this.bichoDAO.getBicho(bicho)	;	
					ResultadoCombate resultadoDeCombate =e.getUbicacionActual().combatir(bichoRetador);
					/**
					 * hasta aca solo se combatio, ahora hay que actualizar
					 * los valores segun el resultado
					 */
					
					Bicho ganador;
					Bicho perdedor;
					
					// el dojo debe tner un  nuevo campeon
					Dojo d=(Dojo) e.getUbicacionActual();
					d.setCampeonActual(resultadoDeCombate.getGanador());
					
					// los bichos debenrecuperar energia
					ganador=resultadoDeCombate.getGanador();
					ganador.recuperarEnergia();
					perdedor=resultadoDeCombate.getPerdedor();
					perdedor.recuperarEnergia();
					
					//el bicho ganador  incrementa su cantidad de victoraias
					 ganador.setCantidadDeVictorias(ganador.getCantidadDeVictorias()+1);
					
					// los entrenadores deben adquirir experiencia
					ganador.getOwner().aumentarExperiencia(expPorCombate);//aca hay que areglar.
					perdedor.getOwner().aumentarExperiencia(expPorCombate);
					
					//los entrenadores deberian aumentar de nivel si pueden
					ganador.getOwner().aumentarDeNivelSiTieneExperiencia();
					perdedor.getOwner().aumentarDeNivelSiTieneExperiencia();
					return resultadoDeCombate;
			
					});
		
		}
	/**
	 * devuelve true o false si el bicho en cestion  esta en condiciones de evolucionar
	 * @param entrenador
	 * @param bicho
	 * @return
	 */

	public boolean puedeEvolucionar(String entrenador, int bicho) {
		//no utilizo el entrenador para nada, ya que la consulta al nivel del  entrenador
		//lo obtengo del bicho q conoce a su entrenador
		return Runner.runInSession(() -> {


					Bicho b= this.bichoDAO.getBicho(bicho)	;	
					Especie esp =b.getEspecie();
					return esp.puedeEvolucionar(b);
		});
	}
	
	/**
	 * evolucionar(String entrenador, int bicho):Bicho evoluciona el bicho especificado 
	 * (si cumple con las codiciones para evolucionar)
	 * 
	 * @param entrenador
	 * @param bicho
	 * @return
	 */
	
	
	public Bicho evolucionar(String entrenador, int bicho) {
		HibernatePuntosDeExperienciaDAO puntosDAO= new HibernatePuntosDeExperienciaDAO(); 

		Bicho bi;
		if (puedeEvolucionar(entrenador, bicho)){
				bi= Runner.runInSession(() -> {
						
							int expPorEvolucionar = puntosDAO.getPuntosDeExperiencia("Evolucion").getPuntaje();
						
							Entrenador e= this.entrenadorDAO.getEntrenador(entrenador);
							Bicho b= this.bichoDAO.getBicho(bicho)	;	
							Especie esp =b.getEspecie().getEvolucionaA();
							b.setEspecie(esp);
							e.aumentarExperiencia(expPorEvolucionar);
							e.aumentarDeNivelSiTieneExperiencia();
							return b;
							});
				return bi;
				}
			else
				return null;
	
	}
}
