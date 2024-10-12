package presentacion.trabajador;

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

import negocio.trabajador.TTrabajadorJCompleta;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;

@SuppressWarnings("serial")
public class GUIListarTrabajadoresJCompleta extends JFrame implements GUI {
	
	private static final String headers[] = { "ID ", "Telefono", "Nif", "Nombre", "Direccion", "ID almacen", "Sueldo base", "Horas", "Precio hora"};
	private String title;
	private static GUIListarTrabajadoresJCompleta instancia;
	private JTable table;
	private DefaultTableModel model;
	private static final String FROM_WHERE = "GUIListarTrabajadoresJCompleta";
	
	public GUIListarTrabajadoresJCompleta() {
		this.initGUI();
	}
	
    public synchronized static GUIListarTrabajadoresJCompleta getInstancia() {
    	if (instancia == null) instancia = new GUIListarTrabajadoresJCompleta();
		return instancia;
	}

	private void initGUI() {
    	this.setTitle(this.title);
    	
    	// MAIN PANEL
    	JPanel mainPanel = new JPanel(new BorderLayout());
    	this.setContentPane(mainPanel);
    	
		// TABLE MODEL
    	this.model = new DefaultTableModel() {
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
    		Controlador.getInstancia().accion(new Context(Eventos.LISTAR_TRABAJADORES_J_COMPLETA, null));
    	});
		// BUTTONS PANEL
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(button_listar);
		
		mainPanel.add(new JScrollPane(this.table), BorderLayout.CENTER);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
    }
    
    private String toBold(String s) {
    	return "<html><b>" + s + "</b></html>";
    }

	@SuppressWarnings("unchecked")
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case LISTAR_TRABAJADORES_J_COMPLETA_OK:
	    	for (TTrabajadorJCompleta o : (ArrayList<TTrabajadorJCompleta>)context.getDatos()) {
    			String id = "" + o.getId(), tlf = "" + o.getTlf(), nif = o.getNIF(), nombre = o.getNombre(), 
    					direccion = o.getDireccion(), id_almacen = "" + o.getIdAlmacen(), sueldo_base = "" + o.getSueldoBase(), horas = "", precio_hora = "";
    			if (o.isActivo()) {
    				id = this.toBold(id);
    				tlf = this.toBold(tlf);
    				nif = this.toBold(nif);
    				nombre = this.toBold(nombre);
    				direccion = this.toBold(direccion);
    				id_almacen = this.toBold(id_almacen);
    				sueldo_base = this.toBold(sueldo_base);
    				horas = this.toBold(horas);
    				precio_hora = this.toBold(precio_hora);
    				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    				renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
    				table.getColumnModel().getColumn(1).setCellRenderer(renderer);
    			}
    			this.model.addRow(new Object[] {id, tlf, nif, nombre, direccion, id_almacen, sueldo_base, horas, precio_hora});
    	}
			break;
		case LISTAR_TRABAJADORES_J_COMPLETA_KO:
			GUIMSG.showMessage("No existen trabajadores a jornada completa", FROM_WHERE, true);
			break;
		default:
			break;
		}
	}

	@Override
	public void clear() {
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
