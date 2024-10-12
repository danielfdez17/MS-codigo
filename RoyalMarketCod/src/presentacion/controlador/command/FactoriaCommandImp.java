package presentacion.controlador.command;

import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.controlador.command.command_almacen.CommandActualizarAlmacen;
import presentacion.controlador.command.command_almacen.CommandAltaAlmacen;
import presentacion.controlador.command.command_almacen.CommandBajaAlmacen;
import presentacion.controlador.command.command_almacen.CommandBuscarAlmacen;
import presentacion.controlador.command.command_almacen.CommandComprobarAlmacen;
import presentacion.controlador.command.command_almacen.CommandGUIAlmacen;
import presentacion.controlador.command.command_almacen.CommandListarAlmacenes;
import presentacion.controlador.command.command_cliente.CommandActualizarClienteNormal;
import presentacion.controlador.command.command_cliente.CommandActualizarClienteVIP;
import presentacion.controlador.command.command_cliente.CommandAltaClienteNormal;
import presentacion.controlador.command.command_cliente.CommandAltaClienteVIP;
import presentacion.controlador.command.command_cliente.CommandBajaCliente;
import presentacion.controlador.command.command_cliente.CommandBuscarCliente;
import presentacion.controlador.command.command_cliente.CommandComprobarCliente;
import presentacion.controlador.command.command_cliente.CommandGUICliente;
import presentacion.controlador.command.command_cliente.CommandListarClientes;
import presentacion.controlador.command.command_curso.CommandActualizarCursoOnline;
import presentacion.controlador.command.command_curso.CommandActualizarCursoPresencial;
import presentacion.controlador.command.command_curso.CommandAltaCursoOnline;
import presentacion.controlador.command.command_curso.CommandAltaCursoPresencial;
import presentacion.controlador.command.command_curso.CommandBajaCurso;
import presentacion.controlador.command.command_curso.CommandBuscarCurso;
import presentacion.controlador.command.command_curso.CommandComprobarCurso;
import presentacion.controlador.command.command_curso.CommandGUICurso;
import presentacion.controlador.command.command_curso.CommandListarCursos;
import presentacion.controlador.command.command_curso.CommandListarCursosPorEmpleado;
import presentacion.controlador.command.command_empleado.CommandActualizarEmpleadoAdmin;
import presentacion.controlador.command.command_empleado.CommandActualizarEmpleadoTecnico;
import presentacion.controlador.command.command_empleado.CommandActualizarNivelEmpCurso;
import presentacion.controlador.command.command_empleado.CommandAltaEmpleadoAdmin;
import presentacion.controlador.command.command_empleado.CommandAltaEmpleadoTecnico;
import presentacion.controlador.command.command_empleado.CommandBajaEmpleado;
import presentacion.controlador.command.command_empleado.CommandBuscarEmpleado;
import presentacion.controlador.command.command_empleado.CommandComprobarEmpleado;
import presentacion.controlador.command.command_empleado.CommandDesincularCurso;
import presentacion.controlador.command.command_empleado.CommandDesincularProyecto;
import presentacion.controlador.command.command_empleado.CommandGUIEmpleado;
import presentacion.controlador.command.command_empleado.CommandListarEmpleados;
import presentacion.controlador.command.command_empleado.CommandListarEmpleadosPorCurso;
import presentacion.controlador.command.command_empleado.CommandListarEmpleadosPorOficina;
import presentacion.controlador.command.command_empleado.CommandListarEmpleadosPorProyecto;
import presentacion.controlador.command.command_empleado.CommandVincularCurso;
import presentacion.controlador.command.command_empleado.CommandVincularProyecto;
import presentacion.controlador.command.command_factura.CommandAbrirFactura;
import presentacion.controlador.command.command_factura.CommandActualizarCarrito;
import presentacion.controlador.command.command_factura.CommandBuscarCarrito;
import presentacion.controlador.command.command_factura.CommandBuscarFactura;
import presentacion.controlador.command.command_factura.CommandCerrarFactura;
import presentacion.controlador.command.command_factura.CommandDevolverFactura;
import presentacion.controlador.command.command_factura.CommandGUIFactura;
import presentacion.controlador.command.command_factura.CommandListarFacturas;
import presentacion.controlador.command.command_factura.CommandListarFacturasPorCliente;
import presentacion.controlador.command.command_factura.CommandListarFacturasPorProducto;
import presentacion.controlador.command.command_main.CommandGUIMain;
import presentacion.controlador.command.command_material.CommandAltaMaterial;
import presentacion.controlador.command.command_material.CommandBajaMaterial;
import presentacion.controlador.command.command_material.CommandBuscarMaterial;
import presentacion.controlador.command.command_material.CommandComprobarMaterial;
import presentacion.controlador.command.command_material.CommandGUIMaterial;
import presentacion.controlador.command.command_material.CommandListarMateriales;
import presentacion.controlador.command.command_material.CommandListarMaterialesPorEmpleado;
import presentacion.controlador.command.command_oficina.CommandActualizarOficina;
import presentacion.controlador.command.command_oficina.CommandAltaOficina;
import presentacion.controlador.command.command_oficina.CommandBajaOficina;
import presentacion.controlador.command.command_oficina.CommandBuscarOficina;
import presentacion.controlador.command.command_oficina.CommandComprobarOficina;
import presentacion.controlador.command.command_oficina.CommandGUIOficina;
import presentacion.controlador.command.command_oficina.CommandListarOficinas;
import presentacion.controlador.command.command_oficina.CommandMostrarNomina;
import presentacion.controlador.command.command_producto.CommandActualizarProducto;
import presentacion.controlador.command.command_producto.CommandAltaProducto;
import presentacion.controlador.command.command_producto.CommandBajaProducto;
import presentacion.controlador.command.command_producto.CommandBuscarProducto;
import presentacion.controlador.command.command_producto.CommandComprobarProducto;
import presentacion.controlador.command.command_producto.CommandGUIProducto;
import presentacion.controlador.command.command_producto.CommandListarProductos;
import presentacion.controlador.command.command_producto.CommandListarProductosPorAlmacen;
import presentacion.controlador.command.command_producto.CommandListarProductosPorProveedor;
import presentacion.controlador.command.command_proveedor.CommandActualizarProveedor;
import presentacion.controlador.command.command_proveedor.CommandAltaProveedor;
import presentacion.controlador.command.command_proveedor.CommandBajaProveedor;
import presentacion.controlador.command.command_proveedor.CommandBuscarProveedor;
import presentacion.controlador.command.command_proveedor.CommandComprobarProveedor;
import presentacion.controlador.command.command_proveedor.CommandDesvincularProducto;
import presentacion.controlador.command.command_proveedor.CommandGUIProveedor;
import presentacion.controlador.command.command_proveedor.CommandListarProveedores;
import presentacion.controlador.command.command_proveedor.CommandListarProveedoresPorProdcuto;
import presentacion.controlador.command.command_proveedor.CommandVincularProducto;
import presentacion.controlador.command.command_proyecto.CommandActualizarProyecto;
import presentacion.controlador.command.command_proyecto.CommandAltaProyecto;
import presentacion.controlador.command.command_proyecto.CommandBajaProyecto;
import presentacion.controlador.command.command_proyecto.CommandBuscarProyecto;
import presentacion.controlador.command.command_proyecto.CommandComprobarProyecto;
import presentacion.controlador.command.command_proyecto.CommandGUIProyecto;
import presentacion.controlador.command.command_proyecto.CommandListarProyectos;
import presentacion.controlador.command.command_proyecto.CommandListarProyectosPorEmpleado;
import presentacion.controlador.command.command_tarea.CommandActualizarTarea;
import presentacion.controlador.command.command_tarea.CommandAltaTarea;
import presentacion.controlador.command.command_tarea.CommandBajaTarea;
import presentacion.controlador.command.command_tarea.CommandBuscarTarea;
import presentacion.controlador.command.command_tarea.CommandComprobarTarea;
import presentacion.controlador.command.command_tarea.CommandGUITarea;
import presentacion.controlador.command.command_tarea.CommandListarTareas;
import presentacion.controlador.command.command_tarea.CommandListarTareasPorProyecto;
import presentacion.controlador.command.command_trabajador.CommandActualizarTrabajadorJCompleta;
import presentacion.controlador.command.command_trabajador.CommandActualizarTrabajadorJParcial;
import presentacion.controlador.command.command_trabajador.CommandAltaTrabajadorJCompleta;
import presentacion.controlador.command.command_trabajador.CommandAltaTrabajadorJParcial;
import presentacion.controlador.command.command_trabajador.CommandBajaTrabajador;
import presentacion.controlador.command.command_trabajador.CommandBuscarTrabajador;
import presentacion.controlador.command.command_trabajador.CommandGUITrabajador;
import presentacion.controlador.command.command_trabajador.CommandListarTrabajadores;
import presentacion.controlador.command.command_trabajador.CommandListarTrabajadoresDespedidos;
import presentacion.controlador.command.command_trabajador.CommandListarTrabajadoresJCompleta;
import presentacion.controlador.command.command_trabajador.CommandListarTrabajadoresPorAlmacen;
import presentacion.controlador.command.command_trabajador.CommandListarTrabajadoresRangoSueldo;


public class FactoriaCommandImp extends FactoriaCommand {
	private static Command commands[] = {
			// Main
			new CommandGUIMain(),
			
			// Almacenes
			new CommandGUIAlmacen(),
			new CommandActualizarAlmacen(),
			new CommandAltaAlmacen(),
			new CommandBajaAlmacen(),
			new CommandBuscarAlmacen(),
			new CommandListarAlmacenes(),
			new CommandComprobarAlmacen(),
			
			// Clientes
			new CommandGUICliente(),
			new CommandActualizarClienteVIP(),
			new CommandActualizarClienteNormal(),
			new CommandAltaClienteVIP(),
			new CommandAltaClienteNormal(),
			new CommandBajaCliente(),
			new CommandBuscarCliente(),
			new CommandListarClientes(),
			new CommandComprobarCliente(),
			
			// Facturas
			new CommandGUIFactura(),
			new CommandAbrirFactura(),
			new CommandActualizarCarrito(),
			new CommandBuscarFactura(),
			new CommandBuscarCarrito(),
			new CommandCerrarFactura(),
			new CommandDevolverFactura(),
			new CommandListarFacturas(),
			new CommandListarFacturasPorCliente(),
			new CommandListarFacturasPorProducto(),
			
			// Productos
			new CommandGUIProducto(),
			new CommandActualizarProducto(),
			new CommandAltaProducto(),
			new CommandBajaProducto(),
			new CommandBuscarProducto(),
			new CommandListarProductos(),
			new CommandListarProductosPorProveedor(),
			new CommandListarProductosPorAlmacen(),
			new CommandComprobarProducto(),
			
			// Proveedores
			new CommandGUIProveedor(),
			new CommandActualizarProveedor(),
			new CommandAltaProveedor(),
			new CommandBajaProveedor(),
			new CommandBuscarProveedor(),
			new CommandDesvincularProducto(),
			new CommandListarProveedores(),
			new CommandListarProveedoresPorProdcuto(),
			new CommandVincularProducto(),
			new CommandComprobarProveedor(),
			
			// Trabajadores
			new CommandGUITrabajador(),
			new CommandActualizarTrabajadorJCompleta(),
			new CommandActualizarTrabajadorJParcial(),
			new CommandAltaTrabajadorJCompleta(),
			new CommandAltaTrabajadorJParcial(),
			new CommandBajaTrabajador(),
			new CommandBuscarTrabajador(),
			new CommandListarTrabajadores(),
			new CommandListarTrabajadoresPorAlmacen(),
			new CommandListarTrabajadoresDespedidos(),
			new CommandListarTrabajadoresJCompleta(),
			new CommandListarTrabajadoresRangoSueldo(),
			
			// Tareas
			new CommandGUITarea(),
			new CommandAltaTarea(),
			new CommandBuscarTarea(),
			new CommandListarTareas(),
			new CommandListarTareasPorProyecto(),
			new CommandActualizarTarea(),
			new CommandBajaTarea(),
			new CommandComprobarTarea(),
			
			//Proyecto
			new CommandGUIProyecto(),
			new CommandActualizarProyecto(),
			new CommandAltaProyecto(),
			new CommandBajaProyecto(),
			new CommandBuscarProyecto(),
			new CommandListarProyectos(),
			new CommandListarProyectosPorEmpleado(),
			new CommandComprobarProyecto(),
			
			//Curso
			new CommandGUICurso(),
			new CommandActualizarCursoOnline(),
			new CommandActualizarCursoPresencial(),
			new CommandAltaCursoOnline(),
			new CommandAltaCursoPresencial(),
			new CommandBajaCurso(),
			new CommandBuscarCurso(),
			new CommandListarCursos(),
			new CommandListarCursosPorEmpleado(),
			new CommandComprobarCurso(),
			
			//Empleados
			new CommandGUIEmpleado(),
			new CommandActualizarEmpleadoAdmin(),
			new CommandActualizarEmpleadoTecnico(),
			new CommandAltaEmpleadoTecnico(),
			new CommandAltaEmpleadoAdmin(),
			new CommandBajaEmpleado(),
			new CommandVincularCurso(),
			new CommandVincularProyecto(),
			new CommandDesincularCurso(),
			new CommandDesincularProyecto(),
			new CommandActualizarNivelEmpCurso(),
			new CommandBuscarEmpleado(),
			new CommandListarEmpleados(),
			new CommandListarEmpleadosPorProyecto(),
			new CommandListarEmpleadosPorCurso(),
			new CommandListarEmpleadosPorOficina(),
			new CommandComprobarEmpleado(),
			
			//Materiales
			new CommandGUIMaterial(),
			new CommandAltaMaterial(),
			new CommandBajaMaterial(),
			new CommandBuscarMaterial(),
			new CommandListarMateriales(),
			new CommandListarMaterialesPorEmpleado(),
			new CommandComprobarMaterial(),
			
			// Oficinas
			new CommandGUIOficina(),
			new CommandAltaOficina(),
			new CommandBuscarOficina(),
			new CommandListarOficinas(),
			new CommandMostrarNomina(),
			new CommandListarOficinas(),
			new CommandComprobarOficina(),
			new CommandActualizarOficina(),
			new CommandBajaOficina(),
	};
	@Override
	public Command crearComando(Eventos id) {
		for (Command c : commands) {
			if (c.getId() == id) return c;
		}
		GUIMSG.showMessage("Comando inexistente", ".FactoriaCommandImp.crearComando()", true);
		return null;
	}
}