package negocio.curso;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@NamedQueries({
	@NamedQuery(name = "negocio.curso.ONCursoPresencial.findByid", query = "select obj from ONCursoPresencial obj where :id = obj.id "),	
	@NamedQuery(name = "negocio.curso.ONCursoPresencial.findByversion", query = "select obj from ONCursoPresencial obj where :version = obj.version "),	
	@NamedQuery(name = "negocio.curso.ONCursoPresencial.findByaula", query = "select obj from ONCursoPresencial obj where :aula = obj.aula "),
	@NamedQuery(name = "negocio.curso.ONCursoPresencial.findBynombre", query = "select obj from ONCursoPresencial obj where :nombre = obj.nombre")

})
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class ONCursoPresencial extends ONCurso implements Serializable {
	private static final long serialVersionUID = 0;


	private int aula;

	public ONCursoPresencial(){}
	public ONCursoPresencial(String nombre, int plazas, int horasDia, int aula) {
		this.setNombre(nombre);
		this.setPlazas(plazas);
		this.setHorasDia(horasDia);
		this.setAula(aula);
	}
	public ONCursoPresencial(String nombre, int plazas, int horasDia, int aula,boolean activo) {
		this.setNombre(nombre);
		this.setPlazas(plazas);
		this.setHorasDia(horasDia);
		this.setActivo(activo);
		this.setAula(aula);
	}
	public ONCursoPresencial(int id, String nombre, int plazas, int horasDia, int aula,boolean activo ) {
		this(nombre, plazas, horasDia, aula,activo);
		this.setId(id);
	}


	public int getAula() {
		return aula;
	}

	public void setAula(int aula) {
		this.aula = aula;
	}

	public TCursoPresencial toTransfer() {
		return new TCursoPresencial(id, nombre, plazas, horasDia, aula, activo);
	}

	public ONCursoPresencial(TCursoPresencial curso) {
		this(curso.getId(), curso.getNombre(), curso.getPlazas(), curso.getHorasDia(), curso.getAula(),curso.isActivo());
	}
}