package presentacion.controlador.command.command_empleado;

import negocio.empleado_curso.TEmpleadoCurso;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandActualizarNivelEmpCurso implements Command {

	@Override
	public Context execute(Object datos) {
		TEmpleadoCurso employeeWithCurse = (TEmpleadoCurso)datos;
		int res = FactoriaNegocio.getInstancia().crearSAEmpleado().actualizarNivelCursoEmpleado(employeeWithCurse);
		if (res <= 0) return new Context(Eventos.ACTUALIZAR_NIVEL_EMP_CURSO_KO, res);
		return new Context(Eventos.ACTUALIZAR_NIVEL_EMP_CURSO_OK, employeeWithCurse);
	}

	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_NIVEL_EMP_CURSO;
	}

}
