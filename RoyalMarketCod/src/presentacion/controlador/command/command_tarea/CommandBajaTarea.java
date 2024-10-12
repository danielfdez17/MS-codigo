package presentacion.controlador.command.command_tarea;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandBajaTarea implements Command {

	@Override
	public Context execute(Object datos) {
		int id = (int)datos;
		int res = FactoriaNegocio.getInstancia().crearSATarea().bajaTarea(id);
		Eventos e = Eventos.BAJA_TAREA_OK;
		if (res <= 0) e = Eventos.BAJA_TAREA_KO;
		return new Context(e, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BAJA_TAREA;
	}

}
