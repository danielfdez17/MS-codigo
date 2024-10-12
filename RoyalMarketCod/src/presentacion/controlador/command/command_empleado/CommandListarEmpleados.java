package presentacion.controlador.command.command_empleado;

import java.util.List;

import negocio.empleado.TEmpleado;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandListarEmpleados implements Command {

	public Context execute(Object datos) {
		List<TEmpleado> res = FactoriaNegocio.getInstancia().crearSAEmpleado().listarEmpleados();
		if (res == null || res.isEmpty()) return new Context(Eventos.LISTAR_EMPLEADOS_KO, res);
		return new Context(Eventos.LISTAR_EMPLEADOS_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_EMPLEADOS;
	}
}
