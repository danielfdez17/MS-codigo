package integracion.cliente;

import java.util.List;

import negocio.cliente.TCliente;
import negocio.cliente.TClienteNormal;
import negocio.cliente.TClienteVIP;

public interface DAOCliente {

	public int altaClienteVIP(TClienteVIP tcv);
	
	public int altaClienteNormal(TClienteNormal tcn);
	
	public TCliente buscarCliente(int id);

	public TCliente buscarClientePorNIF(String nif);

	public List<TCliente> listarClientes();

	public int actualizarClienteVIP(TClienteVIP tcv);
	
	public int actualizarClienteNormal(TClienteNormal tcn);

	public int bajaCliente(int id);
}