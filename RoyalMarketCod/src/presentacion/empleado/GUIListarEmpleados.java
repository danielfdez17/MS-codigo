package presentacion.empleado;

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

import negocio.empleado.TEmpleado;
import negocio.empleado.TEmpleadoAdministrador;
import negocio.empleado.TEmpleadoTecnico;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

public class GUIListarEmpleados extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIListarEmpleados";
	private static final String HEADERS[] = {"ID", "Telefono", "NIF", "Nombre",
			"Direccion", "Reporte trabajo", "Reporte semanal", "Sueldo", "ID oficina"};
	
	private static GUIListarEmpleados instancia;
	private JTable table;
	private DefaultTableModel model;
	
	public GUIListarEmpleados() {
		this.initGUI();
	}
	
	public synchronized static GUIListarEmpleados getInstancia() {
		if (instancia == null) instancia = new GUIListarEmpleados();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Listar empleados");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		this.model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int col) {return false;}
		};
		this.model.setRowCount(0);
		for (String s : HEADERS) this.model.addColumn(s);
		
		this.table = new JTable(model);
		
		JButton buttonListar = new JButton("Listar");
		buttonListar.addActionListener((e) -> {
			this.setVisible(false);
			Controlador.getInstancia().accion(new Context(Eventos.LISTAR_EMPLEADOS, null));
		});
		
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(buttonListar);
		
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

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case LISTAR_EMPLEADOS_OK:
			this.model.setRowCount(0);
			for (TEmpleado e : ((ArrayList<TEmpleado>)context.getDatos())) {
				String id = "" + e.getId(),
						tlf = "" + e.getTlf(),
						nif = e.getNif(),
						nombre = e.getNombre(),
						direccion = e.getDireccion(),
						reporteTrabajo = "",
						reporteSemanal = "",
						sueldo = "" + e.getSueldo(),
						idOficina = "" + e.getIdOficina();
				if (e instanceof TEmpleadoAdministrador) 
					reporteSemanal = ((TEmpleadoAdministrador)e).getReporteSemanal();
				else 
					reporteTrabajo = ((TEmpleadoTecnico)e).getReporteTrabajo();
				if (e.isActivo()) {
					id = this.toBold(id);
					tlf = this.toBold(tlf);
					nif = this.toBold(nif);
					nombre = this.toBold(nombre);
					direccion = this.toBold(direccion);
					reporteTrabajo = this.toBold(reporteTrabajo);
					reporteSemanal = this.toBold(reporteSemanal);
					sueldo = this.toBold(sueldo);
					idOficina = this.toBold(idOficina);
    				DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    				renderer.setFont(renderer.getFont().deriveFont(Font.BOLD));
    				table.getColumnModel().getColumn(1).setCellRenderer(renderer);
				}
				this.model.addRow(new Object[] {id, tlf, nif, nombre, direccion, reporteTrabajo,
						reporteSemanal, sueldo, idOficina});
			}
			break;
		case LISTAR_EMPLEADOS_KO:
			this.model.setRowCount(0);
			GUIMSG.showMessage("No existen empleados", FROM_WHERE, true);
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
	public void clear() {}

	@Override
	public String getErrorMsg(int error) {
		return "";
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return false;
	}

}
