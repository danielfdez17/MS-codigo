package presentacion.controlador.command.command_trabajador;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.trabajador.TTrabajadorJCompleta;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandActualizarTrabajadorJCompleta implements Command {

	public Context execute(Object datos) {
		TTrabajadorJCompleta trabajador = (TTrabajadorJCompleta) datos;
		int res = FactoriaNegocio.getInstancia().crearSATrabajador().actualizarTrabajadorJCompleta(trabajador);
		if(res <= 0) return new Context(Eventos.ACTUALIZAR_TRABAJADOR_J_COMPLETA_KO, res);
		else return new Context(Eventos.ACTUALIZAR_TRABAJADOR_J_COMPLETA_OK, datos);
	}
	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_TRABAJADOR_J_COMPLETA;
	}
}