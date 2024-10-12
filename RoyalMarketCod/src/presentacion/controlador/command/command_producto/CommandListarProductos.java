package presentacion.controlador.command.command_producto;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandListarProductos implements Command {

	public Context execute(Object datos) {
		List<TProducto> res = FactoriaNegocio.getInstancia().crearSAProducto().listarProductos();
		if (res != null && !res.isEmpty()) return new Context(Eventos.LISTAR_PRODUCTOS_OK, res);
		return new Context(Eventos.LISTAR_PRODUCTOS_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_PRODUCTOS;
	}
}