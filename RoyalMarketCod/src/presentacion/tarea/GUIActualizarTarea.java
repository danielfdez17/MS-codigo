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
import presentacion.viewhelper.GUIComprobar;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIActualizarTarea extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIActualizarTarea";
	private static GUIActualizarTarea instancia;
	private GUIComprobar guiComprobar;
	private JTextField textId, textNombre, textIdProyecto;
	private TTarea tt;
	private static final int FIELDS = 3;
	
	public GUIActualizarTarea() {
		this.guiComprobar = new GUIComprobar(Utils.TAREA);
	}
	
	public static synchronized GUIActualizarTarea getInstancia() {
		if (instancia == null) instancia = new GUIActualizarTarea();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Actualizar tarea");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel panelActualizar = new JPanel(new GridLayout(3, 1));
		
		Panel panelId = new Panel("ID");
		this.textId = panelId.getJTextField();
		panelActualizar.add(panelId.getJPanel());
		
		Panel panelNombre = new Panel("Nombre");
		this.textNombre = panelNombre.getJTextField();
		panelActualizar.add(panelNombre.getJPanel());
		
		Panel panelIdProyecto = new Panel("ID proyecto");
		this.textIdProyecto = panelIdProyecto.getJTextField();
		panelActualizar.add(panelIdProyecto.getJPanel());
		
		this.refill();
		
		JButton buttonActualizar = new JButton("Actualizar");
		buttonActualizar.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int id = Integer.parseInt(this.textId.getText());
					String nombre = this.textNombre.getText();
					int idProyecto = Integer.parseInt(this.textIdProyecto.getText());
					boolean activo = this.tt.isActivo();
					this.clear();
					this.setVisible(false);
					Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_TAREA, new TTarea(id, nombre, idProyecto, activo)));
				} catch (NumberFormatException nfe) {
					GUIMSG.showMessage("Los campos 'ID' e 'ID proyecto' solo requieren de numeros", FROM_WHERE, true);
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
		
		JButton buttonRellenar = new JButton("Rellenar campos");
		buttonRellenar.addActionListener((e) -> {
			this.refill();
		});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(buttonRellenar);
		buttonsPanel.add(buttonVaciar);
		buttonsPanel.add(buttonActualizar);
		
		mainPanel.add(panelActualizar, BorderLayout.CENTER);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension((FIELDS +1)*100, 300));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		
	}
	
	public void setTarea(TTarea tt) {
		this.tt = tt;
	}
	
	public void comprobar(boolean b) {
		this.guiComprobar.setVisible(b);
	}
	
	public void init() {
		this.initGUI();
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case ACTUALIZAR_TAREA_OK:
			GUIMSG.showMessage("Tarea actualizada", FROM_WHERE, false);
			break;
		case ACTUALIZAR_TAREA_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}
	
	private void refill() {
		this.textId.setText("" + this.tt.getId());
		this.textNombre.setText(this.tt.getNombre());
		this.textIdProyecto.setText("" + this.tt.getIdProyecto());
	}

	@Override
	public void clear() {
		this.textId.setText("");
		this.textNombre.setText("");
		this.textIdProyecto.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SATarea.ERROR_SINTACTICO: 
			return Utils.ERROR_SINTACTICO;
		case SATarea.TAREA_INEXISTENTE: 
			return "La tarea no existe";
		case SATarea.PROYECTO_INACTIVO: 
			return "El proyecto asociado no esta activo";
		case SATarea.PROYECTO_INEXISTENTE:
			return "El proyecto asociado no existe";
		default: return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty() &&
				this.textNombre.getText().isEmpty() &&
				this.textIdProyecto.getText().isEmpty();
	}
}