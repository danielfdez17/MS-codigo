package presentacion.controlador.command.command_almacen;

import negocio.almacen.TAlmacen;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBuscarAlmacen implements Command {

	public Context execute(Object datos) {
		int id = (int) datos;
		TAlmacen almacen = FactoriaNegocio.getInstancia().crearSAAlmacen().buscarAlmacen(id);
		if (almacen == null) return new Context(Eventos.BUSCAR_ALMACEN_KO, almacen);
		return new Context(Eventos.BUSCAR_ALMACEN_OK, almacen);
	}

	@Override
	public Eventos getId() {
		return Eventos.BUSCAR_ALMACEN;
	}
}