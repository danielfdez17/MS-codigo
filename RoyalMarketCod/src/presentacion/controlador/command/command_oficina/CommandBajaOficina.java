package presentacion.controlador.command.command_oficina;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandBajaOficina implements Command {

	@Override
	public Context execute(Object datos) {
		int id = (int)datos;
		int res = FactoriaNegocio.getInstancia().crearSAOficina().bajaOficina(id);
		if (res <= 0) return new Context(Eventos.BAJA_OFICINA_KO, res);
		return new Context(Eventos.BAJA_OFICINA_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BAJA_OFICINA;
	}

}
