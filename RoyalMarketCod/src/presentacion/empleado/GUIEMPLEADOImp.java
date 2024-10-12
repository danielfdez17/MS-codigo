/**
 * 
 */
package presentacion.empleado;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.empleado.TEmpleadoAdministrador;
import negocio.empleado.TEmpleadoTecnico;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIEMPLEADOImp extends GUIEMPLEADO {
	private static final int BUTTONS = 8;
	private static final String FROM_WHERE = "GUIEMPLEADOImp.actualizar()";
	

	GUIEMPLEADOImp() {
		super();
		this.initGUI();
	}

	
	public void initGUI() {
		this.setTitle("Empleados");

		//Panel principal
		JPanel mainPanel = new JPanel (new BorderLayout());
		this.setContentPane(mainPanel);
		
		//Panel de botones
		JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS, 2));
		
		JButton buttonAltaAdmin = new JButton("Alta Empleado Administrador");
		buttonAltaAdmin.addActionListener((e)-> {
			GUIAltaEmpleadoAdmin.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonAltaAdmin);

		JButton buttonAltaTecnico = new JButton("Alta Empleado Tecnico");
		buttonAltaTecnico.addActionListener((e)-> {
			GUIAltaEmpleadoTecnico.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonAltaTecnico);
		
		JButton buttonBuscar = new JButton("Buscar Empleado");
		buttonBuscar.addActionListener((e) -> {
			GUIBuscarEmpleado.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonBuscar);
		
		JButton buttonListar = new JButton("Listar Empleados");
		buttonListar.addActionListener((e) -> {
			GUIListarEmpleados.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonListar);
		
		JButton buttonListarPorCurso = new JButton("Listar Empleados por Curso");
		buttonListarPorCurso.addActionListener((e) -> {
			GUIListarEmpleadosPorCurso.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonListarPorCurso);
		
		JButton buttonListarPorOficina = new JButton("Listar Empleados por Oficina");
		buttonListarPorOficina.addActionListener((e) -> {
			GUIListarEmpleadosPorOficina.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonListarPorOficina);

		JButton buttonListarPorProyecto = new JButton("Listar Empleados por Proyecto");
		buttonListarPorProyecto.addActionListener((e) -> {
			GUIListarEmpleadosPorProyecto.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonListarPorProyecto);
		
		JButton buttonVincularCuso = new JButton("Vincular curso");
		buttonVincularCuso.addActionListener((e) -> {
			GUIVincularCurso.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonVincularCuso);
		
		JButton buttonDevincularCurso = new JButton("Desvincular curso");
		buttonDevincularCurso.addActionListener((e) -> {
			GUIDesvincularCurso.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonDevincularCurso);
		
		JButton buttonActualizarNivelEmpCurso = new JButton("Actualizar nivel curso empleado");
		buttonActualizarNivelEmpCurso.addActionListener((e) -> {
			GUIActualizarNivelEmpCurso.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonActualizarNivelEmpCurso);
		
		JButton buttonVincularProyecto = new JButton("Vincular proyecto");
		buttonVincularProyecto.addActionListener((e) -> {
			GUIVincularProyecto.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonVincularProyecto);
		
		JButton buttonDesvincularProyecto = new JButton("Desvincular proyecto");
		buttonDesvincularProyecto.addActionListener((e) -> {
			GUIDesvincularProyecto.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonDesvincularProyecto);
		
		JButton buttonActualizarEAdministrador = new JButton("Actualizar Empleado Administrador");
		buttonActualizarEAdministrador.addActionListener((e) -> {
			GUIActualizarEAdministrador.getInstancia().comprobar(true);
		});
		buttonsPanel.add(buttonActualizarEAdministrador);
		
		JButton buttonActualizarETecnico= new JButton("Actualizar Empleado Tecnico" );
		buttonActualizarETecnico.addActionListener((e) -> {
			GUIActualizarETecnico.getInstancia().comprobar(true);
		});
		buttonsPanel.add(buttonActualizarETecnico);
		
		JButton buttonBaja = new JButton("Baja Empleado");
		buttonBaja.addActionListener((e) -> {
			GUIBajaEmpleado.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonBaja);
		
		mainPanel.add(buttonsPanel, BorderLayout.CENTER);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		 this.addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosing(WindowEvent e) {
	            	setVisible(false);
	            }
	        });
		
	}

	@Override
	public void actualizar(Context context) {
		switch (context.getEvent()) {
		case COMPROBAR_EMPLEADO_KO:
			GUIMSG.showMessage("No existe el empleado con dicho id", FROM_WHERE, true);
			break;
		case COMPROBAR_EMPLEADO_OK:
			if (context.getDatos() instanceof TEmpleadoTecnico) {
				TEmpleadoTecnico tet = (TEmpleadoTecnico)context.getDatos();
				GUIActualizarETecnico.getInstancia().setEmpleado(tet);
				GUIActualizarETecnico.getInstancia().init();
				GUIActualizarETecnico.getInstancia().setVisible(true);
			}
			else {
				TEmpleadoAdministrador tea = (TEmpleadoAdministrador)context.getDatos();
				GUIActualizarEAdministrador.getInstancia().setEmpleado(tea);
				GUIActualizarEAdministrador.getInstancia().init();
				GUIActualizarEAdministrador.getInstancia().setVisible(true);
			}
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
		
	}
}