package presentacion.controlador.command.command_oficina;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.oficina.TOficina;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandAltaOficina implements Command {

	@Override
	public Context execute(Object datos) {
		TOficina oficina = (TOficina)datos;
		int res = FactoriaNegocio.getInstancia().crearSAOficina().altaOficina(oficina);
		if (res <= 0) return new Context(Eventos.ALTA_OFICINA_KO, res);
		return new Context(Eventos.ALTA_OFICINA_OK, oficina);
		
	}

	@Override
	public Eventos getId() {
		return Eventos.ALTA_OFICINA;
	}

}
