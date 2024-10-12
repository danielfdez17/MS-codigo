package presentacion.tarea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.tarea.SATarea;
import negocio.tarea.TTarea;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIAltaTarea extends JFrame implements GUI {
	
	private static GUIAltaTarea instancia;
	private JTextField textNombre, textIdProyecto;
	private static final String FROM_WHERE = "GUIAltaTarea";
	
	public GUIAltaTarea() {
		this.initGUI();
	}
	
	public static synchronized GUIAltaTarea getInstancia() {
		if (instancia == null) instancia = new GUIAltaTarea();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Alta tarea");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel panelAlta = new JPanel(new GridLayout(2, 1));
		
		Panel panelNombre = new Panel("Nombre");
		this.textNombre = panelNombre.getJTextField();
		panelAlta.add(panelNombre.getJPanel());
		
		Panel panelIdProyecto = new Panel("ID proyecto");
		this.textIdProyecto = panelIdProyecto.getJTextField();
		panelAlta.add(panelIdProyecto.getJPanel());
		
		JButton buttonAlta = new JButton("Dar de alta");
		buttonAlta.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					String nombre = this.textNombre.getText();
					int idProyecto = Integer.parseInt(this.textIdProyecto.getText());
					Controlador.getInstancia().accion(new Context(Eventos.ALTA_TAREA, new TTarea(nombre, idProyecto)));
					this.clear();
					this.setVisible(false);
				} catch (NumberFormatException nfe) {
					GUIMSG.showMessage("El campo 'ID producto' solo requiere de numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage(Utils.FALTAN_CAMPOS_POR_RELLENAR, FROM_WHERE, true);
			}
		});
		
		JButton buttonVaciar = new JButton("Vaciar");
		buttonVaciar.addActionListener((e) -> {
			this.clear();
		});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(buttonVaciar);
		buttonsPanel.add(buttonAlta);
		
		mainPanel.add(panelAlta, BorderLayout.CENTER);
		
		
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(false);
		
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		this.textIdProyecto.setText("");
		this.textNombre.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SATarea.PROYECTO_INACTIVO:
			return "El proyecto asociado esta inactivo";
		case SATarea.PROYECTO_INEXISTENTE:
			return "El proyecto asociado no existe";
		case SATarea.TAREA_ACTIVA:
			return "La tarea existe y esta activa";
		case SATarea.TAREA_INACTIVA:
			return "La tarea estaba inactiva, queda reactivada";
		case SATarea.ERROR_SINTACTICO:
			return Utils.ERROR_SINTACTICO;
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textNombre.getText().isEmpty() &&
				this.textIdProyecto.getText().isEmpty();
	}
}