package presentacion.controlador.command.command_tarea;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.tarea.TTarea;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandListarTareasPorProyecto implements Command {

	@Override
	public Context execute(Object datos) {
		int idProyecto = (int)datos;
		List<TTarea> res = FactoriaNegocio.getInstancia().crearSATarea().listarTareasPorProyecto(idProyecto);
		if (res == null) return new Context(Eventos.LISTAR_TAREAS_POR_PROYECTO_KO, res);
		return new Context(Eventos.LISTAR_TAREAS_POR_PROYECTO_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_TAREAS_POR_PROYECTO;
	}

}
