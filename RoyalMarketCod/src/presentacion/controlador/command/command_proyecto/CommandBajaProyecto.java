package presentacion.controlador.command.command_proyecto;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandBajaProyecto implements Command{
	public Context execute(Object datos) {
		int id = (int) datos;
		int res = FactoriaNegocio.getInstancia().crearSAProyecto().bajaProyecto(id);
		if (res > 0) return new Context(Eventos.BAJA_PROYECTO_OK, res);
		return new Context(Eventos.BAJA_PROYECTO_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BAJA_PROYECTO;
	}

}
