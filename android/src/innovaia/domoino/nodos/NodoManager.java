package innovaia.domoino.nodos;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.util.Log;

/**
 * Se encarga de manejar la lista de canales.
 * @author Diego
 *
 */
public class NodoManager {
	
	/** ATRIBUTOS **/

	/**
	 * Listado de canales.
	 */
    private ArrayList<Nodo> _nodos;
    
    /** CONSTRUCTORES **/

	/**
     * Constructor básico.
     */
    public NodoManager(){
    	_nodos = new ArrayList<Nodo>();
    }
    
    /** MÉTODOS GETTERS Y SETTERS **/
    
    /**
     * Obtiene el listado de canales.
     * @return canales
     */
    public ArrayList<Nodo> getNodos() {
		return _nodos;
	}
    
    /** MÉTODOS DE COMPORTAMIENTO **/ 
    
    /**
     * Restaura la lista de canales del xml.
     */
    public void restaurar(){
        try {        	 
        	Document doc = this.getXmlFromUrl("http://innovaia.es/arduino/Nodos.xml");
         
        	//optional, but recommended
        	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        	doc.getDocumentElement().normalize();
         
        	NodeList nList = doc.getElementsByTagName("nodo");
         
        	Nodo nodo;
        	String nombre;
        	String protocolo;
        	String descripcion;
        	Canal canal;
        	NodeList canales;
        	String nombreCanal;
        	String codigoCanal;
        	int emisiones;
        	
        	for (int temp = 0; temp < nList.getLength(); temp++) {
        		Node nNode = nList.item(temp);
         
        		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        			Element eElement = (Element) nNode;
        			
        			//Datos del mando.
        			protocolo 	= eElement.getElementsByTagName("protocolo").item(0).getTextContent();
        			nombre 	  	= eElement.getElementsByTagName("nombre").item(0).getTextContent();
        			descripcion	= eElement.getElementsByTagName("descripcion").item(0).getTextContent();
        			nodo = new Nodo(nombre, descripcion, protocolo);
        			
        			//Añadimos los canales.
        			canales 	= eElement.getElementsByTagName("canal");
        			for (int j = 0; j < canales.getLength(); j++) {
                		Node nCanal = canales.item(j);
                		
                		if (nCanal.getNodeType() == Node.ELEMENT_NODE) {
                			Element eCanal = (Element) nCanal;
                			
                			nombreCanal = eCanal.getElementsByTagName("nombre").item(0).getTextContent();
                			codigoCanal	= eCanal.getElementsByTagName("codigo").item(0).getTextContent();
                			try{
                				emisiones = Integer.parseInt(eCanal.getElementsByTagName("emisiones").item(0).getTextContent());
                			}catch(NumberFormatException e){
                				emisiones = 0;
                			}
                			canal = new Canal(codigoCanal, protocolo, nombreCanal);
                			canal.setEmisiones(emisiones);
                			nodo.insertar(canal);
                		}
                	}
        			
        			//Finalmente, añadimos el nodo.
                	this._nodos.add(nodo);
        		}
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /** MÉTODOS AUXILIARES **/

    /**
     * Obtiene un documento XML de una URL.
     * @param url
     * @return
     */
	public Document getXmlFromUrl(String url) {
		Document doc = null;
	
	    try {
	    	Log.i("NODOS", url);
	        // defaultHttpClient
	        DefaultHttpClient httpClient = new DefaultHttpClient();
	        HttpGet httpGet = new HttpGet(url);
	
	        HttpResponse httpResponse = httpClient.execute(httpGet);
	        InputStream is = httpResponse.getEntity().getContent();
	        Log.i("NODOS", httpResponse.getStatusLine().toString());
	        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder builder = factory.newDocumentBuilder();
	        doc = builder.parse(is);
	
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    // return XML
	    return doc;
	}
}