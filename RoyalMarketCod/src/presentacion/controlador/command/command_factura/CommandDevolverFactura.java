package presentacion.controlador.command.command_factura;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandDevolverFactura implements Command {

	public Context execute(Object datos) {
		int id_factura = (int) datos;
		int res = FactoriaNegocio.getInstancia().crearSAFactura().devolverFacturaCompleta(id_factura);
		if (res > 0) return new Context(Eventos.DEVOLVER_FACTURA_OK, id_factura);
		return new Context(Eventos.DEVOLVER_FACTURA_KO, id_factura);
	}

	@Override
	public Eventos getId() {
		return Eventos.DEVOLVER_FACTURA;
	}
}