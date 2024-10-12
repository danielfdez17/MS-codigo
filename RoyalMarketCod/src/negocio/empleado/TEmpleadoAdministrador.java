package negocio.empleado;

import utilities.Utils;

public class TEmpleadoAdministrador extends TEmpleado {
	
	private String reporteSemanal;
	
	public TEmpleadoAdministrador(int tlf, String nif, String nombre, String direccion, String reporteSemanal, double sueldo, int idOficina) {
		super(tlf, nif, nombre, direccion, sueldo, idOficina);
		this.setReporteSemanal(reporteSemanal);
	}

	public TEmpleadoAdministrador(int id, int tlf, String nif, String nombre, String direccion, String reporteSemanal, double sueldo,
			int idOficina, boolean activo) {
		super(id, tlf, nif, nombre, direccion, sueldo, idOficina, activo);
		this.setReporteSemanal(reporteSemanal);
	}
	
	public String getReporteSemanal() {
		return reporteSemanal;
	}

	public void setReporteSemanal(String reporteSemanal) {
		this.reporteSemanal = reporteSemanal;
	}

	@Override
	public String toString() {
		return super.toString() + String.format(Utils.REPORTE, reporteSemanal);
	}

	@Override
	public double getNomina() {
		return this.sueldo * this.reporteSemanal.length() / 100;
	}

	
	
}