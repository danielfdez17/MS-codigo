package negocio.proveedor;

import java.util.List;

public interface SAProveedor {
	
	public static final int PROVEEDOR_INEXSITENTE = -1;
	public static final int PROVEEDOR_INACTIVO = -2;
	public static final int PROVEEDOR_ACTIVO = -3;
	public static final int ERROR_BBDD = -4;
	public static final int ERROR_SINTACTICO = -5;
	public static final int PRODUCTO_INEXISTENTE = -6;
	public static final int PRODUCTO_INACTIVO = -7; 
	public static final int YA_VINCULADOS = -8;
	public static final int YA_DESVINCULADOS = -9;
	public static final int PRODUCTOS_VINCULADOS = -10;

	public int altaProveedor(TProveedor tp);
	public TProveedor buscarProveedor(int id);
	public List<TProveedor> listarProveedores();
	public List<TProveedor> listarProveedoresPorProducto(int idProducto);
	public int vincularProducto(int idProveedor, int idProducto);
	public int desvincularProducto(int idProveedor, int idProducto);
	public int actualizarProveedor(TProveedor tp);
	public int bajaProveedor(int id);
}