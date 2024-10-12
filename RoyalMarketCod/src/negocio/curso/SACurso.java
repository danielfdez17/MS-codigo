package negocio.curso;

import java.util.List;

public interface SACurso {
	
	public static final int ERROR_SINTACTICO = -1;
	public static final int CURSO_INEXISTENTE = -2;
	public static final int CURSO_INACTIVO = -3;
	public static final int CURSO_ACTIVO = -4;
	public static final int EMPLEADO_INEXISTENTE = -5;
	public static final int EMPLEADO_INACTIVO = -6;
	public static final int ERROR_BBDD = -7;
	public static final int CURSO_CON_EMPLEADOS = -8;
	public static final int CURSO_DUPLICADO = -9;
	public static final int CURSO_SIN_CAMBIOS = -10;



	public int altaCursoPresencial(TCursoPresencial tcp);
	
	public int altaCursoOnline(TCursoOnline tco);
	
	public TCurso buscarCurso(int id);
	
	public List<TCurso> listarCursos();
	
	public List<TCurso> listarCursosPorEmpleado(int idEmpleado);
	
	public int actualizarCursoPresencial(TCursoPresencial tcp);

	public int actualizarCursoOnline(TCursoOnline tco);

	public int bajaCurso(int id);
	
}