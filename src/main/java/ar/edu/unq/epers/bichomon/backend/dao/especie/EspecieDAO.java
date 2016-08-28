package ar.edu.unq.epers.bichomon.backend.dao.especie;

import java.util.List;

import ar.edu.unq.epers.bichomon.backend.model.especie.Especie;

/**Interface creada para definir la persistencia de mi clase Especie en mi BD.
 * @author Abel Espínola*/
public interface EspecieDAO {
	
	/**Dada una especie, la persiste en mi base de datos.
	 * 
	 * @param e - Especie que se quiere guardar
	 * @author Abel Espínola*/
	public void saveEspecie(Especie e);
	
	/**Dado el nombre de una especie, la recupera de mi base de datos y la retorna.
	 * 
	 * @param nombre - Nombre de la especie
	 * @author Abel Espínola*/
	public Especie getEspecie(String nombre);
	
	/**Se recupera de la base de datos todas las especies y las retorna en una lista.
	 * @author Abel Espínola*/
	public List<Especie> getAllEspecies();
}