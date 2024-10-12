package negocio.trabajador;

public class TTrabajadorJCompleta extends TTrabajador {

	private double sueldoBase;

	public TTrabajadorJCompleta(int tlf, String nif, String nombre, String direccion, int idAlmacen, double sueldoBase) {
		super(tlf, nif, nombre, direccion, idAlmacen);
		this.sueldoBase = sueldoBase;
	}
	public TTrabajadorJCompleta(int id, int tlf, String nif, String nombre, String direccion, int idAlmacen, double sueldoBase, boolean activo) {
		super(id, tlf, nif, nombre, direccion, idAlmacen, activo);
		this.sueldoBase = sueldoBase;
	}

	public double getSueldoBase() {
		return sueldoBase;
	}

	public void setSueldoBase(double sueldoBase) {
		this.sueldoBase = sueldoBase;
	}
	
	@Override
	public String toString() {
		return super.toString() + 
				"Sueldo base: " + this.sueldoBase + SALTO +
				"Activo: " + (this.activo ? "si" : "no") + SALTO;
	}

}