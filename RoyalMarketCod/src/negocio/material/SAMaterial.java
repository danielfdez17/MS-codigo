package negocio.material;

import java.util.List;

public interface SAMaterial {
	
	public static final int MATERIAL_INEXISTENTE = -1;
	public static final int MATERIAL_INACTIVO = -2;
	public static final int MATERIAL_ACTIVO = -3;
	public static final int EMPLEADO_INEXISTENTE = -4;
	public static final int EMPLEADO_INACTIVO = -5;
	public static final int ERROR_SINTACTICO = -6;
	public static final int ERROR_INESPERADO = -7;
	
	public int altaMaterial(TMaterial material);

	public TMaterial buscarMaterial(int id);

	public List<TMaterial> listarMateriales();

	public List<TMaterial> listarMaterialesPorEmpleado(int idEmpleado);

	public int actualizarMaterial(TMaterial material);

	public int bajaMaterial(int id);
}