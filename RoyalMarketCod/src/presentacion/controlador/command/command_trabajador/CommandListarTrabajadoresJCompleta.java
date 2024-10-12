package presentacion.controlador.command.command_trabajador;

import java.util.List;

import integracion.query.FactoriaQuery;
import negocio.trabajador.TTrabajadorJCompleta;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandListarTrabajadoresJCompleta implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public Context execute(Object datos) {
		List<TTrabajadorJCompleta> res = (List<TTrabajadorJCompleta>) FactoriaQuery.getInstancia().crearQueryTrabajadoresJCompleta().execute(null);
		if (res == null || res.isEmpty()) return new Context(Eventos.LISTAR_TRABAJADORES_J_COMPLETA_KO, res);
		return new Context(Eventos.LISTAR_TRABAJADORES_J_COMPLETA_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_TRABAJADORES_J_COMPLETA;
	}

}
