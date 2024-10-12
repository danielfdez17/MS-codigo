package presentacion.oficina;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import negocio.oficina.TOficina;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIListarOficinas extends JFrame implements GUI{
	private static final String FROM_WHERE = "GUIListarOficinas";
	private static final String headers[] = { "ID", "Nombre"};
	
	private JTable table;
	private DefaultTableModel model;
	private static GUIListarOficinas instancia;
	
	public GUIListarOficinas() {
		this.initGUI();
	}
	
	public static synchronized GUIListarOficinas getInstancia() {
		if (instancia == null) instancia = new GUIListarOficinas();
		return instancia;
	}
	
	private void initGUI() {
		// TODO Auto-generated method stub
		this.setTitle("Listar oficinas");
    	
    	// MAIN PANEL
    	JPanel mainPanel = new JPanel(new BorderLayout());
    	this.setContentPane(mainPanel);
    	
    	
    	// TABLE MODELl
    	this.model = new DefaultTableModel() {
    		@Override
    		public boolean isCellEditable(int row, int col) { return false; }
    	};
    	this.model.setColumnCount(0);
    	for (int i = 0; i < headers.length; i++) {
    		this.model.addColumn(headers[i]);
    	}
    	
    	// MAIN TABLE
    	this.table = new JTable(this.model);
    	
    	// LIST BUTTON
    	JButton listButton = new JButton("Listar");
    	listButton.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.LISTAR_OFICINAS, null));
    	});
    	
    	mainPanel.add(listButton, BorderLayout.PAGE_END);
    	mainPanel.add(new JScrollPane(this.table), BorderLayout.CENTER);
    	
        this.pack();
        this.setVisible(false);
        this.setPreferredSize(new Dimension(300, 300));
        this.setBounds(200, 200, 300, 300);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.table.setShowGrid(false);
        this.table.setBackground(Color.decode("0xEEEEEE"));
		
	}
	 public void updateLista(ArrayList<TOficina> res) {
	        model.setRowCount(0);

	        for (int i = 0; i < res.size(); i++) {

	        	TOficina oficina = res.get(i);
	            if(oficina.isActivo()){
	                int id = oficina.getId();
	                String nombre = oficina.getNombre();
	                Object[] row = new Object[]{id, "</b></html>" + nombre + "</b></html>"};
	                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
	                renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
	                table.getColumnModel().getColumn(1).setCellRenderer(renderer);
	                model.addRow(row);
	            }
	            else
	            {
	                int id = oficina.getId();
	                String nombre = oficina.getNombre();
	                Object[] row = new Object[]{id, nombre};
	                model.addRow(row);
	            }
	        }
	    }
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case LISTAR_OFICINAS_OK:
			this.model.setRowCount(0);
			for (TOficina to : (ArrayList<TOficina>)context.getDatos()) {
				String id = "" + to.getId(),
						nombre = to.getNombre();
				if (to.isActivo()) {
					id = this.toBold(id);
					nombre = this.toBold(nombre);
    				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    				renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
    				table.getColumnModel().getColumn(1).setCellRenderer(renderer);
				}
				this.model.addRow(new Object[] {id, nombre});
			}
			break;
		case LISTAR_OFICINAS_KO:
			this.model.setRowCount(0);
			GUIMSG.showMessage("No existen oficinas", FROM_WHERE, true);
			this.setVisible(false);
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
		return "";
	}
	@Override
	public boolean areTextFieldsEmpty() {
		return false;
	}

	private String toBold(String s) {
    	return "<html><b>" + s + "</b></html>";
    }

}