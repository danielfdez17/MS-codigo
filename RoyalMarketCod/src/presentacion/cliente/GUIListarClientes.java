package presentacion.cliente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import negocio.cliente.TCliente;
import negocio.cliente.TClienteNormal;
import negocio.cliente.TClienteVIP;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIListarClientes extends JFrame implements GUI {

	private static final String FROM_WHERE = "GUIListarClientes";
	private static final String headers[] = {"ID", "Telefono", "NIF", "Nombre", "Direccion", "ID tarjeta VIP", "ID tarjeta normal"};
	
	private JTable table;
	private DefaultTableModel model;
	private static GUIListarClientes instancia;
	
	public GUIListarClientes() {
		this.initGUI();
	}
	
	public synchronized static GUIListarClientes getInstancia() {
		if (instancia == null) instancia = new GUIListarClientes();
		return instancia;
	}
	
    private void initGUI() {
    	this.setTitle("Listar clientes");
    	
    	// MAIN PANEL
    	JPanel mainPanel = new JPanel(new BorderLayout());
    	this.setContentPane(mainPanel);
    	
		// TABLE MODEL
    	this.model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
    		public boolean isCellEditable(int row, int col) { return false; }
    	};
    	this.model.setColumnCount(0);
    	for (String s : headers)
    		this.model.addColumn(s);
    	
    	// MAIN TABLE
    	this.table = new JTable(this.model);
    	
    	// LIST BUTTON
    	JButton button_listar = new JButton("Listar");
    	button_listar.addActionListener((e) -> {
    		Controlador.getInstancia().accion(new Context(Eventos.LISTAR_CLIENTES, null));
    		this.setVisible(true);
    	});
		// BUTTONS PANEL
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(button_listar);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		mainPanel.add(new JScrollPane(this.table), BorderLayout.CENTER);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
    }

	@SuppressWarnings("unchecked")
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case LISTAR_CLIENTES_OK:
			this.model.setRowCount(0);
			for (TCliente tc : ((ArrayList<TCliente>)context.getDatos())) {
    			String id = "" + tc.getId(), tlf = "" + tc.getTlf(), nif = tc.getNIF(), nombre = tc.getNombre(), direccion = tc.getDireccion(),
    					id_tarjeta_vip = "", id_tarjeta_normal = "";
    			if (tc instanceof TClienteVIP) {
    				id_tarjeta_vip += ((TClienteVIP)tc).getIdTarjetaVIP();
    			}
    			else {
    				id_tarjeta_normal += ((TClienteNormal)tc).getIdTarjetaNormal();
    			}
    			if (tc.isActivo()) {
    				id = this.toBold(id);
    				tlf = this.toBold(tlf);
    				nif = this.toBold(nif);
    				nombre = this.toBold(nombre);
    				direccion = this.toBold(direccion);
    				id_tarjeta_vip = this.toBold(id_tarjeta_vip);
    				id_tarjeta_normal = this.toBold(id_tarjeta_normal);
    				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    				renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
    				table.getColumnModel().getColumn(1).setCellRenderer(renderer);
    			}
    			this.model.addRow(new Object[] {id, tlf, nif, nombre, direccion, id_tarjeta_vip, id_tarjeta_normal});
			}
			this.setVisible(true);
			GUIMSG.showMessage("Existen clientes", FROM_WHERE, false);
			break;
		case LISTAR_CLIENTES_KO:
			this.model.setRowCount(0);
			GUIMSG.showMessage("No hay clientes", FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
		
	}

	@Override
	public void clear() {}

	private String toBold(String s) {
    	return "<html><b>" + s + "</b></html>";
    }

	@Override
	public String getErrorMsg(int error) {
		return null;
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return false;
	}

}
