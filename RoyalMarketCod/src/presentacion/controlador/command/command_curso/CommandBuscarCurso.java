package presentacion.controlador.command.command_curso;

import negocio.curso.TCurso;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandBuscarCurso implements Command {

	public Context execute(Object datos) {
		int id = (int)datos;
		TCurso tCurso = FactoriaNegocio.getInstancia().crearSACurso().buscarCurso(id);
		if (tCurso == null) return new Context(Eventos.BUSCAR_CURSO_KO, id);
		return new Context(Eventos.BUSCAR_CURSO_OK, tCurso);
	}

	@Override
	public Eventos getId() {
		return Eventos.BUSCAR_CURSO;
	}
}
