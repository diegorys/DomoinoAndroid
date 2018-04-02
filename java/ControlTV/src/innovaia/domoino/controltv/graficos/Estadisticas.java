package innovaia.domoino.controltv.graficos;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import innovaia.domoino.controltv.aplicacion.Canal;
import innovaia.domoino.controltv.aplicacion.CanalListener;
import innovaia.domoino.controltv.aplicacion.CanalManager;

/**
 * Muestra los gr�ficos de emisiones de las cadenas.
 * @author Diego
 *
 */
public class Estadisticas implements CanalListener{
	
	/** ATRIBUTOS **/
	
	/**
	 * Pantalla.
	 */
    private JFrame frame;

    /** CONSTRUCTORES **/
    
    /**
     * Inicializa la pantalla
     */
	public Estadisticas(){
        frame = new JFrame();

        frame.setBounds(300, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Control TV");
	}
	
	/** M�TODOS **/
	
	/**
	 * Inicia el hilo de ejecuci�n en el que se lanzar�
	 * la pantalla.
	 */
	public void iniciar(){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {                  
                    Estadisticas window = new Estadisticas();
                    window.frame.setVisible(true);
                    listeners.add(window);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}
	
	/**
     * Crea el origen de datos para la gr�fica. 
     */
    private  PieDataset createDataset(ArrayList<Canal> lista) {
        DefaultPieDataset result = new DefaultPieDataset();
        
        Iterator<Canal> it = lista.iterator();
        Canal canal;
        
        //A�ado todos los canales, con sus emisiones.
        while(it.hasNext()){
        	canal = it.next();
        	if(canal.getEmisiones() > 0){
        		result.setValue(canal.getNombre(), canal.getEmisiones());
        	}
        }

        return result;
        
    }
    
    /**
     * Crea la gr�fica.
     */
    private JFreeChart createChart(PieDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createPieChart3D(title,          // chart title
            dataset,                // data
            true,                   // include legend
            true,
            false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }

    /**
     * Actualiza los datos seg�n los canales.
     * @param manager
     */
	@Override
	public void actualizar(CanalManager manager){
		ArrayList<Canal> lista = manager.getCanales();
        frame.getContentPane().removeAll();
        
		PieDataset dataset = createDataset(lista);
        JFreeChart chart = createChart(dataset, "Estad�sticas");
        // we put the chart into a panel
        ChartPanel chartPanel = new ChartPanel(chart);
        
        chartPanel.setAlignmentX((float) 40.0);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));

        frame.getContentPane().add(chartPanel);
        frame.revalidate();
        frame.repaint();
        System.out.println("Gr�ficos actualizados");
	}


}
