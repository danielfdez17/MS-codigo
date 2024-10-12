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
import presentacion.viewhelper.GUIComprobar;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIActualizarClienteNormal extends JFrame implements GUI {
	
	private static final int FIELDS = 8;
	private static final String CLIENTE = "cliente";
	private static final String FROM_WHERE = "GUIActualizarClienteNormal";
	private static GUIActualizarClienteNormal instancia;
	private TClienteNormal tc;
	private GUIComprobar guiComprobar;
	private JTextField textId, textTlf, textNif, textNombre, textDireccion, textNormal;
	
	public GUIActualizarClienteNormal( ){
		this.guiComprobar = new GUIComprobar(CLIENTE);
	}

	public synchronized static GUIActualizarClienteNormal getInstancia() {
		if (instancia == null) instancia = new GUIActualizarClienteNormal();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Actualizar cliente Normal");
		
		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// CREATE PANEL
		JPanel panelActualizar = new JPanel(new GridLayout(FIELDS, 1));
		
		Panel panelId = new Panel("ID");
		this.textId = panelId.getJTextField();
		this.textId.setText("" + this.tc.getId());
		this.textId.setEditable(false);
		
		Panel panelTlf = new Panel("Telefono");
		this.textTlf = panelTlf.getJTextField();
		this.textTlf.setText(this.tc.getTlf() + "");
		panelActualizar.add(panelTlf.getJPanel());
		
		Panel panelNif = new Panel("NIF");
		this.textNif = panelNif.getJTextField();
		this.textNif.setText(this.tc.getNIF());
		panelActualizar.add(panelNif.getJPanel());
		
		Panel panelNombre = new Panel("Nombre");
		this.textNombre = panelNombre.getJTextField();
		this.textNombre.setText(this.tc.getNombre());
		panelActualizar.add(panelNombre.getJPanel());
		
		Panel panelDireccion = new Panel("Direccion");
		this.textDireccion = panelDireccion.getJTextField();
		this.textDireccion.setText(this.tc.getDireccion());
		panelActualizar.add(panelDireccion.getJPanel());
		
		Panel panelVIP = new Panel("ID Normal");
		this.textNormal = panelVIP.getJTextField();
		this.textNormal.setText("" + this.tc.getIdTarjetaNormal());
		panelActualizar.add(panelVIP.getJPanel());
		
		
		JButton button_actualizar = new JButton("Actualizar Normal");
		button_actualizar.addActionListener(l -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int id = Integer.parseInt(this.textId.getText());
					int tlf = Integer.parseInt(this.textTlf.getText());
					String nif = this.textNif.getText();
					String nombre = this.textNombre.getText();
					String direccion = this.textDireccion.getText();
					int idTarjetaNormal = Integer.parseInt(this.textNormal.getText());
					boolean activo = tc.isActivo();
					this.setVisible(false);
					this.clear();
					Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_CLIENTE_NORMAL, new TClienteNormal(id, idTarjetaNormal, tlf, nif, nombre, direccion, activo)));
				} catch(NumberFormatException nfe) {
					GUIMSG.showMessage("Los campos 'Telefono' e 'ID tarjeta Normal' requieren solo numeros", FROM_WHERE, true);
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
		
		JButton resetTextButton = new JButton("Restaurar valores");
		resetTextButton.addActionListener((e) -> {
			this.resetValues();
		});
		
		
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(emptyTextButton);
		buttonsPanel.add(resetTextButton);
		buttonsPanel.add(button_actualizar);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		mainPanel.add(panelActualizar, BorderLayout.CENTER);
		
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
    	this.textNormal.setText("");
    	this.textNif.setText("");
    	this.textTlf.setText("");
    }
    private void resetValues() {
    	this.textNombre.setText(this.tc.getNombre());
    	this.textDireccion.setText(this.tc.getDireccion());
    	this.textNormal.setText(this.tc.getIdTarjetaNormal() + "");
    	this.textNif.setText(this.tc.getNIF());
    	this.textTlf.setText(this.tc.getTlf() + "");
    }
	
	public void setCliente(TClienteNormal tc){
		this.tc = tc;
	}

	public void comprobar(boolean b) {
		this.guiComprobar.setVisible(b);
	}
	
	public void init(){
		this.initGUI();
	}
	
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case ACTUALIZAR_CLIENTE_NORMAL_OK:
			TClienteNormal normal = (TClienteNormal)context.getDatos();
			GUIMSG.showMessage("Cliente actualizado\n" + normal.toString(), FROM_WHERE, false);
			break;
		case ACTUALIZAR_CLIENTE_NORMAL_KO:
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
		case SACliente.ERROR_SINTACTICO:
			return "No se ha podido actualizar el cliente por error sintactico";
		case SACliente.ERROR_BBDD:
			return "No se ha podido actualizar el cliente por error en la BBDD";
		case SACliente.CLIENTE_INEXISTENTE:
			return "No se ha podido actualizar el cliente porque no existe";
		case SACliente.CAMBIO_TIPO:
			return "No se ha podido actualizar el cliente porque existe y es un cliente vip";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}
	@Override
	public boolean areTextFieldsEmpty() {
		return this.textNombre.getText().isEmpty() && this.textDireccion.getText().isEmpty() && this.textNif.getText().isEmpty()
				&& this.textTlf.getText().isEmpty() && this.textNormal.getText().isEmpty();
	}
}