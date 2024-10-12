package negocio.empleado;

import utilities.Utils;

public abstract class TEmpleado {
	
	protected int id;
	protected int tlf;
	protected String nif;
	protected String nombre;
	protected String direccion;
	protected double sueldo;
	protected boolean activo;
	protected int idOficina;
	
	public TEmpleado(int tlf, String nif, String nombre, String direccion, double sueldo, int idOficina) {
		super();
		this.tlf = tlf;
		this.nif = nif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.sueldo = sueldo;
		this.activo = true;
		this.idOficina = idOficina;
	}

	public TEmpleado(int id, int tlf, String nif, String nombre, String direccion, double sueldo, int idOficina, boolean activo) {
		super();
		this.id = id;
		this.tlf = tlf;
		this.nif = nif;
		this.nombre = nombre;
		this.direccion = direccion;
		this.sueldo = sueldo;
		this.activo = activo;
		this.idOficina = idOficina;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(int idOficina) {
		this.idOficina = idOficina;
	}

	@Override
	public String toString() {
		return String.format(Utils.ID, id) + 
				String.format(Utils.TELEFONO, tlf) +
				String.format(Utils.NIF, nif) +
				String.format(Utils.NOMBRE, nombre) +
				String.format(Utils.DIRECCION, direccion) +
				String.format(Utils.SUELDO, String.valueOf(sueldo)) +
				String.format(Utils.ACTIVO, activo);
	}
	
	public abstract double getNomina();
	
}