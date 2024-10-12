package presentacion.controlador.command.command_proveedor;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proveedor.TProveedor;
import presentacion.controlador.command.Command;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;


public class CommandComprobarProveedor implements Command {

	public Context execute(Object datos) {
		int id = (int) datos;
		TProveedor res = FactoriaNegocio.getInstancia().crearSAProveedor().buscarProveedor(id);
		if(res == null) return new Context(Eventos.COMPROBAR_PROVEEDOR_KO, id);
		return new Context(Eventos.COMPROBAR_PROVEEDOR_OK, res);
    }

    @Override
    public Eventos getId() {
        return Eventos.COMPROBAR_PROVEEDOR;
    }
}