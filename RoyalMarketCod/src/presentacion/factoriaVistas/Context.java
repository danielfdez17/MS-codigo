package presentacion.factoriaVistas;

import presentacion.controlador.Eventos;

public class Context {
	private Eventos evento;
	private Object datos;

	public Context(Eventos evento, Object datos) {
		this.evento = evento;
		this.datos = datos;
	}

	public Eventos getEvent() {
		return this.evento;
	}

	public void setEvent(Eventos evento) {
		this.evento = evento;
	}

	public Object getDatos() {
		return this.datos;
	}

	public void setDatos(Object datos) {
		this.datos = datos;
	}
}