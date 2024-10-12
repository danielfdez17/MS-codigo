package negocio.oficina;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import negocio.empleado.ONEmpleado;

import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.oficina.ONOficina.findByid", query = "select obj from ONOficina obj where :id = obj.id "),
		@NamedQuery(name = "negocio.oficina.ONOficina.findBynombre", query = "select obj from ONOficina obj where :nombre = obj.nombre "),
		@NamedQuery(name = "negocio.oficina.ONOficina.findByversion", query = "select obj from ONOficina obj where :version = obj.version "),
		@NamedQuery(name = "negocio.oficina.ONOficina.findByactivo", query = "select obj from ONOficina obj where :activo = obj.activo "),
		@NamedQuery(name = "negocio.oficina.ONOficina.findAll", query = "SELECT o FROM ONOficina o"),
		})
public class ONOficina implements Serializable {
	private static final long serialVersionUID = 0;

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false)
	private String nombre;
	@Version
	private int version;
	private boolean activo;
	
	@OneToMany(mappedBy = "oficina")
	private List<ONEmpleado> empleados;
	
	public ONOficina() {}

	public ONOficina(String nombre) {
		super();
		this.nombre = nombre;
	}

	public ONOficina(int id, String nombre, boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
	}

	public ONOficina(TOficina oficina) {
		this(oficina.getId(), oficina.getNombre(), oficina.isActivo());
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
	
	public List<ONEmpleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<ONEmpleado> empleados) {
		this.empleados = empleados;
	}

	public TOficina toTransfer() {
		return new TOficina(id, nombre, activo);
	}
	
}