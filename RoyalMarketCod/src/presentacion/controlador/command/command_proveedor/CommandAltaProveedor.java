package presentacion.controlador.command.command_proveedor;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proveedor.TProveedor;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandAltaProveedor implements Command {
	@Override
    public Context execute(Object datos) {
        TProveedor tProveedor = (TProveedor) datos;
        int res = FactoriaNegocio.getInstancia().crearSAProveedor().altaProveedor(tProveedor);
        if(res < 0) 
        	return new Context(Eventos.ALTA_PROVEEDOR_KO, tProveedor.getId());
        else
        	return new Context(Eventos.ALTA_PROVEEDOR_OK, datos);
    }

    @Override
    public Eventos getId() {
        return Eventos.ALTA_PROVEEDOR;
    }
}