package negocio.trabajador;

public class TTrabajador {
	
	protected static final String SALTO = "\n";
	protected int id;
	protected int tlf;
	protected String nif;
	protected String nombre;
	protected String direccion;
	protected int idAlmacen;
	protected boolean activo;

	public TTrabajador(int tlf, String nif, String nombre, String direccion, int idAlmacen) {
		this.tlf = tlf;
		this.nif = nif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.idAlmacen = idAlmacen;
		this.activo = true;
	}
	
	public TTrabajador(int id, int tlf, String nif, String nombre, String direccion, int idAlmacen, boolean activo) {
		this.id = id;
		this.tlf = tlf;
		this.nif = nif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.idAlmacen = idAlmacen;
		this.activo = activo;
	}

	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTlf() {
		return this.tlf;
	}
	public void setTlf(Integer tlf) {
		this.tlf = tlf;
	}
	public String getNIF() {
		return this.nif;
	}
	public void setNIF(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return this.nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDireccion() {
		return this.direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public int getIdAlmacen() {
		return this.idAlmacen;
	}
	public void setIdAlmacen(int idAlmacen) {
		this.idAlmacen = idAlmacen;
	}
	public boolean isActivo() {
		return this.activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String toString() {
		return "ID: " + this.id + SALTO +
				"Telefono: " + this.tlf + SALTO +
				"Nombre: " + this.nombre + SALTO +
				"Direccion: " + this.direccion + SALTO +
				"ID almacen: " + this.idAlmacen + SALTO;
	}
}