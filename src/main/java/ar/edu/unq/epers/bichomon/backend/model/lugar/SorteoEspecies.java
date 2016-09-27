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
	 * la coleccion de resultados acumula ls probabilidaddes en cada indice
	 * 
	 */
	private List<Integer> resultados;
	

public SorteoEspecies(int entreCuantos){
	/**
	 * @param entreCuantos - {@link entreCuantos} indica la cantidad de elementos a sortear la probabilidad.
	 * se asigara a cada uno un probabilidad de ocurrencia cuya somatoria formara el 100%
	 */
	this.resultados= new ArrayList<Integer>();
	reasignar(entreCuantos); 
}	
	
/**
 * este metodo resaigna aleatoriamente las probabilidades segun cambien la cantidad de especies dentro del pueblo
 * @param entreCuantos
 */

public void reasignar(int entreCuantos){
	this.resultados= new ArrayList<Integer>(); 
	Integer porcentajeAcumulado=0;
	Integer porcentaje=0;
	
	if (entreCuantos==0)
		this.resultados=null;
	else{
		while (entreCuantos>1){//voy a repartir porcentajes  entre los n-1 elementos, y al ultimo le asigno el resto 
			porcentaje= (int)(Math.random()*(100-porcentajeAcumulado));
			porcentajeAcumulado+=porcentaje;
			this.resultados.add(porcentajeAcumulado);
			entreCuantos--;
			}
		resultados.add(100);// el ultimo elemento deberea ser el total de los porcentajes
		}
	}


/**
 * @param <T>
 * @return 
 * 
 */
public   <T> T sortearEspecie(List<T> especies){
	Double probabilidadSeleccionada= Math.random()*100;//elijo al azar un numero entre 0 y 100
	int indice=0;
	boolean encontrado= false;
	
	for (int i=0; i<this.resultados.size()&& !encontrado; i++){
		 if( this.resultados.get(i)>= probabilidadSeleccionada){
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
	return this.resultados;
}
public void setResultados(List <Integer> resultados){

	this.resultados=resultados;
	
	
}
	
}
