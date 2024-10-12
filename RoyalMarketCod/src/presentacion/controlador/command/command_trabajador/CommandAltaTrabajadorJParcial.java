package presentacion.controlador.command.command_trabajador;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.trabajador.TTrabajadorJParcial;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandAltaTrabajadorJParcial implements Command {

	public Context execute(Object datos) {
		TTrabajadorJParcial tt = (TTrabajadorJParcial)datos;
		int res = FactoriaNegocio.getInstancia().crearSATrabajador().altaTrabajadorJParcial(tt);
		if (res > 0) return new Context(Eventos.ALTA_TRABAJADOR_J_PARCIAL_OK, tt);
		return new Context(Eventos.ALTA_TRABAJADOR_J_PARCIAL_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ALTA_TRABAJADOR_J_PARCIAL;
	}
}