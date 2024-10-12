package negocio.factoriaNegocio;

import negocio.almacen.SAAlmacen;
import negocio.cliente.SACliente;
import negocio.curso.SACurso;
import negocio.empleado.SAEmpleado;
import negocio.factura.SAFactura;
import negocio.material.SAMaterial;
import negocio.oficina.SAOficina;
import negocio.producto.SAProducto;
import negocio.proveedor.SAProveedor;
import negocio.proyecto.SAProyecto;
import negocio.tarea.SATarea;
import negocio.trabajador.SATrabajador;

public abstract class FactoriaNegocio {

	private static FactoriaNegocio instancia;

	public synchronized static FactoriaNegocio getInstancia() {
		if (instancia == null) instancia = new FactoriaNegocioImp();
		return instancia;
	}

	public abstract SAAlmacen crearSAAlmacen();

	public abstract SACliente crearSACliente();

	public abstract SAFactura crearSAFactura();

	public abstract SAProducto crearSAProducto();

	public abstract SAProveedor crearSAProveedor();

	public abstract SATrabajador crearSATrabajador();
	
	public abstract SACurso crearSACurso();
	
	public abstract SAEmpleado crearSAEmpleado();
	
	public abstract SAMaterial crearSAMaterial();
	
	public abstract SAOficina crearSAOficina();
	
	public abstract SAProyecto crearSAProyecto();
	
	public abstract SATarea crearSATarea();
}