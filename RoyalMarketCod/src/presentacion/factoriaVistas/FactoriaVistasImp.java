package presentacion.factoriaVistas;

import presentacion.almacen.GUIALMACEN;
import presentacion.almacen.GUIActualizarAlmacen;
import presentacion.almacen.GUIAltaAlmacen;
import presentacion.almacen.GUIBajaAlmacen;
import presentacion.almacen.GUIBuscarAlmacen;
import presentacion.almacen.GUIListarAlmacenes;
import presentacion.cliente.GUIActualizarClienteNormal;
import presentacion.cliente.GUIActualizarClienteVIP;
import presentacion.cliente.GUIAltaClienteNormal;
import presentacion.cliente.GUIAltaClienteVIP;
import presentacion.cliente.GUIBajaCliente;
import presentacion.cliente.GUIBuscarCliente;
import presentacion.cliente.GUICLIENTE;
import presentacion.cliente.GUIListarClientes;
import presentacion.controlador.GUIMSG;
import presentacion.curso.GUIActualizarCursoOnline;
import presentacion.curso.GUIActualizarCursoPresencial;
import presentacion.curso.GUIAltaCursoOnline;
import presentacion.curso.GUIAltaCursoPresencial;
import presentacion.curso.GUIBajaCurso;
import presentacion.curso.GUIBuscarCurso;
import presentacion.curso.GUICURSO;
import presentacion.curso.GUIListarCursos;
import presentacion.curso.GUIListarCursosPorEmpleado;
import presentacion.empleado.GUIActualizarEAdministrador;
import presentacion.empleado.GUIActualizarETecnico;
import presentacion.empleado.GUIActualizarNivelEmpCurso;
import presentacion.empleado.GUIAltaEmpleadoAdmin;
import presentacion.empleado.GUIAltaEmpleadoTecnico;
import presentacion.empleado.GUIBajaEmpleado;
import presentacion.empleado.GUIBuscarEmpleado;
import presentacion.empleado.GUIDesvincularCurso;
import presentacion.empleado.GUIDesvincularProyecto;
import presentacion.empleado.GUIEMPLEADO;
import presentacion.empleado.GUIListarEmpleados;
import presentacion.empleado.GUIListarEmpleadosPorCurso;
import presentacion.empleado.GUIListarEmpleadosPorOficina;
import presentacion.empleado.GUIListarEmpleadosPorProyecto;
import presentacion.empleado.GUIVincularCurso;
import presentacion.empleado.GUIVincularProyecto;
import presentacion.factura.GUIAbrirFactura;
import presentacion.factura.GUIActualizarCarrito;
import presentacion.factura.GUIBuscarCarrito;
import presentacion.factura.GUIBuscarFactura;
import presentacion.factura.GUICerrarFactura;
import presentacion.factura.GUIDevolverFactura;
import presentacion.factura.GUIFACTURA;
import presentacion.factura.GUIListarFacturas;
import presentacion.factura.GUIListarFacturasPorCliente;
import presentacion.factura.GUIListarFacturasPorProducto;
import presentacion.main.GUIMAIN;
import presentacion.material.GUIActualizarMaterial;
import presentacion.material.GUIAltaMaterial;
import presentacion.material.GUIBajaMaterial;
import presentacion.material.GUIBuscarMaterial;
import presentacion.material.GUIListarMaterial;
import presentacion.material.GUIListarMaterialPorEmpleado;
import presentacion.material.GUIMATERIAL;
import presentacion.oficina.GUIActualizarOficina;
import presentacion.oficina.GUIAltaOficina;
import presentacion.oficina.GUIBajaOficina;
import presentacion.oficina.GUIBuscarOficina;
import presentacion.oficina.GUIListarOficinas;
import presentacion.oficina.GUIMostrarNomina;
import presentacion.oficina.GUIOFICINA;
import presentacion.producto.GUIActualizarProducto;
import presentacion.producto.GUIAltaProducto;
import presentacion.producto.GUIBajaProducto;
import presentacion.producto.GUIBuscarProducto;
import presentacion.producto.GUIListarProductos;
import presentacion.producto.GUIListarProductosPorAlmacen;
import presentacion.producto.GUIListarProductosPorProveedor;
import presentacion.producto.GUIPRODUCTO;
import presentacion.proveedor.GUIAltaProveedor;
import presentacion.proveedor.GUIBajaProveedor;
import presentacion.proveedor.GUIBuscarProveedor;
import presentacion.proveedor.GUIDesvincularProducto;
import presentacion.proveedor.GUIListarProveedores;
import presentacion.proveedor.GUIListarProveedoresPorProducto;
import presentacion.proveedor.GUIPROVEEDOR;
import presentacion.proveedor.GUIVincularProducto;
import presentacion.proyecto.GUIActualizarProyecto;
import presentacion.proyecto.GUIAltaProyecto;
import presentacion.proyecto.GUIBajaProyecto;
import presentacion.proyecto.GUIBuscarProyecto;
import presentacion.proyecto.GUIListarProyectos;
import presentacion.proyecto.GUIListarProyectoPorEmpleado;
import presentacion.proyecto.GUIPROYECTO;
import presentacion.tarea.GUIActualizarTarea;
import presentacion.tarea.GUIAltaTarea;
import presentacion.tarea.GUIBajaTarea;
import presentacion.tarea.GUIBuscarTarea;
import presentacion.tarea.GUIListarTareas;
import presentacion.tarea.GUIListarTareasPorProyecto;
import presentacion.tarea.GUITAREA;
import presentacion.trabajador.GUIActualizarTrabajadorJCompleta;
import presentacion.trabajador.GUIActualizarTrabajadorJParcial;
import presentacion.trabajador.GUIAltaTrabajadorJCompleta;
import presentacion.trabajador.GUIAltaTrabajadorJParcial;
import presentacion.trabajador.GUIBajaTrabajador;
import presentacion.trabajador.GUIBuscarTrabajador;
import presentacion.trabajador.GUIListarRangoSueldo;
import presentacion.trabajador.GUIListarTrabajadores;
import presentacion.trabajador.GUIListarTrabajadoresDespedidos;
import presentacion.trabajador.GUIListarTrabajadoresJCompleta;
import presentacion.trabajador.GUIListarTrabajadoresPorAlmacen;
import presentacion.trabajador.GUITRABAJADOR;

public class FactoriaVistasImp extends FactoriaVistas {
	
	private static final String FROM_WHERE = "FactoriaVistasImp";

	@Override
	public void crearVista(Context context) {
		switch (context.getEvent()) {
		case GUI_MAIN: 
			GUIMAIN.getInstancia().setVisible(true);; break;
		
		case GUI_ALMACEN:
			GUIALMACEN.getInstancia().setVisible(true);; break;
		case ACTUALIZAR_ALMACEN_OK:
		case ACTUALIZAR_ALMACEN_KO:
			GUIActualizarAlmacen.getInstancia().update(context); break;
		case ALTA_ALMACEN_OK:
		case ALTA_ALMACEN_KO:
			GUIAltaAlmacen.getInstancia().update(context); break;
		case BAJA_ALMACEN_OK:
		case BAJA_ALMACEN_KO:
			GUIBajaAlmacen.getInstancia().update(context); break;
		case BUSCAR_ALMACEN_OK:
		case BUSCAR_ALMACEN_KO:
			GUIBuscarAlmacen.getInstancia().update(context); break;
		case BUSCAR_ACTUALIZAR_ALMACEN_KO:
		case BUSCAR_ACTUALIZAR_ALMACEN_OK:
		case LISTAR_ALMACENES_OK:
		case LISTAR_ALMACENES_KO:
			GUIListarAlmacenes.getInstancia().update(context); break;
		case COMPROBAR_ALMACEN_KO:
		case COMPROBAR_ALMACEN_OK:
			GUIALMACEN.getInstancia().actualizar(context); break;
			
		case GUI_CLIENTE:
			GUICLIENTE.getInstancia().setVisible(true); break;
		case ACTUALIZAR_CLIENTE_NORMAL_KO:
		case ACTUALIZAR_CLIENTE_NORMAL_OK:
			GUIActualizarClienteNormal.getInstancia().update(context); break;
		case ACTUALIZAR_CLIENTE_VIP_KO:
		case ACTUALIZAR_CLIENTE_VIP_OK:
			GUIActualizarClienteVIP.getInstancia().update(context); break;
		case ALTA_CLIENTE_VIP_OK:
		case ALTA_CLIENTE_VIP_KO:
			GUIAltaClienteVIP.getInstancia().update(context); break;
		case ALTA_CLIENTE_NORMAL_OK:
		case ALTA_CLIENTE_NORMAL_KO:
			GUIAltaClienteNormal.getInstancia().update(context); break;
		case BAJA_CLIENTE_OK:
		case BAJA_CLIENTE_KO:
			GUIBajaCliente.getInstancia().update(context); break;
		case BUSCAR_CLIENTE_OK:
		case BUSCAR_CLIENTE_KO:
			GUIBuscarCliente.getInstancia().update(context); break;
		case LISTAR_CLIENTES_OK:
		case LISTAR_CLIENTES_KO:
			GUIListarClientes.getInstancia().update(context); 
			GUIListarClientes.getInstancia().setVisible(true);
			break;
		case COMPROBAR_CLIENTE_KO:
		case COMPROBAR_CLIENTE_OK:
			GUICLIENTE.getInstancia().actualizar(context); 
			break;
		
		case GUI_FACTURA:
			GUIFACTURA.getInstancia().setVisible(true); break;
		case ABRIR_FACTURA_OK:
		case ABRIR_FACTURA_KO:
			GUIAbrirFactura.getInstancia().update(context); break;
		case ACTUALIZAR_CARRITO_OK:
		case ACTUALIZAR_CARRITO_KO:
			GUIActualizarCarrito.getInstancia().update(context); break;
		case BUSCAR_FACTURA_OK:
		case BUSCAR_FACTURA_KO:
			GUIBuscarFactura.getInstancia().update(context); break;
		case BUSCAR_CARRITO_OK:
		case BUSCAR_CARRITO_KO:
			GUIBuscarCarrito.getInstancia().update(context); break;
		case CERRAR_FACTURA_OK:
		case CERRAR_FACTURA_KO:
			GUICerrarFactura.getInstancia().update(context); break;
		case DEVOLVER_FACTURA_OK:
		case DEVOLVER_FACTURA_KO:
			GUIDevolverFactura.getInstancia().update(context); break;
		case LISTAR_FACTURAS_OK:
		case LISTAR_FACTURAS_KO:
			GUIListarFacturas.getInstancia().update(context); break;
		case LISTAR_FACTURAS_POR_CLIENTE_OK:
		case LISTAR_FACTURAS_POR_CLIENTE_KO:
			GUIListarFacturasPorCliente.getInstancia().update(context); break;
		case LISTAR_FACTURAS_POR_PRODUCTO_OK:
		case LISTAR_FACTURAS_POR_PRODUCTO_KO:
			GUIListarFacturasPorProducto.getInstancia().update(context); break;
			
		case GUI_PRODUCTO:
			GUIPRODUCTO.getInstancia().setVisible(true); break;
		case ACTUALIZAR_PRODUCTO_OK:
		case ACTUALIZAR_PRODUCTO_KO:
			GUIActualizarProducto.getInstancia().update(context); break;
		case ALTA_PRODUCTO_OK:
		case ALTA_PRODUCTO_KO:
			GUIAltaProducto.getInstancia().update(context); break;
		case BAJA_PRODUCTO_OK:
		case BAJA_PRODUCTO_KO:
			GUIBajaProducto.getInstancia().update(context); break;
		case BUSCAR_PRODUCTO_OK:
		case BUSCAR_PRODUCTO_KO:
			GUIBuscarProducto.getInstancia().update(context); break;
		case LISTAR_PRODUCTOS_OK:
		case LISTAR_PRODUCTOS_KO:
			GUIListarProductos.getInstancia().update(context); break;
		case LISTAR_PRODUCTOS_POR_PROVEEDOR_OK:
		case LISTAR_PRODUCTOS_POR_PROVEEDOR_KO:
			GUIListarProductosPorProveedor.getInstancia().update(context); break;
		case LISTAR_PRODUCTOS_POR_ALMACEN_OK:
		case LISTAR_PRODUCTOS_POR_ALMACEN_KO:
			GUIListarProductosPorAlmacen.getInstancia().update(context); break;
		case COMPROBAR_PRODUCTO_KO:
		case COMPROBAR_PRODUCTO_OK:
			GUIPRODUCTO.getInstancia().actualizar(context); break;
			
		case GUI_PROVEEDOR:
			GUIPROVEEDOR.getInstancia().setVisible(true); break;
		case ACTUALIZAR_PROVEEDOR_OK:
		case ACTUALIZAR_PROVEEDOR_KO:
			GUIActualizarProducto.getInstancia().update(context); break;
		case ALTA_PROVEEDOR_OK:
		case ALTA_PROVEEDOR_KO:
			GUIAltaProveedor.getInstancia().update(context); break;
		case BAJA_PROVEEDOR_OK:
		case BAJA_PROVEEDOR_KO:
			GUIBajaProveedor.getInstancia().update(context); break;
		case BUSCAR_PROVEEDOR_OK:
		case BUSCAR_PROVEEDOR_KO:
			GUIBuscarProveedor.getInstancia().update(context); break;
		case DESVINCULAR_PRODUCTO_OK:
		case DESVINCULAR_PRODUCTO_KO:
			GUIDesvincularProducto.getInstancia().update(context); break;
		case LISTAR_PROVEEDORES_OK:
		case LISTAR_PROVEEDORES_KO:
			GUIListarProveedores.getInstancia().update(context); break;
		case LISTAR_PROVEEDORES_POR_PRODUCTO_OK:
		case LISTAR_PROVEEDORES_POR_PRODUCTO_KO:
			GUIListarProveedoresPorProducto.getInstancia().update(context); break;
		case VINCULAR_PRODUCTO_OK:
		case VINCULAR_PRODUCTO_KO:
			GUIVincularProducto.getInstancia().update(context); break;
		case COMPROBAR_PROVEEDOR_OK:
		case COMPROBAR_PROVEEDOR_KO:
			GUIPROVEEDOR.getInstancia().actualizar(context);
			break;
			
		case GUI_TRABAJADOR:
			GUITRABAJADOR.getInstancia().setVisible(true); break;
		case ACTUALIZAR_TRABAJADOR_J_COMPLETA_KO:
		case ACTUALIZAR_TRABAJADOR_J_COMPLETA_OK:
			GUIActualizarTrabajadorJCompleta.getInstancia().update(context); break;
		case ACTUALIZAR_TRABAJADOR_J_PARCIAL_KO:
		case ACTUALIZAR_TRABAJADOR_J_PARCIAL_OK:
			GUIActualizarTrabajadorJParcial.getInstancia().update(context); break;
		case ALTA_TRABAJADOR_J_COMPLETA_OK:
		case ALTA_TRABAJADOR_J_COMPLETA_KO:
			GUIAltaTrabajadorJCompleta.getInstancia().update(context); break;
		case ALTA_TRABAJADOR_J_PARCIAL_OK:
		case ALTA_TRABAJADOR_J_PARCIAL_KO:
			GUIAltaTrabajadorJParcial.getInstancia().update(context); break;
		case BAJA_TRABAJADOR_OK:
		case BAJA_TRABAJADOR_KO:
			GUIBajaTrabajador.getInstancia().update(context); break;
		case BUSCAR_TRABAJADOR_OK:
		case BUSCAR_TRABAJADOR_KO:
			GUIBuscarTrabajador.getInstancia().update(context); break;
		case LISTAR_TRABAJADORES_OK:
		case LISTAR_TRABAJADORES_KO:
			GUIListarTrabajadores.getInstancia().update(context); break;
		case LISTAR_TRABAJADORES_POR_ALMACEN_OK:
		case LISTAR_TRABAJADORES_POR_ALMACEN_KO:
			GUIListarTrabajadoresPorAlmacen.getInstancia().update(context); break;
		case LISTAR_TRABAJADORES_DESPEDIDOS_OK:
		case LISTAR_TRABAJADORES_DESPEDIDOS_KO:
			GUIListarTrabajadoresDespedidos.getInstancia().update(context); break;
		case LISTAR_TRABAJADORES_RANGO_SUELDO_OK:
		case LISTAR_TRABAJADORES_RANGO_SUELDO_KO:
			GUIListarRangoSueldo.getInstancia().update(context); break;
		case LISTAR_TRABAJADORES_J_COMPLETA_OK:
		case LISTAR_TRABAJADORES_J_COMPLETA_KO:
			GUIListarTrabajadoresJCompleta.getInstancia().update(context); break;
		case COMPROBAR_TRABAJADOR_OK:
		case COMPROBAR_TRABAJADOR_KO:
			GUITRABAJADOR.getInstancia().actualizar(context); break;
			
		case GUI_TAREA:
			GUITAREA.getInstancia().setVisible(true); break;
		case ALTA_TAREA_KO:
		case ALTA_TAREA_OK:
			GUIAltaTarea.getInstancia().update(context); break;
		case BUSCAR_TAREA_KO:
		case BUSCAR_TAREA_OK:
			GUIBuscarTarea.getInstancia().update(context); break;
		case LISTAR_TAREAS_OK:
		case LISTAR_TAREAS_KO:
			GUIListarTareas.getInstancia().update(context); break;
		case LISTAR_TAREAS_POR_PROYECTO_KO:
		case LISTAR_TAREAS_POR_PROYECTO_OK:
			GUIListarTareasPorProyecto.getInstancia().update(context); break;
		case ACTUALIZAR_TAREA_OK:
		case ACTUALIZAR_TAREA_KO:
			GUIActualizarTarea.getInstancia().update(context); break;
		case COMPROBAR_TAREA_OK:
		case COMPROBAR_TAREA_KO:
			GUITAREA.getInstancia().actualizar(context); break;
		case BAJA_TAREA_OK:
		case BAJA_TAREA_KO:
			GUIBajaTarea.getInstancia().update(context); break;
			
		case GUI_PROYECTO:
			GUIPROYECTO.getInstancia().setVisible(true); break;
		
			
		case ALTA_PROYECTO_OK:	
		case ALTA_PROYECTO_KO:
			GUIAltaProyecto.getInstancia().update(context);
			break;
	
		case BUSCAR_PROYECTO_OK:
		case BUSCAR_PROYECTO_KO:
			GUIBuscarProyecto.getInstancia().update(context);
			break;
		
		case	COMPROBAR_PROYECTO_OK:
		case	COMPROBAR_PROYECTO_KO:
			GUIPROYECTO.getInstancia().actualizar(context);
			break;
		case	LISTAR_PROYECTOS_OK:
		case	LISTAR_PROYECTOS_KO:
			GUIListarProyectos.getInstancia().update(context);
			break;
		case	LISTAR_PROYECTOS_POR_EMPLEADO_OK:
		case	LISTAR_PROYECTOS_POR_EMPLEADO_KO:
			GUIListarProyectoPorEmpleado.getInstancia().update(context);
			break;
		case	ACTUALIZAR_PROYECTO_OK:
		case	ACTUALIZAR_PROYECTO_KO:
			GUIActualizarProyecto.getInstancia().update(context);
			break;
		case	BAJA_PROYECTO_OK:
		case	BAJA_PROYECTO_KO:
			GUIBajaProyecto.getInstancia().update(context);
			break;
			
		case GUI_CURSO:
			GUICURSO.getInstancia().setVisible(true); break;
		
			
		case ALTA_CURSO_ONLINE_OK:	
		case ALTA_CURSO_ONLINE_KO:
			GUIAltaCursoOnline.getInstancia().update(context);
			break;
			
		case ALTA_CURSO_PRESENCIAL_OK:	
		case ALTA_CURSO_PRESENCIAL_KO:
				GUIAltaCursoPresencial.getInstancia().update(context);
				break;
				
		case BUSCAR_CURSO_OK:
		case BUSCAR_CURSO_KO:
			GUIBuscarCurso.getInstancia().update(context);
			break;
		
		case	COMPROBAR_CURSO_OK:
		case	COMPROBAR_CURSO_KO:
			GUICURSO.getInstancia().actualizar(context);
			break;
			
		case	LISTAR_CURSOS_OK:
		case	LISTAR_CURSOS_KO:
			GUIListarCursos.getInstancia().update(context);
			break;
			
		case	LISTAR_CURSOS_POR_EMPLEADO_OK:
		case	LISTAR_CURSOS_POR_EMPLEADO_KO:
			GUIListarCursosPorEmpleado.getInstancia().update(context);
			break;
			
		case	ACTUALIZAR_CURSO_ONLINE_OK:
		case	ACTUALIZAR_CURSO_ONLINE_KO:
			GUIActualizarCursoOnline.getInstancia().update(context);
			break;
			
		case	ACTUALIZAR_CURSO_PRESENCIAL_OK:
		case	ACTUALIZAR_CURSO_PRESENCIAL_KO:
			GUIActualizarCursoPresencial.getInstancia().update(context);
			break;
			
		case	BAJA_CURSO_OK:
		case	BAJA_CURSO_KO:
			GUIBajaCurso.getInstancia().update(context);
			break;
			
		//EMPLEADO
		case GUI_EMPLEADO:
			GUIEMPLEADO.getInstancia().setVisible(true); break;
			
		case ALTA_EMPLEADO_ADMINISTRADOR_OK:	
		case ALTA_EMPLEADO_ADMINISTRADOR_KO:
			GUIAltaEmpleadoAdmin.getInstancia().update(context);
			break;
		case ALTA_EMPLEADO_TECNICO_OK:	
		case ALTA_EMPLEADO_TECNICO_KO:
			GUIAltaEmpleadoTecnico.getInstancia().update(context);
			break;	
		case BUSCAR_EMPLEADO_OK:
		case BUSCAR_EMPLEADO_KO:
			GUIBuscarEmpleado.getInstancia().update(context);
			break;
		case	COMPROBAR_EMPLEADO_OK:
		case	COMPROBAR_EMPLEADO_KO:
			GUIEMPLEADO.getInstancia().actualizar(context);
			break;
		case	LISTAR_EMPLEADOS_OK:
		case	LISTAR_EMPLEADOS_KO:
			GUIListarEmpleados.getInstancia().update(context);
			break;
		case	LISTAR_EMPLEADOS_POR_PROYECTO_OK:
		case	LISTAR_EMPLEADOS_POR_PROYECTO_KO:
			GUIListarEmpleadosPorProyecto.getInstancia().update(context);
			break;
		case	LISTAR_EMPLEADOS_POR_OFICINA_OK:
		case	LISTAR_EMPLEADOS_POR_OFICINA_KO:
			GUIListarEmpleadosPorOficina.getInstancia().update(context);
			break;
		case	LISTAR_EMPLEADOS_POR_CURSO_OK:
		case	LISTAR_EMPLEADOS_POR_CURSO_KO:
			GUIListarEmpleadosPorCurso.getInstancia().update(context);
			break;
		case	ACTUALIZAR_EMPLEADO_ADMINISTRADOR_OK:
		case	ACTUALIZAR_EMPLEADO_ADMINISTRADOR_KO:
			GUIActualizarEAdministrador.getInstancia().update(context);
			break;
		case	ACTUALIZAR_EMPLEADO_TECNICO_OK:
		case	ACTUALIZAR_EMPLEADO_TECNICO_KO:
			GUIActualizarETecnico.getInstancia().update(context);
			break;
		case	BAJA_EMPLEADO_OK:
		case	BAJA_EMPLEADO_KO:
			GUIBajaEmpleado.getInstancia().update(context);
			break;
		case	VINCULAR_CURSO_OK:
		case	VINCULAR_CURSO_KO:
			GUIVincularCurso.getInstancia().update(context);
			break;
		case	VINCULAR_PROYECTO_OK:
		case	VINCULAR_PROYECTO_KO:
			GUIVincularProyecto.getInstancia().update(context);
			break;
		case	DESVINCULAR_CURSO_OK:
		case	DESVINCULAR_CURSO_KO:
			GUIDesvincularCurso.getInstancia().update(context);
			break;
		case	DESVINCULAR_PROYECTO_OK:
		case	DESVINCULAR_PROYECTO_KO:
			GUIDesvincularProyecto.getInstancia().update(context);
			break;
		case ACTUALIZAR_NIVEL_EMP_CURSO_OK:
		case ACTUALIZAR_NIVEL_EMP_CURSO_KO:
			GUIActualizarNivelEmpCurso.getInstancia().update(context); 
			break;
			
		case GUI_MATERIAL:
			GUIMATERIAL.getInstancia().setVisible(true); break;
		case ACTUALIZAR_MATERIAL_OK:
		case ACTUALIZAR_MATERIAL_KO:
			GUIActualizarMaterial.getInstancia().update(context); break;
		case ALTA_MATERIAL_OK:
		case ALTA_MATERIAL_KO:
			GUIAltaMaterial.getInstancia().update(context); break;
		case BAJA_MATERIAL_OK:
		case BAJA_MATERIAL_KO:
			GUIBajaMaterial.getInstancia().update(context); break;
		case BUSCAR_MATERIAL_OK:
		case BUSCAR_MATERIAL_KO:
			GUIBuscarMaterial.getInstancia().update(context); break;
		case LISTAR_MATERIALES_OK:
		case LISTAR_MATERIALES_KO:
			GUIListarMaterial.getInstancia().update(context); break;
		case LISTAR_MATERIALES_POR_EMPLEADO_OK:
		case LISTAR_MATERIALES_POR_EMPLEADO_KO:
			GUIListarMaterialPorEmpleado.getInstancia().update(context); break;
		case COMPROBAR_MATERIAL_KO:
		case COMPROBAR_MATERIAL_OK:
			GUIMATERIAL.getInstancia().actualizar(context); break;
			
		case GUI_OFICINA:
			GUIOFICINA.getInstancia().setVisible(true); break;
		case ACTUALIZAR_OFICINA_OK:
		case ACTUALIZAR_OFICINA_KO:
			GUIActualizarOficina.getInstancia().update(context); break;
		case ALTA_OFICINA_OK:
		case ALTA_OFICINA_KO:
			GUIAltaOficina.getInstancia().update(context); break;
		case BAJA_OFICINA_OK:
		case BAJA_OFICINA_KO:
			GUIBajaOficina.getInstancia().update(context); break;
		case BUSCAR_OFICINA_OK:
		case BUSCAR_OFICINA_KO:
			GUIBuscarOficina.getInstancia().update(context); break;
		case LISTAR_OFICINAS_OK:
		case LISTAR_OFICINAS_KO:
			GUIListarOficinas.getInstancia().update(context); break;
		case MOSTRAR_NOMINA_OK:
		case MOSTRAR_NOMINA_KO:
			GUIMostrarNomina.getInstancia().update(context); break;
		case COMPROBAR_OFICINA_OK:
		case COMPROBAR_OFICINA_KO:
			GUIOFICINA.getInstancia().actualizar(context); break;
			
		default:
			GUIMSG.showMessage("Vista no contemplada", FROM_WHERE, true);
			break;
		}
		
	}
}