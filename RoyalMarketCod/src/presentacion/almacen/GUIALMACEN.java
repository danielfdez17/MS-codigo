package presentacion.almacen;

import javax.swing.JFrame;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUIALMACEN extends JFrame {

	private static GUIALMACEN instancia;

	public synchronized static GUIALMACEN getInstancia() {
		if (instancia == null) instancia = new GUIALMACENImp();
		return instancia;
	}

	public abstract void actualizar(Context context);
}