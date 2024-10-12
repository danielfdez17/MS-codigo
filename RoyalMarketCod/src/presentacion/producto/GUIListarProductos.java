package presentacion.producto;

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

import negocio.producto.TProducto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

public class GUIListarProductos extends JFrame implements GUI {
	
	private static final long serialVersionUID = 1L;
	
	private static final String FROM_WHERE = "GUIListarProductos";
	private static final String headers[] = {"ID", "Nombre", "Precio", "Stock", "ID almacen"};
	private static final String TITLE = Utils.format(Utils.LISTAR, Utils.ALMACENES);
	
	private JTable table;
	private DefaultTableModel model;
	private static GUIListarProductos instancia;
	
	public GUIListarProductos() {
		this.initGUI();
	}
	
	public static GUIListarProductos getInstancia() {
		if (instancia == null) instancia = new GUIListarProductos();
		return instancia;
	}
	
    private void initGUI() {
    	this.setTitle(TITLE);
    	
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
    		Controlador.getInstancia().accion(new Context(Eventos.LISTAR_PRODUCTOS, null));
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
		case LISTAR_PRODUCTOS_OK:
			this.model.setRowCount(0);
			for (TProducto tp : ((ArrayList<TProducto>)context.getDatos())) {
    			String id = "" + tp.getId(), nombre = tp.getNombre(), precio = "" + tp.getPrecio(), stock = "" + tp.getStock(),
    					id_almacen = "" + tp.getIdAlmacen();
    			if (tp.isActivo()) {
    				id = this.toBold(id);
    				nombre = this.toBold(nombre);
    				precio = this.toBold(precio);
    				stock = this.toBold(stock);
    				id_almacen = this.toBold(id_almacen);
    				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    				renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
    				table.getColumnModel().getColumn(1).setCellRenderer(renderer);
    			}
    			this.model.addRow(new Object[] {id, nombre, precio, stock, id_almacen});
			}
			this.setVisible(true);
			GUIMSG.showMessage("Existen productos", FROM_WHERE, false);
			break;
		case LISTAR_PRODUCTOS_KO:
			this.setVisible(false);
			GUIMSG.showMessage("No hay productos", FROM_WHERE, true);
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
