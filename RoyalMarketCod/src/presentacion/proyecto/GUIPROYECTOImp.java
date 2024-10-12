package presentacion.proyecto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.proyecto.TProyecto;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIPROYECTOImp extends GUIPROYECTO {
	
	private static final int BUTTONS = 7;
	private static final String FROM_WHERE = "GUIPROYECTOImp.actualizar()";
	
	public GUIPROYECTOImp() {
		super();
		this.initGUI();
	}
	
	private void initGUI() {
		// begin-user-code
		this.setTitle("Proyectos");
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);

		//Panel principal
		JPanel mainPanel = new JPanel (new BorderLayout());
		this.setContentPane(mainPanel);
		
		//Panel de botones
		JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS,1));
		mainPanel.add(buttonsPanel, BorderLayout.CENTER);
		
		//Botones
		
		JButton button_ALTA = new JButton("Alta proyecto");
		buttonsPanel.add(button_ALTA);
		button_ALTA.addActionListener((e)->{
			GUIAltaProyecto.getInstancia().setVisible(true);
		});
		
		
		
		JButton button_BAJA = new JButton("Baja proyecto");
		buttonsPanel.add(button_BAJA);
		button_BAJA.addActionListener((e)->{
			GUIBajaProyecto.getInstancia().setVisible(true);
		});
		
		
		JButton button_ACTUALIZAR = new JButton("Actualizar proyecto");
		buttonsPanel.add(button_ACTUALIZAR);
		button_ACTUALIZAR.addActionListener((e)->{
			GUIActualizarProyecto.getInstancia().comprobar(true);
		});
		
		
		
		JButton button_BUSCAR = new JButton("Buscar proyecto");
		buttonsPanel.add(button_BUSCAR);
		button_BUSCAR.addActionListener((e)->{
			GUIBuscarProyecto.getInstancia().setVisible(true);
		});
		
		JButton button_LISTAR = new JButton("Listar proyecto");
		buttonsPanel.add(button_LISTAR);
		button_LISTAR.addActionListener((e)->{
			GUIListarProyectos.getInstancia().setVisible(true);
		});
		
		
		JButton button_LISTARPORPROVEEDOR = new JButton("Listar proyecto por empleado");
		buttonsPanel.add(button_LISTARPORPROVEEDOR);
		button_LISTARPORPROVEEDOR.addActionListener((e)->{
			GUIListarProyectoPorEmpleado.getInstancia().setVisible(true);
		});
		
		this.pack();
		this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	setVisible(false);
            }
        });
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	@Override
	public void actualizar(Context context) {		
		switch(context.getEvent()){
		case COMPROBAR_PROYECTO_KO:
			GUIMSG.showMessage("No existe producto con dicho id  \n", FROM_WHERE, true);
		break;
		case COMPROBAR_PROYECTO_OK:
			TProyecto p = (TProyecto) context.getDatos();
			GUIActualizarProyecto.getInstancia().setProyecto(p);
			GUIActualizarProyecto.getInstancia().init();
			GUIActualizarProyecto.getInstancia().setVisible(true);
		break;
	default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		
	}
		
	}
}