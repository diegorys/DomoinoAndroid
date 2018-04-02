package innovaia.util.communication;

import java.util.ArrayList;

/**
 * Define el comportamiento y el listado de objetos que 
 * escuchar�n la recepci�n de datos por serial.
 * @author Diego
 *
 */
public interface SerialListener {
	
	/**
	 * Listado de objetos que escuchan.
	 */
	public static ArrayList<SerialListener> listeners = new ArrayList<SerialListener>();

	/**
	 * Procesa los datos recibidos del serial.
	 */
	public void process(String message);
	
}
