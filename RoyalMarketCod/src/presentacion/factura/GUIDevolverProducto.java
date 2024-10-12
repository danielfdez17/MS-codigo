package presentacion.factura;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.factura.SAFactura;
import negocio.factura.TProductoDevuelto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIDevolverProducto extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIDevolverProducto";
	private JTextField textIdFactura, textIdProducto, textCantidad;
	private static GUIDevolverProducto instancia;
	
	public GUIDevolverProducto() {
		this.initGUI();
	}
	
	public static synchronized GUIDevolverProducto getInstancia() {
		if (instancia == null) instancia = new GUIDevolverProducto();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Devolver producto");
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel panelDevolver = new JPanel(new GridLayout(3, 1));
		
		Panel panelIdFactura = new Panel("ID factura");
		this.textIdFactura = panelIdFactura.getJTextField();
		panelDevolver.add(panelIdFactura.getJPanel());
		
		Panel panelIdProducto = new Panel("ID producto");
		this.textIdProducto = panelIdProducto.getJTextField();
		panelDevolver.add(panelIdProducto.getJPanel());
		
		Panel panelCantidad = new Panel("Cantidad");
		this.textCantidad = panelCantidad.getJTextField();
		panelDevolver.add(panelCantidad.getJPanel());
		
		JButton buttonDevolver = new JButton("Devolver");
		buttonDevolver.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int idFactura = Integer.parseInt(this.textIdFactura.getText());
					int idProducto = Integer.parseInt(this.textIdProducto.getText());
					int cantidad = Integer.parseInt(this.textCantidad.getText());
					this.setVisible(false);
					this.clear();
					Controlador.getInstancia().accion(new Context(Eventos.DEVOLVER_PRODUCTO, new TProductoDevuelto(idFactura, idProducto, cantidad)));
				} catch (NumberFormatException nfe) {
					this.clear();
					GUIMSG.showMessage("Todos los campos solo requieren de numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
			}
		});
		
		JButton emptyTextButton = new JButton("Vaciar");
		emptyTextButton.addActionListener((e) -> {
			this.clear();
		});
		
		// BUTTONS PANEL
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(emptyTextButton);
		buttonsPanel.add(buttonDevolver);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		mainPanel.add(panelDevolver, BorderLayout.CENTER);
		
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
		case DEVOLVER_PRODUCTO_OK:
			GUIMSG.showMessage("Se ha devuelto el producto de la factura " + (int)context.getDatos(), FROM_WHERE, false);
			break;
		case DEVOLVER_PRODUCTO_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}
	
	@Override
	public void clear() {
		this.textIdFactura.setText("");
		this.textIdProducto.setText("");
		this.textCantidad.setText("");
	}
	
	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAFactura.ERROR_BBDD:
			return "Error en la BBDD";
		case SAFactura.PRODUCTO_INEXISTENTE:
			return "El producto no existe en la BBDD";
		case SAFactura.ELIMINAR_MAS_PRODUCTOS_QUE_COMPRADOS:
			return "No se pueden eliminar mas productos que los comprados";
		case SAFactura.PRODUCTO_NO_ANYADIDO_AL_CARRITO:
			return "El producto no se ha comprado en dicha factura";
		case SAFactura.FACTURA_INEXISTENTE:
			return "La factura no existe";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}
	@Override
	public boolean areTextFieldsEmpty() {
		return this.textIdFactura.getText().isEmpty() && 
				this.textIdProducto.getText().isEmpty() &&
				this.textCantidad.getText().isEmpty();
	}
}
