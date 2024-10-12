package presentacion.controlador.command;

import presentacion.controlador.Eventos;

public abstract class FactoriaCommand {

	private static FactoriaCommand instancia;

	public synchronized static FactoriaCommand getInstancia() {
		if (instancia == null) instancia = new FactoriaCommandImp();
		return instancia;
	}

	public abstract Command crearComando(Eventos evento);
}