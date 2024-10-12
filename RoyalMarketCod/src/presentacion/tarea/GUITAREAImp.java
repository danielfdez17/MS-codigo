package presentacion.tarea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.tarea.TTarea;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUITAREAImp extends GUITAREA {
	
	private static final String FROM_WHERE = "GUITAREAImp";
	private static final int BUTTONS = 6;
	
	public GUITAREAImp() {
		this.initGUI();
	}
	
	private void initGUI() {
		this.setTitle("Tareas");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS, 1));
		
		JButton buttonAlta = new JButton("Alta tarea");
		buttonAlta.addActionListener((e) -> {
			GUIAltaTarea.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonAlta);
		
		JButton buttonBuscar = new JButton("Buscar tarea");
		buttonBuscar.addActionListener((e) -> {
			GUIBuscarTarea.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonBuscar);
		
		JButton buttonListar = new JButton("Listar tareas");
		buttonListar.addActionListener((e) -> {
			GUIListarTareas.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonListar);
		
		JButton buttonListarPorProyecto = new JButton("Listar tareas por proyecto");
		buttonListarPorProyecto.addActionListener((e) -> {
			GUIListarTareasPorProyecto.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonListarPorProyecto);
		
		JButton buttonActualizar = new JButton("Actualizar tarea");
		buttonActualizar.addActionListener((e) -> {
			GUIActualizarTarea.getInstancia().comprobar(true);
		});
		buttonsPanel.add(buttonActualizar);
		
		JButton buttonBaja = new JButton("Baja tarea");
		buttonBaja.addActionListener((e) -> {
			GUIBajaTarea.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonBaja);
		
		mainPanel.add(buttonsPanel, BorderLayout.CENTER);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	setVisible(false);
            }
        });
		
	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvent()) {
		case COMPROBAR_TAREA_KO:
			GUIMSG.showMessage("No existe la tarea con dicha id", FROM_WHERE, true);
			break;
		case COMPROBAR_TAREA_OK:
			TTarea tt = (TTarea)context.getDatos();
			GUIActualizarTarea.getInstancia().setTarea(tt);
			GUIActualizarTarea.getInstancia().init();
			GUIActualizarTarea.getInstancia().setVisible(true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}
}