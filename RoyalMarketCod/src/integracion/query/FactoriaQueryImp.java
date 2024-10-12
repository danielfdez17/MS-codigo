package integracion.query;

public class FactoriaQueryImp extends FactoriaQuery {

	@Override
	public Query crearQueryTrabajadoresDespedidos() {
		return new TrabajadoresDespedidos();
	}

	@Override
	public Query crearQueryTrabajadoresJCompleta() {
		return new TrabajadoresJCompleta();
	}

	@Override
	public Query crearQueryTrabajadoresPorRangoDeSueldo() {
		return new TrabajadoresPorRangoSueldo();
	}
}