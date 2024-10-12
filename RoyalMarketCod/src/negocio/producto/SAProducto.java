package negocio.producto;

import java.util.List;

public interface SAProducto {
	
	public static final int PRODUCTO_INEXISTENTE = -1;
	public static final int PRODUCTO_INACTIVO = -2;
	public static final int PRODUCTO_ACTIVO = -3;
	public static final int ERROR_BBDD = -4;
	public static final int ERROR_SINTACTICO = -5;
	public static final int ALMACEN_INEXISTENTE = -6;
	public static final int ALMACEN_INACTIVO = -7;

	public int altaProducto(TProducto tp);
	public TProducto buscarProducto(int id);
	public List<TProducto> listarProductos();
	public List<TProducto> listarProductosPorProveedor(int id_proveedor);
	public List<TProducto> listarProductosPorAlmacen(int id_almacen);
	public int actualizarProducto(TProducto tp);
	public int bajaProducto(int id);
}