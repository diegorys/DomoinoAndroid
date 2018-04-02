package innovaia.util.communication;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 

import java.util.Enumeration;
import java.util.Iterator;

/**
 * Clase encargada de leer información por el puerto serie
 * y enviársela a los objetos suscriptos.
 * @author Diego
 *
 */
public class Serial implements SerialPortEventListener {
	
	/** ATRIBUTOS **/
	
	/**
	 * Puerto serial del que se recibirán datos.
	 */
	SerialPort serialPort;
    
	/**
	 * Puertos a utilizar.
	 */
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbmodem1421", // Mac OS X
			"/dev/ttyUSB0", // Linux
			"COM9", // Windows
	};
	
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	protected BufferedReader input;
	
	/** The output stream to the port */
	private OutputStream output;
	
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
	
	/** MÉTODOS PÚBLICOS **/
	
	/**
	 * Inicia el hilo de ejecución.
	 */
	public void iniciar(){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	Serial serial = new Serial();
                    serial.initialize();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
	
	/** MÉTODOS PRIVADOS **/

	/**
	 * Inicializa el puerto.
	 */
	private void initialize() {
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String message=input.readLine();
		        Iterator<SerialListener> it = SerialListener.listeners.iterator();
		        SerialListener listener;
		        while(it.hasNext()){
		        	listener = it.next();
		        	listener.process(message);
		        }
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

}