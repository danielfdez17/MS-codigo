package presentacion.proveedor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.proveedor.SAProveedor;
import negocio.proveedor.TProveedor;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIAltaProveedor extends JFrame implements GUI {
	
	private static final int FIELDS = 3;
	private JTextField textTlf;
	private JTextField textNif;
	private JTextField textDireccion;
	private static final String FROM_WHERE = "GUIAltaProveedor";
	private static GUIAltaProveedor instancia;
	
	public GUIAltaProveedor() {
		this.initGUI();
	}
	
	public synchronized static GUIAltaProveedor getInstancia() {
		if (instancia == null) instancia = new GUIAltaProveedor();
		return instancia;
	}
	
	private void initGUI() {
		
		this.setTitle(FROM_WHERE);
		
		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// CREATE PANEL
		JPanel panelAlta = new JPanel(new GridLayout(FIELDS, 1));
		
		// NAME
		
		Panel panelTlf = new Panel("Telefono");
		this.textTlf = panelTlf.getJTextField();
		panelAlta.add(panelTlf.getJPanel());
		
		Panel panelNif = new Panel("NIF");
		this.textNif = panelNif.getJTextField();
		panelAlta.add(panelNif.getJPanel());
		
		Panel panelDireccion = new Panel("Direccion");
		this.textDireccion = panelDireccion.getJTextField();
		panelAlta.add(panelDireccion.getJPanel());
		
		JButton buttonAlta = new JButton("Dar de alta");
		buttonAlta.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int tlf = Integer.parseInt(this.textTlf.getText());
					String nif = this.textNif.getText();
					String direccion = this.textDireccion.getText();
					setVisible(false);
					this.clear();
					Controlador.getInstancia().accion(new Context(Eventos.ALTA_PROVEEDOR, new TProveedor(tlf, nif, direccion)));
				} catch(NumberFormatException nfe) {
					this.clear();
					GUIMSG.showMessage("El campo 'Telefono' requiere solo numeros", FROM_WHERE, true);
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
	public void update(Context context) {
		switch (context.getEvent()) {
		case ALTA_PROVEEDOR_OK:
			GUIMSG.showMessage("Proveedor dada de alta correctamente\n" + ((TProveedor)context.getDatos()).toString(), FROM_WHERE, false);
			break;
		case ALTA_PROVEEDOR_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		this.textTlf.setText("");
		this.textDireccion.setText("");
		this.textNif.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAProveedor.ERROR_SINTACTICO:
			return "Error sintactico";
		case SAProveedor.PROVEEDOR_INACTIVO:
			return "El proveedor estaba inactivo, queda reactivado";
		case SAProveedor.ERROR_BBDD:
			return "Error en la BBDD";
		case SAProveedor.PROVEEDOR_ACTIVO:
			return "El proveedor existe y esta activo";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textTlf.getText().isEmpty() && 
				this.textDireccion.getText().isEmpty() && 
				this.textNif.getText().isEmpty();
	}
}
	