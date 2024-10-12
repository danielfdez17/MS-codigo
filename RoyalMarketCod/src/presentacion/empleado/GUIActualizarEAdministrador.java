/**
 * 
 */
package presentacion.empleado;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.empleado.SAEmpleado;
import negocio.empleado.TEmpleadoAdministrador;
import negocio.proyecto.TProyecto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.GUIComprobar;
import utilities.Utils;

/** 
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @generated "UML to JPA (com.ibm.xtools.transform.uml2.ejb3.java.jpa.internal.UML2JPATransform)"
 */
public class GUIActualizarEAdministrador extends JFrame implements GUI{

	private static final long serialVersionUID = 1L;
	private static GUIActualizarEAdministrador instancia;
	private int fila = 6;
	private TEmpleadoAdministrador p;
	private static final String FROM_WHERE = "GUIActualizarEAdministrador";
	private GUIComprobar guiComprobar;

	public GUIActualizarEAdministrador(){
		this.guiComprobar = new GUIComprobar("empleado");
	}
	
	public void init(){
		this.initGUI();
	}
	
	public void setEmpleado(TEmpleadoAdministrador p){
		this.p = p;
	}
	
	private void initGUI() {
		setLayout(null);
		this.setTitle("Actualizar Empleado Administrador");

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
		
		JLabel lNIF = new JLabel("NIF: ");
		JTextField tNIF = new JTextField();
		tNIF.setPreferredSize(new Dimension(100,30));
		
		JPanel pNif = new JPanel();
		pNif.add(lNIF);
		pNif.add(tNIF);
		panel_alta.add(pNif);
		
		JLabel lTlf = new JLabel("Teléfono: ");
		JTextField tTlf = new JTextField();
		tTlf.setPreferredSize(new Dimension(100,30));
		
		JPanel pTlf = new JPanel();
		pTlf.add(lTlf);
		pTlf.add(tTlf);
		panel_alta.add(pTlf);
		
		JLabel lDir = new JLabel("Dirrección: ");
		JTextField tDir = new JTextField();
		tDir.setPreferredSize(new Dimension(100,30));
		
		JPanel pDir = new JPanel();
		pDir.add(lDir);
		pDir.add(tDir);
		panel_alta.add(pDir);
		
		JLabel lS = new JLabel("Sueldo: ");
		JTextField tS = new JTextField();
		tS.setPreferredSize(new Dimension(100,30));
		
		JPanel pS = new JPanel();
		pS.add(lS);
		pS.add(tS);
		panel_alta.add(pS);
		
		JLabel lOfi = new JLabel("Oficina: ");
		JTextField tOfi = new JTextField();
		tOfi.setPreferredSize(new Dimension(100,30));
		
		JPanel pOfi = new JPanel();
		pOfi.add(lOfi);
		pOfi.add(tOfi);
		panel_alta.add(pOfi);

		JLabel lrep = new JLabel("Reporte Semanal: ");
		JTextField trep = new JTextField();
		trep.setPreferredSize(new Dimension(100,30));
		
		JPanel prep = new JPanel();
		prep.add(lrep);
		prep.add(trep);
		panel_alta.add(prep);


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
			
			if(tNombre.getText().isEmpty() || tNIF.getText().isEmpty() || tTlf.getText().isEmpty() || tDir.getText().isEmpty() || tS.getText().isEmpty() || tOfi.getText().isEmpty()){
				
				GUIMSG.showMessage("Faltan campos por rellenar", "ACTUALIZAR EMPLEADO ADMINISTRADOR", true);
				
			}
			
			else{
				
				try{ 
					String nombre = tNombre.getText(), nif = tNIF.getText(), dir = tDir.getText(), rep = trep.getText();
					int tlf = Integer.parseInt(tTlf.getText()), oficina = Integer.parseInt(tOfi.getText());
					double sueldo = Integer.parseInt(tS.getText());
					
					TEmpleadoAdministrador emp = new TEmpleadoAdministrador(tlf, nif, nombre, dir, rep, sueldo, oficina);
					
					Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_EMPLEADO_ADMINISTRADOR, emp));
					
					this.setVisible(false);
		    		tNIF.setText("");
		    		tTlf.setText("");
		    		tDir.setText("");
		    		tS.setText("");
		    		tOfi.setText("");
		    		trep.setText("");
		    		
					
				}catch(NumberFormatException nfe){
			
					GUIMSG.showMessage("Campos erroneos", "ACTUALIZAR EMPLEADO ADMINISTRADOR", true);
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
	public static GUIActualizarEAdministrador getInstancia() {
		if (instancia == null) instancia = new GUIActualizarEAdministrador();
		return instancia;
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case ACTUALIZAR_EMPLEADO_ADMINISTRADOR_OK:
			GUIMSG.showMessage("Proyecto dada de alta correctamente \n", FROM_WHERE, false);
			break;
		case ACTUALIZAR_EMPLEADO_ADMINISTRADOR_KO:
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
		case SAEmpleado.ERROR_SINTACTICO:
			return "Error en los campos: Introduzca los datos correctos";
		case SAEmpleado.EMPLEADO_INEXISTENTE:
			return "Empleado no existe";
		case SAEmpleado.OFICINA_INACTIVA:
			return "La oficina introducida no se encuentra activa";
		case SAEmpleado.OFICINA_INEXISTENTE:
			return "La oficina introducida no existe";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	public void comprobar(boolean b) {
		this.guiComprobar.setVisible(b);
	}

}