package presentacion.controlador.command.command_factura;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.factura.TProductoDevuelto;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandDevolverProducto implements Command {

	public Context execute(Object datos) {
		TProductoDevuelto productoDevuelto = (TProductoDevuelto) datos;
		int res = FactoriaNegocio.getInstancia().crearSAFactura().devolverProducto(productoDevuelto);
		if (res > 0) return new Context(Eventos.DEVOLVER_PRODUCTO_OK, productoDevuelto);
		return new Context(Eventos.DEVOLVER_PRODUCTO_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.DEVOLVER_PRODUCTO;
	}
}