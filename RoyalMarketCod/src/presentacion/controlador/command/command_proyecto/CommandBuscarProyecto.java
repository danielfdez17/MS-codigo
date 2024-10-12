package presentacion.controlador.command.command_proyecto;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proyecto.TProyecto;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandBuscarProyecto implements Command {

	public Context execute(Object datos) {
		int id = (int)datos;
		TProyecto p = FactoriaNegocio.getInstancia().crearSAProyecto().buscarProyecto(id);
		if (p == null) return new Context(Eventos.BUSCAR_PROYECTO_KO, id);
		return new Context(Eventos.BUSCAR_PROYECTO_OK, p);
	}

	@Override
	public Eventos getId() {
		return Eventos.BUSCAR_PROYECTO;
	}
}
