package presentacion.controlador.command.command_oficina;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.oficina.TOficina;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandComprobarOficina implements Command {

	@Override
	public Context execute(Object datos) {
		int id = (int)datos;
		TOficina res = FactoriaNegocio.getInstancia().crearSAOficina().buscarOficina(id);
		Eventos e = (res == null ? Eventos.COMPROBAR_OFICINA_KO: Eventos.COMPROBAR_OFICINA_OK);
		return new Context(e, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.COMPROBAR_OFICINA;
	}

}
