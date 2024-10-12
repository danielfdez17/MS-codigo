package negocio.tarea;

public class TTarea {
	
	private static final String SALTO = "\n";

	private int id;
	private String nombre;
	private boolean activo;
	private int idProyecto;

	public TTarea(String nombre, int idProyecto) {
		this.setNombre(nombre);
		this.setIdProyecto(idProyecto);
		this.setActivo(true);
	}

	public TTarea(int id, String nombre, int idProyecto, boolean activo) {
		this.setId(id);
		this.setNombre(nombre);
		this.setIdProyecto(idProyecto);
		this.setActivo(activo);
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
	
	public int getIdProyecto() {
		return idProyecto;
	}
	
	public void setIdProyecto(int idProyecto) {
		this.idProyecto = idProyecto;
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
				"ID proyecto: " + this.idProyecto + SALTO +
				"Activo: " + (this.activo ? "si" : "no") + SALTO;
	}
}