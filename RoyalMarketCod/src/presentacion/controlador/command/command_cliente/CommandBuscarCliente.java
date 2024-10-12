package presentacion.controlador.command.command_cliente;

import negocio.cliente.TCliente;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBuscarCliente implements Command {
	public Context execute(Object datos) {
		int id = (int)datos;
		TCliente cliente = FactoriaNegocio.getInstancia().crearSACliente().buscarCliente(id);
		if (cliente == null) return new Context(Eventos.BUSCAR_CLIENTE_KO, id);
		return new Context(Eventos.BUSCAR_CLIENTE_OK, cliente);
	}

	@Override
	public Eventos getId() {
		return Eventos.BUSCAR_CLIENTE;
	}
}