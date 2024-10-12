package presentacion.controlador.command.command_curso;

import negocio.curso.TCursoPresencial;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandAltaCursoPresencial implements Command{
	@Override
	public Context execute(Object datos) {
		
		TCursoPresencial tco = (TCursoPresencial) datos;
		int res = FactoriaNegocio.getInstancia().crearSACurso().altaCursoPresencial(tco);
		
		if(res > 0){return new Context(Eventos.ALTA_CURSO_PRESENCIAL_OK, res);};
		
		return new Context(Eventos.ALTA_CURSO_PRESENCIAL_OK, res);
	}

	@Override
	public Eventos getId() {
		// TODO Auto-generated method stub
		return Eventos.ALTA_CURSO_PRESENCIAL;
	}
}
