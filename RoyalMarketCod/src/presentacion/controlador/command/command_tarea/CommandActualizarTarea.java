package presentacion.controlador.command.command_tarea;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.tarea.TTarea;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandActualizarTarea implements Command {

	@Override
	public Context execute(Object datos) {
		TTarea tarea = (TTarea) datos;
		int res = FactoriaNegocio.getInstancia().crearSATarea().actualizarTarea(tarea);
		if (res > 0) return new Context(Eventos.ACTUALIZAR_TAREA_KO, res);
		return new Context(Eventos.ACTUALIZAR_TAREA_OK, tarea);
	}

	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_TAREA;
	}

}
