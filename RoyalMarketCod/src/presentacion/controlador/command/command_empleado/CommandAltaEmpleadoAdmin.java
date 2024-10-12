package presentacion.controlador.command.command_empleado;

import negocio.empleado.TEmpleadoAdministrador;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandAltaEmpleadoAdmin implements Command {

	@Override
	public Context execute(Object datos) {
		
		TEmpleadoAdministrador tp = (TEmpleadoAdministrador) datos;
		int res = FactoriaNegocio.getInstancia().crearSAEmpleado().altaEmpleadoAdminsitrador(tp);
		
		if(res > 0){return new Context(Eventos.ALTA_EMPLEADO_ADMINISTRADOR_OK, res);};
		
		return new Context(Eventos.ALTA_EMPLEADO_ADMINISTRADOR_KO, res);
	}

	@Override
	public Eventos getId() {
		// TODO Auto-generated method stub
		return Eventos.ALTA_EMPLEADO_ADMINISTRADOR;
	}
}
