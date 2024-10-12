package presentacion.controlador.command.command_material;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.material.TMaterial;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandAltaMaterial implements Command {

	@Override
	public Context execute(Object datos) {
		TMaterial material = (TMaterial)datos;
		int res = FactoriaNegocio.getInstancia().crearSAMaterial().altaMaterial(material);
		Eventos e = (res <= 0 ? Eventos.ALTA_MATERIAL_KO : Eventos.ALTA_MATERIAL_OK);
		return new Context(e, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ALTA_MATERIAL;
	}

}
