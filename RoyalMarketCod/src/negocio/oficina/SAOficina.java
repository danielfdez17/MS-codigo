package negocio.oficina;

import java.util.List;

public interface SAOficina {
	
	public static final int OFICINA_INEXISTENTE = -1;
	public static final int OFICINA_INACTIVA = -2;
	public static final int OFICINA_ACTIVA = -3;
	public static final int ERROR_SINTACTICO = -4;
	public static final int ERROR_OFICINA_CON_EMPLEADOS_ACTIVOS = -5;
	public static final int ERROR_INESPERADO = -6;
	
	public int altaOficina(TOficina oficina);

	public TOficina buscarOficina(int id);

	public List<TOficina> listarOficinas();

	public double mostrarNomina(int id);

	public int actualizarOficina(TOficina oficina);

	public int bajaOficina(int id);
}