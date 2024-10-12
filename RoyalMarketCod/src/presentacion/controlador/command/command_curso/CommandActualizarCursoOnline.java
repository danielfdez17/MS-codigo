package presentacion.controlador.command.command_curso;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.curso.TCursoOnline;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandActualizarCursoOnline implements Command {
	@Override
	public Context execute(Object datos)  {
		
		TCursoOnline tp = (TCursoOnline) datos;
		int res = FactoriaNegocio.getInstancia().crearSACurso().actualizarCursoOnline(tp);
		
		if(res > 0){return new Context(Eventos.ACTUALIZAR_CURSO_ONLINE_OK, tp);};
		
		return new Context(Eventos.ACTUALIZAR_CURSO_ONLINE_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_CURSO_ONLINE;
	}

}
