package presentacion.controlador.command.command_tarea;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.tarea.TTarea;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandListarTareas implements Command {

	@Override
	public Context execute(Object datos) {
		List<TTarea> res = FactoriaNegocio.getInstancia().crearSATarea().listarTareas();
		if (res == null) return new Context(Eventos.LISTAR_TAREAS_KO, res);
		return new Context(Eventos.LISTAR_TAREAS_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_TAREAS;
	}

}
