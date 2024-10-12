package presentacion.controlador;

import presentacion.controlador.command.Command;
import presentacion.controlador.command.FactoriaCommand;
import presentacion.factoriaVistas.Context;
import presentacion.factoriaVistas.FactoriaVistas;

public class ControladorImp extends Controlador {

	@Override
	public void accion(Context context) {
		FactoriaCommand factoria_command = FactoriaCommand.getInstancia();
		Command command = factoria_command.crearComando(context.getEvent());
		if (command != null) {
			Context res = command.execute(context.getDatos());
			FactoriaVistas factoria_vistas = FactoriaVistas.getInstancia();
			factoria_vistas.crearVista(res);
		}
		else {
			GUIMSG.showMessage(String.format("Commando desconocido, insertalo en la lista de comandos"), "ControladorImp", true);			
		}
	}
	
}