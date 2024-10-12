package presentacion.controlador.command.command_curso;

import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandGUICurso implements Command {

	@Override
	public Context execute(Object datos) {
		return new Context(this.getId(), null);
	}

	@Override
	public Eventos getId() {
		return Eventos.GUI_CURSO;
	}
}
