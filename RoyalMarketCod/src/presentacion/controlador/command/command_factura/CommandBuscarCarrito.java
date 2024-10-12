package presentacion.controlador.command.command_factura;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.factura.TCarrito;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBuscarCarrito implements Command {
	
	public Context execute(Object datos) {
		int id_factura = (int) datos;
		TCarrito res = FactoriaNegocio.getInstancia().crearSAFactura().buscarCarrito(id_factura);
		if (res != null) return new Context(Eventos.BUSCAR_CARRITO_OK, res);
		return new Context(Eventos.BUSCAR_CARRITO_KO, id_factura);
	}

	@Override
	public Eventos getId() {
		return Eventos.BUSCAR_CARRITO;
	}
}