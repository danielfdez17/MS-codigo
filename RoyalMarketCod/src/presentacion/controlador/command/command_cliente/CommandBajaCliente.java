package presentacion.controlador.command.command_cliente;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBajaCliente implements Command {

	public Context execute(Object datos) {
		int id = (int)datos;
		int res = FactoriaNegocio.getInstancia().crearSACliente().bajaCliente(id);
		if (res > 0) return new Context(Eventos.BAJA_CLIENTE_OK, res);
		return new Context(Eventos.BAJA_CLIENTE_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BAJA_CLIENTE;
	}
}