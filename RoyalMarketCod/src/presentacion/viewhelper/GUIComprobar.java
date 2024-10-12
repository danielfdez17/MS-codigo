package presentacion.viewhelper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public class GUIComprobar extends JFrame {
	
	private static final String FROM_WHERE = "COMPROBAR %s";
	
	private String entity;
	
	public GUIComprobar(String entity) {
		this.entity = entity;
		this.initGUI();
	}
	public void initGUI() {
		this.setTitle("Comprobar " + this.entity);

		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panel_buscar =  new JPanel(new GridLayout(2, 2));
		mainPanel.add(panel_buscar);
		
	
		
		JLabel lId = new JLabel("ID: ");
		JTextField tId = new JTextField();
		tId.setPreferredSize(new Dimension(100,30));
		JPanel pId = new JPanel();
		pId.add(lId);
		pId.add(tId);
		panel_buscar.add(pId);
		

		JButton button = new JButton("Comprobar");
		JPanel panelButton =new JPanel();
		panelButton.add(button);
		panel_buscar.add(panelButton);
		
    	JButton emptyButton = new JButton("Vaciar");
    	emptyButton.addActionListener((e) -> {
    		tId.setText("");
    	});
    	panelButton.add(emptyButton);
		
		button.addActionListener((l)->{
			
			if(tId.getText().isEmpty() ){
				
				GUIMSG.showMessage("Faltan campos por rellenar", String.format(FROM_WHERE, this.entity.toUpperCase()), true);
				
			}
			else{
				
				try{
					int id = Integer.parseInt(tId.getText());
					Eventos evento = null;
					switch (this.entity.toLowerCase()) {
					case "almacen":
						evento = Eventos.COMPROBAR_ALMACEN;
						break;
					case "cliente":
						evento = Eventos.COMPROBAR_CLIENTE;
						break;
					case "producto":
						evento = Eventos.COMPROBAR_PRODUCTO;
						break;
					case "proveedor":
						evento = Eventos.COMPROBAR_PROVEEDOR;
						break;
					case "trabajador":
						evento = Eventos.COMPROBAR_TRABAJADOR;
						break;
					case "curso":
						evento = Eventos.COMPROBAR_CURSO;
						break;
					case "empleado":
						evento = Eventos.COMPROBAR_EMPLEADO;
						break;
					case "material":
						evento = Eventos.COMPROBAR_MATERIAL;
						break;
					case "oficina":
						evento = Eventos.COMPROBAR_OFICINA;
						break;
					case "proyecto":
						evento = Eventos.COMPROBAR_PROYECTO;
						break;
					case "tarea":
						evento = Eventos.COMPROBAR_TAREA;
						break;
					default:
						GUIMSG.showMessage(this.entity + " no es una entidad valida", String.format(FROM_WHERE, this.entity.toUpperCase()), true);
						break;
					}
					if (evento != null) {
						Controlador.getInstancia().accion(new Context(evento, id));
						this.setVisible(false);
						tId.setText("");						
					}
					
					
				}catch(NumberFormatException nfe){
					GUIMSG.showMessage("Campos erroneos", String.format(FROM_WHERE, this.entity.toUpperCase()), true);
				}
				
			}

		
		});
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(3*100, 150));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
//		this.setBounds(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200, 400, 400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		
	}

}
