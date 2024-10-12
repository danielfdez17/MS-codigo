package presentacion.controlador.command.command_proyecto;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proyecto.TProyecto;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandAltaProyecto implements Command {

	@Override
	public Context execute(Object datos) {
		
		TProyecto tp = (TProyecto) datos;
		int res = FactoriaNegocio.getInstancia().crearSAProyecto().altaProyecto(tp);
		
		if(res > 0){return new Context(Eventos.ALTA_PROYECTO_OK, res);};
		
		return new Context(Eventos.ALTA_PROYECTO_KO, res);
	}

	@Override
	public Eventos getId() {
		// TODO Auto-generated method stub
		return Eventos.ALTA_PROYECTO;
	}

}
