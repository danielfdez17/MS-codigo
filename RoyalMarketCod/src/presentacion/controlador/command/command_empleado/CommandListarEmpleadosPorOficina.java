package presentacion.controlador.command.command_empleado;

import java.util.List;

import negocio.empleado.TEmpleado;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandListarEmpleadosPorOficina implements Command{
	public Context execute(Object datos) {
		int id = (int) datos;
		List<TEmpleado> res = FactoriaNegocio.getInstancia().crearSAEmpleado().listarEmpleadosPorOficina(id);
		if (res != null && !res.isEmpty()) return new Context(Eventos.LISTAR_EMPLEADOS_POR_OFICINA_OK, res);
		return new Context(Eventos.LISTAR_EMPLEADOS_POR_OFICINA_KO, res);
		
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_EMPLEADOS_POR_OFICINA;
	}
}