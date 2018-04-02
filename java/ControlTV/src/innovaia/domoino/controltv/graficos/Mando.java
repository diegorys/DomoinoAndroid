package innovaia.domoino.controltv.graficos;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import innovaia.domoino.controltv.aplicacion.Canal;
import innovaia.domoino.controltv.aplicacion.CanalListener;
import innovaia.domoino.controltv.aplicacion.CanalManager;

/**
 * Muestra el listado de canales y permite su edición.
 * @author Diego
 *
 */
public class Mando implements CanalListener{
	
	/** ATRIBUTOS **/
	
	/**
	 * Pantalla.
	 */
    private JFrame frame;

    /** CONSTRUCTORES **/
    
    /**
     * Inicializa la pantalla
     */
	public Mando(){
        frame = new JFrame();
        frame.setTitle("Control TV");
        frame.setBounds(100, 100, 200, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBounds(6, 27, 200, 480);
	}
	
	/** MÉTODOS **/
	
	/**
	 * Inicia el hilo de ejecución en el que se lanzará
	 * la pantalla.
	 */
	public void iniciar(){
        EventQueue.invokeLater(new Runnable() {
            // run es el mŽtodo que se ejecuta
            // al arrancar el hilo
            public void run() {
                try {
                    // crea una variable de la clase y se inicializa 
                    // con el constructor de la misma                   
                    Mando window = new Mando();
                    listeners.add(window);
                    // muestra la ventana
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
	
    /**
     * Actualiza los datos según los canales.
     * @param manager
     */
	@Override
	public void actualizar(final CanalManager manager){
		ArrayList<Canal> lista = manager.getCanales();
        frame.getContentPane().removeAll();
        
        Canal canal;
        JButton boton;
        JTextField textoNombre;
        final JTextField textoCambiar = new JTextField();
        JPanel fila;
        JPanel contenedor  = new JPanel();
        contenedor.setLayout(new GridLayout(0, 1));
        
        Iterator<Canal> it = lista.iterator();
        
        fila = new JPanel();
        textoCambiar.setPreferredSize(new Dimension(100, 20));
        fila.add(textoCambiar);
        contenedor.add(fila);
        
        //Por cada canal, insertamos un botón y un texto que muestra
        //la frecuencia.
        while(it.hasNext()){
        	canal = it.next();
        	boton = new JButton(canal.getNombre());
        	boton.setPreferredSize(new Dimension(100, 20));
        	textoNombre = new JTextField(canal.getCodigo());
        	textoNombre.setPreferredSize(new Dimension(55, 20));
        	fila = new JPanel();
        	fila.add(boton);
        	fila.add(textoNombre);
        	contenedor.add(fila);
        	System.out.println(canal.getNombre());
        	
        	boton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                	Object source = e.getSource();
                    if (source instanceof JButton) {
                    	JButton btn = (JButton)source;
                    	String antiguo = btn.getText();
                    	String nuevo = textoCambiar.getText();
                    	manager.actualizar(antiguo, nuevo);
                    }
                }
            });
        }
        JScrollPane scroll = new JScrollPane(contenedor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.getContentPane().add(scroll);
        
        frame.setContentPane(frame.getContentPane());
        System.out.println("Actualizo: "+lista.size());
	}

}
