package negocio.empleado;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import javax.persistence.ManyToOne;

import negocio.empleado_curso.ONEmpleadoCurso;
import negocio.material.ONMaterial;
import negocio.oficina.ONOficina;
import negocio.proyecto.ONProyecto;

import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.empleado.ONEmpleado.findByid", query = "select obj from ONEmpleado obj where :id = obj.id "),
		@NamedQuery(name = "negocio.empleado.ONEmpleado.findByversion", query = "select obj from ONEmpleado obj where :version = obj.version "),
		@NamedQuery(name = "negocio.empleado.ONEmpleado.findBytlf", query = "select obj from ONEmpleado obj where :tlf = obj.tlf "),
		@NamedQuery(name = "negocio.empleado.ONEmpleado.findBynif", query = "select obj from ONEmpleado obj where :nif = obj.nif "),
		@NamedQuery(name = "negocio.empleado.ONEmpleado.findBynombre", query = "select obj from ONEmpleado obj where :nombre = obj.nombre "),
		@NamedQuery(name = "negocio.empleado.ONEmpleado.findBydireccion", query = "select obj from ONEmpleado obj where :direccion = obj.direccion "),
		@NamedQuery(name = "negocio.empleado.ONEmpleado.findByactivo", query = "select obj from ONEmpleado obj where :activo = obj.activo "),
		@NamedQuery(name = "negocio.empleado.ONEmpleado.findBysueldo", query = "select obj from ONEmpleado obj where :sueldo = obj.sueldo "),
		@NamedQuery(name = "negocio.empleado.ONEmpleado.findAll", query = "select obj from ONEmpleado obj "),
		@NamedQuery(name = "negocio.empleado.ONEmpleado.findAllByCurso", 
					query = "SELECT emp FROM ONEmpleado emp "
							+ "LEFT JOIN ONEmpleadoCurso oec "
							+ "WHERE emp.id = oec.empleado.id AND oec.curso.id = :idCurso")
		
		})
@Inheritance(strategy = InheritanceType.JOINED) //Agregamos esto para las tablas de herencia
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class ONEmpleado implements Serializable {
	private static final long serialVersionUID = 0;

	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int id;
	@Version
	private int version;
	protected int tlf;
	@Column(unique = true, nullable = false)
	protected String nif;
	@Column(nullable = false)
	protected String nombre;
	@Column(nullable = false)
	protected String direccion;
	protected double sueldo;
	protected boolean activo;
	
	@ManyToOne(optional = false)
	protected ONOficina oficina;
	
	@ManyToMany
	protected List<ONProyecto> proyectos;

	@OneToMany(mappedBy = "empleado")
	protected List<ONEmpleadoCurso> cursos;

	@OneToMany(mappedBy = "empleado")
	protected List<ONMaterial> materiales;
	
	public ONEmpleado() {}

	public ONEmpleado(int tlf, String nif, String nombre, String direccion, double sueldo) {
		super();
		this.tlf = tlf;
		this.nif = nif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.sueldo = sueldo;
		this.activo = true;
	}

	public ONEmpleado(int id, int tlf, String nif, String nombre, String direccion, double sueldo, boolean activo) {
		super();
		this.id = id;
		this.tlf = tlf;
		this.nif = nif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.sueldo = sueldo;
		this.activo = activo;
	}
	
	public ONEmpleado(TEmpleado empleado) {
		this(empleado.getId(), empleado.getTlf(), empleado.getNif(), empleado.getNombre(), empleado.getDireccion(), empleado.getSueldo(), empleado.isActivo());
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

	public int getTlf() {
		return tlf;
	}

	public void setTlf(int tlf) {
		this.tlf = tlf;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public double getSueldo() {
		return sueldo;
	}

	public void setSueldo(double sueldo) {
		this.sueldo = sueldo;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	public ONOficina getOficina() {
		return oficina;
	}

	public void setOficina(ONOficina oficina) {
		this.oficina = oficina;
	}

	public List<ONProyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<ONProyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public List<ONEmpleadoCurso> getCursos() {
		return cursos;
	}

	public void setCursos(List<ONEmpleadoCurso> cursos) {
		this.cursos = cursos;
	}

	public List<ONMaterial> getMateriales() {
		return materiales;
	}

	public void setMateriales(List<ONMaterial> materiales) {
		this.materiales = materiales;
	}

	public abstract double getNomina();
	
	public abstract TEmpleado toTransfer();
	
}