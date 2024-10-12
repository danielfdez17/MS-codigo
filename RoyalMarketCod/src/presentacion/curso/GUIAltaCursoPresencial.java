package presentacion.curso;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.persistence.Version;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.curso.SACurso;
import negocio.curso.TCurso;
import negocio.curso.TCursoPresencial;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.curso.GUIAltaCursoPresencial;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIAltaCursoPresencial extends JFrame implements GUI {
	
	private static GUIAltaCursoPresencial instancia;
	private JTextField textNombre, textPlazas, textHorasDia, textAulas;
	private static final String FROM_WHERE = "GUIAltaCursoPresencial";
	
	public GUIAltaCursoPresencial() {
		this.initGUI();
	}
	
	public static synchronized GUIAltaCursoPresencial getInstancia() {
		if (instancia == null) instancia = new GUIAltaCursoPresencial();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Alta curso presencial");

		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel panelAlta = new JPanel(new GridLayout(2, 1));
		
		Panel panelNombre = new Panel("Nombre");
		this.textNombre = panelNombre.getJTextField();
		panelAlta.add(panelNombre.getJPanel());
		
		Panel panelPlazas = new Panel("Plazas");
		this.textPlazas = panelPlazas.getJTextField();
		panelAlta.add(panelPlazas.getJPanel());
		
		Panel textHorasDia = new Panel("Horas Dia");
		this.textHorasDia = textHorasDia.getJTextField();
		panelAlta.add(textHorasDia.getJPanel());
		
		Panel textAulas = new Panel("Aula");
		this.textAulas = textAulas.getJTextField();
		panelAlta.add(textAulas.getJPanel());
		
		JButton buttonAlta = new JButton("Dar de alta");
		buttonAlta.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					String nombre = this.textNombre.getText();
					int aulas = Integer.parseInt(this.textAulas.getText());
					int plazas = Integer.parseInt(this.textPlazas.getText());
					int horasDia = Integer.parseInt(this.textHorasDia.getText());
					Controlador.getInstancia().accion(new Context(Eventos.ALTA_CURSO_PRESENCIAL, new TCursoPresencial(nombre, plazas,horasDia,aulas)));
					this.clear();
					this.setVisible(false);
				} catch (NumberFormatException nfe) {
					GUIMSG.showMessage("Los campos aula, plazas y horas dia solo requieren de numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage(Utils.FALTAN_CAMPOS_POR_RELLENAR, FROM_WHERE, true);
			}
		});
		
		JButton buttonVaciar = new JButton("Vaciar");
		buttonVaciar.addActionListener((e) -> {
			this.clear();
		});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(buttonVaciar);
		buttonsPanel.add(buttonAlta);
		
		mainPanel.add(panelAlta, BorderLayout.CENTER);
		
		
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
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
		case ALTA_CURSO_PRESENCIAL_OK:
			GUIMSG.showMessage("Curso presencial dado de alta correctamente \n", FROM_WHERE, false);
			break;
		case ALTA_CURSO_PRESENCIAL_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		this.textPlazas.setText("");
		this.textHorasDia.setText("");
		this.textAulas.setText("");
		this.textNombre.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SACurso.EMPLEADO_INACTIVO:
			return "El empleado asociado esta inactivo";
		case SACurso.EMPLEADO_INEXISTENTE:
			return "El empleado asociado no existe";
		case SACurso.CURSO_ACTIVO:
			return "El curso existe y esta activo";
		case SACurso.CURSO_INACTIVO:
			return "El curso estaba inactivo, queda reactivado";
		case SACurso.ERROR_SINTACTICO:
			return Utils.ERROR_SINTACTICO;
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textNombre.getText().isEmpty() &&
				this.textPlazas.getText().isEmpty() &&
				this.textHorasDia.getText().isEmpty() &&
				this.textAulas.getText().isEmpty() &&
				this.textNombre.getText().isEmpty();
	}
}
