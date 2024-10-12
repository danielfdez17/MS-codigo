package integracion.factura;

import negocio.factura.TFactura;
import java.util.List;

public interface DAOFactura {
	
	public TFactura buscarFactura(int id);

	public List<TFactura> listarFacturas();

	public List<TFactura> listarFacturasPorCliente(int id_cliente);

	public int actualizarFactura(TFactura tf);

	public int cerrarFactura(TFactura tf);
}