package presentacion.controlador.command.command_producto;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandAltaProducto implements Command {

	public Context execute(Object datos) {
		TProducto producto = (TProducto) datos;
		int res = FactoriaNegocio.getInstancia().crearSAProducto().altaProducto(producto);
		if (res > 0) return new Context(Eventos.ALTA_PRODUCTO_OK, producto);
		return new Context(Eventos.ALTA_PRODUCTO_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ALTA_PRODUCTO;
	}
}