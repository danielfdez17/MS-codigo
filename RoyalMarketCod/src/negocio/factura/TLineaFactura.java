package negocio.factura;

public class TLineaFactura {
	
	private static final String SALTO = "\n";
	
	private int idFactura;
	private int idProducto;
	private int cantidad;
	private double precioProducto;

	public TLineaFactura(int idProducto, int cantidad) {
		this.setIdProducto(idProducto);
		this.setCantidad(cantidad);
	}
	public TLineaFactura(int idProducto, int cantidad, double precioProducto) {
		this.setIdProducto(idProducto);
		this.setCantidad(cantidad);
		this.setPrecioProducto(precioProducto);
	}
	public TLineaFactura(int idFactura, int idProducto, int cantidad, double precioProducto) {
		this.setIdFactura(idFactura);
		this.setIdProducto(idProducto);
		this.setCantidad(cantidad);
		this.setPrecioProducto(precioProducto);
	}

	public int getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public double getPrecioProducto() {
		return precioProducto;
	}
	public void setPrecioProducto(double precioProducto) {
		this.precioProducto = precioProducto;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	@Override
	public String toString() {
		return "ID factura: " + this.idFactura + SALTO +
				"ID producto: " + this.idProducto + SALTO +
				"Cantidad: " + this.cantidad + SALTO +
				"Precio producto: " + this.precioProducto + SALTO;
	}
}