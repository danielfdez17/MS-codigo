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
import negocio.almacen.TAlmacen;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIAltaAlmacen extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIAltaAlmacen";	
	private static final int FIELDS = 1;
	private JTextField textDireccion;
	private static GUIAltaAlmacen instancia;
	
	public GUIAltaAlmacen() {
		this.initGUI();
	}
	
	public synchronized static GUIAltaAlmacen getInstancia() {
		if (instancia == null) instancia = new GUIAltaAlmacen();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Alta de almacen");
		
		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// CREATE PANEL
		JPanel panelAlta = new JPanel(new GridLayout(FIELDS, 1));
		
		Panel panelDireccion = new Panel("Direccion");
		this.textDireccion = panelDireccion.getJTextField();
		panelAlta.add(panelDireccion.getJPanel());
		
		
		// CREATE BUTTON
		JButton buttonAlta = new JButton("Dar de alta");
		buttonAlta.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					String direccion = this.textDireccion.getText();
					setVisible(false);
					this.clear();
					Controlador.getInstancia().accion(new Context(Eventos.ALTA_ALMACEN, new TAlmacen(direccion)));
				} catch(NumberFormatException nfe) {
					this.clear();
					GUIMSG.showMessage("El campo 'ID' requiere solo numeros", "ALTA DE ALMACEN", true);
				}
			}
			else {
				GUIMSG.showMessage("Faltan campos por rellenar", "ALTA DE ALMACEN", true);
			}
		});
		
		JButton emptyTextButton = new JButton("Vaciar");
		emptyTextButton.addActionListener((e) -> {
			this.clear();
		});
		
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(emptyTextButton);
		buttonsPanel.add(buttonAlta);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		mainPanel.add(panelAlta, BorderLayout.CENTER);
		
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
    	this.textDireccion.setText("");
    }

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case ALTA_ALMACEN_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		case ALTA_ALMACEN_OK:
			TAlmacen almacen = (TAlmacen)context.getDatos();
			GUIMSG.showMessage("Almacen dado de alta \n" + almacen.toString(), FROM_WHERE, false);
			break;
		default:
			GUIMSG.showMessage("¡Respuesta no contemplada!", FROM_WHERE, true);
			break;
		}
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAAlmacen.ERROR_SINTACTICO:
			return "No se ha podido dar de alta el almacen por error sintactico";
		case SAAlmacen.ERROR_BBDD:
			return "No se ha podido dar de alta el almacen por error en la BBDD";
		case SAAlmacen.ALMACEN_ACTIVO:
			return "No se ha podido dar de alta el almacen porque ya esta activo";
		case SAAlmacen.ALMACEN_INACTIVO:
			return "El almacen estaba inactivo, queda reactivado";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textDireccion.getText().isEmpty();
	}
}