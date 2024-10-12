package presentacion.controlador.command.command_proveedor;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proveedor.TProveedor;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class CommandActualizarProveedor implements Command {
	public Context execute(Object datos) {
		TProveedor tProveedor = (TProveedor) datos;
        int res = FactoriaNegocio.getInstancia().crearSAProveedor().actualizarProveedor(tProveedor);
        if(res < 0) 
        	return new Context(Eventos.ACTUALIZAR_PROVEEDOR_KO, tProveedor.getId());
        else
        	return new Context(Eventos.ACTUALIZAR_PROVEEDOR_OK, datos);
	}

	@Override
	public Eventos getId() {
		return Eventos.ACTUALIZAR_PROVEEDOR;
	}
}