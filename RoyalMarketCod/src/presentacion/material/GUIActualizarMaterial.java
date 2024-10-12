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
import presentacion.viewhelper.GUIComprobar;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIActualizarMaterial extends JFrame implements GUI {
	
	private GUIComprobar guiComprobar;
	private static GUIActualizarMaterial instancia;
	private JTextField textId, textNombre, textIdEmpleado;
	private TMaterial tmat;
	private static final int FIELDS = 3;
	private static final String FROM_WHERE = "GUIActualizarMaterial";
	
	public GUIActualizarMaterial() {
		this.guiComprobar = new GUIComprobar(Utils.MATERIAL);
	}
	
	public static GUIActualizarMaterial getInstancia() {
		if(instancia == null) instancia = new GUIActualizarMaterial();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Actualizar material");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel actualizarPanel = new JPanel(new GridLayout(3,1));
		
		Panel idPanel = new Panel("ID");
		this.textId = idPanel.getJTextField();
		actualizarPanel.add(idPanel.getJPanel());
		
		Panel nombrePanel = new Panel("Nombre");
		this.textNombre = nombrePanel.getJTextField();
		actualizarPanel.add(nombrePanel.getJPanel());
		
		Panel idEmpleadoPanel = new Panel("ID empleado");
		this.textIdEmpleado = idEmpleadoPanel.getJTextField();
		actualizarPanel.add(idEmpleadoPanel.getJPanel());
		
		this.refill();
		
		JButton actualizarButton = new JButton("Actualizar");
		actualizarButton.addActionListener((e) -> {
			if(!this.areTextFieldsEmpty()) {
				try {
					int id = Integer.parseInt(this.textId.getText());
					String nombre = this.textNombre.getText();
					int idEmpleado = Integer.parseInt(this.textIdEmpleado.getText());
					boolean activo = this.tmat.isActivo();
					Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_MATERIAL, new TMaterial(id, nombre, idEmpleado, activo)));
					this.clear();
					this.setVisible(false);
				} catch(NumberFormatException nfe) {
					GUIMSG.showMessage("Los campos 'ID' e 'ID empleado' solo requieren de numeros", FROM_WHERE, true);
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
		
		JButton rellenarButton = new JButton("Rellenar campos");
		rellenarButton.addActionListener((e) -> {
			this.refill();
		});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(rellenarButton);
		buttonsPanel.add(vaciarButton);
		buttonsPanel.add(actualizarButton);
		
		mainPanel.add(actualizarPanel, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension((FIELDS +1)*100, 300));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
	}

	private void refill() {
		this.textId.setText("" + this.tmat.getId());
		this.textNombre.setText(this.tmat.getNombre());
		this.textIdEmpleado.setText("" + this.tmat.getIdEmpleado());
		
	}
	
	public void setMaterial(TMaterial tmat) {
		this.tmat = tmat;
	}
	
	public void comprobar(boolean b) {
		this.guiComprobar.setVisible(b);
	}
	
	public void init() {
		this.initGUI();
	}

	@Override
	public void update(Context context) {
		switch(context.getEvent()) {
		case ACTUALIZAR_MATERIAL_OK:
			GUIMSG.showMessage("Material actualizado", FROM_WHERE, false);
			break;
		case ACTUALIZAR_MATERIAL_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
		
	}

	@Override
	public void clear() {
		this.textId.setText("");
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
		case SAMaterial.MATERIAL_INEXISTENTE:
			return "El material no existe";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty() && this.textIdEmpleado.getText().isEmpty() && this.textNombre.getText().isEmpty();
	}
}