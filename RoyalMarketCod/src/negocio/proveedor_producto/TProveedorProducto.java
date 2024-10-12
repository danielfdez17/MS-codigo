package negocio.proveedor_producto;

public class TProveedorProducto {

	private static final String SALTO = "\n";
	private int idProveedor;
	private int idProducto;
	
	public TProveedorProducto(int idProveedor, int idProducto) {
		this.setIdProveedor(idProveedor);
		this.setIdProducto(idProducto);
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	@Override
	public String toString() {
		return "ID proveedor: " + this.idProveedor + SALTO +
				"ID producto: " + this.idProducto + SALTO;
	}
	
}