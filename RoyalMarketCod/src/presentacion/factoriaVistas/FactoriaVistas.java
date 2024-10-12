package presentacion.factoriaVistas;

public abstract class FactoriaVistas {

	private static FactoriaVistas instancia;

	public synchronized static FactoriaVistas getInstancia() {
		if (instancia == null) instancia = new FactoriaVistasImp();
		return instancia;
	}


	public abstract void crearVista(Context context);
}