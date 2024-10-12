package presentacion.factura;

import javax.swing.JFrame;

import negocio.factura.TCarrito;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUIFACTURA extends JFrame {
	
	private static GUIFACTURA instancia;
	protected TCarrito carrito;

	public synchronized static GUIFACTURA getInstancia() {
		if (instancia == null) instancia = new GUIFACTURAImp();
		instancia.setVisible(true);
		return instancia;
	}

	public abstract void actualizar(Context context);
	public abstract TCarrito getCarrito();
	public abstract void setCarrito(TCarrito carrito);
}