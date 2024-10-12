package presentacion.proyecto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.proyecto.SAProyecto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

public class GUIBajaProyecto extends JFrame implements GUI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String FROM_WHERE = "GUIBajaProyecto";
	private static GUIBajaProyecto instancia;
	public GUIBajaProyecto (){
		
		
		super();
		iniGUI();
	}
	
	public static synchronized GUIBajaProyecto getInstancia() {
		
		if (instancia == null) instancia = new GUIBajaProyecto();
		return instancia;
	}
	
	private void iniGUI() {
		setLayout(null);
		this.setTitle("Baja proyecto");

		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panel_alta = new JPanel(new GridLayout(1, 1));
		mainPanel.add(panel_alta, BorderLayout.PAGE_START);
		
		
		JLabel lNombre = new JLabel("Id: ");
		JTextField tNombre = new JTextField();
		tNombre.setPreferredSize(new Dimension(50,30));
		
		JPanel pNombre = new JPanel();
		pNombre.add(lNombre);
		pNombre.add(tNombre);
		panel_alta.add(pNombre);


		JButton button = new JButton("Baja");
		JPanel panelButton =new JPanel();
		
		panelButton.add(button);
		
		
    	JButton emptyButton = new JButton("Vaciar");
    	emptyButton.addActionListener((e) -> {
    		tNombre.setText("");
    	});
    	panelButton.add(emptyButton);
    	mainPanel.add(panelButton, BorderLayout.PAGE_END);	
		

		button.addActionListener((e)->{
			
			if(tNombre.getText().isEmpty()){
				
				GUIMSG.showMessage("Faltan campos por rellenar", "BAJA PROYECTO", true);
				
			}
			else{
				
				try{ 
					int id = Integer.parseInt(tNombre.getText());
					
					
					Controlador.getInstancia().accion(new Context(Eventos.BAJA_PROYECTO, id));
					
					this.setVisible(false);
		    		tNombre.setText("");
					
				}catch(NumberFormatException nfe){
			
					GUIMSG.showMessage("Campos erroneos", "BAJA PROYECTO", true);
				}
				
			}	
		});
		
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
	}
	
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case BAJA_PROYECTO_OK:
			GUIMSG.showMessage("Proyecto dada de baja correctamente \n", FROM_WHERE, false);
			break;
		case BAJA_PROYECTO_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAProyecto.ERROR_PROYECTO_DADO_DE_BAJA:
			return "Proyecto no existe en BBDD";
		case SAProyecto.ERROR_PROYECTO_CON_TAREAS_ACTIVAS:
			return "Proyecto tiene tareas activadas";
		case SAProyecto.ERROR_PROYECTO_CON_EMPLEADOS_ACTIVOS:
			return "Proyecto tiene empleados activados";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
