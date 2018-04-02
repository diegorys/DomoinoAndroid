package innovaia.domoino.controltv.aplicacion;

import java.util.StringTokenizer;
import innovaia.util.communication.Serial;
import innovaia.util.communication.SerialListener;

/**
 * Clase encargada de crear canales a trav�s de la informaci�n
 * recibida por el puerto serie.
 * @author Diego
 *
 */
public class CanalFactorySerial extends Serial implements SerialListener{
	
	/** ATRIBUTOS **/
	
	/**
	 * Manejador de canales.
	 */
	private CanalManager _canales;
	
	/** CONSTRUCTORES **/
	
	/**
	 * Crea canales y los a�ade al manejador.
	 * @param canales
	 */
	public CanalFactorySerial(CanalManager canales){
		this._canales = canales;
		listeners.add(this);
	}
	
	/** M�TODOS **/
	
	/**
	 * Procesa los datos recibidos del serial.
	 */
	public void process(String message){
		StringTokenizer tr = new StringTokenizer(message, "#");
		//Parseo el mensaje, compuesto de protocolo y c�digo.
		if(tr.countTokens() == 2){
			System.out.println("Fabricar: "+message);
			String protocolo  = tr.nextToken();
			String codigo = tr.nextToken();
			//Creo e inserto el canal.
			Canal canal = new Canal(codigo, protocolo);
			_canales.insertar(canal);
		}
	}

}
