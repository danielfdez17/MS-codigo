package presentacion.controlador.command.command_empleado;

import negocio.empleado.TEmpleadoTecnico;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandActualizarEmpleadoTecnico implements Command {

	@Override
	public Context execute(Object datos) {
		
		TEmpleadoTecnico tp = (TEmpleadoTecnico) datos;
		int res = FactoriaNegocio.getInstancia().crearSAEmpleado().actualizarEmpleadoTecnico(tp);
		
		if(res > 0){return new Context(Eventos.ACTUALIZAR_EMPLEADO_TECNICO_OK, res);};
		
		return new Context(Eventos.ACTUALIZAR_EMPLEADO_TECNICO_KO, res);
	}

	@Override
	public Eventos getId() {
		// TODO Auto-generated method stub
		return Eventos.ACTUALIZAR_EMPLEADO_TECNICO;
	}
}
