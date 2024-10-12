package presentacion.controlador.command.command_producto;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandListarProductosPorProveedor implements Command {
	public Context execute(Object datos) {
		int id_proveedor = (int) datos;
		List<TProducto> res = FactoriaNegocio.getInstancia().crearSAProducto().listarProductosPorProveedor(id_proveedor);
		if (res != null && !res.isEmpty()) return new Context(Eventos.LISTAR_PRODUCTOS_POR_PROVEEDOR_OK, res);
		return new Context(Eventos.LISTAR_PRODUCTOS_POR_PROVEEDOR_KO, res);
		
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_PRODUCTOS_POR_PROVEEDOR;
	}
}