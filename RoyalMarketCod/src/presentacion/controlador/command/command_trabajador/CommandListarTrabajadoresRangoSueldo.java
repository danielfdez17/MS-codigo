package presentacion.controlador.command.command_trabajador;

import java.util.ArrayList;
import java.util.List;

import integracion.query.FactoriaQuery;
import negocio.trabajador.TTrabajador;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandListarTrabajadoresRangoSueldo implements Command {

	@SuppressWarnings("unchecked")
	@Override
	public Context execute(Object datos) {
		ArrayList<Double> rangos = (ArrayList<Double>)datos;
		List<TTrabajador> res = (List<TTrabajador>) FactoriaQuery.getInstancia().crearQueryTrabajadoresPorRangoDeSueldo().execute(rangos);
		if (res == null || res.isEmpty()) return new Context(Eventos.LISTAR_TRABAJADORES_RANGO_SUELDO_KO, res);
		return new Context(Eventos.LISTAR_TRABAJADORES_RANGO_SUELDO_OK, res);
	}

	@Override
	public Eventos getId() {
		return Eventos.LISTAR_TRABAJADORES_RANGO_SUELDO;
	}

}
