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
import negocio.proyecto.TProyecto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.GUIComprobar;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIActualizarProyecto extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIActualizarProyecto";
	private static GUIActualizarProyecto instancia;
	private TProyecto p;
	private int fila = 6; 
	private GUIComprobar guiComprobar;
	
	public GUIActualizarProyecto(){
		this.guiComprobar = new GUIComprobar("proyecto");
	}
	
	public static synchronized GUIActualizarProyecto getInstancia() {
		if (instancia == null) instancia = new GUIActualizarProyecto();
		return instancia;
	}
	
	private void initGUI() {
		
		
		this.setTitle("Actualizar proyecto");

		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panel_actualizar =  new JPanel(new GridLayout(fila, 1));
		mainPanel.add(panel_actualizar);
		
	
		
		JLabel lId =new JLabel("Id del proyecto: ");
		JTextField tId = new JTextField("" + this.p.getId());
		tId.setEditable(false);
		tId.setPreferredSize(new Dimension(100,30));
		JPanel pId = new JPanel();
		pId.add(lId);
		pId.add(tId);
		panel_actualizar.add(pId);
		
		
		JLabel lNombre = new JLabel("Nombre: ");
		JTextField tNombre = new JTextField(this.p.getNombre());
		tNombre.setPreferredSize(new Dimension(100,30));
		
		JPanel pNombre = new JPanel();
		pNombre.add(lNombre);
		pNombre.add(tNombre);
		panel_actualizar.add(pNombre);
	

		
		JButton button = new JButton("Actualizar");
		JPanel panelButton =new JPanel();
		panelButton.add(button);
		panel_actualizar.add(panelButton);
		
    	JButton emptyButton = new JButton("Restaurar");
    	emptyButton.addActionListener((e) -> {
    		tNombre.setText(this.p.getNombre());
    	});
    	panelButton.add(emptyButton);
		
		button.addActionListener((e)->{
			
			if(tNombre.getText().isEmpty()
						){
				
				GUIMSG.showMessage("Faltan campos por rellenar", "ACTUALIZAR PROYECTO", true);
				
			}
			else{
				
				try{
					int id = Integer.parseInt(tId.getText());

					String nombre = tNombre.getText();
					
					TProyecto py = new TProyecto(id,nombre,true);
					
					Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_PROYECTO, py));
					this.setVisible(false);
					
				}catch(NumberFormatException nfe){
					
					GUIMSG.showMessage("Campos erroneos", "ACTUALIZAR PROYECTO", true);
				}
				
			}	
		});
		
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension((fila +1)*100, 300));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
//		this.setBounds(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200, 400, 400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
	}
	
	public void setProyecto(TProyecto p){
		this.p = p;
	}

	public void comprobar(boolean b) {
		this.guiComprobar.setVisible(b);
	}
	
	public void init(){
		this.initGUI();
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case ACTUALIZAR_PROYECTO_OK:
			TProyecto pus = (TProyecto) context.getDatos();
			GUIMSG.showMessage("Proyecto actualizado \n" + pus.toString(), FROM_WHERE, false);
			break;
		case ACTUALIZAR_PROYECTO_KO:
			switch ((int)context.getDatos()) {
			case SAProyecto.ERROR_PROYECTO_DUPLICADO:
				GUIMSG.showMessage("Proyecto duplicado \n", FROM_WHERE, true);
			break;
			case SAProyecto.ERROR_SINTACTICO_NOMBRE_PROYECTO:
				GUIMSG.showMessage("Nombre con error sintactico \n", FROM_WHERE, true);
				break;
			}
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		
	}

	@Override
	public String getErrorMsg(int error) {
return null;
	}

	@Override
	public boolean areTextFieldsEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}