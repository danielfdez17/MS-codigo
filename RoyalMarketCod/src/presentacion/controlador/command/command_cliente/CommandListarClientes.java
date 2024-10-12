package presentacion.controlador.command.command_cliente;

import java.util.List;

import negocio.cliente.TCliente;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandListarClientes implements Command {
	public Context execute(Object datos) {
		List<TCliente> res = FactoriaNegocio.getInstancia().crearSACliente().listarClientes();
		if (res != null && !res.isEmpty()) return new Context(Eventos.LISTAR_CLIENTES_OK, res);
		return new Context(Eventos.LISTAR_CLIENTES_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_CLIENTES;
	}
}