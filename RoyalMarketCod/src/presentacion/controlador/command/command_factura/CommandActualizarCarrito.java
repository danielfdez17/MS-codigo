package presentacion.controlador.command.command_factura;

import java.util.HashMap;

import negocio.factura.TCarrito;
import negocio.factura.TLineaFactura;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;
import presentacion.factura.GUIFACTURA;

public class CommandActualizarCarrito implements Command {

	@Override
	public Context execute(Object datos) {
		TCarrito carrito = GUIFACTURA.getInstancia().getCarrito();
		TLineaFactura linea = (TLineaFactura)datos;
		int id_producto = linea.getIdProducto(), cantidad = linea.getCantidad();
		HashMap<Integer, TLineaFactura> map = carrito.getLineas();
		if (linea.getCantidad() < 0) {
			if (map.containsKey(id_producto)) {
				TLineaFactura contenida = map.get(id_producto);
				if (contenida.getCantidad() < cantidad) {
					return new Context(Eventos.ACTUALIZAR_CARRITO_KO, carrito);
				}
				else if (contenida.getCantidad() == -cantidad) {
					map.remove(id_producto);
				}
				else {
					int nueva_cantidad = contenida.getCantidad() + cantidad;
					contenida.setCantidad(nueva_cantidad);
				}
			}
		}
		else {
			if (map.containsKey(id_producto)) {
				map.get(id_producto).setCantidad(carrito.getLineas().get(id_producto).getCantidad() + cantidad);
			}
			else {
				map.put(id_producto, linea);
			}
		}
		
		return new Context(Eventos.ACTUALIZAR_CARRITO_OK, carrito);
	}

	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_CARRITO;
	}

}
