package presentacion.tarea;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.tarea.TTarea;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIBuscarTarea extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIBuscarTarea";
	private JTextField textId;
	private static GUIBuscarTarea instancia;
	
	public GUIBuscarTarea() {
		this.initGUI();
	}

	public static synchronized GUIBuscarTarea getInstancia() {
		if (instancia == null) instancia = new GUIBuscarTarea();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Buscar tarea");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel panelBaja = new JPanel(new GridLayout(1, 1));
		
		Panel panelId = new Panel("ID");
		this.textId = panelId.getJTextField();
		panelBaja.add(panelId.getJPanel());
		
		JButton buttonBaja = new JButton("Dar de baja");
		buttonBaja.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int id = Integer.parseInt(this.textId.getText());
					this.setVisible(false);
					this.clear();
					Controlador.getInstancia().accion(new Context(Eventos.BUSCAR_TAREA, id));
				} catch (NumberFormatException nfe) {
					this.clear();
					GUIMSG.showMessage("El campo 'ID' requiere solo de numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
			}
		});
		
		JButton emptyTextButton = new JButton("Vaciar");
		emptyTextButton.addActionListener((e) -> {
			this.clear();
		});
		
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(emptyTextButton);
		buttonsPanel.add(buttonBaja);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		mainPanel.add(panelBaja, BorderLayout.CENTER);
		
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
		case BUSCAR_TAREA_OK:
			TTarea tt = (TTarea)context.getDatos();
			GUIMSG.showMessage("Tarea encontrada\n" + tt.toString(), FROM_WHERE, false);
			break;
		case BUSCAR_TAREA_KO:
			GUIMSG.showMessage("La tarea no existe", FROM_WHERE, true);
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		this.textId.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		return null;
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty();
	}
}
