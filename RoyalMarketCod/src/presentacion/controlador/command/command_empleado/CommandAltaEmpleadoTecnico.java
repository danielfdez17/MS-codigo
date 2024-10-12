package presentacion.controlador.command.command_empleado;

import negocio.empleado.TEmpleadoTecnico;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandAltaEmpleadoTecnico implements Command {

	@Override
	public Context execute(Object datos) {
		
		TEmpleadoTecnico tp = (TEmpleadoTecnico) datos;
		int res = FactoriaNegocio.getInstancia().crearSAEmpleado().altaEmpleadoTecnico(tp);
		
		if(res > 0){return new Context(Eventos.ALTA_EMPLEADO_TECNICO_OK, res);};
		
		return new Context(Eventos.ALTA_EMPLEADO_TECNICO_KO, res);
	}

	@Override
	public Eventos getId() {
		// TODO Auto-generated method stub
		return Eventos.ALTA_EMPLEADO_TECNICO;
	}
}
