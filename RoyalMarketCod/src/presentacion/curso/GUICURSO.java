package presentacion.curso;

import javax.swing.JFrame;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUICURSO extends JFrame {

	private static GUICURSO instancia;

	public synchronized static GUICURSO getInstancia() {
		if (instancia == null) instancia = new GUICURSOImp();
		return instancia;
	}

	public abstract void actualizar(Context context);
}