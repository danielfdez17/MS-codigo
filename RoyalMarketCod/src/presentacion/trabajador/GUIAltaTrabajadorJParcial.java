package presentacion.trabajador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.trabajador.SATrabajador;
import negocio.trabajador.TTrabajadorJParcial;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIAltaTrabajadorJParcial extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIAltaTrabajadorJParcial";
	private static GUIAltaTrabajadorJParcial instancia;
	private static final int FIELDS = 7;
	
	private JTextField textTlf;
	private JTextField textNif;
	private JTextField textNombre;
	private JTextField textDireccion;
	private JTextField textIdAlmacen;
	private JTextField textHoras;
	private JTextField textPrecioHora;
	
	public GUIAltaTrabajadorJParcial() {
		this.initGUI();
	}
	
	public synchronized static GUIAltaTrabajadorJParcial getInstancia() {
		if (instancia == null) instancia = new GUIAltaTrabajadorJParcial();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Alta de trabajador Jornada Parcial");
		
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

		Panel panelTarjeta = new Panel("ID almacen");
		this.textIdAlmacen = panelTarjeta.getJTextField();
		panelAlta.add(panelTarjeta.getJPanel());
		
		Panel panelHoras = new Panel("Horas");
		this.textHoras = panelHoras.getJTextField();
		panelAlta.add(panelHoras.getJPanel());
		
		Panel panelPrecioHora = new Panel("Precio hora");
		this.textPrecioHora = panelPrecioHora.getJTextField();
		panelAlta.add(panelPrecioHora.getJPanel());
		
		JButton buttonAlta = new JButton("Dar de alta"); 
		buttonAlta.addActionListener(l -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int tlf = Integer.parseInt(this.textTlf.getText());
					String nif = this.textNif.getText();
					String nombre = this.textNombre.getText();
					String direccion = this.textDireccion.getText();
					int idAlmacen = Integer.parseInt(this.textIdAlmacen.getText());
					int horas = Integer.parseInt(this.textHoras.getText());
					double precioHora = Double.parseDouble(this.textPrecioHora.getText());
					Controlador.getInstancia().accion(new Context(Eventos.ALTA_TRABAJADOR_J_PARCIAL, new TTrabajadorJParcial(tlf, nif, nombre, direccion, idAlmacen, horas, precioHora)));
					
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
    public void clear() {
    	this.textNombre.setText("");
    	this.textDireccion.setText("");
    	this.textIdAlmacen.setText("");
    	this.textNif.setText("");
    	this.textTlf.setText("");
    	this.textNombre.setText("");
    	this.textHoras.setText("");
    	this.textPrecioHora.setText("");
    }
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
	    case ALTA_TRABAJADOR_J_PARCIAL_OK:
	        TTrabajadorJParcial trabajador = (TTrabajadorJParcial) context.getDatos();
	        GUIMSG.showMessage("Trabajador dado de alta\n" + trabajador.toString(), FROM_WHERE, false);
	        break;
	    case ALTA_TRABAJADOR_J_PARCIAL_KO:
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
		case SATrabajador.ERROR_BBDD:
			return "Error en la BBDD";
		case SATrabajador.TRABAJADOR_INACTIVO:
			return "El trabajador estaba inactivo, queda reactivado";
		case SATrabajador.CAMBIO_TIPO_TRABAJADOR:
			return "El trabajador existe y tiene contrato a jornada completa";
		case SATrabajador.TRABAJADOR_ACTIVO:
			return "El trabajador existe y esta activo";
		case SATrabajador.ERROR_SINTACTICO:
			return "Error sintactico";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textNombre.getText().isEmpty() && 
				this.textDireccion.getText().isEmpty() && 
				this.textNif.getText().isEmpty() && 
				this.textTlf.getText().isEmpty() && 
				this.textIdAlmacen.getText().isEmpty() && 
				this.textHoras.getText().isEmpty() && 
				this.textPrecioHora.getText().isEmpty();
	}
}
