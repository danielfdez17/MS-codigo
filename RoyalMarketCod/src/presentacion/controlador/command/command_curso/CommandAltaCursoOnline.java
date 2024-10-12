package presentacion.controlador.command.command_curso;

import negocio.curso.TCursoOnline;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandAltaCursoOnline implements Command{

	@Override
	public Context execute(Object datos) {
		
		TCursoOnline tco = (TCursoOnline) datos;
		int res = FactoriaNegocio.getInstancia().crearSACurso().altaCursoOnline(tco);
		
		if(res > 0){return new Context(Eventos.ALTA_CURSO_ONLINE_OK, res);};
		
		return new Context(Eventos.ALTA_CURSO_ONLINE_OK, res);
	}

	@Override
	public Eventos getId() {
		// TODO Auto-generated method stub
		return Eventos.ALTA_CURSO_ONLINE;
	}

}
