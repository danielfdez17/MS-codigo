package presentacion.controlador.command.command_empleado;

import negocio.empleado.TEmpleadoProyecto;
import negocio.empleado_curso.TEmpleadoCurso;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandVincularProyecto implements Command {

	@Override
	public Context execute(Object datos) {
		TEmpleadoProyecto tep = (TEmpleadoProyecto)datos;
		int res = FactoriaNegocio.getInstancia().crearSAEmpleado().vincularProyecto(tep);
		if (res <= 0) return new Context(Eventos.VINCULAR_PROYECTO_KO, res);
		return new Context(Eventos.VINCULAR_PROYECTO_OK, tep);
	}

	@Override
	public Eventos getId() {
		return Eventos.VINCULAR_PROYECTO;
	}

}
