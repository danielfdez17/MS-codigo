package negocio.curso;

import utilities.Utils;

public class TCursoOnline extends TCurso {
	private String enlaceSesion;

	public TCursoOnline(String nombre, int plazas, int horasDia, String enlaceSesion) {
		super(nombre, plazas, horasDia);
		this.setEnlaceSesion(enlaceSesion);
	}

	public TCursoOnline(int id, String nombre, int plazas, int horasDia, String enlaceSesion, boolean activo) {
		super(id, nombre, plazas, horasDia, activo);
		this.setEnlaceSesion(enlaceSesion);
	}
	public TCursoOnline(String nombre, int plazas, int horasDia, String enlaceSesion, boolean activo) {
		super(nombre, plazas, horasDia, activo);
		this.setEnlaceSesion(enlaceSesion);
	}
	
	public String getEnlaceSesion() {
		return enlaceSesion;
	}

	public void setEnlaceSesion(String enlaceSesion) {
		this.enlaceSesion = enlaceSesion;
	}

	public String toString() {
		return super.toString() + 
				String.format(Utils.ENLACE, enlaceSesion);
	}
}