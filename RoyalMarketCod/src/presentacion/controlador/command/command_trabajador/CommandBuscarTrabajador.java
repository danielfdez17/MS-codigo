package presentacion.controlador.command.command_trabajador;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBuscarTrabajador implements Command {
	public Context execute(Object datos) {
		int idTrabajador = (int) datos;
		Object trab = FactoriaNegocio.getInstancia().crearSATrabajador().buscarTrabajador(idTrabajador);
		if(trab == null) return new Context(Eventos.BUSCAR_TRABAJADOR_KO, idTrabajador);
		return new Context(Eventos.BUSCAR_TRABAJADOR_OK, trab);
	}
	@Override
	public Eventos getId() {
		return Eventos.BUSCAR_TRABAJADOR;
	}
}