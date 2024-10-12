package negocio.almacen;

public class TAlmacen {
	
	private static final String SALTO = "\n";

	private int id;
	private String direccion;
	private boolean activo;

	public TAlmacen(String direccion) {
		this.direccion = direccion;
		this.activo = true;
	}
	public TAlmacen(int id, String direccion, boolean activo) {
		this.setId(id);
		this.setDireccion(direccion);
		this.setActivo(activo);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return "ID: " + this.id + SALTO +
				"Direccion: " + this.direccion + SALTO;
	}
}