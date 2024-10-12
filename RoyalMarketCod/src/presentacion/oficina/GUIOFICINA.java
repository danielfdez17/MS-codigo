package presentacion.oficina;

import javax.swing.JFrame;

import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUIOFICINA extends JFrame {

	private static GUIOFICINA instancia;

	public synchronized static GUIOFICINA getInstancia() {
		if (instancia == null) instancia = new GUIOFICINAImp();
		return instancia;
	}

	public abstract void actualizar(Context context);
}