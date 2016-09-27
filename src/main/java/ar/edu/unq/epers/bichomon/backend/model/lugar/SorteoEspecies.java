package ar.edu.unq.epers.bichomon.backend.model.lugar;

import java.util.ArrayList;
import java.util.List;

import javassist.bytecode.Descriptor.Iterator;


/**
 * Esta clase se utiliza para sortear los porcentajes de probabilidad de ocurrencia de cada especie en cada pueblo 
 * y para sortear el bicho que sera seleccionado al buscar en el pueblo
 * @author Pedro
 *
 */

public class SorteoEspecies {
	/**
	 * la coleccion de aacumulados acumula ls probabilidaddes en cada indice
	 * 
	 */
	private List<Integer> acumulados;
	

public SorteoEspecies(List <EspecieConProbabilidad> especies){
	/**
	 * @param especies - {@link especies} indica la cantidad de elementos a sortear con su probabilidad.
	 * 
	 */
	this.acumulados= new ArrayList<Integer>();
	this.asignar(especies); 
}	
	
/**
 * este metodo saigna  las probabilidades segun cambien la cantidad de especies dentro del pueblo
 * @param e
 */

public void asignar(List<EspecieConProbabilidad> especies){
	int anterior=0;
	this.acumulados= new ArrayList<Integer>(); 
	
	for(EspecieConProbabilidad e: especies){
		this.acumulados.add(e.getProbabilidad() +anterior);
		anterior+=e.getProbabilidad();
		}
	}


/**
 * @param <T>
 * @return 
 * 
 */




public   <T> T sortearEspecie(List<T> especies){
	//elijo al azar un numero  entre 0 y el ultimo de las peobabilidades acumuladas
	Integer probabilidadSeleccionada= (int) (Math.random()*(this.acumulados.get(this.acumulados.size()-1)));
	int indice=0;
	boolean encontrado= false;
	
	for (int i=0; i<this.acumulados.size()&& !encontrado; i++){
		 if( this.acumulados.get(i)>= probabilidadSeleccionada){
			 indice=i;
			 encontrado=true;
		 	}
		}
	return especies.get(indice);

	}

public   <T> T sortearEspecie(List<T> especies, int sorteada){
	//elijo al azar un numero  entre 0 y el ultimo de las peobabilidades acumuladas
	Integer probabilidadSeleccionada= sorteada;
	int indice=0;
	boolean encontrado= false;
	
	for (int i=0; i<this.acumulados.size()&& !encontrado; i++){
		 if( this.acumulados.get(i)>= probabilidadSeleccionada){
			 indice=i;
			 encontrado=true;
		 	}
		}
	return especies.get(indice);

	}







/**
 * 
 * @return resultados {@link resultados} devuelve los resultados de las asignaciones de probabilidades
 */

public List<Integer> getResultados(){
	return this.acumulados;
}
public void setResultados(List <Integer> acumulados){

	this.acumulados=acumulados;
	
	
}
	
}
