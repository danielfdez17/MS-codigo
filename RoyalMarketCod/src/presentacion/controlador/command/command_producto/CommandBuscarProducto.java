package presentacion.controlador.command.command_producto;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBuscarProducto implements Command {

	public Context execute(Object datos) {
		int id = (int)datos;
		TProducto producto = FactoriaNegocio.getInstancia().crearSAProducto().buscarProducto(id);
		if (producto == null) return new Context(Eventos.BUSCAR_PRODUCTO_KO, id);
		return new Context(Eventos.BUSCAR_PRODUCTO_OK, producto);
	}

	@Override
	public Eventos getId() {
		return Eventos.BUSCAR_PRODUCTO;
	}
}