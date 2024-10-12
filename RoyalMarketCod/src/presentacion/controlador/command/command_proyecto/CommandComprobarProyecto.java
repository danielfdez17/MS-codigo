package presentacion.controlador.command.command_proyecto;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proyecto.TProyecto;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandComprobarProyecto  implements Command {
	public Context execute(Object datos) {
		int id = (int) datos;
		TProyecto res = FactoriaNegocio.getInstancia().crearSAProyecto().buscarProyecto(id);
		if(res == null) return new Context(Eventos.COMPROBAR_PROYECTO_KO, id);
		return new Context(Eventos.COMPROBAR_PROYECTO_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.COMPROBAR_PROYECTO;
	}

}
