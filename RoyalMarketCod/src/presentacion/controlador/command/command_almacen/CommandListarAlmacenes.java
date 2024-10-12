package presentacion.controlador.command.command_almacen;

import java.util.List;

import negocio.almacen.TAlmacen;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandListarAlmacenes implements Command {

	public Context execute(Object datos) {
		List<TAlmacen> res = FactoriaNegocio.getInstancia().crearSAAlmacen().listarAlmacenes();
		if (res == null || res.isEmpty()) return new Context(Eventos.LISTAR_ALMACENES_KO, res);
		return new Context(Eventos.LISTAR_ALMACENES_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_ALMACENES;
	}
}