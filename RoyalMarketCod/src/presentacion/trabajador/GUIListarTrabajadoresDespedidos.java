package presentacion.trabajador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import negocio.trabajador.TTrabajador;
import negocio.trabajador.TTrabajadorJCompleta;
import negocio.trabajador.TTrabajadorJParcial;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;

@SuppressWarnings("serial")
public class GUIListarTrabajadoresDespedidos extends JFrame implements GUI {
	
	private static final String headers[] = { "ID ", "Telefono", "Nif", "Nombre", "Direccion", "ID almacen", "Sueldo base", "Horas", "Precio hora"};
	private String title;
	private static GUIListarTrabajadoresDespedidos instancia;
	private JTable table;
	private DefaultTableModel model;
	private static final String FROM_WHERE = "GUIListarTrabajadoresDespedidos";
	
	public GUIListarTrabajadoresDespedidos() {
		this.initGUI();
	}
	
    public synchronized static GUIListarTrabajadoresDespedidos getInstancia() {
    	if (instancia == null) instancia = new GUIListarTrabajadoresDespedidos();
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
    		Controlador.getInstancia().accion(new Context(Eventos.LISTAR_TRABAJADORES_DESPEDIDOS, null));
    		this.setVisible(true);
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
    
    public void updateLista(ArrayList<TTrabajador> lista) {
    	this.model.setRowCount(0);
    	for (TTrabajador o : lista) {
    			TTrabajador tt = (TTrabajador)o;
    			String id = "" + tt.getId(), tlf = "" + tt.getTlf(), nif = tt.getNIF(), nombre = tt.getNombre(), 
    					direccion = tt.getDireccion(), id_almacen = "" + tt.getIdAlmacen(), sueldo_base = "", horas = "", precio_hora = "";
    			if (tt instanceof TTrabajadorJCompleta) {
    				sueldo_base += ((TTrabajadorJCompleta)tt).getSueldoBase();
    			}
    			else {
    				horas += ((TTrabajadorJParcial)tt).getHoras();
    				precio_hora += ((TTrabajadorJParcial)tt).getPrecioHora();
    			}
    			this.model.addRow(new Object[] {id, tlf, nif, nombre, direccion, id_almacen, sueldo_base, horas, precio_hora});
    	}
    }

	@SuppressWarnings("unchecked")
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case LISTAR_TRABAJADORES_DESPEDIDOS_OK:
			for (TTrabajador o : (ArrayList<TTrabajador>)context.getDatos()) {
    			TTrabajador tt = (TTrabajador)o;
    			String id = "" + tt.getId(), tlf = "" + tt.getTlf(), nif = tt.getNIF(), nombre = tt.getNombre(), 
    					direccion = tt.getDireccion(), id_almacen = "" + tt.getIdAlmacen(), sueldo_base = "", horas = "", precio_hora = "";
    			if (tt instanceof TTrabajadorJCompleta) {
    				sueldo_base += ((TTrabajadorJCompleta)tt).getSueldoBase();
    			}
    			else {
    				horas += ((TTrabajadorJParcial)tt).getHoras();
    				precio_hora += ((TTrabajadorJParcial)tt).getPrecioHora();
    			}
    			this.model.addRow(new Object[] {id, tlf, nif, nombre, direccion, id_almacen, sueldo_base, horas, precio_hora});
    	}
			break;
		case LISTAR_TRABAJADORES_DESPEDIDOS_KO:
			GUIMSG.showMessage("No existen trabajadores despedidos", FROM_WHERE, true);
			break;
		default:
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
		// TODO Auto-generated method stub
		return false;
	}
}
