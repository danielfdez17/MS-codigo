package presentacion.empleado;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.empleado.SAEmpleado;
import negocio.empleado_curso.TEmpleadoCurso;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

public class GUIDesvincularCurso extends JFrame implements GUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String FROM_WHERE = "GUIDesvincularCurso";
	private static GUIDesvincularCurso instancia;
	private JTextField textIdEmpleado, textIdCurso;
	
	
	public GUIDesvincularCurso() {
		this.initGUI();
	}
	
	public static GUIDesvincularCurso getInstancia() {
		if (instancia == null) instancia = new GUIDesvincularCurso();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Desvincular Empleado Curso");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panel_vincular = new JPanel(new GridLayout(6, 1));
		mainPanel.add(panel_vincular);
		
		JLabel lide = new JLabel("idEmpleado: ");
		JTextField tide = new JTextField();
		tide.setPreferredSize(new Dimension(100,30));
		
		JPanel pide = new JPanel();
		pide.add(lide);
		pide.add(tide);
		panel_vincular.add(pide);
		
		JLabel lidc = new JLabel("idCurso: ");
		JTextField tidc = new JTextField();
		tidc.setPreferredSize(new Dimension(100,30));
		
		JPanel pidc = new JPanel();
		pidc.add(lidc);
		pidc.add(tidc);
		panel_vincular.add(pidc);
		
		JButton buttonBaja = new JButton("Desvincular Curso");
		buttonBaja.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int idp = Integer.parseInt(tidc.getText());
					int ide = Integer.parseInt(tide.getText());
					int idn = 0;
					this.setVisible(false);
					this.clear();
					TEmpleadoCurso transfer = new TEmpleadoCurso(ide, idp, idn);
					Controlador.getInstancia().accion(new Context(Eventos.DESVINCULAR_CURSO, transfer));
				} catch (NumberFormatException nfe) {
					this.clear();
					GUIMSG.showMessage("El campo 'ID' requiere solo de numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
			}
		});
		
		JButton emptyTextButton = new JButton("Vaciar");
		emptyTextButton.addActionListener((e) -> {
			this.clear();
		});
		
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(emptyTextButton);
		buttonsPanel.add(buttonBaja);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		mainPanel.add(panel_vincular, BorderLayout.CENTER);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(false);

		
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case DESVINCULAR_CURSO_OK:
			GUIMSG.showMessage("Se ha desvinculado al empleado con el curso", FROM_WHERE, false);
			break;
		case DESVINCULAR_CURSO_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		String t = "";
		this.textIdEmpleado.setText(t);
		this.textIdCurso.setText(t);
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAEmpleado.ERROR_INESPERADO:
			return Utils.ERROR_INESPERADO;
		case SAEmpleado.EMPLEADO_INEXISTENTE:
			return "El empleado no existe";
		case SAEmpleado.EMPLEADO_INACTIVO:
			return "El empleado esta inactivo";
		case SAEmpleado.CURSO_INEXISTENTE:
			return "El curso no existe";
		case SAEmpleado.CURSO_INACTIVO:
			return "El curso esta inactivo";
		case SAEmpleado.CURSO_YA_DESVINCULADO:
			return "El empleado ya estaba desvinculado del curso";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textIdCurso.getText().isEmpty() &&
				this.textIdEmpleado.getText().isEmpty();
		}

}
