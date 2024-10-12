package presentacion.controlador.command.command_curso;

import negocio.curso.TCursoPresencial;
import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandActualizarCursoPresencial implements Command{
	@Override
	public Context execute(Object datos)  {
		
		TCursoPresencial tp = (TCursoPresencial) datos;
		int res = FactoriaNegocio.getInstancia().crearSACurso().actualizarCursoPresencial(tp);
		
		if(res > 0){return new Context(Eventos.ACTUALIZAR_CURSO_PRESENCIAL_OK, tp);};
		
		return new Context(Eventos.ACTUALIZAR_CURSO_PRESENCIAL_KO, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_CURSO_PRESENCIAL;
	}
}
