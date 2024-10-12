package presentacion.controlador.command.command_factura;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.factura.TFactura;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandListarFacturas implements Command {

	public Context execute(Object datos) {
		List<TFactura> res = FactoriaNegocio.getInstancia().crearSAFactura().listarFacturas();
		if (res == null || res.isEmpty()) return new Context(Eventos.LISTAR_FACTURAS_KO, res);
		return new Context(Eventos.LISTAR_FACTURAS_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_FACTURAS;
	}
}