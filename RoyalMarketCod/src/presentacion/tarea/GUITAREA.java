package presentacion.tarea;

import javax.swing.JFrame;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUITAREA extends JFrame {
	private static GUITAREA instancia;

	public synchronized static GUITAREA getInstancia() {
		if (instancia == null) instancia = new GUITAREAImp();
		return instancia;
	}

	public abstract void actualizar(Context context);
}