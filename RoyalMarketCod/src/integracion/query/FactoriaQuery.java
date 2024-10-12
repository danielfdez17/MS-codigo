package integracion.query;

public abstract class FactoriaQuery {

	private static FactoriaQuery instancia;
	public synchronized static FactoriaQuery getInstancia() {
		if (instancia == null) instancia = new FactoriaQueryImp();
		return instancia;
	}

	public abstract Query crearQueryTrabajadoresDespedidos();
	public abstract Query crearQueryTrabajadoresJCompleta();
	public abstract Query crearQueryTrabajadoresPorRangoDeSueldo();
}