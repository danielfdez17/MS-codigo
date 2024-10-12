package negocio.curso;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@NamedQueries({
	@NamedQuery(name = "negocio.curso.ONCursoOnline.findByid", query = "select obj from ONCursoOnline obj where :id = obj.id "),	
	@NamedQuery(name = "negocio.curso.ONCursoOnline.findByversion", query = "select obj from ONCursoOnline obj where :version = obj.version "),	
	@NamedQuery(name = "negocio.curso.ONCursoOnline.findByenlaceSesion", query = "select obj from ONCursoOnline obj where :enlaceSesion = obj.enlaceSesion "),
	@NamedQuery(name = "negocio.curso.ONCursoOnline.findBynombre", query = "select obj from ONCursoOnline obj where :nombre = obj.nombre")

})
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class ONCursoOnline extends ONCurso implements Serializable {
	private static final long serialVersionUID = 0;

	@Column(nullable = false)
	private String enlaceSesion;
	
	public ONCursoOnline(){}

	public ONCursoOnline(String nombre, int plazas, int horasDia, String enlaceSesion) {
		this.setNombre(nombre);
		this.setPlazas(plazas);
		this.setHorasDia(horasDia);
		this.setEnlaceSesion(enlaceSesion);
	}

	public ONCursoOnline(TCursoOnline curso) {
		this(curso.getId(), curso.getNombre(), curso.getPlazas(), curso.getHorasDia(), curso.getEnlaceSesion(), curso.isActivo());
	}
	
	public ONCursoOnline(int id, String nombre, int plazas, int horasDia, String enlaceSesion, boolean activo) {
		this(nombre, plazas, horasDia, enlaceSesion, activo);
		this.setId(id);
	}
	public ONCursoOnline(String nombre, int plazas, int horasDia, String enlaceSesion, boolean activo) {
		this.setNombre(nombre);
		this.setPlazas(plazas);
		this.setHorasDia(horasDia);
		this.setEnlaceSesion(enlaceSesion);
		this.setActivo(activo);
	}

	
	public ONCursoOnline(String enlaceSesion) {
		super();
		this.enlaceSesion = enlaceSesion;
	}


	public String getEnlaceSesion() {
		return enlaceSesion;
	}

	public void setEnlaceSesion(String enlaceSesion) {
		this.enlaceSesion = enlaceSesion;
	}

	public TCursoOnline toTransfer() {
		return new TCursoOnline(id, nombre, plazas, horasDia, enlaceSesion, activo);
	}

}