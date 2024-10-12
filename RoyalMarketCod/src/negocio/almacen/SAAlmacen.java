package negocio.almacen;

import java.util.List;

public interface SAAlmacen {

	public static final int ALMACEN_INEXISTENTE = -1;
	public static final int ALMACEN_INACTIVO = -2;
	public static final int ALMACEN_ACTIVO = -3;
	public static final int ERROR_BBDD = -4;
	public static final int ERROR_SINTACTICO = -5;
	public static final int PRODUCTOS_EN_ALMACEN = -6;
	
	public int altaAlmacen(TAlmacen ta);
	public TAlmacen buscarAlmacen(int id);
	public List<TAlmacen> listarAlmacenes();
	public int actualizarAlmacen(TAlmacen ta);
	public int bajaAlmacen(int id);
}