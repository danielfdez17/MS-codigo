package presentacion.controlador.command.command_proveedor;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proveedor_producto.TProveedorProducto;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandVincularProducto implements Command {
	public Context execute(Object datos) {
		TProveedorProducto tpp = (TProveedorProducto) datos;
        int res = FactoriaNegocio.getInstancia().crearSAProveedor().vincularProducto(tpp.getIdProveedor(), tpp.getIdProducto());
        if(res < 0)
        	return new Context(Eventos.VINCULAR_PRODUCTO_KO, tpp);
        else
        	return new Context(Eventos.VINCULAR_PRODUCTO_OK, tpp);
    }

    @Override
    public Eventos getId() {
        return Eventos.VINCULAR_PRODUCTO;
    }
}