package presentacion.producto;

import javax.swing.JFrame;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUIPRODUCTO extends JFrame {
	private static GUIPRODUCTO instancia;
	public synchronized static GUIPRODUCTO getInstancia() {
		if (instancia == null) instancia = new GUIPRODUCTOImp();
		instancia.setVisible(true);
		return instancia;
	}

	public abstract void actualizar(Context context);
}