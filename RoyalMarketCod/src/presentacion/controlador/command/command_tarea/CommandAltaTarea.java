package presentacion.controlador.command.command_tarea;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.tarea.TTarea;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandAltaTarea implements Command {

	@Override
	public Context execute(Object datos) {
		TTarea tarea = (TTarea)datos;
		int res = FactoriaNegocio.getInstancia().crearSATarea().altaTarea(tarea);
		Eventos e = Eventos.ALTA_TAREA_OK;
		if (res <= 0) e = Eventos.ALTA_TAREA_KO;
		return new Context(e, tarea);
	}

	@Override
	public Eventos getId() {
		return Eventos.ALTA_TAREA;
	}

}
