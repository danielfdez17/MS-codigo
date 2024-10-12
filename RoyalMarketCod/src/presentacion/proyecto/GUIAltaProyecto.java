/**
 * 
 */
package presentacion.proyecto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.producto.SAProducto;
import negocio.producto.TProducto;
import negocio.proyecto.SAProyecto;
import negocio.proyecto.TProyecto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.producto.GUIAltaProducto;
import presentacion.viewhelper.GUI;
import utilities.Utils;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author danie
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public class GUIAltaProyecto extends JFrame implements GUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fila = 1;
	private static final String FROM_WHERE = "GUIAltaProyecto";
	private static GUIAltaProyecto instancia;
	
	public GUIAltaProyecto(){
		this.iniGUI();
	}	

	private void iniGUI() {
		setLayout(null);
		this.setTitle("Alta proyecto");

		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panel_alta = new JPanel(new GridLayout(fila, 1));
		mainPanel.add(panel_alta);
		
		
		JLabel lNombre = new JLabel("Nombre: ");
		JTextField tNombre = new JTextField();
		tNombre.setPreferredSize(new Dimension(100,30));
		
		JPanel pNombre = new JPanel();
		pNombre.add(lNombre);
		pNombre.add(tNombre);
		panel_alta.add(pNombre);


		JButton button = new JButton("Alta");
		JPanel panelButton =new JPanel();
		
		panelButton.add(button);
		mainPanel.add(panelButton, BorderLayout.PAGE_END);	
		
    	JButton emptyButton = new JButton("Vaciar");
    	emptyButton.addActionListener((e) -> {
    		tNombre.setText("");
    	});
    	panelButton.add(emptyButton);
		
		button.addActionListener((e)->{
			
			if(tNombre.getText().isEmpty()){
				
				GUIMSG.showMessage("Faltan campos por rellenar", "ALTA PROYECTO", true);
				
			}
			else{
				
				try{ 
					String nombre = tNombre.getText();
					TProyecto p = new TProyecto(nombre);
					
					Controlador.getInstancia().accion(new Context(Eventos.ALTA_PROYECTO, p));
					
					this.setVisible(false);
		    		tNombre.setText("");
					
				}catch(NumberFormatException nfe){
			
					GUIMSG.showMessage("Campos erroneos", "ALTA PROYECTO", true);
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

	public static GUIAltaProyecto getInstancia() {
		
		if (instancia == null) instancia = new GUIAltaProyecto();
		return instancia;
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case ALTA_PROYECTO_OK:
			GUIMSG.showMessage("Proyecto dada de alta correctamente \n", FROM_WHERE, false);
			break;
		case ALTA_PROYECTO_KO:
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
		case SAProyecto.ERROR_PROYECTO_DUPLICADO:
			return "Proyecto ya existente en BBDD";
		case SAProyecto.ERROR_SINTACTICO_NOMBRE_PROYECTO:
			return "Nombre del proyecto no valido";
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