package presentacion.controlador.command.command_cliente;

import negocio.cliente.TClienteNormal;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandActualizarClienteNormal implements Command {

	public Context execute(Object datos) {
		TClienteNormal cliente = (TClienteNormal)datos;
		int res = FactoriaNegocio.getInstancia().crearSACliente().actualizarClienteNormal(cliente);
		if (res > 0) return new Context(Eventos.ACTUALIZAR_CLIENTE_NORMAL_OK, cliente);
		return new Context(Eventos.ACTUALIZAR_CLIENTE_NORMAL_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_CLIENTE_NORMAL;
	}
}