package presentacion.curso;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.curso.SACurso;
import negocio.curso.TCursoPresencial;
import negocio.curso.TCursoPresencial;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.GUIComprobar;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIActualizarCursoPresencial extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIActualizarCursoPresencial";
	private static GUIActualizarCursoPresencial instancia;
	private GUIComprobar guiComprobar;
	private JTextField textId, textNombre, textPlazas, textHorasDia, textAula;
	private TCursoPresencial tc;
	private static final int FIELDS = 3;
	
	public GUIActualizarCursoPresencial() {
		this.guiComprobar = new GUIComprobar(Utils.CURSO);
	}
	
	public static synchronized GUIActualizarCursoPresencial getInstancia() {
		if (instancia == null) instancia = new GUIActualizarCursoPresencial();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Actualizar Curso Presencial");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel panelActualizar = new JPanel(new GridLayout(3, 1));
		
		Panel panelId = new Panel("ID");
		this.textId = panelId.getJTextField();
		this.textId.setEditable(false);
		panelActualizar.add(panelId.getJPanel());
		
		Panel panelNombre = new Panel("Nombre");
		this.textNombre = panelNombre.getJTextField();
		panelActualizar.add(panelNombre.getJPanel());
		
		Panel panelPlazas = new Panel("Plazas");
		this.textPlazas = panelPlazas.getJTextField();
		panelActualizar.add(panelPlazas.getJPanel());
		
		Panel panelHorasDia = new Panel("Horas Dia");
		this.textHorasDia = panelHorasDia.getJTextField();
		panelActualizar.add(panelHorasDia.getJPanel());
		
		Panel panelAula = new Panel("Aula");
		this.textAula = panelAula.getJTextField();
		panelActualizar.add(panelAula.getJPanel());
		
		this.refill();
		
		JButton butconActualizar = new JButton("Actualizar");
		butconActualizar.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int id = Integer.parseInt(this.textId.getText());
					String nombre = this.textNombre.getText();
					int aula = Integer.parseInt(this.textAula.getText());
					int plazas = Integer.parseInt(this.textPlazas.getText());
					int horasDia = Integer.parseInt(this.textHorasDia.getText());
					boolean activo = this.tc.isActivo();
					this.clear();
					this.setVisible(false);
					Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_CURSO_PRESENCIAL, new TCursoPresencial(id, nombre, plazas,horasDia, aula,activo)));
				} catch (NumberFormatException nfe) {
					GUIMSG.showMessage("Los campos 'ID', 'Plazas', 'Horas Dia' y 'Aula' solo requieren de numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage(Utils.FALTAN_CAMPOS_POR_RELLENAR, FROM_WHERE, true);
			}
		});
		
		JButton butconVaciar = new JButton("Vaciar");
		butconVaciar.addActionListener((e) -> {
			this.clear();
		});
		
		JButton butconRellenar = new JButton("Rellenar campos");
		butconRellenar.addActionListener((e) -> {
			this.refill();
		});
		
		JPanel butconsPanel = new JPanel();
		butconsPanel.add(butconRellenar);
		butconsPanel.add(butconVaciar);
		butconsPanel.add(butconActualizar);
		
		mainPanel.add(panelActualizar, BorderLayout.CENTER);
		
		mainPanel.add(butconsPanel, BorderLayout.PAGE_END);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension((FIELDS +1)*100, 300));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		
	}
	
	public void setCurso(TCursoPresencial tc) {
		this.tc = tc;
	}
	
	public void comprobar(boolean b) {
		this.guiComprobar.setVisible(b);
	}
	
	public void init() {
		this.initGUI();
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case ACTUALIZAR_CURSO_PRESENCIAL_OK:
			GUIMSG.showMessage("Curso presencial actualizado", FROM_WHERE, false);
			break;
		case ACTUALIZAR_CURSO_PRESENCIAL_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}
	
	private void refill() {
		this.textId.setText("" + this.tc.getId());
		this.textNombre.setText(this.tc.getNombre());
		this.textPlazas.setText("" + this.tc.getPlazas());
		this.textHorasDia.setText("" + this.tc.getHorasDia());
		this.textAula.setText(""+this.tc.getAula());
	}

	@Override
	public void clear() {
		this.textPlazas.setText("");
		this.textHorasDia.setText("");
		this.textAula.setText("");
		this.textNombre.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SACurso.ERROR_SINTACTICO: 
			return Utils.ERROR_SINTACTICO;
		case SACurso.CURSO_INEXISTENTE: 
			return "El curso no existe";
		case SACurso.EMPLEADO_INACTIVO: 
			return "El empleado asociado no esta activo";
		case SACurso.EMPLEADO_INEXISTENTE:
			return "El empleado asociado no existe";
		case SACurso.CURSO_SIN_CAMBIOS:
			return "No se ha hecho ningun cambio";
		default: return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textNombre.getText().isEmpty() &&
				this.textPlazas.getText().isEmpty() &&
				this.textHorasDia.getText().isEmpty() &&
				this.textAula.getText().isEmpty() &&
				this.textNombre.getText().isEmpty();
	}
}