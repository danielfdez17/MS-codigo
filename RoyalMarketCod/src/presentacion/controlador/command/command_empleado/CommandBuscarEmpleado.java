package presentacion.controlador.command.command_empleado;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.empleado.TEmpleado;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandBuscarEmpleado implements Command {
	
	public Context execute(Object datos) {
		int id = (int)datos;
		TEmpleado p = FactoriaNegocio.getInstancia().crearSAEmpleado().buscarEmpleado(id);
		if (p == null) return new Context(Eventos.BUSCAR_EMPLEADO_KO, id);
		return new Context(Eventos.BUSCAR_EMPLEADO_OK, p);
	}

	@Override
	public Eventos getId() {
		return Eventos.BUSCAR_EMPLEADO;
	}
}
