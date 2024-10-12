package presentacion.controlador.command.command_almacen;

import negocio.almacen.TAlmacen;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandActualizarAlmacen implements Command {
	
	public Context execute(Object datos) {
		TAlmacen ta = (TAlmacen) datos;
		int res = FactoriaNegocio.getInstancia().crearSAAlmacen().actualizarAlmacen(ta);
		if (res > 0) return new Context(Eventos.ACTUALIZAR_ALMACEN_OK, ta);
		return new Context(Eventos.ACTUALIZAR_ALMACEN_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_ALMACEN;
	}
}