package presentacion.controlador.command.command_curso;

import java.util.List;

import negocio.curso.TCurso;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandListarCursos implements Command {
	public Context execute(Object datos) {
		List<TCurso> res = FactoriaNegocio.getInstancia().crearSACurso().listarCursos();
		if (res != null && !res.isEmpty()) return new Context(Eventos.LISTAR_CURSOS_OK, res);
		return new Context(Eventos.LISTAR_CURSOS_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_CURSOS;
	}
}
