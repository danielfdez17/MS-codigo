package utilities;

public class Utils {
	
	public static final String SALTO = "\n";
	
	// PARA LOS toString de los transfers
	public static final String ID = "ID: %d" + SALTO;
	public static final String NOMBRE = "Nombre: %s" + SALTO;
	public static final String DIRECCION = "Direccion: %s" + SALTO;
	public static final String NIF = "NIF: %s" + SALTO;
	public static final String REPORTE = "Reporte: %s" + SALTO;
	public static final String ENLACE = "Enlace sesion: %s" + SALTO;
	public static final String ACTIVO = "Activo: %b" + SALTO;
	public static final String ID_ENTIDAD = "ID %s: %d" + SALTO;
	public static final String TELEFONO = "Telefono: %d" + SALTO;
	public static final String CANTIDAD = "Cantidad: %d" + SALTO;
	public static final String AULA = "Aula: %d" + SALTO;
	public static final String PLAZAS = "Plazas: %d" + SALTO;
	public static final String HORAS_AL_DIA = "Horas al dia: %d" + SALTO;
	public static final String NIVEL = "Nivel: %d" + SALTO;
	public static final String PRECIO = "Precio: %d" + SALTO;
	public static final String SUELDO = "Sueldo: %s" + SALTO;
	public static final String ALTA = "Alta de %s";
	public static final String BAJA = "Baja de %s";
	public static final String BUSCAR = "Buscar %s";
	public static final String LISTAR = "Listar %s";
	public static final String ACTUALIZAR = "Actualizar %s";
	
	// Mensajes de error
	public static final String RESPUESTA_NO_CONTEMPLADA = "�Respuesta no contemplada!";
	public static final String ERROR_SINTACTICO = "Error sintactico";
	public static final String ERROR_BBDD = "Error en la BBDD";
	public static final String ERROR_INESPERADO = "Ha ocurrido un error inesperado";
	public static final String FALTAN_CAMPOS_POR_RELLENAR = "Faltan campos por rellenar";
	
	public static final String ALMACEN = "Almacen";
	public static final String ALMACENES = "Almacenes";
	
	public static final String CLIENTE = "Cliente";
	public static final String CLIENTE_VIP = CLIENTE + " vip";
	public static final String CLIENTE_NORMAL = CLIENTE + " normal";
	public static final String CLIENTES = CLIENTE + "s";
	

	public static final String CURSO = "Curso";
	public static final String CURSOS = CURSO + "s";
	
	public static final String TAREA = "Tarea";
	public static final String TAREAS = TAREA + "s";
	
	public static final String MATERIAL = "Material";
	public static final String MATERIALES = MATERIAL + "es";

	
	
	public static String format(String format, String args) {
		return String.format(format, args);
	}
	
}
