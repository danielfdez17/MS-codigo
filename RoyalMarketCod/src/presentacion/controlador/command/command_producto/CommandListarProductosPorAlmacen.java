package presentacion.controlador.command.command_producto;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandListarProductosPorAlmacen implements Command {
	public Context execute(Object datos) {
		int id_almacen = (int) datos;
		List<TProducto> res = FactoriaNegocio.getInstancia().crearSAProducto().listarProductosPorAlmacen(id_almacen);
		if (res != null && !res.isEmpty()) return new Context(Eventos.LISTAR_PRODUCTOS_POR_ALMACEN_OK, res);
		return new Context(Eventos.LISTAR_PRODUCTOS_POR_ALMACEN_KO, res);
		
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_PRODUCTOS_POR_ALMACEN;
	}
}