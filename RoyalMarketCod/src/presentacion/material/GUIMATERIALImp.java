package presentacion.material;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.material.TMaterial;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIMATERIALImp extends GUIMATERIAL {
	
	private static final String FROM_WHERE = "GUIMATERIALImp";
	private static final int BUTTONS = 6;
	
	public GUIMATERIALImp() {
		this.initGUI();
	}
	
	private void initGUI() {
		this.setTitle("Materiales");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS, 1));
		
		JButton altaButton = new JButton("Alta material");
		altaButton.addActionListener((e) -> {
			GUIAltaMaterial.getInstancia().setVisible(true);
		});
		buttonsPanel.add(altaButton);
		
		JButton bajaButton = new JButton("Baja material");
		bajaButton.addActionListener((e) -> {
			GUIBajaMaterial.getInstancia().setVisible(true);
		});
		buttonsPanel.add(bajaButton);
		
		JButton actualizarButton = new JButton("Actualizar material");
		actualizarButton.addActionListener((e) -> {
			GUIActualizarMaterial.getInstancia().comprobar(true);
		});
		buttonsPanel.add(actualizarButton);
		
		
		JButton buscarButton = new JButton("Buscar material");
		buscarButton.addActionListener((e) -> {
			GUIBuscarMaterial.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buscarButton);
		
		JButton listarButton = new JButton("Listar material");
		listarButton.addActionListener((e) -> {
			GUIListarMaterial.getInstancia().setVisible(true);
		});
		buttonsPanel.add(listarButton);
		
		JButton listarPorEmpleadoButton = new JButton("Listar material por empleado");
		listarPorEmpleadoButton.addActionListener((e) -> {
			GUIListarMaterialPorEmpleado.getInstancia().setVisible(true);
		});
		buttonsPanel.add(listarPorEmpleadoButton);
		
		mainPanel.add(buttonsPanel, BorderLayout.CENTER);		
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	setVisible(false);
            }
        });
	}

	@Override
	public void actualizar(Context context) {
		switch(context.getEvent()) {
		case COMPROBAR_MATERIAL_OK:
			TMaterial tmat = (TMaterial)context.getDatos();
			GUIActualizarMaterial.getInstancia().setMaterial(tmat);
			GUIActualizarMaterial.getInstancia().init();
			GUIActualizarMaterial.getInstancia().setVisible(true);
			break;
		case COMPROBAR_MATERIAL_KO:
			GUIMSG.showMessage("No existe el material con dicho id", FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
		
	}
}