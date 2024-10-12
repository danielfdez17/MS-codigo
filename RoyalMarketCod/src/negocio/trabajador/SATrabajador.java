package negocio.trabajador;

import java.util.List;


public interface SATrabajador {
	
	public static final int TRABAJADOR_INEXISTENTE = -1;
	public static final int TRABAJADOR_INACTIVO = -2;
	public static final int TRABAJADOR_ACTIVO = -3;
	public static final int ALMACEN_INEXISTENTE = -4;
	public static final int ALMACEN_INACTIVO = -5;
	public static final int CAMBIO_TIPO_TRABAJADOR = -6;
	public static final int ERROR_SINTACTICO = -7;
	public static final int ERROR_BBDD = -8;

	public int altaTrabajadorJCompleta(TTrabajadorJCompleta ttjc);
	public int altaTrabajadorJParcial(TTrabajadorJParcial ttjp);
	public TTrabajador buscarTrabajador(int id);
	public List<TTrabajador> listarTrabajadores();
	public List<TTrabajador> listarTrabajadoresPorAlmacen(int idAlmacen);
	public int actualizarTrabajadorJCompleta(TTrabajadorJCompleta ttjc);
	public int actualizarTrabajadorJParcial(TTrabajadorJParcial ttjp);
	public int bajaTrabajador(int id);
	
}