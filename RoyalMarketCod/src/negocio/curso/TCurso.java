package negocio.curso;

import utilities.Utils;

public class TCurso {
	
	private int id;
	private String nombre;
	private int plazas;
	private int horasDia;
	private boolean activo;
	
	public TCurso(String nombre, int plazas, int horasDia) {
		super();
		this.nombre = nombre;
		this.plazas = plazas;
		this.horasDia = horasDia;
		this.activo = true;
	}

	public TCurso(int id, String nombre, int plazas, int horasDia, boolean activo) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.plazas = plazas;
		this.horasDia = horasDia;
		this.activo = activo;
	}
	public TCurso(String nombre, int plazas, int horasDia, boolean activo) {
		super();
		this.nombre = nombre;
		this.plazas = plazas;
		this.horasDia = horasDia;
		this.activo = activo;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPlazas() {
		return plazas;
	}

	public void setPlazas(int plazas) {
		this.plazas = plazas;
	}

	public int getHorasDia() {
		return horasDia;
	}

	public void setHorasDia(int horasDia) {
		this.horasDia = horasDia;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return String.format(Utils.ID, id) +
				String.format(Utils.NOMBRE, nombre) +
				String.format(Utils.PLAZAS, plazas) +
				String.format(Utils.HORAS_AL_DIA, horasDia) +
				String.format(Utils.ACTIVO, activo);
	}

}