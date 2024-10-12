package presentacion.almacen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.almacen.SAAlmacen;
import negocio.almacen.TAlmacen;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.GUIComprobar;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIActualizarAlmacen extends JFrame implements GUI {
	
	private static final int BUTTONS = 2;
	private static final String ALMACEN = "almacen";
	private static GUIActualizarAlmacen instancia;
	private GUIComprobar guiComprobar;
	private TAlmacen ta;
	private JTextField textId, textDireccion;
	private static final String FROM_WHERE = "GUIActualizarAlmacen";
	
	public GUIActualizarAlmacen() {
		this.guiComprobar = new GUIComprobar(ALMACEN);
	}
	
	public synchronized static GUIActualizarAlmacen getInstancia() {
		if (instancia == null) instancia = new GUIActualizarAlmacen();
		return instancia;
	}
	
	private void initGUI() {
		
		
		this.setTitle("Actualizar almacen");

		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panelActualizar =  new JPanel(new GridLayout(BUTTONS, 1));
		mainPanel.add(panelActualizar);
		
		
		Panel panelId = new Panel("ID");
		this.textId = panelId.getJTextField();
		this.textId.setText("" + this.ta.getId());
		this.textId.setEditable(false);
		panelActualizar.add(panelId.getJPanel());
		
		
		Panel panelDireccion = new Panel("Direccion");
		this.textDireccion = panelDireccion.getJTextField();
		this.textDireccion = panelDireccion.getJTextField();
		this.textDireccion.setText(this.ta.getDireccion());
		panelActualizar.add(panelDireccion.getJPanel());

		
		JButton buttonActualizar = new JButton("Actualizar");
		panelActualizar.add(buttonActualizar);
		
    	JButton buttonRecuperarDatos = new JButton("Recuperar datos");
    	buttonRecuperarDatos.addActionListener((e) -> {
    		this.textDireccion.setText(this.ta.getDireccion());
    	});
    	panelActualizar.add(buttonRecuperarDatos);
		
    	buttonActualizar.addActionListener((e) -> {
			
			if(this.areTextFieldsEmpty()){
				
				GUIMSG.showMessage("Faltan campos por rellenar", "ACTUALIZAR PRODUCTO", true);
				
			}
			else{
				
				try{
					int id = Integer.parseInt(textId.getText());
					String direccion = this.textDireccion.getText();
					TAlmacen almacen = new TAlmacen(id, direccion, ta.isActivo());
					
					Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_ALMACEN, almacen));
					this.setVisible(false);
					
				}catch(NumberFormatException nfe){
					
					GUIMSG.showMessage("Campos erroneos", "ACTUALIZAR ALMACEN", true);
				}
				
			}	
		});
		
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension((BUTTONS +1)*100, 300));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
	}
	
	public void setAlmacen(TAlmacen ta){
		this.ta = ta;
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
		case ACTUALIZAR_ALMACEN_OK:
			TAlmacen almacen = (TAlmacen)context.getDatos();
			GUIMSG.showMessage("Almacen actualizado \n" + almacen.toString(), FROM_WHERE, false);
			break;
		case ACTUALIZAR_ALMACEN_KO:
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
		this.textDireccion.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAAlmacen.ERROR_SINTACTICO:
			return "No se ha podido actualizar el almacen por error sintactico";
		case SAAlmacen.ERROR_BBDD:
			return "No se ha podido actualizar el almacen por error en la BBDD";
		case SAAlmacen.ALMACEN_INEXISTENTE:
			return "No se ha podido actualizar el almacen porque no existe";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty() &&
				this.textDireccion.getText().isEmpty();
	}

}