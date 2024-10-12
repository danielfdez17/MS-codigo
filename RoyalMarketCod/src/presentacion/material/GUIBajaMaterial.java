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
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIBajaMaterial extends JFrame implements GUI {
	
	private static GUIBajaMaterial instancia;
	private JTextField textId;
	private static final String FROM_WHERE = "GUIBajaMaterial";
	
	public GUIBajaMaterial() {
		this.initGUI();
	}
	
	public static GUIBajaMaterial getInstancia() {
		if(instancia == null) instancia = new GUIBajaMaterial();
		return instancia;
	}

	private void initGUI() {
		this.setTitle("Baja material");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel bajaPanel = new JPanel(new GridLayout(1,1));
		
		Panel idPanel = new Panel("ID");
		this.textId = idPanel.getJTextField();
		bajaPanel.add(idPanel.getJPanel());
		
		JButton bajaButton = new JButton("Baja");
		bajaButton.addActionListener((e) -> {
			if(!this.areTextFieldsEmpty()) {
				try {
					int id = Integer.parseInt(this.textId.getText());
					Controlador.getInstancia().accion(new Context(Eventos.BAJA_MATERIAL, id));
					this.clear();
					this.setVisible(false);
				} catch(NumberFormatException nfe) {
					GUIMSG.showMessage("El campo 'ID' solo requiere de numeros", FROM_WHERE, true);
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
		buttonsPanel.add(bajaButton);
		
		mainPanel.add(bajaPanel, BorderLayout.CENTER);
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
		case BAJA_MATERIAL_OK:
			GUIMSG.showMessage("Material dado de baja", FROM_WHERE, false);
			break;
		case BAJA_MATERIAL_KO:
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
		
	}

	@Override
	public String getErrorMsg(int error) {
		switch(error) {
		case SAMaterial.MATERIAL_INEXISTENTE:
			return "El material no existe";
		case SAMaterial.MATERIAL_INACTIVO:
			return "El material estaba inactivo, queda reactivado";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty();
	}

}
