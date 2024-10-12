package presentacion.controlador.command.command_proyecto;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proyecto.TProyecto;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandListarProyectosPorEmpleado implements Command {
	public Context execute(Object datos) {
		int id = (int) datos;
		List<TProyecto> res = FactoriaNegocio.getInstancia().crearSAProyecto().listarProyectosPorEmpleado(id);
		if (res != null && !res.isEmpty()) return new Context(Eventos.LISTAR_PROYECTOS_POR_EMPLEADO_OK, res);
		return new Context(Eventos.LISTAR_PROYECTOS_POR_EMPLEADO_KO, res);
		
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_PROYECTOS_POR_EMPLEADO;
	}
}
