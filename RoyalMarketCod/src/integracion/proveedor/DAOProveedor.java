package integracion.proveedor;

import negocio.proveedor.TProveedor;
import java.util.List;

public interface DAOProveedor {

	public int altaProveedor(TProveedor tp);
	public TProveedor buscarProveedor(int id);
	public TProveedor buscarProveedorPorNIF(String nif);
	public List<TProveedor> listarProveedores();
	public List<TProveedor> listarProveedoresPorProducto(int id_producto);
	public int actualizarProveedor(TProveedor tp);
	public int bajaProveedor(int id);
}