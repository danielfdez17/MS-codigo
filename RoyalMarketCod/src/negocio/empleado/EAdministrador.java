package negocio.empleado;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.NamedQueries;

@Entity
@NamedQueries({
		@NamedQuery(name = "negocio.empleado.EAdministrador.findByid", query = "select obj from EAdministrador obj where :id = obj.id "),
		@NamedQuery(name = "negocio.empleado.EAdministrador.findByversion", query = "select obj from EAdministrador obj where :version = obj.version "),
		@NamedQuery(name = "negocio.empleado.EAdministrador.findByreporteSemanal", query = "select obj from EAdministrador obj where :reporteSemanal = obj.reporteSemanal ") })
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class EAdministrador extends ONEmpleado implements Serializable {

	private static final long serialVersionUID = 0;
	@Column(nullable = false)
	private String reporteSemanal;
	
	public EAdministrador() {}
	
	public EAdministrador(int tlf, String nif, String nombre, String direccion, double sueldo, String reporteSemanal) {
		super(tlf, nif, nombre, direccion, sueldo);
		this.reporteSemanal = reporteSemanal;
	}
	
	public EAdministrador(int id, int tlf, String nif, String nombre, String direccion, double sueldo, String reporteSemanal, boolean activo) {
		super(id, tlf, nif, nombre, direccion, sueldo, activo);
		this.reporteSemanal = reporteSemanal;
	}
	
	public EAdministrador(TEmpleadoAdministrador empleado) {
		this(empleado.getId(), empleado.getTlf(), empleado.getNif(), empleado.getNombre(), empleado.getDireccion(), empleado.getSueldo(), empleado.getReporteSemanal(), empleado.isActivo());
	}
	
	public String getReporteSemanal() {
		return reporteSemanal;
	}

	public void setReporteSemanal(String reporteSemanal) {
		this.reporteSemanal = reporteSemanal;
	}

	@Override
	public double getNomina() {
		return this.toTransfer().getNomina();
	}

	@Override
	public TEmpleado toTransfer() {
		return new TEmpleadoAdministrador(id, tlf, nif, nombre, direccion, reporteSemanal, sueldo, oficina.getId(), activo);
	}

}