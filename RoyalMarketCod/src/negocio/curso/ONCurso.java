package negocio.curso;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import negocio.empleado_curso.ONEmpleadoCurso;

import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.curso.ONCurso.findByid", query = "select obj from ONCurso obj where :id = obj.id "),
		@NamedQuery(name = "negocio.curso.ONCurso.findBynombre", query = "select obj from ONCurso obj where :nombre = obj.nombre "),
		@NamedQuery(name = "negocio.curso.ONCurso.findByplazas", query = "select obj from ONCurso obj where :plazas = obj.plazas "),
		@NamedQuery(name = "negocio.curso.ONCurso.findByhoras_dia", query = "select obj from ONCurso obj where :horasDia = obj.horasDia "),
		@NamedQuery(name = "negocio.curso.ONCurso.findByactivo", query = "select obj from ONCurso obj where :activo = obj.activo "),
		@NamedQuery(name = "negocio.curso.ONCurso.findByversion", query = "select obj from ONCurso obj where :version = obj.version "),
		@NamedQuery(name = "negocio.curso.ONCurso.findByIdEmpleado", query = "SELECT obj FROM ONCurso obj JOIN obj.empleados e WHERE e.id.empleadoId  = :idEmpleado"),
		@NamedQuery(name = "negocio.curso.ONCurso.findAll", query = "select obj from ONCurso obj")})
@Inheritance(strategy = InheritanceType.JOINED)
public class ONCurso implements Serializable {
	private static final long serialVersionUID = 0;

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	@Version
	private int version;
	
	@Column(unique = true, nullable = false)
	protected String nombre;
	protected int plazas;
	protected int horasDia;
	protected boolean activo;
	
	@OneToMany(mappedBy = "curso")
	protected List<ONEmpleadoCurso> empleados;

	public ONCurso() {}

	public ONCurso(String nombre, int plazas, int horasDia) {
		super();
		this.nombre = nombre;
		this.plazas = plazas;
		this.horasDia = horasDia;
		this.activo = true;
	}
	
	public ONCurso(int id, String nombre, int plazas, int horasDia, boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.plazas = plazas;
		this.horasDia = horasDia;
		this.activo = activo;
	}
	
	public ONCurso(TCurso tc) {
		this(tc.getId(), tc.getNombre(), tc.getPlazas(), tc.getHorasDia(), tc.isActivo());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	public int getHorasDia() {
		return horasDia;
	}

	public void setHorasDia(int horasDia) {
		this.horasDia = horasDia;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public void setEmpleados(List<ONEmpleadoCurso> empleados){
		this.empleados=empleados;
	}
	public List<ONEmpleadoCurso> getEmpleados(){
		return empleados;
	}
	
	public TCurso toTransfer() {
        return new TCurso(id, nombre,plazas,horasDia, activo);
    }
	
}