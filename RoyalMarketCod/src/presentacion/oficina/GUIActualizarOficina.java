/**
 * 
 */
package presentacion.oficina;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.oficina.SAOficina;
import negocio.oficina.TOficina;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.GUIComprobar;
import utilities.Utils;
import presentacion.factoriaVistas.Context;


@SuppressWarnings("serial")
public class GUIActualizarOficina extends JFrame implements GUI{
	private static final String FROM_WHERE = "GUIActualizarOficina";
	private static GUIActualizarOficina instancia;
	private JTextField textIdOficina, textNombre;
	private GUIComprobar guiComprobar;
	private TOficina of;
	
	public GUIActualizarOficina() {
		this.guiComprobar = new GUIComprobar("oficina");
	}
	
	public static synchronized GUIActualizarOficina getInstancia() {
		if (instancia == null) instancia = new GUIActualizarOficina();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Actualiza Oficina");
		
		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panel_actualizar = new JPanel(new GridLayout(1, 1));
		mainPanel.add(panel_actualizar, BorderLayout.CENTER);
		
		// Oficina ID
		JLabel lId =new JLabel("Id de la oficina: ");
		JTextField tId = new JTextField("" + this.of.getId());
		tId.setEditable(false);
		tId.setPreferredSize(new Dimension(100,30));
		JPanel pId = new JPanel();
		pId.add(lId);
		pId.add(tId);
		panel_actualizar.add(pId);
		
		// Oficina nombre
		
		JLabel lNombre =new JLabel("Nombre: ");
		JTextField tNombre = new JTextField("" + this.of.getId());
		tNombre.setEditable(false);
		tNombre.setPreferredSize(new Dimension(100,30));
		JPanel pNombre = new JPanel();
		pId.add(lNombre);
		pId.add(tNombre);
		panel_actualizar.add(pNombre);
		
		JButton button = new JButton("Actualizar");
		JPanel panelButton =new JPanel();
		panelButton.add(button);
		panel_actualizar.add(panelButton);

		JButton emptyButton = new JButton("Vaciar");
		emptyButton.addActionListener((e) -> {
		  		tNombre.setText(this.of.getNombre());
		});
		panelButton.add(emptyButton);
		
		button.addActionListener((e)->{
			
			if(tNombre.getText().isEmpty()
						){
				
				GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
				
			}
			else{
				
				try{
					int id = Integer.parseInt(tId.getText());

					String nombre = tNombre.getText();
					
					TOficina ofc = new TOficina(id,nombre,true);
					
					Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_OFICINA, ofc));
					this.setVisible(false);
					
				}catch(NumberFormatException nfe){
					
					GUIMSG.showMessage("Campos erroneos", FROM_WHERE, true);
				}
				
			}	
		});
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension((2)*100, 300));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
//		this.setBounds(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200, 400, 400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
	}
		
	
	public void setOficina(TOficina o){
		this.of = o;
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
		case ACTUALIZAR_OFICINA_OK:
			TOficina pus = (TOficina) context.getDatos();
			GUIMSG.showMessage("Oficina actualizada \n" + pus.toString(), FROM_WHERE, false);
			break;
		case ACTUALIZAR_OFICINA_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		String t = "";
		this.textIdOficina.setText(t);
		this.textNombre.setText(t);
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAOficina.ERROR_INESPERADO:
			return Utils.ERROR_INESPERADO;
		case SAOficina.ERROR_SINTACTICO:
			return Utils.ERROR_SINTACTICO;
		case SAOficina.OFICINA_INEXISTENTE:
			return "La oficina no existe";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textIdOficina.getText().isEmpty() &&
				this.textNombre.getText().isEmpty();
	}

}