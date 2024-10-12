package presentacion.almacen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.almacen.SAAlmacen;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIBajaAlmacen extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIBajaAlmacen";
	private JTextField textId;
	private static GUIBajaAlmacen instancia;
	public GUIBajaAlmacen() {
		this.initGUI();
	}
	
	public synchronized static GUIBajaAlmacen getInstancia() {
		if (instancia == null) instancia = new GUIBajaAlmacen();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Baja almacen");
		
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
					setVisible(false);
					this.clear();
					Controlador.getInstancia().accion(new Context(Eventos.BAJA_ALMACEN, id));
				} catch (NumberFormatException nfe) {
					this.clear();
					GUIMSG.showMessage("El campo 'ID' requiere solo de numeros", "Baja almacen", true);
				}
			}
			else {
				GUIMSG.showMessage("Faltan campos por rellenar", "Baja almacen", true);
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
	public void clear() {
		this.textId.setText("");
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case BAJA_ALMACEN_OK:
			int id = (int)context.getDatos();
			GUIMSG.showMessage(String.format("Almacen con id %d dado de baja", id), FROM_WHERE, false);
			break;
		case BAJA_ALMACEN_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
		
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAAlmacen.ALMACEN_INEXISTENTE:
			return "No se ha podido dar de baja el almacen porque no existe";
		case SAAlmacen.ALMACEN_INACTIVO:
			return "No se ha podido dar de baja el almacen porque ya estaba inactivo";
		case SAAlmacen.ERROR_BBDD: 
			return "No se ha podido dar de baja el almacen por error en BD";
		case SAAlmacen.PRODUCTOS_EN_ALMACEN:
			return "No se ha podido dar de baja el almacen porque tiene productos guardados";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty();
	}
}
