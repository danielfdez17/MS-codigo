package presentacion.proveedor;

import javax.swing.JFrame;

import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUIPROVEEDOR extends JFrame {

	private static GUIPROVEEDOR instancia;

	public synchronized static GUIPROVEEDOR getInstancia() {
		if (instancia == null) instancia = new GUIPROVEEDORImp();
		instancia.setVisible(true);
		return instancia;
	}

	public abstract void actualizar(Context context);
}