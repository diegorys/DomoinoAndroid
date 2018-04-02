package innovaia.communication;

import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/**
 * Clase encargada de la comunicación por Bluetooth.
 * @author Diego
 *
 */
public class ComunicacionBluetooth {
	
	/** ATRIBUTOS **/
	
	/**
	 * Identificador del dispositivo con el que conectamos.
	 */
	private UUID MY_UUID;
	
	/**
	 * Canal de salida.
	 */
    private OutputStream outputStream;
	
	/**
	 * Adaptador de Bluetooth.
	 */
	BluetoothAdapter mBluetoothAdapter;
	
	/** CONSTRUCTORES **/
	
	/**
	 * Constructor básico.
	 * TODO: Permitir configurar el UUID.
	 */
	public ComunicacionBluetooth(){
		MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
		outputStream = null;
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	}
	
	/** MÉTODOS **/

	/**
	 * Comprueba si el dispositivo tiene Bluetooth
	 * y si está activo.
	 * @return True si conecta, false si puede, pero no está habilitado.
	 * @throws Exception Si no soporta Bluetooth.
	 */
	public boolean conectar() throws Exception{
		if (mBluetoothAdapter == null) {
		    // Device does not support Bluetooth
			throw new Exception("Bluetooth no soportado");
		}
		
		if (!mBluetoothAdapter.isEnabled()) {
			Log.e("BT", "Bluetooth deshabilitado");
			return false;
		}
		return true;
	}
	
	/**
	 * Se asocia con el dispositivo configurado.
	 * @throws Exception Si no consigue asociarse con el dispositivo.
	 */
	public void asociar() throws Exception{
		//Obtenemos los dispositivos vinculados
		Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		// If there are paired devices
		if (pairedDevices.size() > 0) {
		    // Loop through paired devices
		    for (BluetoothDevice device : pairedDevices) {
		    	String dispositivo = device.getName() + device.getAddress();
		        // Add the name and address to an array adapter to show in a ListView
		    	//Toast.makeText(this, dispositivo, Toast.LENGTH_SHORT).show();
		    	Log.i("Bluetooth", dispositivo);
		    	BluetoothSocket btSocket = null;
		        btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
		        btSocket.connect();
		        outputStream = btSocket.getOutputStream();		
		    }
		}else{
			Log.e("BT", "No hay dispositivos vinculados");
		}
	}

	/**
	 * Envía datos al nodo.
	 */
	public void enviarDatos(String datos) throws Exception{
		datos = datos + '\n'; //Retorno de carro
		String d = "";
		for(int i = 0; i < datos.length(); i++){
			d = ""+datos.charAt(i);
			outputStream.write(d.getBytes());
		}
	}

}
