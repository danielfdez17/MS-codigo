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
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIAltaOficina extends JFrame implements GUI{
	private static final long serialVersionUID = 1L;
	private static final String FROM_WHERE = "GUIAltaOficina";
	private JTextField textNombre;
	private static GUIAltaOficina instancia;
	
	public GUIAltaOficina() {
		this.initGUI();
	}
	
	public static synchronized GUIAltaOficina getInstancia() {
		if (instancia == null) instancia = new GUIAltaOficina();
		return instancia;
	}
	
	private void initGUI() {
		setLayout(null);
		this.setTitle("Alta Oficina");
		
		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panel_alta = new JPanel(new GridLayout(1, 1));
		mainPanel.add(panel_alta, BorderLayout.CENTER);
			
		// Oficina nombre
		
		JLabel lNombre = new JLabel("Nombre: ");
		this.textNombre = new JTextField();
		this.textNombre.setPreferredSize(new Dimension(100, 30));
		

		JPanel pNombre = new JPanel();
		pNombre.add(lNombre);
		pNombre.add(this.textNombre);
		panel_alta.add(pNombre);
		
		// ACCEPT BUTTON
		JButton button = new JButton("Alta oficina");
		JPanel panelButton = new JPanel();

		panelButton.add(button);
		mainPanel.add(panelButton, BorderLayout.PAGE_END);

		JButton emptyButton = new JButton("Vaciar");
		emptyButton.addActionListener((e) -> {
			this.textNombre.setText("");
		});
		
		button.addActionListener((e)->{

			if (this.areTextFieldsEmpty()) {

				GUIMSG.showMessage("Faltan campos por rellenar", "ALTA OFICINA", true);

			}
			else {

				String nombre = this.textNombre.getText();
				TOficina ofi = new TOficina(nombre);
				this.clear();
				this.setVisible(false);
				Controlador.getInstancia().accion(new Context(Eventos.ALTA_OFICINA, ofi));


			}
		});
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int)dims_frame.getWidth(), (int)dims_frame.getHeight());


		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
	}
	
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case ALTA_OFICINA_OK:
			TOficina to = (TOficina)context.getDatos();
			GUIMSG.showMessage("Oficina dada de alta correctamente \n" + to.toString(), FROM_WHERE, false);
			break;
		case ALTA_OFICINA_KO:
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
		this.textNombre.setText(t);
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAOficina.OFICINA_ACTIVA:
			return "Oficina ya existe en la BBDD";
		case SAOficina.OFICINA_INACTIVA:
			return "La oficina estaba inactiva en la BBDD, queda reactivada";
		case SAOficina.ERROR_INESPERADO:
			return Utils.ERROR_INESPERADO;
		case SAOficina.ERROR_SINTACTICO:
			return "Nombre de oficina no valido";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textNombre.getText().isEmpty();
	}
	
}