/**
 * 
 */
package presentacion.controlador;

import presentacion.factoriaVistas.Context;

public abstract class Controlador {

	private static Controlador instancia;

	public synchronized static Controlador getInstancia() {
		if (instancia == null) instancia = new ControladorImp();
		return instancia;
	}

	public abstract void accion(Context context);
}