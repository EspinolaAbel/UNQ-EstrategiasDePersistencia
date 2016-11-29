package ar.edu.unq.epers.bichomon.backend.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ar.edu.unq.epers.bichomon.backend.model.lugar.Lugar;

@Entity(name="Entrenadores")
public class Entrenador implements Serializable {
	
	private static final long serialVersionUID = -4161386306977110596L;
	
	@Id
	private String  nombre;
	private Integer experiencia;
	
	@OneToOne
	private Lugar ubicacion;
	
	@OneToOne
	private Nivel nivelActual;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="owner", fetch=FetchType.EAGER)
	private List<Bicho> bichosCapturados;
	
	private int monedas;

	
	
	public Entrenador(String nombre) {
		this();
		this.nombre = nombre;
	}
	
	public Entrenador() {
		this.bichosCapturados = new ArrayList<Bicho>();
		this.experiencia = 0;
	}

//	GETTERS Y SETTERS:
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public int getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(Integer experiencia) {
		this.experiencia = experiencia;
	}

	public Lugar getUbicacionActual() {
		return ubicacion;
	}

	public void setUbicacionActual(Lugar ubicacion) {
		this.ubicacion = ubicacion;
	}

	public Nivel getNivelActual() {
		return this.nivelActual;
	}

	public void setNivelActual(Nivel nivelActual) {
		this.nivelActual = nivelActual;
	} 
	
	public List<Bicho> getBichosCapturados() {
		return this.bichosCapturados;
	}
	
	public int getMonedas() {
		return monedas;
	}

	public void setMonedas(int monedas) {
		this.monedas = monedas;
	}
	
// NIVEL DEL ENTRENADOR:
    
    /** Dado un  entrenador , aumenta la experiencia  en las unidades que se pasan por parametros
     * y si con  el nuevo valor  puede , aumenta el nivel que tiene.
     */
    public void aumentarDeNivelSiTieneExperiencia(int experiencioaGanada){
    	this.experiencia+=experiencioaGanada;
    	if (this.getExperiencia()>=this.getNivelActual().getPuntosParaSubirDeNivel())
    		this.setNivelActual(nivelActual.getSiguienteNivel());
    	}
    

//	ACCIONES CON BICHOS:
    
	/** Dado un {@link Bicho} se lo agrega a la lista de bichos capturados del entrenador.
     * Este método también agrega a este entrenador como el owner del bicho.
	 * @param bichoCapturado - {@link Bicho} capturado a agregar a la lista de bichos capturados. */
    public  void agregarBichoCapturado(Bicho bichoCapturado){
    	this.bichosCapturados.add(bichoCapturado);
    	bichoCapturado.setOwner(this);
    }
    
    /** Dado un {@link Bicho} se lo elimina de la lista de bichos capturados del entrenador.
     * Este método también elimina a este entrenador como el owner del bicho.
     * @param bicho - El {@link int }establece que bichos de la coleccion sera eliminado */
    public void descartarBichoCapturado(Bicho bichoADescartar) {
		this.bichosCapturados.remove(bichoADescartar);
		bichoADescartar.setOwner(null);
		bichoADescartar.setIdUltimoDueño(this.getNombre());
    }	
	
	public Bicho buscarBicho(int expPorBusqueda) {
		
		Bicho bichoRecuperado=null;
		
		if ( this.puedeCapturar()){
			// el lugar puedo no devolver bichos si no se dan las condiciones adecuadas
			bichoRecuperado=this.getUbicacionActual().retornarUnBichoDelLugar(this);
			if (bichoRecuperado!= null){
				this.agregarBichoCapturado(bichoRecuperado);
				this.aumentarDeNivelSiTieneExperiencia(expPorBusqueda);
				bichoRecuperado.setTiempoDesdeSuCaptura(System.nanoTime());
				}
			}
		return bichoRecuperado;
			
	}
	
	public boolean puedeCapturar(){
		return (this.getBichosCapturados().size() <  this.getNivelActual().getMaxCantidadDeBichos());
		
	}

	/**
	 * Evoluciona un bicho de su  coleccion, el bicjo debe estar en condiciones de evolucionr 
	 * @param expPorEvolucionar
	 */
	public void evolucionarBicho(int expPorEvolucionar, Bicho bichoAEvolucionar) {
		
		bichoAEvolucionar.evolucionar();
		this.aumentarDeNivelSiTieneExperiencia(expPorEvolucionar);
		
	}
/**
 * el entrenador abandona un bicho de su lista, 
 * precondicion :el bicho debe pertenecerle 
 * @param bicho
 */
	public void abandonarBicho(Bicho bicho) {
		if (this.puedeAbandonar()){
			this.getUbicacionActual().recibirBichoAbandonado(bicho);
			this.descartarBichoCapturado(bicho);
		}
	}

	/**
	 * un entrenador puede abandonar un  bicho solo si no se queda sin ninguno
	 * @return
	 */
	private boolean puedeAbandonar() {
		return (this.getBichosCapturados().size()>1);
	}

	public ResultadoCombate combatir(Bicho bichoRetador, int expPorCombate) {

		ResultadoCombate resultado=	this.getUbicacionActual().combatir(bichoRetador);
		
		Entrenador entrenadorGanador= resultado.getGanador().getOwner();
		entrenadorGanador.aumentarDeNivelSiTieneExperiencia(expPorCombate);
		
		Entrenador entrenadorPerdedor= resultado.getPerdedor().getOwner();
		entrenadorPerdedor.aumentarDeNivelSiTieneExperiencia(expPorCombate);
		
		return resultado;
	}
	
// ACCIONES CON MONEDAS:	
	
	/** Se agregan monedas al entrenador.
	 * @param cantMonedas - cantidad de monedas que se agregaran al entrenador.*/
	public void agregarMonedas(int cantMonedas) {
		this.setMonedas(this.getMonedas() + cantMonedas);
	}

	/**@param monto - un monto en monedas. 
	 * @return Se responde si puede pagar el monto dado con las monedas que posee actualmente.*/
	public boolean puedePagar(Integer monto) {
		return this.getMonedas() >= monto;
	}

	/** El entrenador paga un monto dado con las monedas que posee actualmente.
	 * El pago se realizará solo si el entrenador tiene suficientes monedas para pagar el monto.
	 * @param monto - un monto en monedas.
	 * @throws {@link FondosInsuficientesException} si no tiene suficientes monedas para realizar 
	 * el pago.*/
	public void pagar(Integer monto) {
		if(this.puedePagar(monto))
			this.setMonedas(this.getMonedas() - monto);
		else
			throw new FondosInsuficientesException(monto);
	}
	
//	VIAJES:
	
	/** El entrenador viaja al destino dado como parámetro. 
	 * El viaje solo se realizará si el entrenador puede pagar el costo del viaje. Caso contrario
	 * fallará con una excepción.
	 * @param destino - {@link Lugar} a donde el entrenador viajará.
	 * @param costoDelViaje - cantidad de monedas a pagar para poder realizar el viaje.*/
	public void viajarALugar(Lugar destino, Integer costoDelViaje){
		this.pagar(costoDelViaje);
		this.setUbicacionActual(destino);
	}
	

//	REDEFINICIÓN EQUALS Y HASHCODE:
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o instanceof Entrenador) {
			Entrenador otroEntrenador = (Entrenador) o;
			boolean idemNombre= this.nombre.equals(otroEntrenador.getNombre());
			
			return 	idemNombre;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int num = 44;
		int result = 111;
		result =	num * result + this.nombre.hashCode();
		
		return result;
	}
	
}
