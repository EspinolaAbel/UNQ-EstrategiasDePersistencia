package ar.edu.unq.epers.bichomon.backend.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import ar.edu.unq.epers.bichomon.backend.model.condicionesevolucion.CondicionDeEvolucion;

/**
 * Representa una {@link Especie} de bicho.
 * TODO: contrato
 * - Si dos especies son iguales (mismo nombre) entonces tienen misma evolucion.
 * - Si dos especies son iguales (mismo nombre) entonces tienen mismas condiciones de evolucion.
 * - Dos especies del mismo arbol genealógico tienen siempre la misma especie raiz.
 * @author Charly Backend */
@Entity(name="Especies")
public class Especie {

	@Id
	private String nombre;
	private int altura;
	private int peso;
	private TipoBicho tipo;
	private int energiaInicial;
	private String urlFoto;
	private int cantidadBichos;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Especie evolucionaA;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Especie raiz;

	@ManyToMany(fetch=FetchType.EAGER)
	private List<CondicionDeEvolucion> condicionesDeEvolucion;
	
	
	public Especie(){}

	public Especie(String nombre, TipoBicho tipo) {
		this.condicionesDeEvolucion = new ArrayList<CondicionDeEvolucion>();
		this.nombre = nombre;
		this.tipo = tipo;
		this.raiz=this;
	}
	
	//TODO
	public Especie(String nombre, TipoBicho tipo, Especie raiz) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.raiz=raiz;
	}
	

	
	
	/**
	 * @return el nombre de la especie (por ejemplo: Perromon)
	 */
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return la altura de todos los bichos de esta especie
	 */
	public Integer getAltura() {
		return this.altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	/**
	 * @return el peso de todos los bichos de esta especie
	 */
	public Integer getPeso() {
		return this.peso;
	}
	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	/**
	 * @return una url que apunta al un recurso imagen el cual será
	 * utilizado para mostrar un thumbnail del bichomon por el frontend.
	 */
	public String getUrlFoto() {
		return this.urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	/**
	 * @return la cantidad de energia de poder iniciales para los bichos
	 * de esta especie.
	 */
	public Integer getEnergiaInicial() {
		return this.energiaInicial;
	}
	public void setEnergiaInicial(int energiaInicial) {
		this.energiaInicial = energiaInicial;
	}

	/**
	 * @return el tipo de todos los bichos de esta especie
	 */
	public TipoBicho getTipo() {
		return this.tipo;
	}
	public void setTipo(TipoBicho tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * @return la cantidad de bichos que se han creado para esta
	 * especie.
	 */
	public Integer getCantidadBichos() {
		return this.cantidadBichos;
	}
	
	public void setCantidadBichos(int i) {
		this.cantidadBichos = i;
	}
	
	
	
	public List<CondicionDeEvolucion> getCondicionesDeEvolucion() {
		return condicionesDeEvolucion;
	}

	public void setCondicionesDeEvolucion(List<CondicionDeEvolucion> condicionesDeEvolucion) {
		this.condicionesDeEvolucion = condicionesDeEvolucion;
	}

	public Especie getRaiz() {
		return this.raiz;
	}
	
	public void setRaiz(Especie especieRaiz) {
		this.raiz = especieRaiz;
	}

	//TODO
	public Especie dameRaiz (){
		return this.raiz;
	}

	public Especie getEvolucionaA() {
		return this.evolucionaA;
	}
	
	public void setEvolucionaA(Especie evolucion) {
		this.evolucionaA = evolucion;
	}
	
	/** Dada una {@link CondicionDeEvolucion}, se la agrega a la lista que contiene las condiciones
	 * para evolucionar de esta {@link Especie}.
	 * @param condicionDeEvolucion - Una {@link CondicionDeEvolucion}.
	 * @author ae */
	public void agregarCondicionDeEvolucion(CondicionDeEvolucion condicionDeEvolucion) {
		this.condicionesDeEvolucion.add(condicionDeEvolucion);
	}
	
	
	/** Se responde si el {@link Bicho} dado está en condiciones de evolucionar.
	 * @param bichoAEvaluar
	 * @return Boolean que indica si puede evolucionar.
	 * @author ae */
	public Boolean puedeEvolucionar(Bicho bichoAEvaluar) {
		Boolean evoluciona = true;
		for(CondicionDeEvolucion condicion: this.condicionesDeEvolucion) {
			evoluciona = condicion.apruebaLaCondicion(bichoAEvaluar) && evoluciona;
		}
		return evoluciona;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o instanceof Especie) {
			Especie otraEspecie = (Especie) o;
			boolean idemNombre = this.nombre.equals( otraEspecie.getNombre() );
	
			return 	idemNombre;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int num = 421;
		int result = 10;
		result =	num * result + this.nombre.hashCode();
		
		return result;
	}
	
	
}
