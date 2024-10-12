package presentacion.controlador.command.command_cliente;

import negocio.cliente.TClienteVIP;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandActualizarClienteVIP implements Command {

	public Context execute(Object datos) {
		TClienteVIP cliente = (TClienteVIP)datos;
		int res = FactoriaNegocio.getInstancia().crearSACliente().actualizarClienteVIP(cliente);
		if (res > 0) return new Context(Eventos.ACTUALIZAR_CLIENTE_VIP_OK, cliente);
		return new Context(Eventos.ACTUALIZAR_CLIENTE_VIP_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_CLIENTE_VIP;
	}
}