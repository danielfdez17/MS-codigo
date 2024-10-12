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
import negocio.trabajador.TTrabajadorJCompleta;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.GUIComprobar;
import presentacion.viewhelper.Panel;
import utilities.Utils;


@SuppressWarnings("serial")
public class GUIActualizarTrabajadorJCompleta extends JFrame implements GUI {
	
	private static final String TRABAJADOR = "trabajador";
	private static final String FROM_WHERE = "GUIActualizarTrabajadorJCompleta";
	private static final int BUTTONS = 7;
	private static GUIActualizarTrabajadorJCompleta instancia;
	
	private TTrabajadorJCompleta ttjc;
	private GUIComprobar guiComprobar;
	private JTextField textId;
	private JTextField textTlf;
	private JTextField textNif;
	private JTextField textNombre;
	private JTextField textDireccion;
	private JTextField textIdAlmacen;
	private JTextField textSueldoBase;
	
	public GUIActualizarTrabajadorJCompleta() {
		this.guiComprobar = new GUIComprobar(TRABAJADOR);
	}
	
	public static synchronized GUIActualizarTrabajadorJCompleta getInstancia() {
		if (instancia == null) instancia = new GUIActualizarTrabajadorJCompleta();
		return instancia;
	}

	private void initGUI() {
		this.setTitle("Actualizar trabajador a jornada completa");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel panelActualizar = new JPanel(new GridLayout(BUTTONS, 1));
		
		Panel panelId = new Panel("ID");
		this.textId.setText("" + this.ttjc.getId());
		this.textId.setEditable(false);
		panelActualizar.add(panelId.getJPanel());
		
		Panel panelTlf = new Panel("Telefono");
		this.textTlf = panelTlf.getJTextField();
		this.textTlf.setText("" + this.ttjc.getTlf());
		panelActualizar.add(panelTlf.getJPanel());
		
		Panel panelNif = new Panel("NIF");
		this.textNif = panelNif.getJTextField();
		this.textNif.setText(this.ttjc.getNIF());
		panelActualizar.add(panelNif.getJPanel());
		
		Panel panelNombre = new Panel("Nombre");
		this.textNombre = panelNombre.getJTextField();
		this.textNombre.setText(this.ttjc.getNombre());
		panelActualizar.add(panelNombre.getJPanel());
		
		Panel panelDireccion = new Panel("Direccion");
		this.textDireccion = panelDireccion.getJTextField();
		this.textDireccion.setText(this.ttjc.getDireccion());
		panelActualizar.add(panelDireccion.getJPanel());

		Panel panelTarjeta = new Panel("ID almacen");
		this.textIdAlmacen = panelTarjeta.getJTextField();
		this.textIdAlmacen.setText("" + this.ttjc.getIdAlmacen());
		panelActualizar.add(panelTarjeta.getJPanel());
		
		Panel panelSueldoBase = new Panel("Precio hora");
		this.textSueldoBase = panelSueldoBase.getJTextField();
		this.textSueldoBase.setText("" + this.ttjc.getSueldoBase());
		panelActualizar.add(panelSueldoBase.getJPanel());
		
		JButton buttonActualizar = new JButton("Actualizar");
		buttonActualizar.addActionListener(e -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int id = Integer.parseInt(this.textId.getText());
					int tlf = Integer.parseInt(this.textTlf.getText());
					String nif = this.textNif.getText();
					String nombre = this.textNombre.getText();
					String direccion = this.textDireccion.getText();
					int idAlmacen = Integer.parseInt(this.textIdAlmacen.getText());
					double sueldoBase = Double.parseDouble(this.textSueldoBase.getText());
					boolean activo = this.ttjc.isActivo();
					this.clear();
					this.setVisible(false);
					Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_TRABAJADOR_J_COMPLETA, new TTrabajadorJCompleta(id, tlf, nif, nombre, direccion, idAlmacen, sueldoBase, activo)));
					
				} catch(NumberFormatException nfe) {
					GUIMSG.showMessage("Los campos 'Telefono' e 'ID tarjeta normal' requieren solo numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
			}
		});
		
		JButton buttonRecuperarDatos = new JButton("Recuperar datos");
		buttonRecuperarDatos.addActionListener(e -> {
			this.textTlf.setText("" + this.ttjc.getTlf());
			this.textNif.setText(this.ttjc.getNIF());
			this.textNombre.setText(this.ttjc.getNombre());
			this.textDireccion.setText(this.ttjc.getDireccion());
			this.textIdAlmacen.setText("" + this.ttjc.getIdAlmacen());
			this.textSueldoBase.setText("" + this.ttjc.getSueldoBase());
		});
		
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(buttonActualizar);
		buttonsPanel.add(buttonRecuperarDatos);
		
		mainPanel.add(panelActualizar, BorderLayout.CENTER);
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
	public void setttjc(TTrabajadorJCompleta ttjc) {
		this.ttjc = ttjc;
	}
	public void comprobar(boolean b) {
		this.guiComprobar.setVisible(true);
	}
	public void init() {
		this.initGUI();
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
	    case ACTUALIZAR_TRABAJADOR_J_COMPLETA_KO:
	        GUIMSG.showMessage("Trabajador Actualizado", FROM_WHERE, false);
	        break;
	    case ACTUALIZAR_TRABAJADOR_J_COMPLETA_OK:
	    	GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
	        break;
		default:
			break;
		}
	}

	@Override
	public void clear() {
		this.textId.setText("");
		this.textDireccion.setText("");
		this.textNif.setText("");
		this.textIdAlmacen.setText("");
		this.textSueldoBase.setText("");
		this.textTlf.setText("");
		this.textIdAlmacen.setText("");
	}
	
	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SATrabajador.TRABAJADOR_INEXISTENTE:
			return "El trabajador no existe";
		case SATrabajador.CAMBIO_TIPO_TRABAJADOR:
			return "El trabajador existe y tiene contrato a jornada parcial";
		case SATrabajador.ERROR_BBDD:
			return "Error en la BBDD";
		case SATrabajador.ERROR_SINTACTICO:
			return "Error sintactico";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty() 
				&& this.textNombre.getText().isEmpty() 
				&& this.textDireccion.getText().isEmpty() 
				&& this.textNif.getText().isEmpty()
				&& this.textTlf.getText().isEmpty() 
				&& this.textIdAlmacen.getText().isEmpty() 
				&& this.textSueldoBase.getText().isEmpty();
	}
}