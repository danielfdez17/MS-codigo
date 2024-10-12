package presentacion.controlador.command.command_almacen;

import negocio.almacen.TAlmacen;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandAltaAlmacen implements Command {

	public Context execute(Object datos) {
		TAlmacen almacen = (TAlmacen) datos;
		int res = FactoriaNegocio.getInstancia().crearSAAlmacen().altaAlmacen(almacen);
		if (res > 0) return new Context(Eventos.ALTA_ALMACEN_OK, almacen);
		return new Context(Eventos.ALTA_ALMACEN_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ALTA_ALMACEN;
	}
}