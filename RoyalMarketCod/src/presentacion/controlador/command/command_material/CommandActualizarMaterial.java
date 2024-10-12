package presentacion.controlador.command.command_material;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.material.TMaterial;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandActualizarMaterial implements Command {

	@Override
	public Context execute(Object datos) {
		TMaterial material = (TMaterial)datos;
		int res = FactoriaNegocio.getInstancia().crearSAMaterial().actualizarMaterial(material);
		Eventos e = (res <= 0 ? Eventos.ACTUALIZAR_MATERIAL_KO : Eventos.ACTUALIZAR_MATERIAL_OK);
		return new Context(e, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_MATERIAL;
	}

}
