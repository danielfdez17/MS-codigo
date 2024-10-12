package presentacion.oficina;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import negocio.oficina.SAOficina;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

public class GUIBajaOficina extends JFrame implements GUI {

	private static final String FROM_WHERE = "GUIBajaOficina";
	private JTextField textId;
	private static final long serialVersionUID = 1L;
	private static GUIBajaOficina instancia;

	public GUIBajaOficina() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				initGUI();
			}
		});
	}

	public static synchronized GUIBajaOficina getInstancia() {
		if (instancia == null) instancia = new GUIBajaOficina();
		return instancia;
	}
	
	private void initGUI() {
		// Configuración del mainPanel
		setTitle("Baja Oficina");
		JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		mainPanel.setPreferredSize(new Dimension(450, 100));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		setLocationRelativeTo(getParent());

		mainPanel.add(IdPanel());
		this.textId = new JTextField(10);
		
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		buttonPanel.add(eliminarButton());
		buttonPanel.add(cancelarButton());

		mainPanel.add(buttonPanel);
		// Configuración del mainPanel
		pack();
		setLocationRelativeTo(getParent());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//setVisible(false);
	}
	public JPanel IdPanel()
	{
		JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JLabel idLabel = new JLabel("Id: ");
		idLabel.setPreferredSize(new Dimension(200, 20));

		JSpinner idSpinner = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
		idSpinner.setPreferredSize(new Dimension(100, 20));
		int id = (Integer) idSpinner.getValue();
		idSpinner.addChangeListener(new ChangeListener()
		{

			@Override
			public void stateChanged(ChangeEvent e) {
				textId.setText("" + (Integer) idSpinner.getValue());
			}
			
		});
		idPanel.add(idLabel);
		idPanel.add(idSpinner);
		
		return idPanel;
		
	}
	JButton eliminarButton()
	{
		JButton delButton = new JButton("Eliminar");
		delButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!areTextFieldsEmpty()) {
					try {
						int id = Integer.parseInt(textId.getText());
						clear();
						setVisible(false);
						Controlador.getInstancia().accion(new Context(Eventos.BAJA_OFICINA, id));
					} catch (NumberFormatException nfe) {
					GUIMSG.showMessage("El campo 'Id' solo requiere de numeros", FROM_WHERE, true);	
					}
				}
				else {
					GUIMSG.showMessage(Utils.FALTAN_CAMPOS_POR_RELLENAR, FROM_WHERE, true);
				}
			}
		});
		return delButton;
	}
	JButton cancelarButton()
	{
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
//				Controlador.getInstancia().accion(new Context(Eventos.GUI_OFICINA, null));
			}
			
			
		});
		return cancelButton;
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case BAJA_OFICINA_OK:
			GUIMSG.showMessage("Se ha dado de baja la oficina con id " + (int)context.getDatos(), FROM_WHERE, false);
			break;
		case BAJA_OFICINA_KO:
			GUIMSG.showMessage(getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		this.textId.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAOficina.OFICINA_INEXISTENTE:
			return "La oficina no existe";
		case SAOficina.ERROR_OFICINA_CON_EMPLEADOS_ACTIVOS:
			return "La oficina tiene empleado activos";
		case SAOficina.ERROR_INESPERADO:
			return Utils.ERROR_INESPERADO;
		case SAOficina.OFICINA_INACTIVA:
			return "La oficina ya estaba dada de baja";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty();
	}
}
