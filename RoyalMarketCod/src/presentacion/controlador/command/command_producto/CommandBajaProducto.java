package presentacion.controlador.command.command_producto;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBajaProducto implements Command {

	public Context execute(Object datos) {
		int id = (int) datos;
		int res = FactoriaNegocio.getInstancia().crearSAProducto().bajaProducto(id);
		if (res > 0) return new Context(Eventos.BAJA_PRODUCTO_OK, res);
		return new Context(Eventos.BAJA_PRODUCTO_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BAJA_PRODUCTO;
	}
}