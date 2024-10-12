package integracion.proveedor_producto;

import negocio.proveedor_producto.TProveedorProducto;

import java.util.List;

public interface DAOProveedorProducto {

	public int createProveedorProducto(TProveedorProducto tpp);
	public List<TProveedorProducto> readAllByProveedor(int id_proveedor);
	public List<TProveedorProducto> readAllByProducto(int id_producto);
	public int deleteProveedorProducto(int id_proveedor, int id_producto);
	public TProveedorProducto readProveedorProducto(int id_proveedor, int id_producto);
}