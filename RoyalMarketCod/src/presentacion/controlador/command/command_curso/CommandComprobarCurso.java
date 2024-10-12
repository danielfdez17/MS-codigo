package presentacion.controlador.command.command_curso;

import negocio.curso.TCurso;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandComprobarCurso  implements Command {
	public Context execute(Object datos) {
		int id = (int) datos;
		TCurso res = FactoriaNegocio.getInstancia().crearSACurso().buscarCurso(id);
		if(res == null) return new Context(Eventos.COMPROBAR_CURSO_KO, id);
		return new Context(Eventos.COMPROBAR_CURSO_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.COMPROBAR_CURSO;
	}
}
