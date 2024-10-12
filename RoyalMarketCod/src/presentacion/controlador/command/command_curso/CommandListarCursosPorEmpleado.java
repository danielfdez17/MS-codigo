package presentacion.controlador.command.command_curso;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.curso.TCurso;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandListarCursosPorEmpleado implements Command {
	public Context execute(Object datos) {
		int id = (int) datos;
		List<TCurso> res = FactoriaNegocio.getInstancia().crearSACurso().listarCursosPorEmpleado(id);
		if (res != null && !res.isEmpty()) return new Context(Eventos.LISTAR_CURSOS_POR_EMPLEADO_OK, res);
		return new Context(Eventos.LISTAR_CURSOS_POR_EMPLEADO_KO, res);
		
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_CURSOS_POR_EMPLEADO;
	}
}