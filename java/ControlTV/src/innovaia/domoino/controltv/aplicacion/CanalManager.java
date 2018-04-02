package innovaia.domoino.controltv.aplicacion;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * Se encarga de manejar la lista de canales.
 * @author Diego
 *
 */
public class CanalManager {
	
	/** ATRIBUTOS **/

	/**
	 * Listado de canales.
	 */
    private ArrayList<Canal> _canales;
    
    /** CONSTRUCTORES **/

	/**
     * Constructor básico.
     */
    public CanalManager(){
    	_canales = new ArrayList<Canal>();
    }
    
    /** MÉTODOS GETTERS Y SETTERS **/
    
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
        Iterator<Canal> it = _canales.iterator();
        Canal c;
        boolean emitido = false;
        //Si existe, incremento las emisiones.
        while(it.hasNext()){
        	c = it.next();
        	if(c.getCodigo().compareToIgnoreCase(canal.getCodigo()) == 0){
        		c.setEmisiones(c.getEmisiones()+1);
        		emitido = true;
        	}
        }
        //Si no, lo añado a la lista.
        if(!emitido){
        	_canales.add(canal);
        }
        //Informo a los suscriptores que escuchan.
    	informar();
    	//Almaceno en el fichero de datos.
		almacenar();
    }
    
    /**
     * Actualiza un canal ya existente.
     * @param nombre
     * @param nuevoNombre
     */
    public void actualizar(String nombre, String nuevoNombre){
        Iterator<Canal> it = _canales.iterator();
        Canal c;
        //Lo busco y lo actualizo.
        while(it.hasNext()){
        	c = it.next();
        	if(c.getNombre().compareToIgnoreCase(nombre) == 0){
        		c.setNombre(nuevoNombre);
        	}
        }
        //Informo a los suscriptores que escuchan.
    	informar();
    	//Almaceno en el fichero de datos.
		almacenar();
    }
    
    /**
     * Elimina un canal.
     * @param canal
     */
    public void eliminar(Canal canal){
    	_canales.remove(canal);
    }
    
    /**
     * Almacena en un xml la lista de canales.
     */
    public void almacenar(){
        System.out.println("Guardando");
    	if(_canales.size() <= 0){
    		return;
    	}
    	try{
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder builder = factory.newDocumentBuilder();
        	DOMImplementation implementation = builder.getDOMImplementation();
        	
        	Document document = implementation.createDocument(null, "nodos", null);
        	document.setXmlVersion("1.0");
        	
        	Element raiz = document.getDocumentElement();
        	
        	Element nodo = document.createElement("nodo");
        	raiz.appendChild(nodo);
        
        	Element nombreNodo = document.createElement("nombre");
        	Text nombreNodoTexto = document.createTextNode("Mando a distancia");
        	nombreNodo.appendChild(nombreNodoTexto);
        	nodo.appendChild(nombreNodo);
        
        	Element protocolo = document.createElement("protocolo");
        	Text protocoloTexto = document.createTextNode(_canales.get(0).getProtocolo());
        	protocolo.appendChild(protocoloTexto);
        	nodo.appendChild(protocolo);
        	
        	Element nodoCanales = document.createElement("canales");
        	
            Iterator<Canal> it = this._canales.iterator();
            Canal canal;
            while(it.hasNext()){
            	canal = it.next();
            	Element eCanal		= document.createElement("canal");
            	Element eNombre		= document.createElement("nombre");
            	Element eCodigo = document.createElement("codigo");
            	Element eEmisiones	= document.createElement("emisiones");
            	
            	Text tNombre	 = document.createTextNode(canal.getNombre());
            	Text tCodigo = document.createTextNode(canal.getCodigo());
            	Text tEmisiones	 = document.createTextNode(canal.getEmisiones()+"");
            	
            	eNombre.appendChild(tNombre);
            	eCodigo.appendChild(tCodigo);
            	eEmisiones.appendChild(tEmisiones);
            	
            	eCanal.appendChild(eNombre);
            	eCanal.appendChild(eCodigo);
            	eCanal.appendChild(eEmisiones);
            	
            	nodoCanales.appendChild(eCanal);
            }
            nodo.appendChild(nodoCanales);
            Source source = new DOMSource(document);
            Result result = new StreamResult(new java.io.File("data.xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);
            System.out.println("Datos guardados");
    	}catch(Exception e){
			e.printStackTrace();
    	}
    	
    }
    
    /**
     * Restaura la lista de canales del xml.
     */
    public void restaurar(){
        try {        	 
        	File fXmlFile = new File("data.xml");
        	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        	Document doc = dBuilder.parse(fXmlFile);
         
        	//optional, but recommended
        	//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        	doc.getDocumentElement().normalize();
         
        	NodeList nList = doc.getElementsByTagName("nodo");
         
        	Canal canal;
        	String nombre;
        	String protocolo;
        	String codigo;
        	NodeList canales;
        	int emisiones;
        	
        	for (int temp = 0; temp < nList.getLength(); temp++) {
        		Node nNode = nList.item(temp);
         
        		if (nNode.getNodeType() == Node.ELEMENT_NODE) {
        			Element eElement = (Element) nNode;
        			
        			protocolo = eElement.getElementsByTagName("protocolo").item(0).getTextContent();
        			canales	  = eElement.getElementsByTagName("canal");
        			
                	for (int j = 0; j < canales.getLength(); j++) {
                		Node nCanal = canales.item(j);
                		
                		if (nCanal.getNodeType() == Node.ELEMENT_NODE) {
                			Element eCanal = (Element) nCanal;
                			
                			nombre 	   = eCanal.getElementsByTagName("nombre").item(0).getTextContent();
                			codigo = eCanal.getElementsByTagName("codigo").item(0).getTextContent();
                			try{
                				emisiones = Integer.parseInt(eCanal.getElementsByTagName("emisiones").item(0).getTextContent());
                			}catch(NumberFormatException e){
                				emisiones = 0;
                			}
                			canal = new Canal(codigo, protocolo, nombre);
                			canal.setEmisiones(emisiones);
                			this._canales.add(canal);
                			System.out.println("Añadido "+canal.getNombre());
                		}
                	}
        		}
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.informar();
    }
    
    /** MÉTODOS PRIVADOS **/
    
    /**
     * Informa a los suscriptores de cambios.
     */
    private void informar(){
        Iterator<CanalListener> it = CanalListener.listeners.iterator();
        CanalListener listener;
        while(it.hasNext()){
        	listener = it.next();
        	listener.actualizar(this);
        }
    }
	
}
