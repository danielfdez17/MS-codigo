package presentacion.controlador.command.command_factura;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.factura.TCarrito;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;
import presentacion.factura.GUIFACTURA;

public class CommandAbrirFactura implements Command {

	public Context execute(Object datos) {
		int id_cliente = (int) datos;
		TCarrito carrito = FactoriaNegocio.getInstancia().crearSAFactura().generarCarrito(id_cliente);
		if (carrito == null) return new Context(Eventos.ABRIR_FACTURA_KO, id_cliente);
		GUIFACTURA.getInstancia().setCarrito(carrito);
		return new Context(Eventos.ABRIR_FACTURA_OK, carrito);
	}

	@Override
	public Eventos getId() {
		return Eventos.ABRIR_FACTURA;
	}
}