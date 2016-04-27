package innovaia.domoino.smartcontroller;
import java.util.ArrayList;
import java.util.Iterator;

import innovaia.communication.ComunicacionBluetooth;
import innovaia.domoino.nodos.Canal;
import innovaia.domoino.nodos.Nodo;
import innovaia.domoino.nodos.NodoManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Actividad principal, encargada de iniciar las conexiones
 * y lanzar la vista principal.
 * @author Diego
 *
 */
public class SmartController extends Activity{
	
	private static final int REQUEST_ENABLE_BT = 0;

	LinearLayout nodos;
	
	ComunicacionBluetooth bluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mando);
        nodos = (LinearLayout) findViewById(R.id.nodos);
        bluetooth = new ComunicacionBluetooth();
        try {
        	//Si no estoy conectado, pido conexión y espero.
			if(!bluetooth.conectar()){
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
				Log.i("BT", "Pidiendo conexión");
			}else{
				//Si lo estoy, inicio.
				this.iniciar();
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			Log.e("BT error", e.getMessage());
		}
        //new InterfazCreator().execute();
    }
    
    /**
     * Encargado de iniciar la aplicación cuando se activa el Bluetooth.
     * TODO: Comprobar requestCode y resultCode. Actualmente no es
     * imprescindible porque sólo hay uno.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Log.i("BT", "Request Code: "+requestCode);
    	Log.i("BT", "Result Code: "+resultCode);
    	this.iniciar();
    }

    protected void iniciar(){
    	try {
    		new InterfazCreator().execute();
			bluetooth.asociar();
			Toast.makeText(getApplicationContext(), "Conexión correcta", Toast.LENGTH_LONG).show();
			Log.i("BT", "Asociado");
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Error al conectar con el nodo", Toast.LENGTH_LONG).show();
			Log.e("BT error", e.getMessage());
		}
    }
    
    /**
     * Clase privada encargada de la creación de la interfaz
     * en base a los nodos obtenidos de un servicio web.
     * @author Diego
     *
     */
    private class InterfazCreator extends AsyncTask<Void, Void, Void> {
    	NodoManager manager = new NodoManager();
    	
    	/**
    	 * Se encarga de restaurar la lista de nodos.
    	 */
        @Override
        protected Void doInBackground(Void... params) {
        	manager.restaurar();
			return null;        	
        }
        
        /**
         * Una vez restaurada la lista de nodos, crea
         * la interfaz de usuario.
         */
        protected void onPostExecute(Void result) {
        	ArrayList<Nodo> lista = manager.getNodos();
            Iterator<Nodo> it = lista.iterator();
            Nodo nodo;
            //Creamos una vista para cada nodo.
            while(it.hasNext()){
            	nodo = it.next();
            	LinearLayout vistaNodo = new LinearLayout(getApplicationContext());
            	vistaNodo.setPadding(5, 5, 5, 5);
            	vistaNodo.setOrientation(LinearLayout.VERTICAL);;
            	TextView nombre = new TextView(getApplicationContext());
            	nombre.setText(nodo.getDescripcion());
            	vistaNodo.addView(nombre);
            	ArrayList<Canal> canales = nodo.getCanales();
            	Iterator<Canal> it2 = canales.iterator();
            	Canal canal;
            	//Y dentro de cada vista, un botón para cada canal.
                while(it2.hasNext()){
                	canal = it2.next();
            		Button btnCanal = new Button(getApplicationContext());
                	btnCanal.setTag(canal);
            		btnCanal.setText(canal.getNombre());
            		btnCanal.setOnClickListener(new View.OnClickListener() {
	                   public void onClick(View v) {
	                	   Button btnCanal = (Button)v;
	                	   Canal canal = (Canal)btnCanal.getTag();
	                	   String datos = canal.getProtocolo()+"#"+canal.getCodigo();
	                	   try {
	                		   bluetooth.enviarDatos(datos);
							} catch (Exception e) {
								Toast.makeText(getApplicationContext(), "Ningún dispositivo vinculado", Toast.LENGTH_LONG).show();
							}
	                	   Log.i(canal.getNombre(), datos);
	                   }
	            	});
            		vistaNodo.addView(btnCanal);
                }
                ScrollView scrollVista = new ScrollView(getApplicationContext());
                scrollVista.addView(vistaNodo);
            	nodos.addView(scrollVista);
            }
       }
   }

}
