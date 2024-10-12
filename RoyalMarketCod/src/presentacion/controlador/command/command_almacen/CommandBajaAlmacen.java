package presentacion.controlador.command.command_almacen;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBajaAlmacen implements Command {

	public Context execute(Object datos) {
		int id = (int) datos;
		int res = FactoriaNegocio.getInstancia().crearSAAlmacen().bajaAlmacen(id);
		if (res > 0) return new Context(Eventos.BAJA_ALMACEN_OK, res);
		return new Context(Eventos.BAJA_ALMACEN_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BAJA_ALMACEN;
	}
}