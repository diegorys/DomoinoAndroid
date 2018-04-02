package innovaia.domoino.controltv;

import innovaia.domoino.controltv.aplicacion.CanalFactorySerial;
import innovaia.domoino.controltv.aplicacion.CanalManager;
import innovaia.domoino.controltv.graficos.Mando;
import innovaia.domoino.controltv.graficos.Estadisticas;

/**
 * Clase principal que arranca el programa.
 * @author Diego
 *
 */
public class ControlTV {

	/**
	 * Arranca el programa.
	 * @param args
	 */
    public static void main(String[] args) {
    	//Manejador de la lista de canales.
		CanalManager manager = new CanalManager();
		
		//Gráficos del mando a distancia.
        Mando mando = new Mando();
        mando.iniciar();
        
        //Estadísticas de las veces que se pulsa cada tecla.
        Estadisticas estadisticas = new Estadisticas();
        estadisticas.iniciar();
        
        //Receptor y creador de nodos a través del puerto serie.
		CanalFactorySerial fabrica = new CanalFactorySerial(manager);
		fabrica.iniciar();
		
		//Restauramos los datos del XML.
		manager.restaurar();
    }

}

