package presentacion.controlador.command.command_trabajador;

import java.util.List;

import integracion.query.FactoriaQuery;
import negocio.trabajador.TTrabajador;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandListarTrabajadoresDespedidos implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public Context execute(Object datos) {
		List<TTrabajador> res = (List<TTrabajador>) FactoriaQuery.getInstancia().crearQueryTrabajadoresDespedidos().execute(null);
		if (res == null || res.isEmpty()) return new Context(Eventos.LISTAR_TRABAJADORES_DESPEDIDOS_KO, res);
		return new Context(Eventos.LISTAR_TRABAJADORES_DESPEDIDOS_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_TRABAJADORES_DESPEDIDOS;
	}

}
