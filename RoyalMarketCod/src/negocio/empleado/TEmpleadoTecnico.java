package negocio.empleado;

import utilities.Utils;

public class TEmpleadoTecnico extends TEmpleado {
	
	private String reporteTrabajo;

	public TEmpleadoTecnico(int tlf, String nif, String nombre, String direccion, String reporteTrabajo, double sueldo, int idOficina) {
		super(tlf, nif, nombre, direccion, sueldo, idOficina);
		this.setReporteTrabajo(reporteTrabajo);
	}

	public TEmpleadoTecnico(int id, int tlf, String nif, String nombre, String direccion, String reporteTrabajo, double sueldo, int idOficina,
			boolean activo) {
		super(id, tlf, nif, nombre, direccion, sueldo, idOficina, activo);
		this.setReporteTrabajo(reporteTrabajo);
	}
	
	public String getReporteTrabajo() {
		return reporteTrabajo;
	}
	
	public void setReporteTrabajo(String reporteTrabajo) {
		this.reporteTrabajo = reporteTrabajo;
	}
	
	@Override
	public String toString() {
		return super.toString() + String.format(Utils.REPORTE, reporteTrabajo);
	}
	
	@Override
	public double getNomina() {
		return this.sueldo * this.reporteTrabajo.length() / 100;
	}
}