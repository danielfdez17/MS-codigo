package presentacion.controlador.command.command_material;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.material.TMaterial;
import presentacion.controlador.Eventos;
import presentacion.controlador.command.Command;
import presentacion.factoriaVistas.Context;

public class CommandListarMaterialesPorEmpleado implements Command {

	@Override
	public Context execute(Object datos) {
		int idEmpleado = (int)datos;
		List<TMaterial> res = FactoriaNegocio.getInstancia().crearSAMaterial().listarMaterialesPorEmpleado(idEmpleado);
		Eventos e;
		if (res != null && !res.isEmpty()) e = Eventos.LISTAR_MATERIALES_POR_EMPLEADO_KO;
		else e = Eventos.LISTAR_MATERIALES_POR_EMPLEADO_OK;
		return new Context(e, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_MATERIALES_POR_EMPLEADO;
	}

}
