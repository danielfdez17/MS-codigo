package negocio.factura;

import java.sql.Date;

public class TFactura {
	
	private static final String SALTO = "\n";
	
	private int id;
	private double costeTotal;
	private Date fecha;
	private boolean activo;
	private int idCliente;
	
	public TFactura(int idCliente) {
		this.setIdCliente(idCliente);
		this.setActivo(true);
		this.setCosteTotal(0);
		this.fecha = new Date(System.currentTimeMillis());
	}
	public TFactura(int id, double costeTotal, Date fecha, int idCliente, boolean activo) {
		this.setId(id);
		this.setCosteTotal(costeTotal);
		this.setFecha(fecha);
		this.setIdCliente(idCliente);
		this.setActivo(activo);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getCosteTotal() {
		return costeTotal;
	}
	public void setCosteTotal(double costeTotal) {
		this.costeTotal = costeTotal;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	@Override
	public String toString() {
		return "ID: " + this.id + SALTO +
				"Fecha: " + this.fecha.toString() + SALTO +
				"ID Cliente: " + this.idCliente + SALTO +
				"Coste total: " + this.costeTotal + SALTO;
	}
}