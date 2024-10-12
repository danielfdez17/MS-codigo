package presentacion.controlador.command.command_producto;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandActualizarProducto implements Command {

	public Context execute(Object datos) {
		TProducto producto = (TProducto)datos;
		int res = FactoriaNegocio.getInstancia().crearSAProducto().actualizarProducto(producto);
		if (res > 0) return new Context(Eventos.ACTUALIZAR_PRODUCTO_OK, producto);
		return new Context(Eventos.ACTUALIZAR_PRODUCTO_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_PRODUCTO;
	}
}