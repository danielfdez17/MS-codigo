package presentacion.controlador.command.command_proyecto;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proyecto.TProyecto;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandActualizarProyecto implements Command {

	@Override
	public Context execute(Object datos) {
		// TODO Auto-generated method stub
		
		TProyecto tp = (TProyecto) datos;
		int res = FactoriaNegocio.getInstancia().crearSAProyecto().actualizarProyecto(tp);
		
		if(res > 0){return new Context(Eventos.ACTUALIZAR_PROYECTO_OK, tp);};
		
		return new Context(Eventos.ACTUALIZAR_PROYECTO_KO, res);
	}

	@Override
	public Eventos getId() {
		// TODO Auto-generated method stub
		return Eventos.ACTUALIZAR_PROYECTO;
	}

}
