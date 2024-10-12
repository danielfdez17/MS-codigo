/**
 * 
 */
package presentacion.curso;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.cliente.TClienteNormal;
import negocio.cliente.TClienteVIP;
import negocio.curso.TCurso;
import negocio.curso.TCursoOnline;
import negocio.curso.TCursoPresencial;
import presentacion.cliente.GUIActualizarClienteNormal;
import presentacion.cliente.GUIActualizarClienteVIP;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.curso.GUIActualizarCursoOnline;
import presentacion.curso.GUIAltaCursoOnline;
import presentacion.curso.GUIAltaCursoPresencial;

import presentacion.curso.GUIBajaCurso;
import presentacion.curso.GUIBuscarCurso;
import presentacion.curso.GUIListarCursos;
import presentacion.curso.GUIListarCursosPorEmpleado;
import utilities.Utils;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author danie
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public class GUICURSOImp extends GUICURSO {
	private static final String FROM_WHERE = "GUICURSOImp";
	private static final int BUTTONS = 6;
	
	public GUICURSOImp() {
		this.initGUI();
	}
	
	private void initGUI() {
		this.setTitle("Cursos");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS, 1));
		
		JButton buttonAltaOnline = new JButton("Alta curso online");
		buttonAltaOnline.addActionListener((e) -> {
			GUIAltaCursoOnline.getInstancia().setVisible(true);
			
		});
		buttonsPanel.add(buttonAltaOnline);
		
		JButton buttonAltaPresencial = new JButton("Alta curso presencial");
		buttonAltaPresencial.addActionListener((e) -> {
			GUIAltaCursoPresencial.getInstancia().setVisible(true);
			
		});
		buttonsPanel.add(buttonAltaPresencial);
		
		JButton buttonBuscar = new JButton("Buscar curso");
		buttonBuscar.addActionListener((e) -> {
			GUIBuscarCurso.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonBuscar);
		
		JButton buttonListar = new JButton("Listar cursos");
		buttonListar.addActionListener((e) -> {
			GUIListarCursos.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonListar);
		
		JButton buttonListarPorProyecto = new JButton("Listar cursos por empleado");
		buttonListarPorProyecto.addActionListener((e) -> {
			GUIListarCursosPorEmpleado.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonListarPorProyecto);
		
		JButton buttonActualizarOnline = new JButton("Actualizar curso online");
		buttonActualizarOnline.addActionListener((e) -> {
			GUIActualizarCursoOnline.getInstancia().comprobar(true);
		});
		buttonsPanel.add(buttonActualizarOnline);
		
		JButton buttonActualizarPresencial = new JButton("Actualizar curso presencial");
		buttonActualizarPresencial.addActionListener((e) -> {
			GUIActualizarCursoPresencial.getInstancia().comprobar(true);
		});
		buttonsPanel.add(buttonActualizarPresencial);
		
		JButton buttonBaja = new JButton("Baja curso");
		buttonBaja.addActionListener((e) -> {
			GUIBajaCurso.getInstancia().setVisible(true);
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
		case COMPROBAR_CURSO_KO:
			GUIMSG.showMessage("No existe el curso con dicho id", FROM_WHERE, true);
			break;
		case COMPROBAR_CURSO_OK:
			if (context.getDatos() instanceof TCursoOnline) {
				TCursoOnline tco = (TCursoOnline)context.getDatos();
				GUIActualizarCursoOnline.getInstancia().setCurso(tco);
				GUIActualizarCursoOnline.getInstancia().init();
				GUIActualizarCursoOnline.getInstancia().setVisible(true);
			}
			else {
				TCursoPresencial tcp = (TCursoPresencial)context.getDatos();
				GUIActualizarCursoPresencial.getInstancia().setCurso(tcp);
				GUIActualizarCursoPresencial.getInstancia().init();
				GUIActualizarCursoPresencial.getInstancia().setVisible(true);
			}
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}
}