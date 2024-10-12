package presentacion.controlador.command.command_producto;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.producto.TProducto;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandComprobarProducto implements Command {
	public Context execute(Object datos) {
		int id = (int) datos;
		TProducto res = FactoriaNegocio.getInstancia().crearSAProducto().buscarProducto(id);
		if(res == null) return new Context(Eventos.COMPROBAR_PRODUCTO_KO, id);
		return new Context(Eventos.COMPROBAR_PRODUCTO_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.COMPROBAR_PRODUCTO;
	}
}