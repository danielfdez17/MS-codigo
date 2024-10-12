package negocio.factura;

import java.util.HashMap;
import java.util.Map.Entry;

public class TCarrito {
	
	private static final String SALTO = "\n";
	
	private TFactura factura;
	private HashMap<Integer, TLineaFactura> lineas;
	private double precio;
	
	public TCarrito() {
		this.setFactura(null);
		this.setLineas(new HashMap<Integer, TLineaFactura>());
		this.setPrecio(0);
	}
	public TCarrito(TFactura factura) {
		this();
		this.setFactura(factura);
	}
	public TCarrito(TFactura factura, HashMap<Integer, TLineaFactura> lineas, double precio) {
		this.setFactura(factura);
		this.setLineas(lineas);
		this.setPrecio(precio);
	}
	public TFactura getFactura() {
		return factura;
	}
	public void setFactura(TFactura factura) {
		this.factura = factura;
	}
	public HashMap<Integer, TLineaFactura> getLineas() {
		return lineas;
	}
	public void setLineas(HashMap<Integer, TLineaFactura> lineas) {
		this.lineas = lineas;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	@Override
	public String toString() {
		String s = "Factura = " + factura.toString() + SALTO + 
				"Precio = " + precio + SALTO +
				"Lineas: " + SALTO;
		for (Entry<Integer, TLineaFactura> l : this.lineas.entrySet()) {
			s += l.toString();
		}
		return s;
	}
}
