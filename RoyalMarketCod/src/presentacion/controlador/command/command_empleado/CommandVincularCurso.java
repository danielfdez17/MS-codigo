package presentacion.controlador.command.command_empleado;

import negocio.empleado_curso.TEmpleadoCurso;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandVincularCurso implements Command {

	@Override
	public Context execute(Object datos) {
		TEmpleadoCurso tec = (TEmpleadoCurso)datos;
		int res = FactoriaNegocio.getInstancia().crearSAEmpleado().vincularCurso(tec);
		if (res <= 0) return new Context(Eventos.VINCULAR_CURSO_KO, res);
		return new Context(Eventos.VINCULAR_CURSO_OK, tec);
	}

	@Override
	public Eventos getId() {
		return Eventos.VINCULAR_CURSO;
	}

}
