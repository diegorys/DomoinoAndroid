package innovaia.domoino.controltv.aplicacion;

/**
 * Representa un canal de TV.
 * @author Diego
 *
 */
public class Canal {
	
	/** ATRIBUTOS **/
	
	/**
	 * Código en hexadecimal.
	 */
	private String _codigo;
	
	/**
	 * Protocolo en el que se emite el código.
	 */
	private String _protocolo;
	
	/**
	 * Nombre de la cadena de TV.
	 */
	private String _nombre;
	
	/**
	 * Número de veces que se ha pulsado la tecla de la cadena.
	 */
	private int _emisiones;
	
	/** CONSTRUCTORES **/
	
	/**
	 * Constructor que inicializa todos los parámetros.
	 * @param codigo
	 * @param protocolo
	 * @param nombre
	 */
	public Canal(String codigo, String protocolo, String nombre){
		this._codigo 	 = codigo;
		this._protocolo  = protocolo;
		this._nombre 	 = nombre;
		this._emisiones  = 0;
	}
	
	/**
	 * Constructor que pone como nombre el código.
	 */
	public Canal(String codigo, String protocolo){
		this._codigo 	= codigo;
		this._protocolo = protocolo;
		this._nombre 	= codigo;
		this._emisiones = 0;
	}
	
	/** MÉTODOS GETTERS Y SETTERS **/
	
	/**
	 * Obtiene el código.
	 * @return codigo
	 */
	public String getCodigo() {
		return _codigo;
	}
	
	/**
	 * Obtiene el protocolo.
	 * @return protocolo
	 */
	public String getProtocolo() {
		return _protocolo;
	}

	/**
	 * Obtiene el nombre.
	 * @return nombre
	 */
	public String getNombre() {
		return _nombre;
	}
	
	/**
	 * Asigna el nombre.
	 * @param nombre
	 */
	public void setNombre(String nombre){
		this._nombre = nombre;
	}

	/**
	 * Obtiene las emisiones.
	 * @return emisiones
	 */
	public int getEmisiones() {
		return _emisiones;
	}
	
	/**
	 * Asigna las emisiones.
	 * @param emisiones
	 */
	public void setEmisiones(int emisiones){
		this._emisiones = emisiones;
	}
	
}