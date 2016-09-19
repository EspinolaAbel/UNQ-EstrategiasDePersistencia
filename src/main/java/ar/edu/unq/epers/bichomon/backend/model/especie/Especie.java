package ar.edu.unq.epers.bichomon.backend.model.especie;

/**
 * Representa una {@link Especie} de bicho.
 * 
 * @author Charly Backend
 */
public class Especie {

	private String nombre;
	private int altura;
	private int peso;
	private TipoBicho tipo;

	private int energiaInicial;
	
	private String urlFoto;
	
	private int cantidadBichos;
	
// modificacionrespecto al tp1
	private Especie raiz;
	
	
	private CondicionDeEvolucion condiconEvolucion;
	
	
	
	public Especie(){
	}


	public Especie(String nombre, TipoBicho tipo) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.raiz=this;
	}
	
	
	public Especie(String nombre, TipoBicho tipo, Especie raiz) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.raiz=raiz;
	}
	

	/**
	 * @return retorna la raiz de la especie (por ejemplo: Perromon)
	 */
	public String getRaiz() {
		return this.nombre;
	}
	public void setRaiz(String nombre) {
		this.nombre = nombre;
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
	public int getAltura() {
		return this.altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	
	/**
	 * @return el peso de todos los bichos de esta especie
	 */
	public int getPeso() {
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
	public int getEnergiaInicial() {
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
	public int getCantidadBichos() {
		return this.cantidadBichos;
	}
	public void setCantidadBichos(int i) {
		this.cantidadBichos = i;
	}
	
	public Especie dameRaiz (){
		return this.raiz;
	}
	
}
