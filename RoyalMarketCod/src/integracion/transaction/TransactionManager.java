package integracion.transaction;

public abstract class TransactionManager {
	
	private static TransactionManager instancia;

	public synchronized static TransactionManager getInstancia() {
		if (instancia == null) instancia = new TransactionManagerImp();
		return instancia;
	}

	public abstract Transaction nuevaTransaccion();

	public abstract Transaction getTransaccion();

	public abstract void eliminaTransaccion();
}