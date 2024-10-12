package presentacion.material;

import javax.swing.JFrame;

import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUIMATERIAL extends JFrame {
	private static GUIMATERIAL instancia;

	public synchronized static GUIMATERIAL getInstancia() {
		if (instancia == null) instancia = new GUIMATERIALImp();
		return instancia;
	}

	public abstract void actualizar(Context context);
}