package negocio.cliente;

public class TCliente {
	
	protected static final String SALTO = "\n";

	protected int id;
	protected int tlf;
	protected String nif;
	protected String nombre;
	protected String direccion;
	protected boolean activo;

	public TCliente(int tlf, String nif, String nombre, String direccion) {
		this.tlf = tlf;
		this.nif = nif;
		this.nombre = nombre;
		this.direccion = direccion;
	}
	public TCliente(int id, int tlf, String nif, String nombre, String direccion, boolean activo) {
		this.id = id;
		this.tlf = tlf;
		this.nif = nif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.activo = activo;
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String toString() {
		return "ID: " + this.id + SALTO +
				"Telefono: " + this.tlf + SALTO +
				"NIF: " + this.nif + SALTO +
				"Nombre: " + this.nombre + SALTO +
				"Direccion: " + this.direccion + SALTO;
	}
}