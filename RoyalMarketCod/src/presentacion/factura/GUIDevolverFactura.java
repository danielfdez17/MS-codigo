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

import negocio.factura.SAFacturaImp;
import negocio.factura.TFactura;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIDevolverFactura extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIDevolverFactura";
	private JTextField text_id_factura;
	private static GUIDevolverFactura instancia;
	
	
	public GUIDevolverFactura() {
		this.initGUI();
	}
	
	public synchronized static GUIDevolverFactura getInstancia() {
		if (instancia == null) instancia = new GUIDevolverFactura();
		return instancia;
	}
    
	private void initGUI() {
		this.setTitle("Devolucion de factura");
		
		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// DELETE PANE
		JPanel panel_devolver = new JPanel(new GridLayout(1, 1));
		
		// ID
		JPanel panel_id_factura = new JPanel(new GridLayout(1, 2));
		JLabel label_id_factura = new JLabel("ID: ");
		this.text_id_factura = new JTextField(10);
		panel_id_factura.add(label_id_factura);
		panel_id_factura.add(this.text_id_factura);
		panel_devolver.add(panel_id_factura);
		
		// CREATE BUTTON
		JButton deleteButton = new JButton("Devolver");
		deleteButton.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int id = Integer.parseInt(text_id_factura.getText());
					this.clear();
					setVisible(false);
					Controlador.getInstancia().accion(new Context(Eventos.DEVOLVER_FACTURA, id));
				} catch(NumberFormatException nfe) {
					this.clear();
					GUIMSG.showMessage("El campo 'ID' solo requiere de numeros", "DEVOLVER FACTURA", true);
				}
			}
			else {
				GUIMSG.showMessage("Faltan campos por rellenar", "DEVOLVER FACTURA", true);
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
		buttonsPanel.add(deleteButton);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		mainPanel.add(panel_devolver, BorderLayout.CENTER);
		
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
    	this.text_id_factura.setText("");
    }

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case DEVOLVER_FACTURA_OK:
			TFactura factura = (TFactura) context.getDatos();
			GUIMSG.showMessage("Factura devuelta" + factura.toString(), FROM_WHERE, false);
			break;
		case DEVOLVER_FACTURA_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAFacturaImp.FACTURA_INEXISTENTE:
			return "La factura no existe";
		case SAFacturaImp.PRODUCTO_INEXISTENTE:
			return "Un producto comprado en la factura ya no existe";
		case SAFacturaImp.ERROR_BBDD:
			return "No se ha podido devolver la factura por error en la BBDD";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.text_id_factura.getText().isEmpty();
	}
}