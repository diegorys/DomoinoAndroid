package innovaia.domoino.controltv.aplicacion;

import java.util.ArrayList;

/**
 * Define el comportamiento y el listado de objetos que 
 * escuchar�n cambios en la lista de canales.
 * @author Diego
 *
 */
public interface CanalListener {
	
	/**
	 * Listado de objetos que escuchan.
	 */
	public static ArrayList<CanalListener> listeners = new ArrayList<CanalListener>();

	/**
	 * Actualiza los datos seg�n los canales.
	 * @param manager
	 */
	public void actualizar(CanalManager manager);
	
}
