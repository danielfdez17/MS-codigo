package presentacion.proveedor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.proveedor.SAProveedor;
import negocio.proveedor.TProveedor;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.GUIComprobar;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIActualizarProveedor extends JFrame implements GUI {

	private TProveedor proveedor;
	private int fila = 5; 
	private GUIComprobar guiComprobar;
	private static GUIActualizarProveedor instancia;
	private static final String FROM_WHERE = "GUIActualizarProveedor";
	private JTextField textId, textDireccion, textTlf, textNif;
	
	public GUIActualizarProveedor() {
		this.guiComprobar = new GUIComprobar("proveedor");
		this.setVisible(false);
	}
	
	public synchronized static GUIActualizarProveedor getInstancia() {
		if (instancia == null) instancia = new GUIActualizarProveedor();
		return instancia;
	}
	
	private void initGUI(){
		
		
		this.setTitle(FROM_WHERE);

		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panelActualizar =  new JPanel(new GridLayout(fila, 1));
		mainPanel.add(panelActualizar);
		
		Panel panelId = new Panel("ID");
		this.textId = panelId.getJTextField();
		this.textId.setText(this.proveedor.getId() + "");
		panelActualizar.add(panelId.getJPanel());
		
		Panel panelTlf = new Panel("Telefono: ");
		this.textTlf = panelTlf.getJTextField();
		this.textTlf.setText(this.proveedor.getTlf() + "");
		panelActualizar.add(panelTlf.getJPanel());
		
		Panel panelNif = new Panel("NIF");
		this.textNif = panelNif.getJTextField();
		this.textNif.setText(this.proveedor.getNif());
		panelActualizar.add(panelNif.getJPanel());
		
		Panel panelDireccion = new Panel("Direccion");
		this.textDireccion = panelDireccion.getJTextField();
		this.textDireccion.setText(this.proveedor.getDireccion());
		panelActualizar.add(panelDireccion.getJPanel());
		
		JButton emptyButton = new JButton("Vaciar");
		emptyButton.addActionListener((e) -> {
			this.clear();
		});
		
		JButton restoreButton = new JButton("Restarurar datos");
		restoreButton.addActionListener((e) -> {
			this.textId.setText(this.proveedor.getId() + "");
			this.textTlf.setText(this.proveedor.getTlf() + "");
			this.textNif.setText(this.proveedor.getNif());
			this.textDireccion.setText(this.proveedor.getDireccion());
		});
		
		
		JButton buttonActualizar = new JButton("Actualizar");
		JPanel panelButton = new JPanel();
		panelActualizar.add(panelButton);
		panelButton.add(buttonActualizar);
		panelButton.add(emptyButton);
		panelButton.add(restoreButton);
		
		
		buttonActualizar.addActionListener((e)->{
			
			if(this.areTextFieldsEmpty()) {
				GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
			}
			else{
				try{
					int id = Integer.parseInt(this.textId.getText());
					int tlf = Integer.parseInt(this.textTlf.getText());
					String nif = this.textNif.getText();
					String direccion = this.textDireccion.getText();
					boolean activo = this.proveedor.isActivo();
					Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_PROVEEDOR, new TProveedor(id, tlf, nif, direccion, activo)));
					this.setVisible(false);
				} catch(NumberFormatException nfe){
					GUIMSG.showMessage("El campo 'Telefono' solo requiere de numeros", FROM_WHERE, true);
				}
			}	
		});
		
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension((fila +1)*100, 300));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
	}
	
	public void setProveedor(TProveedor p){
		this.proveedor = p;
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
		case ACTUALIZAR_PROVEEDOR_OK:
			GUIMSG.showMessage("Proveedor actualizado \n", FROM_WHERE, false);
			break;
		case ACTUALIZAR_PROVEEDOR_KO:
			GUIMSG.showMessage("No se pudo actualizar dicho proveedor \n", FROM_WHERE, true);
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
		this.textNif.setText("");
		this.textTlf.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAProveedor.ERROR_SINTACTICO:
			return "Error sintactico";
		case SAProveedor.PROVEEDOR_INEXSITENTE:
			return "El proveedor no existe";
		case SAProveedor.ERROR_BBDD:
			return "Error en la BBDD";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty() &&
				this.textDireccion.getText().isEmpty() &&
				this.textNif.getText().isEmpty() &&
				this.textTlf.getText().isEmpty();
	}
}
