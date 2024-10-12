package negocio.trabajador;

public class TTrabajadorJParcial extends TTrabajador {

	private int horas;
	private double precioHora;

	public TTrabajadorJParcial(int tlf, String nif, String nombre, String direccion, int idAlmacen,
			int horas, double precioHora) {
		super(tlf, nif, nombre, direccion, idAlmacen);
		this.horas = horas;
		this.precioHora = precioHora;
	}

	public TTrabajadorJParcial(int id, int tlf, String nif, String nombre, String direccion, int idAlmacen,
			int horas, double precioHora ,boolean activo) {
		super(id, tlf, nif, nombre, direccion, idAlmacen, activo);
		this.horas = horas;
		this.precioHora = precioHora;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}

	public double getPrecioHora() {
		return precioHora;
	}

	public void setPrecioHora(double precioHora) {
		this.precioHora = precioHora;
	}
	
	@Override
	public String toString() {
		return super.toString() + 
				"Horas: " + this.horas + SALTO +
				"Precio por hora: " + this.precioHora + SALTO +
				"Activo: " + (this.activo ? "si" : "no") + SALTO;
	}

}