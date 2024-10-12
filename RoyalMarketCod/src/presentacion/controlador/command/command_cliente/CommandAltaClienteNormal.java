package presentacion.controlador.command.command_cliente;

import negocio.cliente.TClienteNormal;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandAltaClienteNormal implements Command {

	public Context execute(Object datos) {
		TClienteNormal cliente = (TClienteNormal)datos;
		int res = FactoriaNegocio.getInstancia().crearSACliente().altaClienteNormal(cliente);
		if (res > 0) return new Context(Eventos.ALTA_CLIENTE_NORMAL_OK, cliente);
		return new Context(Eventos.ALTA_CLIENTE_NORMAL_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ALTA_CLIENTE_NORMAL;
	}
}