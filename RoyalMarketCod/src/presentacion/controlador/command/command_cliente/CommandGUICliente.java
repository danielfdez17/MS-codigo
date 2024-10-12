package presentacion.controlador.command.command_cliente;

import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandGUICliente implements Command {

	@Override
	public Context execute(Object datos) {
		return new Context(this.getId(), null);
	}

	@Override
	public Eventos getId() {
		return Eventos.GUI_CLIENTE;
	}

}
