package presentacion.controlador.command.command_oficina;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.oficina.TOficina;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandBuscarOficina implements Command {

	@Override
	public Context execute(Object datos) {
		int id = (int)datos;
		TOficina res = FactoriaNegocio.getInstancia().crearSAOficina().buscarOficina(id);
		if (res == null) return new Context(Eventos.BUSCAR_OFICINA_KO, id);
		return new Context(Eventos.BUSCAR_OFICINA_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BUSCAR_OFICINA;
	}

}
