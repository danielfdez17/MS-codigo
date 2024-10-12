package negocio.proyecto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import negocio.empleado.ONEmpleado;
import negocio.tarea.ONTarea;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.proyecto.ONProyecto.findProjectByIdAndNotExistOtheProjectWithNameSame",
					query = "SELECT obj FROM ONProyecto obj WHERE obj.id =:projectId AND NOT EXISTS (SELECT 1 FROM ONProyecto obj2 WHERE obj2.nombre =:nameProject AND obj2.id <>:projectId)"),
		@NamedQuery(name = "negocio.proyecto.ONProyecto.getEmployeeActiveAndTaskActive",
					query = "SELECT obj FROM ONProyecto obj "
							+ "LEFT JOIN obj.empleados e "
							+ "LEFT JOIN obj.tareas t "
							+ "WHERE obj.id =:idProject AND obj.activo=true "),
		@NamedQuery(name = "negocio.proyecto.ONProyecto.findByIdProject",
					query = "SELECT obj FROM ONProyecto obj JOIN obj.empleados e WHERE obj.id =:idProject AND e.activo=true"),
		@NamedQuery(name = "negocio.proyecto.ONProyecto.findAllOnlyActive", 
					query = "SELECT obj FROM ONProyecto obj WHERE obj.activo=true"),
		@NamedQuery(name = "negocio.proyecto.ONProyecto.findByIdEmpleado", 
					query = "SELECT obj FROM ONProyecto obj JOIN obj.empleados e WHERE e.id = :idEmpleado"),
		@NamedQuery(name = "negocio.proyecto.ONProyecto.findByTarea", 
					query = "SELECT obj FROM ONProyecto obj JOIN obj.tareas t WHERE t.id = :idTarea"),
		@NamedQuery(name = "negocio.proyecto.ONProyecto.findBynombre", query = "select obj from ONProyecto obj where :nombre = obj.nombre "),
		@NamedQuery(name = "negocio.proyecto.ONProyecto.findByversion", query = "select obj from ONProyecto obj where :version = obj.version "),
		@NamedQuery(name = "negocio.proyecto.ONProyecto.findByactivo", query = "select obj from ONProyecto obj where :activo = obj.activo ") })
public class ONProyecto implements Serializable {

	private static final long serialVersionUID = 0;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false)
	private String nombre;
	@Version
	private int version;
	private boolean activo;
	
	@OneToMany(mappedBy = "proyecto")
	private List<ONTarea> tareas;
	
	@ManyToMany
	private List<ONEmpleado> empleados;

	public ONProyecto() {}

	public ONProyecto(int id) {
		this.id = id;
	}
	
	public ONProyecto(String nombre) {
		this.setNombre(nombre);
		this.setActivo(true);
		this.tareas = new ArrayList<>();
		this.empleados = new ArrayList<>();
	}
	
	public ONProyecto(int id, String nombre, boolean activo) {
		this.setId(id);
		this.setNombre(nombre);
		this.setActivo(activo);
		this.tareas = new ArrayList<>();
		this.empleados = new ArrayList<>();
	}

	public ONProyecto(TProyecto proyecto) {
		this(proyecto.getId(), proyecto.getNombre(), proyecto.isActivo());
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public void setTareas(List<ONTarea> tareas) {
		this.tareas = tareas;
	}

	public void setEmpleados(List<ONEmpleado> empleados) {
		this.empleados = empleados;
	}

	public List<ONEmpleado> getEmpleados() {
		return empleados;
	}

	public List<ONTarea> getTask(){
		return tareas;
	}
	
	public TProyecto toTransfer() {
		return new TProyecto(id, nombre, activo);
	}
}