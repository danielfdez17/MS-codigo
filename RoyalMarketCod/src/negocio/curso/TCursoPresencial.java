package negocio.curso;

import utilities.Utils;

public class TCursoPresencial extends TCurso {
	private int aula;

	public TCursoPresencial(String nombre, int plazas, int horasDia, int aula) {
		super(nombre, plazas, horasDia);
		this.setAula(aula);
	}

	public TCursoPresencial(int id, String nombre, int plazas, int horasDia, int aula, boolean activo) {
		super(id, nombre, plazas, horasDia, activo);
		this.setAula(aula);
	}
	public TCursoPresencial(String nombre, int plazas, int horasDia, int aula, boolean activo) {
		super(nombre, plazas, horasDia, activo);
		this.setAula(aula);
	}
	public int getAula() {
		return aula;
	}

	public void setAula(int aula) {
		this.aula = aula;
	}

	public String toString() {
		return super.toString() +
				String.format(Utils.AULA, aula);
	}
}