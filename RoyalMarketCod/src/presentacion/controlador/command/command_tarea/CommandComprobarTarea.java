package presentacion.controlador.command.command_tarea;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.tarea.TTarea;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandComprobarTarea implements Command {

	@Override
	public Context execute(Object datos) {
		int id = (int)datos;
		TTarea tarea = FactoriaNegocio.getInstancia().crearSATarea().buscarTarea(id);
		Eventos e = Eventos.COMPROBAR_TAREA_OK;
		if (tarea == null) e = Eventos.COMPROBAR_TAREA_KO;
		return new Context(e, tarea);
	}

	@Override
	public Eventos getId() {
		return Eventos.COMPROBAR_TAREA;
	}

}