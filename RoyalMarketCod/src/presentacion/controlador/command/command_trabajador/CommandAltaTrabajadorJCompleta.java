package presentacion.controlador.command.command_trabajador;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.trabajador.TTrabajadorJCompleta;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandAltaTrabajadorJCompleta implements Command {
	public Context execute(Object datos) {
		TTrabajadorJCompleta tt = (TTrabajadorJCompleta)datos;
		int res = FactoriaNegocio.getInstancia().crearSATrabajador().altaTrabajadorJCompleta(tt);
		if (res > 0) return new Context(Eventos.ALTA_TRABAJADOR_J_COMPLETA_OK, tt);
		return new Context(Eventos.ALTA_TRABAJADOR_J_COMPLETA_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ALTA_TRABAJADOR_J_COMPLETA;
	}
}