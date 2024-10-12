package presentacion.controlador.command.command_oficina;

import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandGUIOficina implements Command {

	@Override
	public Context execute(Object datos) {
		return new Context(Eventos.GUI_OFICINA, null);
	}

	@Override
	public Eventos getId() {
		return Eventos.GUI_OFICINA;
	}

}
