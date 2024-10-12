package integracion.linea_factura;

import java.util.List;

import negocio.factura.TLineaFactura;

public interface DAOLineaFactura {
	public int crearLinea(TLineaFactura linea);
	public TLineaFactura buscarLinea(int idFactura, int idProducto);
	public List<TLineaFactura> listarLineasPorFactura(int idFactura);
	public List<TLineaFactura> listarLineasPorProducto(int idProducto);
	public int actualizarLinea(TLineaFactura linea);
	public int eliminarLinea(int idFactura, int idProducto);
}
