package negocio.proyecto;

public class TProyecto {
	
	private static final String SALTO = "\n";
	
	private int id;
	private String nombre;
	private boolean activo;
	public TProyecto(String nombre) {
		super();
		this.nombre = nombre;
	}
	public TProyecto(int id, String nombre, boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
				"Nombre: " + this.nombre + SALTO +
				"Activo: " + (this.activo ? "si" : "no") + SALTO;
	}
}