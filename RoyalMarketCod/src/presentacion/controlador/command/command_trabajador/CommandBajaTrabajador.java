package presentacion.controlador.command.command_trabajador;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBajaTrabajador implements Command {
	public Context execute(Object datos) {
		int id = (int)datos;
		int res = FactoriaNegocio.getInstancia().crearSATrabajador().bajaTrabajador(id);
		if (res > 0) return new Context(Eventos.BAJA_TRABAJADOR_OK, res);
		return new Context(Eventos.BAJA_TRABAJADOR_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BAJA_TRABAJADOR;
	}
}