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
import utilities.Utils;

public class GUIBuscarOficina extends JFrame implements GUI{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final String FROM_WHERE = "GUIBuscarOficina";
	private static GUIBuscarOficina instancia;


	public GUIBuscarOficina() {
		super();
		iniGUI();

	}
	public static synchronized GUIBuscarOficina getInstancia() {

		if (instancia == null) instancia = new GUIBuscarOficina();
		return instancia;
	}

	public void iniGUI() {
		setLayout(null);
		this.setTitle(FROM_WHERE);

		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);

		// OPEN PANEL
		JPanel panel_alta = new JPanel(new GridLayout(1, 1));
		mainPanel.add(panel_alta, BorderLayout.PAGE_START);


		JLabel lID = new JLabel("Id: ");
		JTextField tID = new JTextField();
		tID.setPreferredSize(new Dimension(50, 30));

		JPanel pNombre = new JPanel();
		pNombre.add(lID);
		pNombre.add(tID);
		panel_alta.add(pNombre);


		JButton button = new JButton("Buscar");
		JPanel panelButton = new JPanel();

		panelButton.add(button);


		JButton emptyButton = new JButton("Vaciar");
		emptyButton.addActionListener((e) -> {
			tID.setText("");
		});
		panelButton.add(emptyButton);
		mainPanel.add(panelButton, BorderLayout.PAGE_END);


		button.addActionListener((e)->{

			if (tID.getText().isEmpty()) {

				GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);

			}
			else {

				try {
					int id = Integer.parseInt(tID.getText());


					Controlador.getInstancia().accion(new Context(Eventos.BUSCAR_OFICINA, id));

					this.setVisible(false);
					tID.setText("");

				}
				catch (NumberFormatException nfe) {

					GUIMSG.showMessage("Campos erroneos", FROM_WHERE, true);
				}

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
		case BUSCAR_OFICINA_OK:
			TOficina p = (TOficina)context.getDatos();
			GUIMSG.showMessage("Oficina encontrada \n " + p.toString(), FROM_WHERE, false);
			break;
		case BUSCAR_OFICINA_KO:
			GUIMSG.showMessage("La oficina con id " + (int)context.getDatos() + " no existe en la BBDD", FROM_WHERE, true);
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
		switch (error) {
			case SAOficina.OFICINA_INEXISTENTE:
				return "Oficina no existe en la BBDD";
			default:
				return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
		public boolean areTextFieldsEmpty() {
		return false;
	}

}
