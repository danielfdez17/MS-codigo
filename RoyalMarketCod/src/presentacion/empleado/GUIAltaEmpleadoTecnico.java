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
import negocio.empleado.TEmpleadoTecnico;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

public class GUIAltaEmpleadoTecnico extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIAltaEmpleadoTecnico";
	private static GUIAltaEmpleadoTecnico instancia;
	private static final int BUTTONS = 7;
	
	private JTextField textTlf, textNif, textNombre, textDireccion, textReporteTrabajo, textSueldo, textIdOficina;
	
	public GUIAltaEmpleadoTecnico() {
		this.initGUI();
	}
	
	public synchronized static GUIAltaEmpleadoTecnico getInstancia() {
		if (instancia == null) instancia = new GUIAltaEmpleadoTecnico();
		return instancia;
	}
	
	private void initGUI() {
		setLayout(null);
		this.setTitle("Alta Empleado Tecnico");

		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panel_alta = new JPanel(new GridLayout(BUTTONS, 1));
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
				
				GUIMSG.showMessage("Faltan campos por rellenar", "ALTA EMPLEADO TECNICO", true);
				
			}
			
			else{
				
				try{ 
					String nombre = tNombre.getText(), nif = tNIF.getText(), dir = tDir.getText();
					int tlf = Integer.parseInt(tTlf.getText()), oficina = Integer.parseInt(tOfi.getText());
					double sueldo = Integer.parseInt(tS.getText());
					
					TEmpleadoTecnico emp = new TEmpleadoTecnico(tlf, nif, nombre, dir, "", sueldo, oficina);
					
					Controlador.getInstancia().accion(new Context(Eventos.ALTA_EMPLEADO_TECNICO, emp));
					
					this.setVisible(false);
		    		tNIF.setText("");
		    		tTlf.setText("");
		    		tDir.setText("");
		    		tS.setText("");
		    		tOfi.setText("");
		    		
					
				}catch(NumberFormatException nfe){
			
					GUIMSG.showMessage("Campos erroneos", "ALTA EMPLEADO TECNICO", true);
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
		case ALTA_EMPLEADO_TECNICO_OK:
			TEmpleadoTecnico tecnico = (TEmpleadoTecnico) context.getDatos();
			GUIMSG.showMessage("Se ha dado de alta al empleado tecnico\n" + tecnico.toString(), FROM_WHERE, false);
			break;
		case ALTA_EMPLEADO_TECNICO_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		String t = "";
		this.textTlf.setText(t);
		this.textNif.setText(t);
		this.textNombre.setText(t);
		this.textDireccion.setText(t);
		this.textReporteTrabajo.setText(t);
		this.textSueldo.setText(t);
		this.textIdOficina.setText(t);
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAEmpleado.ERROR_SINTACTICO:
			return "Error sintactico";
		case SAEmpleado.EMPLEADO_ACTIVO:
			return "El empleado ya estaba activo";
		case SAEmpleado.EMPLEADO_INACTIVO:
			return "El empleado estaba inactivo, queda reactivado";
		case SAEmpleado.OFICINA_INACTIVA:
			return "La oficina asociada al empleado esta inactiva";
		case SAEmpleado.OFICINA_INEXISTENTE:
			return "La oficina asociada al empleado no existe";
		case SAEmpleado.ERROR_INESPERADO:
			return "Ha ocurrido un error inesperado que no se sabe cual es";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textTlf.getText().isEmpty() &&
				this.textNif.getText().isEmpty() && 
				this.textNombre.getText().isEmpty() &&
				this.textDireccion.getText().isEmpty() &&
				this.textReporteTrabajo.getText().isEmpty() &&
				this.textSueldo.getText().isEmpty() &&
				this.textIdOficina.getText().isEmpty();
	}
}