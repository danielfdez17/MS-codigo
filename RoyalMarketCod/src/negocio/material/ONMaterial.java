package negocio.material;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import negocio.empleado.ONEmpleado;

import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.material.ONMaterial.findByid", query = "select obj from ONMaterial obj where :id = obj.id "),
		@NamedQuery(name = "negocio.material.ONMaterial.findBynombre", query = "select obj from ONMaterial obj where :nombre = obj.nombre "),
		@NamedQuery(name = "negocio.material.ONMaterial.findByversion", query = "select obj from ONMaterial obj where :version = obj.version "),
		@NamedQuery(name = "negocio.material.ONMaterial.findByactivo", query = "select obj from ONMaterial obj where :activo = obj.activo "),
		@NamedQuery(name = "negocio.material.ONMaterial.findAll", query = "SELECT obj FROM ONMaterial obj")
})
public class ONMaterial implements Serializable {
	private static final long serialVersionUID = 0;

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true, nullable = false)
	private String nombre;
	@Version
	private int version;
	private boolean activo;
	
	@ManyToOne(optional = false)
	private ONEmpleado empleado;
	
	
	public ONMaterial() {}

	public ONMaterial(String nombre) {
		super();
		this.nombre = nombre;
	}

	public ONMaterial(int id, String nombre, boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.activo = activo;
	}

	public ONMaterial(TMaterial material) {
		this(material.getId(), material.getNombre(), material.isActivo());
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

	public TMaterial toTransfer() {
		return new TMaterial(id, nombre, empleado.getId(), activo);
	}

	public ONEmpleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(ONEmpleado empleado) {
		this.empleado = empleado;
		
	}
	
	
	
	
}