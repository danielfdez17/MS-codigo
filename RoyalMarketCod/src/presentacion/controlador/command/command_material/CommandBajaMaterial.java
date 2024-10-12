package presentacion.controlador.command.command_material;

import negocio.factoriaNegocio.FactoriaNegocio;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandBajaMaterial implements Command {

	@Override
	public Context execute(Object datos) {
		int id = (int)datos;
		int res = FactoriaNegocio.getInstancia().crearSAMaterial().bajaMaterial(id);
		Eventos e;
		if (res <= 0) e = Eventos.BAJA_MATERIAL_KO;
		else e = Eventos.BAJA_MATERIAL_OK;
		return new Context(e, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BAJA_MATERIAL;
	}

}
