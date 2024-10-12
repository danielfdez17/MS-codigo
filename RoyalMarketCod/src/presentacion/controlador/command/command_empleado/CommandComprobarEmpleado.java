package presentacion.controlador.command.command_empleado;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.empleado.TEmpleado;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandComprobarEmpleado  implements Command {
	public Context execute(Object datos) {
		int id = (int) datos;
		TEmpleado res = FactoriaNegocio.getInstancia().crearSAEmpleado().buscarEmpleado(id);
		if(res == null) return new Context(Eventos.COMPROBAR_EMPLEADO_KO, id);
		return new Context(Eventos.COMPROBAR_EMPLEADO_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.COMPROBAR_EMPLEADO;
	}

}
