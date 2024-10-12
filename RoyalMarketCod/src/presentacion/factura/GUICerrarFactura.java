package presentacion.factura;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import negocio.factura.SAFacturaImp;
import negocio.factura.TCarrito;
import negocio.factura.TFactura;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;
@SuppressWarnings("serial")

public class GUICerrarFactura extends JFrame implements GUI {
	
	private static GUICerrarFactura instancia;
	private static final String FROM_WHERE = "GUICerrarFactura";
	public GUICerrarFactura() {
		this.initGUI();
	}
	
	public synchronized static GUICerrarFactura getInstancia() {
		if (instancia == null) instancia = new GUICerrarFactura();
		return instancia;
	}
    
	private void initGUI() {
		this.setTitle("Cerrar factura");
		
		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// DELETE PANE
		JPanel panel_cerrar = new JPanel(new GridLayout(1, 1));
		
		// DELETE BUTTON
		JButton button_cerrar = new JButton("Cerrar");
		button_cerrar.addActionListener((e) -> {
			if (GUIFACTURA.getInstancia().getCarrito() == null) GUIMSG.showMessage("No hay factura que cerrar", FROM_WHERE, true);
			else Controlador.getInstancia().accion(new Context(Eventos.CERRAR_FACTURA, GUIFACTURA.getInstancia().getCarrito()));
		});
		
		// BUTTONS PANEL
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(button_cerrar);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		
		mainPanel.add(panel_cerrar, BorderLayout.CENTER);
		
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
	public void clear() {}
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case CERRAR_FACTURA_OK:
			TCarrito carrito = (TCarrito) context.getDatos();
			GUIMSG.showMessage("Factura cerrada\n" + carrito.toString() , FROM_WHERE, false);
			GUIFACTURA.getInstancia().setCarrito(null);
			break;
		case CERRAR_FACTURA_KO:
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
			return "No hay una factura abierta para poder cerrarla";
		case SAFacturaImp.CLIENTE_INEXISTENTE:
			return "El cliente asociado a la factura no existe";
		case SAFacturaImp.CLIENTE_INACTIVO:
			return "El cliente asociado a la factura no esta activo";
		case SAFacturaImp.ERROR_BBDD:
			return "No se ha podido cerrar la factura por error en la BBDD";
		case SAFacturaImp.PRODUCTO_INEXISTENTE:
			return "Un producto del carrito no existe";
		case SAFacturaImp.PRODUCTO_INACTIVO:
			return "Un producto del carrito no esta activo";
		case SAFacturaImp.STOCK_INSUFICIENTE:
			return "Un producto del carrito no tiene stock suficiente";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return false;
	}
}