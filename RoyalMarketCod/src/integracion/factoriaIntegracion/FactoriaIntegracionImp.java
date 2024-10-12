package integracion.factoriaIntegracion;

import integracion.almacen.DAOAlmacen;
import integracion.almacen.DAOAlmacenImp;
import integracion.cliente.DAOCliente;
import integracion.cliente.DAOClienteImp;
import integracion.factura.DAOFactura;
import integracion.factura.DAOFacturaImp;
import integracion.linea_factura.DAOLineaFactura;
import integracion.linea_factura.DAOLineaFacturaImp;
import integracion.producto.DAOProducto;
import integracion.producto.DAOProductoImp;
import integracion.proveedor.DAOProveedor;
import integracion.proveedor.DAOProveedorImp;
import integracion.proveedor_producto.DAOProveedorProducto;
import integracion.proveedor_producto.DAOProveedorProductoImp;
import integracion.trabajador.DAOTrabajador;
import integracion.trabajador.DAOTrabajadorImp;

public class FactoriaIntegracionImp extends FactoriaIntegracion {

	@Override
	public DAOAlmacen crearDAOAlmacen() {
		return new DAOAlmacenImp();
	}

	@Override
	public DAOCliente crearDAOCliente() {
		return new DAOClienteImp();
	}

	@Override
	public DAOFactura crearDAOFactura() {
		return new DAOFacturaImp();
	}

	@Override
	public DAOProducto creaDAOProducto() {
		return new DAOProductoImp();
	}

	@Override
	public DAOProveedor crearDAOProveedor() {
		return new DAOProveedorImp();
	}

	@Override
	public DAOTrabajador crearDAOTrabajador() {
		return new DAOTrabajadorImp();
	}

	@Override
	public DAOProveedorProducto crearDAOPoveedorProducto() {
		return new DAOProveedorProductoImp();
	}

	@Override
	public DAOLineaFactura crearDAOLineaFactura() {
		return new DAOLineaFacturaImp();
	}
}