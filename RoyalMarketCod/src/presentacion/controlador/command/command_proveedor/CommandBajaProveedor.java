package presentacion.controlador.command.command_proveedor;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBajaProveedor implements Command {
	@Override
	public Context execute(Object datos) {
		int idProveedor = (int) datos;
		int res = FactoriaNegocio.getInstancia().crearSAProveedor().bajaProveedor(idProveedor);
		if (res < 0)
			return new Context(Eventos.BAJA_PROVEEDOR_KO, idProveedor);
		return new Context(Eventos.BAJA_PROVEEDOR_OK, datos);
	}

	@Override
	public Eventos getId() {
		return Eventos.BAJA_PROVEEDOR;
	}
}