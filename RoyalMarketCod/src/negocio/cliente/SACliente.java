package negocio.cliente;

import java.util.List;

public interface SACliente {

	public static final int CLIENTE_INEXISTENTE = -1;
	public static final int CLIENTE_INACTIVO = -2;
	public static final int CLIENTE_ACTIVO = -3;
	public static final int ERROR_BBDD = -4;
	public static final int ERROR_SINTACTICO = -5;
	public static final int CAMBIO_TIPO = -6;
	
	public int altaClienteVIP(TClienteVIP tcv);
	
	public int altaClienteNormal(TClienteNormal tcn);

	public TCliente buscarCliente(int id);

	public List<TCliente> listarClientes();

	public int actualizarClienteVIP(TClienteVIP tcv);

	public int actualizarClienteNormal(TClienteNormal tcn);
	
	public int bajaCliente(int id);
}