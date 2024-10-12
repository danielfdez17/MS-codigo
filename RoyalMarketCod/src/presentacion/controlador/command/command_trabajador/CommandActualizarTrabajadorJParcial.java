package presentacion.controlador.command.command_trabajador;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.trabajador.TTrabajadorJParcial;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandActualizarTrabajadorJParcial implements Command {

	public Context execute(Object datos) {
		TTrabajadorJParcial trabajador = (TTrabajadorJParcial) datos;
		int res = FactoriaNegocio.getInstancia().crearSATrabajador().actualizarTrabajadorJParcial(trabajador);
		if(res <= 0) return new Context(Eventos.ACTUALIZAR_TRABAJADOR_J_PARCIAL_KO, res);
		else return new Context(Eventos.ACTUALIZAR_TRABAJADOR_J_PARCIAL_OK, datos);
	}
	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_TRABAJADOR_J_PARCIAL;
	}
}