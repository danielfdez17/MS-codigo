package negocio.empleado_curso;

import utilities.Utils;

public class TEmpleadoCurso {
	
	private int idEmpleado;
	private int idCurso;
	private int nivel;
	
	public TEmpleadoCurso(int idEmpleado, int idCurso, int nivel) {
		super();
		this.idEmpleado = idEmpleado;
		this.idCurso = idCurso;
		this.nivel = nivel;
	}
	public int getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public int getIdCurso() {
		return idCurso;
	}
	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	@Override
	public String toString() {
		return String.format(Utils.ID_ENTIDAD, "empleado", idEmpleado) + 
				String.format(Utils.ID_ENTIDAD, "curso", idCurso) + 
				String.format(Utils.NIVEL, nivel);
	}
	
	

}
