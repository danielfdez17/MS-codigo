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

import negocio.factura.TFactura;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIBuscarFactura extends JFrame implements GUI {
	
	private JTextField textId;
	private static GUIBuscarFactura instancia;
	private static final String FROM_WHERE = "GUIBuscarFactura";
	
	public GUIBuscarFactura() {
		this.initGUI();
	}
	
	public synchronized static GUIBuscarFactura getInstancia() {
		if (instancia == null) instancia = new GUIBuscarFactura();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle(FROM_WHERE);
		
		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// SEARCH PANEL
		JPanel panelBuscar = new JPanel(new GridLayout(1, 1));
		
		// ID
		JPanel panelIdFactura = new JPanel(new GridLayout(1, 2));
		JLabel labelIdFactura = new JLabel("ID: ");
		this.textId = new JTextField(10);
		panelIdFactura.add(labelIdFactura);
		panelIdFactura.add(this.textId);
		panelBuscar.add(panelIdFactura);
		
		// CREATE BUTTON
		JButton button_buscar = new JButton("Buscar");
		button_buscar.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int id = Integer.parseInt(textId.getText());
					this.clear();
					setVisible(false);
					Controlador.getInstancia().accion(new Context(Eventos.BUSCAR_FACTURA, id));
				} catch(NumberFormatException nfe) {
					this.clear();
					GUIMSG.showMessage("El campo 'ID' solo requiere de numeros", FROM_WHERE, true);
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
		buttonsPanel.add(button_buscar);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		mainPanel.add(panelBuscar, BorderLayout.CENTER);
		
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
	public void clear() {
		this.textId.setText("");
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case BUSCAR_FACTURA_OK:
			TFactura factura = (TFactura) context.getDatos();
			GUIMSG.showMessage("Factura encontrada\n" + factura.toString(), FROM_WHERE, false);
			break;
		case BUSCAR_FACTURA_KO:
			GUIMSG.showMessage("Factura inexistente", FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public String getErrorMsg(int error) {
		return null;
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty();
	}
}