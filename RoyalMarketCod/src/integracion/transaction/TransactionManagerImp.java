package integracion.transaction;

import java.util.concurrent.ConcurrentHashMap;

public class TransactionManagerImp extends TransactionManager {

	private ConcurrentHashMap<Thread, Transaction> transacciones;
	
	public TransactionManagerImp() {
		this.transacciones = new ConcurrentHashMap<Thread, Transaction>();
	}

	@Override
	public Transaction nuevaTransaccion() {
		Thread thread = Thread.currentThread();
		if (this.transacciones != null) {
			try {
				Transaction t = this.transacciones.get(thread);
				if (t == null) {
					TransactionFactory transaction_factory = TransactionFactory.getInstancia();
					t = transaction_factory.getTransaccion();
					this.transacciones.put(thread, t);
				}
				else {
//					throw new Exception("¡Ya existe una transaccion en marcha!");
				}
				return t;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Transaction getTransaccion() {
		Thread thread = Thread.currentThread();
		if (this.transacciones != null) {
			Transaction t = this.transacciones.get(thread);
			return t;
		}
		return null;
	}

	@Override
	public void eliminaTransaccion() {
		Thread thread = Thread.currentThread();
		if (this.transacciones != null) {
			this.transacciones.remove(thread);
		}
	}
}