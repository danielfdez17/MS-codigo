package presentacion.proveedor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.proveedor.SAProveedor;
import negocio.proveedor_producto.TProveedorProducto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIDesvincularProducto extends JFrame implements GUI {
	
	private static final int FIELDS = 2;
	private static final String FROM_WHERE = "GUIDesvincularProducto";
	private JTextField textProveedor, textProducto;
	private static GUIDesvincularProducto instancia;
	
	public GUIDesvincularProducto() {
		this.initGUI();
	}
	
	public synchronized static GUIDesvincularProducto getInstancia() {
		if (instancia == null) instancia = new GUIDesvincularProducto();
		return instancia;
	}

	public void initGUI() {
		setTitle("Desvincular producto");

		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel panelDesvincular = new JPanel(new GridLayout(FIELDS, 1));
		
		Panel panelProveedor = new Panel("ID del proveedor");
		this.textProveedor = panelProveedor.getJTextField();
		panelDesvincular.add(panelProveedor.getJPanel());
		
		Panel panelProducto = new Panel("ID del producto");
		this.textProducto = panelProducto.getJTextField();
		panelDesvincular.add(panelProducto.getJPanel());
		

		//DesvincularButton
		JButton desvincularButton = new JButton("Desvincular");
		desvincularButton.addActionListener(e -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int idProveedor = Integer.parseInt(this.textProveedor.getText());
					int idProducto = Integer.parseInt(this.textProducto.getText());
					Controlador.getInstancia().accion(new Context(Eventos.DESVINCULAR_PRODUCTO, new TProveedorProducto(idProveedor, idProducto)));
				} catch (NumberFormatException nfe) {
					GUIMSG.showMessage("Los campos solo requieren de numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
			}
		});
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(desvincularButton);
		
		//CancelarButton
		JButton cancelButton = new JButton("Cancelar");
		cancelButton.addActionListener(e -> {
			this.setVisible(false);
			this.clear();
		});
		buttonsPanel.add(cancelButton);
		
		mainPanel.add(panelDesvincular, BorderLayout.CENTER);
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
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
		this.textProducto.setText("");
		this.textProveedor.setText("");
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case DESVINCULAR_PRODUCTO_OK:
			GUIMSG.showMessage("Producto desvinculado con exito\n" + context.getDatos().toString(), FROM_WHERE, false);
			break;	
		case DESVINCULAR_PRODUCTO_KO:
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
		case SAProveedor.YA_DESVINCULADOS:
			return "El producto no estaba vinculado";
		case SAProveedor.ERROR_BBDD:
			return "Error en la BBDD";
		case SAProveedor.PRODUCTO_INACTIVO:
			return "El producto no esta activo";
		case SAProveedor.PRODUCTO_INEXISTENTE:
			return "El producto no existe";
		case SAProveedor.PROVEEDOR_INACTIVO:
			return "El proveedor no esta activo";
		case SAProveedor.PROVEEDOR_INEXSITENTE:
			return "El proveedor no existe";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textProveedor.getText().isEmpty() &&
				this.textProducto.getText().isEmpty();
	}

}