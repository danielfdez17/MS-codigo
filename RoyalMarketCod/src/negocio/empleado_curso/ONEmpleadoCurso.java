package negocio.empleado_curso;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import negocio.curso.ONCurso;
import negocio.empleado.ONEmpleado;

@Entity
@NamedQueries({
	@NamedQuery(name = "negocio.empleado_curso.ONEmpleadoCurso.findByonempleado", query = "select obj from ONEmpleadoCurso obj where :id_empleado = obj.empleado.id"),
	@NamedQuery(name = "negocio.empleado_curso.ONEmpleadoCurso.findByoncurso", query = "select obj from ONEmpleadoCurso obj where :id_curso = obj.curso.id"),
	@NamedQuery(name = "negocio.empleado_curso.ONEmpleadoCurso.findByEmpAndCurso", 
				query = "SELECT oec FROM ONEmpleadoCurso oec WHERE oec.empleado.id = :idEmpleado AND oec.curso.id = :idCurso")
})
public class ONEmpleadoCurso implements Serializable {

	private static final long serialVersionUID = 0;
	
	@EmbeddedId
	private ONEmpleadoCursoEmbed id;
	
	@ManyToOne
	@MapsId("empleadoId") 
	private ONEmpleado empleado;
	
	@ManyToOne
	@MapsId("cursoId") 
	private ONCurso curso;
	
	@Version
	private int version;
	private int nivel;
	
	
	public ONEmpleadoCurso() {}
	
	public ONEmpleadoCurso(final ONEmpleado e, final ONCurso c, final int nivel) {
		this.empleado = e;
		this.curso = c;
		this.nivel = nivel;
		this.id = new ONEmpleadoCursoEmbed();
	}
	
	public ONEmpleadoCursoEmbed getEmbed() {
		return id;
	}

	public void setId(ONEmpleadoCursoEmbed id) {
		this.id = id;
	}

	public ONEmpleado getEmpleado() {
		return empleado;
	}
	
	public ONCurso getCurso() {
		return curso;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
	public TEmpleadoCurso toTransfer() {
		return new TEmpleadoCurso(empleado.getId(), curso.getId(), this.nivel);
	}

}