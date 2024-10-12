package presentacion.controlador.command.command_material;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.material.TMaterial;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandListarMateriales implements Command {

	@Override
	public Context execute(Object datos) {
		List<TMaterial> res = FactoriaNegocio.getInstancia().crearSAMaterial().listarMateriales();
		Eventos e;
		if (res != null && !res.isEmpty()) e = Eventos.LISTAR_MATERIALES_KO;
		else e = Eventos.LISTAR_MATERIALES_OK;
		return new Context(e, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_MATERIALES;
	}

}
