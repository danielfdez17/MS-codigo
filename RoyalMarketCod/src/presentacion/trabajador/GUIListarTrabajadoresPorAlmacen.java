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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import negocio.trabajador.TTrabajador;
import negocio.trabajador.TTrabajadorJCompleta;
import negocio.trabajador.TTrabajadorJParcial;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;

@SuppressWarnings("serial")
public class GUIListarTrabajadoresPorAlmacen extends JFrame implements GUI {
	private static final String FROM_WHERE = "GUIListarTrabajadoresPorAlmacen";
	private static GUIListarTrabajadoresPorAlmacen instancia;
	private String headers[] = { "ID ", "Telefono", "Nif", "Nombre", "Direccion", "ID almacen", "Sueldo base", "Horas", "Precio hora"};
	
	private JTable table;
	private DefaultTableModel model;
	private JTextField textId;
	
	public GUIListarTrabajadoresPorAlmacen() {
		this.initGUI();
	}
	
	public synchronized static GUIListarTrabajadoresPorAlmacen getInstancia() {
		if (instancia == null) instancia = new GUIListarTrabajadoresPorAlmacen();
		return instancia;
	}
	
    private void initGUI() {
    	this.setTitle("Listar trabajadores");
    	
    	// MAIN PANEL
    	JPanel mainPanel = new JPanel(new BorderLayout());
    	this.setContentPane(mainPanel);
    	
		// TABLE MODEL
    	this.model = new DefaultTableModel() {
    		@Override
    		public boolean isCellEditable(int row, int col) { return false; }
    	};
    	this.model.setColumnCount(0);
    	for (String s : this.headers)
    		this.model.addColumn(s);
    	
    	// MAIN TABLE
    	this.table = new JTable(this.model);
    	
    	Panel panelAlmacen = new Panel("ID almacen");
    	this.textId = panelAlmacen.getJTextField();
    	mainPanel.add(panelAlmacen.getJPanel(), BorderLayout.CENTER);
    	
    	// LIST BUTTON
    	JButton button_listar = new JButton("Listar");
    	button_listar.addActionListener((e) -> {
    		if (!this.areTextFieldsEmpty()) {
    			try {
    				int id = Integer.parseInt(this.textId.getText());
					Controlador.getInstancia().accion(new Context(Eventos.LISTAR_TRABAJADORES_POR_ALMACEN, id));
					setVisible(true);
    			} catch (NumberFormatException nfe) {
    				this.clear();
    				GUIMSG.showMessage("El campo 'ID almacen' solo requiere de numeros", FROM_WHERE, true);
    			}
    		}
    		else {
    			GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
    		}
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
    private String toBold(String s) {
    	return "<html><b>" + s + "</b></html>";
    }

	@SuppressWarnings("unchecked")
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
	    case LISTAR_TRABAJADORES_OK:
	    	for (TTrabajador tt : (ArrayList<TTrabajador>)context.getDatos()) {
	    		String id = "" + tt.getId(), tlf = "" + tt.getTlf(), nif = tt.getNIF(), nombre = tt.getNombre(), 
    					direccion = tt.getDireccion(), id_almacen = "" + tt.getIdAlmacen(), sueldo_base = "", horas = "", precio_hora = "";
    			if (tt instanceof TTrabajadorJCompleta) {
    				sueldo_base += ((TTrabajadorJCompleta)tt).getSueldoBase();
    			}
    			else if (tt instanceof TTrabajadorJParcial){
    				horas += ((TTrabajadorJParcial)tt).getHoras();
    				precio_hora += ((TTrabajadorJParcial)tt).getPrecioHora();
    			}
    			if (tt.isActivo()) {
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
    case LISTAR_TRABAJADORES_KO:
    	 GUIMSG.showMessage(String.format("No hay trabajadores que listar", (int) context.getDatos()), FROM_WHERE, true);
        break;
        default:
        	break;
		}
	}

	@Override
	public void clear() {
		this.textId.setText("");
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
