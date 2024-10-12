/**
 * 
 */
package presentacion.material;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.material.SAMaterial;
import negocio.material.TMaterial;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIAltaMaterial extends JFrame implements GUI {
	
	private static GUIAltaMaterial instancia;
	private JTextField textNombre, textIdEmpleado;
	private static final String FROM_WHERE = "GUIAltaMaterial";
	
	public GUIAltaMaterial() {
		this.initGUI();
	}
	
	public static GUIAltaMaterial getInstancia() {
		if(instancia == null) instancia = new GUIAltaMaterial();
		return instancia;
	}

	private void initGUI() {
		this.setTitle("Alta material");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel altaPanel = new JPanel(new GridLayout(2, 1));
		
		Panel nombrePanel = new Panel("Nombre");
		this.textNombre = nombrePanel.getJTextField();
		altaPanel.add(nombrePanel.getJPanel());
		
		Panel idEmpleadoPanel = new Panel("ID empleado");
		this.textIdEmpleado = idEmpleadoPanel.getJTextField();
		altaPanel.add(idEmpleadoPanel.getJPanel());
		
		JButton altaButton = new JButton("Alta");
		altaButton.addActionListener((e) -> {
			if(!this.areTextFieldsEmpty()) {
				try {
					String nombre = this.textNombre.getText();
					int idEmpleado = Integer.parseInt(this.textIdEmpleado.getText());
					Controlador.getInstancia().accion(new Context(Eventos.ALTA_MATERIAL, new TMaterial(nombre, idEmpleado)));
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
		
		JButton vaciarButton = new JButton("Vaciar");
		vaciarButton.addActionListener((e) -> {
			this.clear();
		});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(vaciarButton);
		buttonsPanel.add(altaButton);
		
		mainPanel.add(altaPanel, BorderLayout.CENTER);
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
		switch(context.getEvent()) {
		case ALTA_MATERIAL_OK:
			GUIMSG.showMessage("Material dado de alta", FROM_WHERE, false);
			break;
		case ALTA_MATERIAL_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
		
	}

	@Override
	public void clear() {
		this.textIdEmpleado.setText("");
		this.textNombre.setText("");
		
	}

	@Override
	public String getErrorMsg(int error) {
		switch(error) {
		case SAMaterial.EMPLEADO_INACTIVO:
			return "El empleado asociado esta inactivo";
		case SAMaterial.EMPLEADO_INEXISTENTE:
			return "El empleado asociado no existe";
		case SAMaterial.ERROR_SINTACTICO:
			return Utils.ERROR_SINTACTICO;
		case SAMaterial.MATERIAL_ACTIVO:
			return "El material existe y esta activo";
		case SAMaterial.MATERIAL_INACTIVO:
			return "El material estaba inactivo, queda reactivado";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textIdEmpleado.getText().isEmpty() && this.textNombre.getText().isEmpty();
	}
}