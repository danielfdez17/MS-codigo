package presentacion.controlador.command.command_empleado;

import negocio.empleado_curso.TEmpleadoCurso;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandDesincularCurso implements Command {

	@Override
	public Context execute(Object datos) {
		TEmpleadoCurso tec = (TEmpleadoCurso)datos;
		int res = FactoriaNegocio.getInstancia().crearSAEmpleado().desvincularCurso(tec);
		if (res <= 0) return new Context(Eventos.DESVINCULAR_CURSO_KO, res);
		return new Context(Eventos.DESVINCULAR_CURSO_OK, tec);
	}

	@Override
	public Eventos getId() {
		return Eventos.DESVINCULAR_CURSO;
	}

}
