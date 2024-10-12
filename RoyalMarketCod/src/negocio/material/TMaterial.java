package negocio.material;

import utilities.Utils;

public class TMaterial {
	
	private int id;
	private String nombre;
	private boolean activo;
	private int idEmpleado;

	

	public TMaterial(String nombre, int idEmpleado) {
		super();
		this.nombre = nombre;
		this.idEmpleado = idEmpleado;
		this.activo = true;
	}
	
	public TMaterial(int id, String nombre, int idEmpleado, boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
		this.idEmpleado = idEmpleado;
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
		return String.format(Utils.ID, id) + String.format(Utils.NOMBRE, nombre) + String.format(Utils.ACTIVO, activo);
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}
	
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
}