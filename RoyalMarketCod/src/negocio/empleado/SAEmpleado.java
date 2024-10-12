package negocio.empleado;

import java.util.List;

import negocio.empleado_curso.TEmpleadoCurso;

public interface SAEmpleado {
	
	
	public static final int ERROR_SINTACTICO = -1;
	public static final int EMPLEADO_ACTIVO = -2;
	public static final int EMPLEADO_INACTIVO = -3;
	public static final int ERROR_INESPERADO = -4;
	public static final int EMPLEADO_INEXISTENTE = -5;
	public static final int PROYECTO_INACTIVO = -6;
	public static final int PROYECTO_INEXISTENTE = -7;
	public static final int OFICINA_INEXISTENTE = -8;
	public static final int OFICINA_INACTIVA = -9;
	public static final int ERROR_BBDD = -10;
	public static final int BAJA_FAIL_EMPLEADO = -11;
	public static final int BAJA_ACTIVO = -12;
	public static final int EMPLEADO_CON_MATERIALES_ACTIVOS = -13;
	public static final int CURSO_INEXISTENTE = -14;
	public static final int CURSO_INACTIVO = -15;
	public static final int CURSO_YA_VINCULADO = -16;
	public static final int CURSO_YA_DESVINCULADO = -17;
	public static final int PROYECTO_YA_VINCULADO = -18;
	public static final int PROYECTO_YA_DESVINCULADO = -19;

	public int altaEmpleadoAdminsitrador(TEmpleadoAdministrador empleado);

	public int altaEmpleadoTecnico(TEmpleadoTecnico empleado);

	public TEmpleado buscarEmpleado(int id);

	public List<TEmpleado> listarEmpleados();

	public List<TEmpleado> listarEmpleadosPorCurso(int idCurso);

	public List<TEmpleado> listarEmpleadosPorOficina(int idOficina);

	public List<TEmpleado> listarEmpleadosPorProyecto(int idProyecto);
	
	public int vincularCurso(TEmpleadoCurso tec);
	
	public int desvincularCurso(TEmpleadoCurso tec);
	
	public int actualizarNivelCursoEmpleado(TEmpleadoCurso employeeWithCurse);

	public int vincularProyecto(TEmpleadoProyecto tep);
	
	public int desvincularProyecto(TEmpleadoProyecto tep);

	public int actualizarEmpleadoAdministrador(TEmpleadoAdministrador empleado);

	public int actualizarEmpleadoTecnico(TEmpleadoTecnico empleado);

	public int bajaEmpleado(int id);
	
}