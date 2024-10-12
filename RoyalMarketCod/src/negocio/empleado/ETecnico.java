package negocio.empleado;

import javax.persistence.Column;
import javax.persistence.Entity;

import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.empleado.ETecnico.findByid", query = "select obj from ETecnico obj where :id = obj.id "),
		@NamedQuery(name = "negocio.empleado.ETecnico.findByversion", query = "select obj from ETecnico obj where :version = obj.version "),
		@NamedQuery(name = "negocio.empleado.ETecnico.findByreporteTrabajo", query = "select obj from ETecnico obj where :reporteTrabajo = obj.reporteTrabajo ") })
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class ETecnico extends ONEmpleado  implements Serializable {

	private static final long serialVersionUID = 0;
	@Column(nullable = false)
	private String reporteTrabajo;

	public ETecnico() {}
	
	public ETecnico(int tlf, String nif, String nombre, String direccion, double sueldo, String reporteTrabajo) {
		super(tlf, nif, nombre, direccion, sueldo);
		this.reporteTrabajo = reporteTrabajo;
	}
	
	public ETecnico(int id, int tlf, String nif, String nombre, String direccion, double sueldo, String reporteTrabajo, boolean activo) {
		super(id, tlf, nif, nombre, direccion, sueldo, activo);
		this.reporteTrabajo = reporteTrabajo;
	}
	
	public ETecnico(TEmpleadoTecnico empleado) {
		this(empleado.getId(), empleado.getTlf(), empleado.getNif(), empleado.getNombre(), empleado.getDireccion(), empleado.getSueldo(), empleado.getReporteTrabajo(), empleado.isActivo());
	}

	public String getReporteTrabajo() {
		return reporteTrabajo;
	}

	public void setReporteTrabajo(String reporteTrabajo) {
		this.reporteTrabajo = reporteTrabajo;
	}

	@Override
	public double getNomina() {
		return this.toTransfer().getNomina();
	}

	@Override
	public TEmpleado toTransfer() {
		return new TEmpleadoTecnico(id, tlf, nif, nombre, direccion, reporteTrabajo, sueldo, oficina.getId(), activo);
	}
	
	
}