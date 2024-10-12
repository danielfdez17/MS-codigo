package presentacion.factura;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.factura.TCarrito;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIAbrirFactura extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIAbrirFactura";
	private JTextField textIdCliente;
	private static GUIAbrirFactura instancia;
	
	public GUIAbrirFactura() {
		this.initGUI();
	}
	
	public synchronized static GUIAbrirFactura getInstancia() {
		if (instancia == null) instancia = new GUIAbrirFactura();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Abrir factura");		
		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panel_abrir = new JPanel(new GridLayout(2, 1));
		mainPanel.add(panel_abrir, BorderLayout.CENTER);
		
		// Client ID
		JPanel panel_id_cliente = new JPanel(new GridLayout(1, 2));
		JLabel clientIDLabel = new JLabel("ID cliente: ");
		this.textIdCliente = new JTextField(10);
		panel_id_cliente.add(clientIDLabel);
		panel_id_cliente.add(this.textIdCliente);
		panel_abrir.add(panel_id_cliente);
		
		
		// OPEN BUTTON
		JButton openButton = new JButton("Abrir");
		openButton.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int id_cliente = Integer.parseInt(this.textIdCliente.getText());
					this.clear();
					setVisible(false);
					Controlador.getInstancia().accion(new Context(Eventos.ABRIR_FACTURA, id_cliente));
				} catch(NumberFormatException nfe) {
					this.clear();
					GUIMSG.showMessage("El campo 'ID cliente' solo requiere de numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
			}
		});
		// EMPTY JTEXTFIELDS BUTTON
		JButton emptyTextButton = new JButton("Vaciar");
		emptyTextButton.addActionListener((e) -> {
			this.clear();
		});
		
		// BUTTONS PANEL
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(emptyTextButton);
		buttonsPanel.add(openButton);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		mainPanel.add(panel_abrir, BorderLayout.CENTER);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(false);
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case ABRIR_FACTURA_OK:
			TCarrito carrito = (TCarrito) context.getDatos();
			GUIMSG.showMessage("Carrito generado para la factura abierta\n" + carrito.toString(), FROM_WHERE, false);
			break;
		case ABRIR_FACTURA_KO:
			GUIMSG.showMessage("Cliente inexistente", FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		this.textIdCliente.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		return null;
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textIdCliente.getText().isEmpty();
	}
}