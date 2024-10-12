package presentacion.factura;

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

import negocio.factura.TFactura;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIListarFacturas extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIListarFacturas";
	private static final String headers[] = { "ID", "Fecha", "ID cliente", "Coste total"};
	private static GUIListarFacturas instancia;
	
	private JTable table;
	private DefaultTableModel model;
	
	public GUIListarFacturas() {
		this.initGUI();
	}
	
	public static GUIListarFacturas getInstancia() {
		if (instancia == null) instancia = new GUIListarFacturas();
		return instancia;
	}
	
    private void initGUI() {
    	this.setTitle("Listar facturas");
    	
    	// MAIN PANEL
    	JPanel mainPanel = new JPanel(new BorderLayout());
    	this.setContentPane(mainPanel);
    	
    	// TABLE MODEL
    	this.model = new DefaultTableModel() {
    		@Override
    		public boolean isCellEditable(int row, int col) { return false; }
    	};
    	this.model.setColumnCount(0);
    	for (String s :headers) this.model.addColumn(s);
    	
    	// MAIN TABLE
    	this.table = new JTable(this.model);
    	
    	// LIST BUTTON
    	JButton button_listar = new JButton("Listar");
    	button_listar.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.LISTAR_FACTURAS, null));
			setVisible(true);
    	});
		
		// BUTTONS PANEL
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 1));
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
		this.setVisible(false);
    }
    private String toBold(String s) {
    	return "<html><b>" + s + "</b></html>";
    }

	@SuppressWarnings("unchecked")
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case LISTAR_FACTURAS_OK:
			GUIMSG.showMessage("Listado de facturas", FROM_WHERE, false);
	        this.model.setRowCount(0);
	        for (TFactura factura : (ArrayList<TFactura>)context.getDatos()) {
	        	String id = "" + factura.getId(), fecha = factura.getFecha().toString(), id_cliente = "" + factura.getIdCliente(), coste_total = "" + factura.getCosteTotal();
	        	if (factura.isActivo()) {
	        		id = this.toBold(id);
	        		fecha = this.toBold(fecha);
	        		id_cliente = this.toBold(id_cliente);
	        		coste_total = this.toBold(coste_total);
	        		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	        		renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
	        		table.getColumnModel().getColumn(1).setCellRenderer(renderer);
	        	}
	        	this.model.addRow(new Object[] {id, fecha, id_cliente, coste_total});
	        }
			break;
		case LISTAR_FACTURAS_KO:
			GUIMSG.showMessage("No hay facturas que listar", FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {}

	@Override
	public String getErrorMsg(int error) {
		return null;
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return false;
	}
}