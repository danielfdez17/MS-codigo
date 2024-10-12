package integracion.factoriaIntegracion;

import integracion.almacen.DAOAlmacen;
import integracion.cliente.DAOCliente;
import integracion.factura.DAOFactura;
import integracion.linea_factura.DAOLineaFactura;
import integracion.producto.DAOProducto;
import integracion.proveedor.DAOProveedor;
import integracion.trabajador.DAOTrabajador;
import integracion.proveedor_producto.DAOProveedorProducto;

public abstract class FactoriaIntegracion { 
	
	private static FactoriaIntegracion instancia;

	public synchronized static FactoriaIntegracion getInstancia() {
		if (instancia == null) instancia = new FactoriaIntegracionImp();
		return instancia;
	}

	public abstract DAOAlmacen crearDAOAlmacen();
	public abstract DAOCliente crearDAOCliente();
	public abstract DAOFactura crearDAOFactura();
	public abstract DAOProducto creaDAOProducto();
	public abstract DAOProveedor crearDAOProveedor();
	public abstract DAOTrabajador crearDAOTrabajador();
	public abstract DAOProveedorProducto crearDAOPoveedorProducto();
	public abstract DAOLineaFactura crearDAOLineaFactura();
}