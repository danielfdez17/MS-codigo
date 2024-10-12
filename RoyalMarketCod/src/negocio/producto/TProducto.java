package negocio.producto;

public class TProducto {
	
	private static final String SALTO = "\n";
	
	private int id;
	private String nombre;
	private double precio;
	private int stock;
	private boolean activo;
	private int idAlmacen;
	
	public TProducto(String nombre, double precio, int stock, int idAlmacen) {
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.idAlmacen = idAlmacen;
		this.activo = true;
	}
	
	public TProducto(int id, String nombre, double precio, int stock, boolean activo, int idAlmacen) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.stock = stock;
		this.activo = activo;
		this.idAlmacen = idAlmacen;
	}
	
	
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public double getPrecio() {
		return precio;
	}
	public int getStock() {
		return stock;
	}
	public boolean isActivo() {
		return activo;
	}
	public int getIdAlmacen() {
		return idAlmacen;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public void setIdAlmacen(int idAlmacen) {
		this.idAlmacen = idAlmacen;
	}

	@Override
	public String toString() {
		return  "ID: " + this.id + SALTO +
				"Nombre: " + this.nombre + SALTO +
				"Precio: " + this.precio + SALTO +
				"Stock: " + this.stock + SALTO + 
				"ID almacen: " + this.idAlmacen + SALTO;
	}
}