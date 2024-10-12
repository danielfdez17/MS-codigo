package negocio.factura;

import java.util.List;

public interface SAFactura {
	
	public static final int FACTURA_INEXISTENTE = -1;
	public static final int FACTURA_INACTIVA = -2;
	public static final int CLIENTE_INEXISTENTE = -3;
	public static final int CLIENTE_INACTIVO = -4;
	public static final int PRODUCTO_INEXISTENTE = -5;
	public static final int PRODUCTO_INACTIVO = -6;
	public static final int STOCK_INSUFICIENTE = -7;
	public static final int ERROR_BBDD = -8;
	public static final int PRODUCTO_NO_ANYADIDO_AL_CARRITO = -9;
	public static final int ELIMINAR_MAS_PRODUCTOS_QUE_COMPRADOS = -10;
	public static final int NO_HAY_FACTURAS = -11;

	public TCarrito generarCarrito(int idCliente);
	public TCarrito buscarCarrito(int idFactura);
	public TFactura buscarFactura(int id);
	public List<TFactura> listarFacturas();
	public List<TFactura> listarFacturasPorCliente(int idCliente);
	public List<TFactura> listarFacturasPorProducto(int idProducto);
	public int actualizarFactura(TFactura tf);
	public int devolverFacturaCompleta(int idFactura);
	public int devolverProducto(TProductoDevuelto productoDevuelto);
	public int cerrarFactura(TCarrito carrito);
}