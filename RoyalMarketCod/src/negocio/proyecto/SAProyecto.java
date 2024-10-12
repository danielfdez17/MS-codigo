package negocio.proyecto;

import java.util.List;

public interface SAProyecto {
	final byte ERROR_INESPERADO = 0;
	final byte ERROR_SINTACTICO_NOMBRE_PROYECTO = -1;
	final byte ERROR_PROYECTO_DUPLICADO = -2;
	final byte ERROR_PROYECTO_NO_ENCONTRADO = -3;
	final byte ERROR_PROYECTO_CON_TAREA_NO_ENCONTRADA = -4;
	final byte ERROR_PROYECTO_CON_EMPLEADOS_ACTIVOS = -5;
	final byte ERROR_PROYECTO_CON_TAREAS_ACTIVAS = -6;
	final byte ERROR_PROYECTO_DADO_DE_BAJA = -7;

	public int altaProyecto(TProyecto proyecto);
	public TProyecto buscarProyecto(int id);
	public List<TProyecto> listarProyectos();
	public List<TProyecto> listarProyectosPorEmpleado(int idEmpleado);
	public int actualizarProyecto(TProyecto proyecto);
	public int bajaProyecto(int id);
}