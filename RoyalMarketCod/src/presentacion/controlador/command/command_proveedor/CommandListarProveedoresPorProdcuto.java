package presentacion.controlador.command.command_proveedor;

import java.util.List;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proveedor.TProveedor;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandListarProveedoresPorProdcuto implements Command {
	public Context execute(Object datos) {
		int idProducto = (int) datos;
        List<TProveedor> tProveedores = FactoriaNegocio.getInstancia().crearSAProveedor().listarProveedoresPorProducto(idProducto);
        if(tProveedores == null || tProveedores.isEmpty())
        	return new Context(Eventos.LISTAR_PROVEEDORES_POR_PRODUCTO_KO, idProducto);
        else
        	return new Context(Eventos.LISTAR_PROVEEDORES_POR_PRODUCTO_OK, tProveedores);
    }

    @Override
    public Eventos getId() {
        return Eventos.LISTAR_PROVEEDORES_POR_PRODUCTO;
    }
}