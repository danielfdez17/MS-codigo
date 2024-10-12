package presentacion.cliente;

import javax.swing.JFrame;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUICLIENTE extends JFrame {

	private static GUICLIENTE instancia;

	public synchronized static GUICLIENTE getInstancia() {
		if (instancia == null) instancia = new GUICLIENTEImp();
		instancia.setVisible(true);
		return instancia;
	}

	public abstract void actualizar(Context context);
}