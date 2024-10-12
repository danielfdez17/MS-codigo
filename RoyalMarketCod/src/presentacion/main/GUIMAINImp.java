package presentacion.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public class GUIMAINImp extends GUIMAIN {
	
	private static final int MODULOS = 6;
	private static final int DIM = 250;
	private static final String ALMACENES = "Almacenes";
	private static final String CLIENTES = "Clientes";
	private static final String FACTURAS = "Facturas";
	private static final String PRODUCTOS = "Productos";
	private static final String PROVEEDORES = "Proveedores";
	private static final String TRABAJADORES = "Trabajadores";
	private static final String CURSOS = "Cursos";
	private static final String EMPLEADOS = "Empleados";
	private static final String MATERIALES = "Materiales";
	private static final String OFICINAS = "Oficinas";
	private static final String PROYECTOS = "Proyectos";
	private static final String TAREAS = "Tareas";
	
	public GUIMAINImp() {
		super();
		this.initGUI();
	}
	
	private void initGUI() {
		this.setTitle("ROYAL MARKET");
		// Panel principal
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(new JLabel("ROYAL MARKET"), BorderLayout.PAGE_START);
		this.setContentPane(mainPanel);
		
		// Panel de botones
		JPanel buttonsPanelDAO = new JPanel(new GridLayout(MODULOS, 1));
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		
		// Boton almacenes
		JButton almacenes = new JButton(ALMACENES);
		almacenes.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_ALMACEN, null));
		});
		almacenes.setMinimumSize(new Dimension(DIM, DIM));
		almacenes.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelDAO.add(almacenes);
		
		// Boton clientes
		JButton clientes = new JButton(CLIENTES);
		clientes.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_CLIENTE, null));
		});
		clientes.setMinimumSize(new Dimension(DIM, DIM));
		clientes.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelDAO.add(clientes);
		
		// Boton facturas
		JButton facturas = new JButton(FACTURAS);
		facturas.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_FACTURA, null));
		});
		facturas.setMinimumSize(new Dimension(DIM, DIM));
		facturas.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelDAO.add(facturas);
		
		// Boton productos
		JButton productos = new JButton(PRODUCTOS);
		productos.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_PRODUCTO, null));
		});
		productos.setMinimumSize(new Dimension(DIM, DIM));
		productos.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelDAO.add(productos);
		
		// Boton proveedores
		JButton proveedores = new JButton(PROVEEDORES);
		proveedores.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_PROVEEDOR, null));
		});
		proveedores.setMinimumSize(new Dimension(DIM, DIM));
		proveedores.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelDAO.add(proveedores);
		
		// Boton trabajadores
		JButton trabajadores = new JButton(TRABAJADORES);
		trabajadores.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_TRABAJADOR, null));
		});
		trabajadores.setMinimumSize(new Dimension(DIM, DIM));
		trabajadores.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelDAO.add(trabajadores);
		
		buttonsPanel.add(buttonsPanelDAO);
		
		
		JPanel buttonsPanelJPA = new JPanel(new GridLayout(MODULOS, 1));
		mainPanel.add(buttonsPanelJPA, BorderLayout.CENTER);
		
		JButton cursos = new JButton(CURSOS);
		cursos.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_CURSO, null));
		});
		cursos.setMinimumSize(new Dimension(DIM, DIM));
		cursos.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelJPA.add(cursos);
		
		JButton empleados = new JButton(EMPLEADOS);
		empleados.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_EMPLEADO, null));
		});
		empleados.setMinimumSize(new Dimension(DIM, DIM));
		empleados.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelJPA.add(empleados);
		
		JButton materiales = new JButton(MATERIALES);
		materiales.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_MATERIAL, null));
		});
		materiales.setMinimumSize(new Dimension(DIM, DIM));
		materiales.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelJPA.add(materiales);
		
		JButton oficinas = new JButton(OFICINAS);
		oficinas.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_OFICINA, null));
		});
		oficinas.setMinimumSize(new Dimension(DIM, DIM));
		oficinas.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelJPA.add(oficinas);

		JButton proyectos = new JButton(PROYECTOS);
		proyectos.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_PROYECTO, null));
		});
		proyectos.setMinimumSize(new Dimension(DIM, DIM));
		proyectos.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelJPA.add(proyectos);
		
		JButton tareas = new JButton(TAREAS);
		tareas.addActionListener((e) -> {
			Controlador.getInstancia().accion(new Context(Eventos.GUI_TAREA, null));
		});
		tareas.setMinimumSize(new Dimension(DIM, DIM));
		tareas.setMaximumSize(new Dimension(DIM, DIM));
		buttonsPanelJPA.add(tareas);
		buttonsPanel.add(buttonsPanelJPA);
		mainPanel.add(buttonsPanel, BorderLayout.CENTER);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
}