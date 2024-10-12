package negocio.empleado;

public class TEmpleadoProyecto {
	private int idEmpleado, idProyecto;

	public TEmpleadoProyecto(int idEmpleado, int idProyecto) {
		super();
		this.idEmpleado = idEmpleado;
		this.idProyecto = idProyecto;
	}

	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public int getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(int idProyecto) {
		this.idProyecto = idProyecto;
	}
	
	
}
