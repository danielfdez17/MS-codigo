package negocio.factoriaNegocio;

import negocio.almacen.SAAlmacen;
import negocio.almacen.SAAlmacenImp;
import negocio.cliente.SACliente;
import negocio.cliente.SAClienteImp;
import negocio.curso.SACurso;
import negocio.curso.SACursoImp;
import negocio.empleado.SAEmpleado;
import negocio.empleado.SAEmpleadoImp;
import negocio.factura.SAFactura;
import negocio.factura.SAFacturaImp;
import negocio.material.SAMaterial;
import negocio.material.SAMaterialImp;
import negocio.oficina.SAOficina;
import negocio.oficina.SAOficinaImp;
import negocio.producto.SAProducto;
import negocio.producto.SAProductoImp;
import negocio.proveedor.SAProveedor;
import negocio.proveedor.SAProveedorImp;
import negocio.proyecto.SAProyecto;
import negocio.proyecto.SAProyectoImp;
import negocio.tarea.SATarea;
import negocio.tarea.SATareaImp;
import negocio.trabajador.SATrabajador;
import negocio.trabajador.SATrabajadorImp;

public class FactoriaNegocioImp extends FactoriaNegocio {

	@Override
	public SAAlmacen crearSAAlmacen() {
		return new SAAlmacenImp();
	}

	@Override
	public SACliente crearSACliente() {
		return new SAClienteImp();
	}

	@Override
	public SAFactura crearSAFactura() {
		return new SAFacturaImp();
	}

	@Override
	public SAProducto crearSAProducto() {
		return new SAProductoImp();
	}

	@Override
	public SAProveedor crearSAProveedor() {
		return new SAProveedorImp();
	}

	@Override
	public SATrabajador crearSATrabajador() {
		return new SATrabajadorImp();
	}

	@Override
	public SACurso crearSACurso() {
		return new SACursoImp();
	}

	@Override
	public SAEmpleado crearSAEmpleado() {
		return new SAEmpleadoImp();
	}

	@Override
	public SAMaterial crearSAMaterial() {
		return new SAMaterialImp();
	}

	@Override
	public SAOficina crearSAOficina() {
		return new SAOficinaImp();
	}

	@Override
	public SAProyecto crearSAProyecto() {
		return new SAProyectoImp();
	}

	@Override
	public SATarea crearSATarea() {
		return new SATareaImp();
	}
}