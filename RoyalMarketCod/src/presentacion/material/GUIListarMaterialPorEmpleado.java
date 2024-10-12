package presentacion.material;

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

import negocio.material.TMaterial;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIListarMaterialPorEmpleado extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIListarMaterial";
	private static final String headers[] = {"ID", "Nombre", "ID empleado"};
	
	private static GUIListarMaterialPorEmpleado instancia;
	private JTable table;
	private DefaultTableModel model;
	private JTextField textIdEmpleado;
	
	public GUIListarMaterialPorEmpleado() {
		this.initGUI();
	}
	
	public static GUIListarMaterialPorEmpleado getInstancia() {
		if(instancia == null) instancia = new GUIListarMaterialPorEmpleado();
		return instancia;
	}

	private void initGUI() {
		this.setTitle("Listar materiales por empleado");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		this.model = new DefaultTableModel() {
			private static final long serialVersionUID = 1L;
			@Override
    		public boolean isCellEditable(int row, int col) { return false; }
		};
		
		this.model.setColumnCount(0);
		for(String s : headers)
			this.model.addColumn(s);
		
		this.table = new JTable(this.model);
		
		Panel idEmpleadoPanel = new Panel("ID empleado");
		this.textIdEmpleado = idEmpleadoPanel.getJTextField();
		mainPanel.add(idEmpleadoPanel.getJPanel(), BorderLayout.PAGE_START);
		
		JButton listarButton = new JButton("Listar");
		listarButton.addActionListener((e) -> {
			if(!this.areTextFieldsEmpty()) {
				try {
					int id = Integer.parseInt(this.textIdEmpleado.getText());
					Controlador.getInstancia().accion(new Context(Eventos.LISTAR_MATERIALES_POR_EMPLEADO, id));
					this.clear();
					this.setVisible(false);
				} catch(NumberFormatException nfe) {
					GUIMSG.showMessage("El campo 'ID empleado' solo requiere de numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage(Utils.FALTAN_CAMPOS_POR_RELLENAR, FROM_WHERE, true);
			}
		});
		
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(listarButton);
		
		mainPanel.add(new JScrollPane(this.table), BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
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
		case LISTAR_MATERIALES_POR_EMPLEADO_OK:
			this.model.setRowCount(0);
			for (TMaterial tmat : ((ArrayList<TMaterial>)context.getDatos())) {
				String id = "" + tmat.getId(), nombre = tmat.getNombre(), idEmpleado = "" + tmat.getIdEmpleado();
    			if (tmat.isActivo()) {
    				id = this.toBold(id);
    				nombre = this.toBold(nombre);
    				idEmpleado = this.toBold(idEmpleado);
    				
    				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    				renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
    				table.getColumnModel().getColumn(1).setCellRenderer(renderer);
    			}
    			this.model.addRow(new Object[] {id, nombre, idEmpleado});
			}
			
			this.setVisible(true);
			GUIMSG.showMessage("Existen materiales asociados al empleado", FROM_WHERE, false);
			break;
		case LISTAR_MATERIALES_POR_EMPLEADO_KO:
			GUIMSG.showMessage("No hay materiales asociados al empleado", FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
		
	}
	
	private String toBold(String s) {
    	return "<html><b>" + s + "</b></html>";
    }

	@Override
	public void clear() {
		this.textIdEmpleado.setText("");
		
	}

	@Override
	public String getErrorMsg(int error) {
		return null;
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textIdEmpleado.getText().isEmpty();
	}

}
