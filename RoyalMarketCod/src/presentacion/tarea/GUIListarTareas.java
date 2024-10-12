package presentacion.tarea;

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

import negocio.tarea.TTarea;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

public class GUIListarTareas extends JFrame implements GUI {
	
	private static final long serialVersionUID = 1L;
	
	private static final String FROM_WHERE = "GUIListarTareas";
	private static final String headers[] = {"ID", "Nombre", "ID proyecto"};
	
	private JTable table;
	private DefaultTableModel model;
	private static GUIListarTareas instancia;
	
	public GUIListarTareas() {
		this.initGUI();
	}
	
	public static synchronized GUIListarTareas getInstancia() {
		if (instancia == null) instancia = new GUIListarTareas();
		return instancia;
	}
	
    private void initGUI() {
    	this.setTitle("Listar tareas");
    	
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
    		this.setVisible(false);
    		Controlador.getInstancia().accion(new Context(Eventos.LISTAR_TAREAS, null));
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
		case LISTAR_TAREAS_OK:
			this.model.setRowCount(0);
			for (TTarea tt : ((ArrayList<TTarea>)context.getDatos())) {
				String id = "" + tt.getId(), 
						nombre = tt.getNombre(), 
						idProyecto = "" + tt.getIdProyecto();
    			if (tt.isActivo()) {
    				id = this.toBold(id);
    				nombre = this.toBold(nombre);
    				idProyecto = this.toBold(idProyecto);
    				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    				renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
    				table.getColumnModel().getColumn(1).setCellRenderer(renderer);
    			}
    			this.model.addRow(new Object[] {id, nombre, idProyecto});
			}
			this.setVisible(true);
			GUIMSG.showMessage("Existen tareas", FROM_WHERE, false);
			break;
		case LISTAR_TAREAS_KO:
			GUIMSG.showMessage("No hay tareas", FROM_WHERE, true);
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
