package presentacion.cliente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.cliente.SACliente;
import negocio.cliente.TClienteNormal;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIAltaClienteNormal extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIAltaClienteNormal";
	private static final int FIELDS = 5;
	private static GUIAltaClienteNormal instancia;
	
	private JTextField textTlf;
	private JTextField textNif;
	private JTextField textNombre;
	private JTextField textDireccion;
	private JTextField textTarjetaNormal;
	
	public GUIAltaClienteNormal() {
		this.initGUI();
	}
	
	public synchronized static GUIAltaClienteNormal getInstancia() {
		if (instancia == null) instancia = new GUIAltaClienteNormal();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Alta de cliente normal");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel panelAlta = new JPanel(new GridLayout(FIELDS, 1));
		
		Panel panelTlf = new Panel("Telefono");
		this.textTlf = panelTlf.getJTextField();
		panelAlta.add(panelTlf.getJPanel());
		
		Panel panelNif = new Panel("NIF");
		this.textNif = panelNif.getJTextField();
		panelAlta.add(panelNif.getJPanel());
		
		Panel panelNombre = new Panel("Nombre");
		this.textNombre = panelNombre.getJTextField();
		panelAlta.add(panelNombre.getJPanel());
		
		Panel panelDireccion = new Panel("Direccion");
		this.textDireccion = panelDireccion.getJTextField();
		panelAlta.add(panelDireccion.getJPanel());

		Panel panelTarjeta = new Panel("ID tarjeta normal");
		this.textTarjetaNormal = panelTarjeta.getJTextField();
		panelAlta.add(panelTarjeta.getJPanel());
		
		
		JButton buttonAlta = new JButton("Dar de alta"); 
		buttonAlta.addActionListener(l -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int tlf = Integer.parseInt(this.textTlf.getText());
					String nif = this.textNif.getText();
					String nombre = this.textNombre.getText();
					String direccion = this.textDireccion.getText();
					int idTarjetaNormal = Integer.parseInt(this.textTarjetaNormal.getText());
					this.setVisible(false);
					this.clear();
					Controlador.getInstancia().accion(new Context(Eventos.ALTA_CLIENTE_NORMAL, new TClienteNormal(idTarjetaNormal, tlf, nif, nombre, direccion, true)));
				} catch(NumberFormatException nfe) {
					GUIMSG.showMessage("Los campos 'Telefono' e 'ID tarjeta normal' requieren solo numeros", FROM_WHERE, true);
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
		case ALTA_CLIENTE_NORMAL_OK:
			TClienteNormal cliente = (TClienteNormal) context.getDatos();
			GUIMSG.showMessage("Cliente dado de alta\n" + cliente.toString(), FROM_WHERE, false);
			break;
		case ALTA_CLIENTE_NORMAL_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		this.textNombre.setText("");
		this.textDireccion.setText("");
		this.textTarjetaNormal.setText("");
		this.textNif.setText("");
		this.textTlf.setText("");
		this.textNombre.setText("");
	}
	
	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SACliente.ERROR_SINTACTICO:
			return "No se ha podido dar de alta el cliente por error sintactico";
		case SACliente.ERROR_BBDD:
			return "No se ha podido dar de alta el cliente por error en BD";
		case SACliente.CLIENTE_ACTIVO:
			return "No se ha podido dar de alta el cliente porque ya esta activo";
		case SACliente.CLIENTE_INACTIVO:
			return "El cliente estaba inactivo, reactivado";
		case SACliente.CAMBIO_TIPO:
			return "El cliente existe y es un cliente vip";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;	
		}
	}
	@Override
	public boolean areTextFieldsEmpty() {
		return this.textNombre.getText().isEmpty() && this.textDireccion.getText().isEmpty() && this.textNif.getText().isEmpty()
				&& this.textTlf.getText().isEmpty() && this.textTarjetaNormal.getText().isEmpty();
	}
}
