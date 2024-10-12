package presentacion.controlador.command.command_producto;

import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandGUIProducto implements Command {

	@Override
	public Context execute(Object datos) {
		return new Context(this.getId(), null);
	}

	@Override
	public Eventos getId() {
		return Eventos.GUI_PRODUCTO;
	}

}
