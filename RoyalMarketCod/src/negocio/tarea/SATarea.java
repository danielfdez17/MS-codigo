package negocio.tarea;

import java.util.List;

public interface SATarea {
	
	public static final int ERROR_INESPERADO = 0;
	public static final int TAREA_INEXISTENTE = -1;
	public static final int ERROR_SINTACTICO = -2;
	public static final int TAREA_ACTIVA = -3;
	public static final int TAREA_INACTIVA = -4;
	public static final int PROYECTO_INEXISTENTE = -5;
	public static final int PROYECTO_INACTIVO = -6;
	
	public int altaTarea(TTarea tarea);

	public TTarea buscarTarea(int id);

	public List<TTarea> listarTareas();

	public List<TTarea> listarTareasPorProyecto(int idProyecto);

	public int actualizarTarea(TTarea tarea);

	public int bajaTarea(int id);
}