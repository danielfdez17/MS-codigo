package integracion.producto;

import negocio.producto.TProducto;

import java.util.List;

public interface DAOProducto {

	public int altaProducto(TProducto tp);
	public TProducto buscarProducto(int id);
	public TProducto buscarProductoPorNombre(String nombre);
	public List<TProducto> listarProductos();
	public List<TProducto> listarProductosPorAlmacen(int idAlmacen);
	public int actualizarProducto(TProducto tp);
	public int bajaProducto(int id);
}