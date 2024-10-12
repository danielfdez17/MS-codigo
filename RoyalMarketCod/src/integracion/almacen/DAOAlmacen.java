package integracion.almacen;

import negocio.almacen.TAlmacen;
import java.util.List;

public interface DAOAlmacen {
 
	public int altaAlmacen(TAlmacen ta);
	public TAlmacen buscarAlmacen(int id);
	public TAlmacen buscarAlmacenPorDireccion(String direccion);
	public List<TAlmacen> listarAlmacenes();
	public int actualizarAlmacen(TAlmacen ta);
	public int bajaAlmacen(int id);
}