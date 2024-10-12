package presentacion.controlador.command.command_factura;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.factura.TCarrito;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandCerrarFactura implements Command {

	public Context execute(Object datos) {
		TCarrito carrito = (TCarrito) datos;
		int res = FactoriaNegocio.getInstancia().crearSAFactura().cerrarFactura(carrito);
		if (res > 0) return new Context(Eventos.CERRAR_FACTURA_OK, carrito);
		return new Context(Eventos.CERRAR_FACTURA_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.CERRAR_FACTURA;
	}
}