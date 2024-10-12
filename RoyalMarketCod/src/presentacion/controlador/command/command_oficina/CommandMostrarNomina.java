package presentacion.controlador.command.command_oficina;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.oficina.TOficina;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandMostrarNomina implements Command {

	@Override
	public Context execute(Object datos) {
		int id = (int)datos;
		double res = FactoriaNegocio.getInstancia().crearSAOficina().mostrarNomina(id);
		if (res < 0) return new Context(Eventos.MOSTRAR_NOMINA_KO, (int)res);
		return new Context(Eventos.MOSTRAR_NOMINA_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.MOSTRAR_NOMINA;
	}

}
