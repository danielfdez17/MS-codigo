package negocio.proveedor;

public class TProveedor {

	private static final String SALTO = "\n";
	private int id;
	private int tlf;
	private String nif;
	private String direccion;
	private boolean activo;
	
	public TProveedor(int tlf, String nif, String direccion) {
		this.setTlf(tlf);
		this.setNif(nif);
		this.setDireccion(direccion);
		this.setActivo(true);
	}
	public TProveedor(int id, int tlf, String nif, String direccion, boolean activo) {
		this.setId(id);
		this.setTlf(tlf);
		this.setNif(nif);
		this.setDireccion(direccion);
		this.setActivo(activo);
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTlf() {
		return tlf;
	}

	public void setTlf(int tlf) {
		this.tlf = tlf;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	
	@Override
	public String toString() {
		String s = "ID: " + this.id + SALTO +
				"Telefono: " + this.tlf + SALTO +
				"NIF: " + this.nif + SALTO +
				"Direccion: " + this.direccion + SALTO;
		if (this.activo) return this.toBold(s);
		return s;
	}
	private String toBold(String s) {
		return "<html><b>" + s + "</b></html>";
	}
}