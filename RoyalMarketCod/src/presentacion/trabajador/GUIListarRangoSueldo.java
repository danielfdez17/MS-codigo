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
public class GUIListarRangoSueldo extends JFrame implements GUI {
	
	private String headers[] = { "ID ", "Telefono", "Nif", "Nombre", "Direccion", "ID almacen", "Sueldo base", "Horas", "Precio hora"};
	private static final String FROM_WHERE = "GUIListarRangoSueldo";
	private JTable table;
	private DefaultTableModel model;
	private static GUIListarRangoSueldo instancia;
	private JTextField textMinimo, textMaximo;
	
	public GUIListarRangoSueldo() {
		this.initGUI();
	}
	
	public synchronized static GUIListarRangoSueldo getInstancia() {
		if (instancia == null) instancia = new GUIListarRangoSueldo();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Listar trabajadores por rango de sueldo");
		
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
		
		Panel panelMinimo = new Panel("Rango minimo");
		this.textMinimo = panelMinimo.getJTextField();
		
		Panel panelMaximo = new Panel("Rango maximo");
		this.textMaximo = panelMaximo.getJTextField();
		
		JPanel fieldsPanel = new JPanel(new GridLayout(1, 2));
		fieldsPanel.add(panelMaximo.getJPanel());
		fieldsPanel.add(panelMinimo.getJPanel());
		
		// LIST BUTTON
		JButton button_listar = new JButton("Listar");
		button_listar.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					double minimo = Double.parseDouble(this.textMinimo.getText());
					double maximo = Double.parseDouble(this.textMaximo.getText());
					ArrayList<Double> lista = new ArrayList<>();
					lista.add(minimo);
					lista.add(maximo);
					Controlador.getInstancia().accion(new Context(Eventos.LISTAR_TRABAJADORES_RANGO_SUELDO, lista));
					this.setVisible(true);
				}
				catch (NumberFormatException nfe) {
					GUIMSG.showMessage("Los campos 'Rango maximo' y 'Rango minimo' solo requieren de numeros", "Listar trabajadores por rango de sueldo", true);
				}
			}
			else {
				GUIMSG.showMessage("Faltan campos por rellenar", "Listar trabajadores por rango de sueldo", true);
			}
		});
		// BUTTONS PANEL
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(button_listar);
		
		mainPanel.add(fieldsPanel, BorderLayout.PAGE_START);
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
		case LISTAR_TRABAJADORES_RANGO_SUELDO_OK:
			for (TTrabajador tt : (ArrayList<TTrabajador>)context.getDatos()) {
				String id = "" + tt.getId(), tlf = "" + tt.getTlf(), nif = tt.getNIF(), nombre = tt.getNombre(), 
						direccion = tt.getDireccion(), id_almacen = "" + tt.getIdAlmacen(), sueldo_base = "", horas = "", precio_hora = "";
				if (tt instanceof TTrabajadorJCompleta) {
					sueldo_base += ((TTrabajadorJCompleta)tt).getSueldoBase();
				}
				else {
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
		case LISTAR_TRABAJADORES_RANGO_SUELDO_KO:
			GUIMSG.showMessage("No hay trabajadores que cobren en el rango [" + this.textMinimo + "," + this.textMaximo + "]", FROM_WHERE, true);
			break;
		default:
			break;
		}
	}
	@Override
	public void clear() {
		this.textMaximo.setText("");
		this.textMaximo.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		return null;
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textMinimo.getText().isEmpty() && 
				this.textMaximo.getText().isEmpty();
	}
}
