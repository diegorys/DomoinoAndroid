package innovaia.domoino.nodos;

import java.util.ArrayList;

/**
 * Representa un nodo esclavo.
 * @author Diego
 *
 */
public class Nodo {
	
	/** ATRIBUTOS **/
	
	/**
	 * Nombre.
	 */
	private String _nombre;
	
	/**
	 * Descripcion.
	 */
	private String _descripcion;
	
	/**
	 * Protocolo con el que comunicarse con el nodo.
	 */
	private String _protocolo;
	
	/**
	 * Listado de canales manejados por el nodo.
	 */
	private ArrayList<Canal> _canales;

	
	/** CONSTRUCTORES **/
	
	/**
	 * Constructor que inicializa todos los parámetros.
	 * @param nombre
	 * @param descripcion
	 * @param protocolo
	 */
	public Nodo(String nombre, String descripcion, String protocolo){
		this._nombre 	 	= nombre;
		this._descripcion  	= descripcion;
		this._protocolo		= protocolo;
		this._canales		= new ArrayList<Canal>();
	}
	
	/** MÉTODOS GETTERS Y SETTERS **/
	
	/**
	 * Obtiene el nombre.
	 * @return nombre
	 */
	public String getNombre() {
		return _nombre;
	}
	
	/**
	 * Obtiene la descrición.
	 * @return descripcion
	 */
	public String getDescripcion() {
		return _descripcion;
	}
	
	/**
	 * Obtiene el protocolo.
	 * @return protocolo
	 */
	public String getProtocolo() {
		return _protocolo;
	}
	
    /**
     * Obtiene el listado de canales.
     * @return canales
     */
    public ArrayList<Canal> getCanales() {
		return _canales;
	}
    
    /** MÉTODOS DE COMPORTAMIENTO **/

	/**
     * Inserta un nuevo canal.
     * @param canal
     */
    public void insertar(Canal canal){
    	_canales.add(canal);
    }
	
}