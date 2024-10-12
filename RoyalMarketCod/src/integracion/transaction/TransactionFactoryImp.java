package integracion.transaction;

public class TransactionFactoryImp extends TransactionFactory {

	@Override
	public Transaction getTransaccion() {
		return new TransactionMySQL();
	}
}