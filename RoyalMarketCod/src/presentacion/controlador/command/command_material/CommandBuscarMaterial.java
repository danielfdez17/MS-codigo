package presentacion.controlador.command.command_material;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.material.TMaterial;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandBuscarMaterial implements Command {

	@Override
	public Context execute(Object datos) {
		int id = (int)datos;
		TMaterial res = FactoriaNegocio.getInstancia().crearSAMaterial().buscarMaterial(id);
		Eventos e;
		if (res == null) e = Eventos.BUSCAR_MATERIAL_KO;
		else e = Eventos.BUSCAR_MATERIAL_OK;
		return new Context(e, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.BUSCAR_MATERIAL;
	}

}
