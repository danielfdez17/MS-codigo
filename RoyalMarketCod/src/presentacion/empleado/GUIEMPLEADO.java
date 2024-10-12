package presentacion.empleado;

import javax.swing.JFrame;

import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUIEMPLEADO extends JFrame {

	private static GUIEMPLEADO instancia;

	public synchronized static GUIEMPLEADO getInstancia() {
		if (instancia == null) instancia = new GUIEMPLEADOImp();
		return instancia;
	}

	public abstract void actualizar(Context context);
}