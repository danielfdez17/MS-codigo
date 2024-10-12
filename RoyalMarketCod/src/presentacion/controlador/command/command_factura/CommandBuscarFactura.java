package presentacion.controlador.command.command_factura;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.factura.TFactura;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBuscarFactura implements Command {
	
	public Context execute(Object datos) {
		int id_factura = (int) datos;
		TFactura res = FactoriaNegocio.getInstancia().crearSAFactura().buscarFactura(id_factura);
		if (res != null) return new Context(Eventos.BUSCAR_FACTURA_OK, res);
		return new Context(Eventos.BUSCAR_FACTURA_KO, id_factura);
	}

	@Override
	public Eventos getId() {
		return Eventos.BUSCAR_FACTURA;
	}
}