package presentacion.controlador.command.command_oficina;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.oficina.TOficina;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandListarOficinas implements Command {

	@Override
	public Context execute(Object datos) {
		List<TOficina> res = FactoriaNegocio.getInstancia().crearSAOficina().listarOficinas();
		Eventos e = (res == null || res.isEmpty() ? Eventos.LISTAR_OFICINAS_KO : Eventos.LISTAR_OFICINAS_OK);
		return new Context(e, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_OFICINAS;
	}

}
