package presentacion.trabajador;

import javax.swing.JFrame;

import negocio.trabajador.TTrabajador;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUITRABAJADOR extends JFrame {
	
	private static GUITRABAJADOR instancia;
	protected TTrabajador trabajador;	
	
	public synchronized static GUITRABAJADOR getInstancia() {
		if (instancia == null) instancia = new GUITRABAJADORImp();
		instancia.setVisible(true);
		return instancia;
	}

	
	public abstract void actualizar(Context context);
}