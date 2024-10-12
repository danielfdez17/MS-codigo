package presentacion.controlador.command.command_empleado;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandBajaEmpleado implements Command{
	public Context execute(Object datos) {
		int id = (int) datos;
		int res = FactoriaNegocio.getInstancia().crearSAEmpleado().bajaEmpleado(id);
		if (res > 0) return new Context(Eventos.BAJA_EMPLEADO_OK, res);
		return new Context(Eventos.BAJA_EMPLEADO_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BAJA_EMPLEADO;
	}
}
