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
import negocio.factura.TLineaFactura;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIActualizarCarrito extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIActualizarCarrito";
	private static final int OPTIONS = 3;
	private JTextField textIdProducto;
	private JTextField textCantidad;
	private static GUIActualizarCarrito instancia;
	
	public GUIActualizarCarrito() {
		this.initGUI();
	}
	
	public synchronized static GUIActualizarCarrito getInstancia() {
		if (instancia == null) instancia = new GUIActualizarCarrito();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle(FROM_WHERE);
		
		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// ADD PANEL
		JPanel addPanel = new JPanel(new GridLayout(OPTIONS, 1));
		mainPanel.add(addPanel, BorderLayout.CENTER);

		// PRODUCT
		JPanel productPanel = new JPanel(new GridLayout(1, 2));
		JLabel productLabel = new JLabel("ID producto: ");
		this.textIdProducto = new JTextField(10);
		productPanel.add(productLabel);
		productPanel.add(this.textIdProducto);
		addPanel.add(productPanel);

		// AMOUNT
		JPanel amountPanel = new JPanel(new GridLayout(1, 2));
		JLabel amountLabel = new JLabel("Cantidad: ");
		this.textCantidad = new JTextField(10);
		amountPanel.add(amountLabel);
		amountPanel.add(this.textCantidad);
		addPanel.add(amountPanel);
		

		// ADD BUTTON
		JButton addButton = new JButton("Anyadir");
		addButton.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					TCarrito carrito = GUIFACTURA.getInstancia().getCarrito();
					int id_producto = Integer.parseInt(this.textIdProducto.getText());
					int cantidad = Integer.parseInt(this.textCantidad.getText());
					if (carrito != null) {
						GUIFACTURA.getInstancia().setCarrito(carrito);
						this.clear();
						setVisible(false);
						Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_CARRITO, new TLineaFactura(id_producto, cantidad)));
					}
					else {
						GUIMSG.showMessage("No hay una factura abierta", FROM_WHERE, true);
					}
				} catch(NumberFormatException nfe) {
					this.clear();
					GUIMSG.showMessage("Los campos 'ID producto' y 'Cantidad' solo requieren de numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
			}
		});
		
		// REMOVE BUTTON
		JButton removeButton = new JButton("Eliminar");
		removeButton.addActionListener((e) -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					TCarrito carrito = GUIFACTURA.getInstancia().getCarrito();
					int id_producto = Integer.parseInt(this.textIdProducto.getText());
					int cantidad = Integer.parseInt(this.textCantidad.getText());
					if (carrito != null) {
						GUIFACTURA.getInstancia().setCarrito(carrito);
						this.clear();
						setVisible(false);
						Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_CARRITO, new TLineaFactura(id_producto, -cantidad)));
					}
					else {
						GUIMSG.showMessage("No hay una factura abierta", FROM_WHERE, true);
					}
				} catch(NumberFormatException nfe) {
					this.clear();
					GUIMSG.showMessage("Los campos 'ID producto' y 'Cantidad' solo requieren de numeros", FROM_WHERE, true);
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
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 4));
		buttonsPanel.add(emptyTextButton);
		buttonsPanel.add(addButton);
		buttonsPanel.add(removeButton);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		mainPanel.add(addPanel, BorderLayout.CENTER);
		
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
		case ACTUALIZAR_CARRITO_OK:
			GUIFACTURA.getInstancia().setCarrito((TCarrito)context.getDatos());
			GUIMSG.showMessage("Carrito actualizado", FROM_WHERE, false);
			break;
		case ACTUALIZAR_CARRITO_KO:
			GUIMSG.showMessage("No se ha podido actualizar el carrito", FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		this.textIdProducto.setText("");
		this.textCantidad.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		return null;
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textCantidad.getText().isEmpty() 
				&& this.textIdProducto.getText().isEmpty();
	}
}
