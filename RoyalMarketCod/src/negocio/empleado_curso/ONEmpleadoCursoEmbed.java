package negocio.empleado_curso;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Version;

@Embeddable
public class ONEmpleadoCursoEmbed implements Serializable {

	private static final long serialVersionUID = 0;
	
	private int empleadoId;
	private int cursoId;

	
	public ONEmpleadoCursoEmbed() {}
	
	public ONEmpleadoCursoEmbed(int onempleado, int oncurso) {
		this.setONEmpleado(onempleado);
		this.setONCurso(oncurso);
	}
	
	
	public int getONEmpleado() {
		return empleadoId;
	}

	public void setONEmpleado(int onempleado) {
		this.empleadoId = onempleado;
	}

	public int getONCurso() {
		return cursoId;
	}

	public void setONCurso(int oncurso) {
		this.cursoId = oncurso;
	}

	
	
	

}