package presentacion.controlador.command.command_curso;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandBajaCurso implements Command {
	public Context execute(Object datos) {
		int id = (int) datos;
		int res = FactoriaNegocio.getInstancia().crearSACurso().bajaCurso(id);
		if (res > 0) return new Context(Eventos.BAJA_CURSO_OK, res);
		return new Context(Eventos.BAJA_CURSO_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BAJA_CURSO;
	}
}
