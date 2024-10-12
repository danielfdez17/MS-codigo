package presentacion.controlador.command.command_proveedor;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proveedor.TProveedor;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandBuscarProveedor implements Command {
	public Context execute(Object datos) {
        int res = (int) datos;
        TProveedor tProveedor = FactoriaNegocio.getInstancia().crearSAProveedor().buscarProveedor(res);
        if(tProveedor == null)
        	return new Context(Eventos.BUSCAR_PROVEEDOR_KO, res);
        else
        	return new Context(Eventos.BUSCAR_PROVEEDOR_OK, tProveedor);
    }

    @Override
    public Eventos getId() {
        return Eventos.BUSCAR_PROVEEDOR;
    }
}