package presentacion.proveedor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import negocio.proveedor.TProveedor;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

public class GUIListarProveedoresPorProducto extends JFrame implements GUI {
	
	private static final long serialVersionUID = 1L;
	
	private static final String FROM_WHERE = "GUIListarProveedorePorProducto";
	private static final String headers[] = {"ID", "Telefono", "NIF", "Direccion"};
	
	private JTable table;
	private DefaultTableModel model;
	private static GUIListarProveedoresPorProducto instancia;
	private JTextField textId;
	
	public GUIListarProveedoresPorProducto() {
		this.initGUI();
	}
	
	public synchronized static GUIListarProveedoresPorProducto getInstancia() {
		if (instancia == null) instancia = new GUIListarProveedoresPorProducto();
		return instancia;
	}
	
    private void initGUI() {
    	this.setTitle("Listar proveedores por producto");
    	
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
    	
		// ID
		JPanel panelIdProveedor = new JPanel(new GridLayout(1, 2));
		JLabel labelIdProveedor = new JLabel("ID producto: ");
		this.textId = new JTextField(10);
		panelIdProveedor.add(labelIdProveedor);
		panelIdProveedor.add(this.textId);
		mainPanel.add(panelIdProveedor, BorderLayout.PAGE_START);
    	
    	// LIST BUTTON
    	JButton button_listar = new JButton("Listar");
    	button_listar.addActionListener((e) -> {
    		if (!this.areTextFieldsEmpty()) {
    			try {
    				int id = Integer.parseInt(this.textId.getText());
    				this.setVisible(false);
    				Controlador.getInstancia().accion(new Context(Eventos.LISTAR_PROVEEDORES_POR_PRODUCTO, id));
    			} catch (NumberFormatException nfe) {
    				GUIMSG.showMessage("El campo 'ID producto' solo requiere de numeros", FROM_WHERE, true);
    			}
    		}
    		else {
    			GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
    		}
    	});
		// BUTTONS PANEL
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(button_listar);
		
		mainPanel.add(panelIdProveedor, BorderLayout.PAGE_START);
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
		case LISTAR_PROVEEDORES_POR_PRODUCTO_OK:
			this.model.setRowCount(0);
			for (TProveedor tp : (ArrayList<TProveedor>)context.getDatos()) {
    			String id = "" + tp.getId(), tlf = "" + tp.getTlf(), nif = tp.getNif(), direccion = tp.getDireccion();
    			if (tp.isActivo()) {
    				id = this.toBold(id);
    				tlf = this.toBold(tlf);
    				nif = this.toBold(nif);
    				direccion = this.toBold(direccion);
    				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    				renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
    				table.getColumnModel().getColumn(1).setCellRenderer(renderer);
    			}
    			this.model.addRow(new Object[] {id, tlf, nif, direccion});
			}
			this.setVisible(true);
			GUIMSG.showMessage("Existen proveedores asociados al producto", FROM_WHERE, false);
			break;
		case LISTAR_PROVEEDORES_POR_PRODUCTO_KO:
			GUIMSG.showMessage("No hay proveedores asociados al producto", FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
		
	}

	@Override
	public void clear() {
		this.textId.setText("");
	}

	private String toBold(String s) {
    	return "<html><b>" + s + "</b></html>";
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
