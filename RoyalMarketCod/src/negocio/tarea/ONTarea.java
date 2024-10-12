package negocio.tarea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import negocio.proyecto.ONProyecto;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.tarea.ONTarea.findByid", query = "select obj from ONTarea obj where :id = obj.id "),
		@NamedQuery(name = "negocio.tarea.ONTarea.findBynombre", query = "select obj from ONTarea obj where obj.nombre = :nombre"),
		@NamedQuery(name = "negocio.tarea.ONTarea.findByversion", query = "select obj from ONTarea obj where :version = obj.version "),
		@NamedQuery(name = "negocio.tarea.ONTarea.findByactivo", query = "select obj from ONTarea obj where :activo = obj.activo "), 
		@NamedQuery(name = "negocio.tarea.ONTarea.findAll", query = "SELECT obj FROM ONTarea obj"),
		@NamedQuery(name = "negocio.tarea.ONTarea.findAllByProyecto", 
		query = "SELECT t FROM ONTarea t JOIN t.proyecto p WHERE p.id = :idProyecto")
})
public class ONTarea implements Serializable {

	private static final long serialVersionUID = 0;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false)
	private String nombre;
	@Version
	private int version;
	private boolean activo;
	
	@ManyToOne(optional = false)
	private ONProyecto proyecto;
	
	public ONProyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(ONProyecto proyecto) {
		this.proyecto = proyecto;
	}

	public ONTarea() {}

	public ONTarea(String nombre) {
		this.setNombre(nombre);
	}

	public ONTarea(int id, String nombre, boolean activo) {
		this.setId(id);
		this.setNombre(nombre);
		this.setActivo(activo);
	}
	public ONTarea(TTarea tarea) {
		this(tarea.getId(), tarea.getNombre(), tarea.isActivo());
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
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
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public TTarea toTransfer() {
		return new TTarea(this.id, this.nombre, this.proyecto.getId(), this.activo);
	}
}