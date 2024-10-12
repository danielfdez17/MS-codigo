package presentacion.proyecto;

import javax.swing.JFrame;

import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public abstract class GUIPROYECTO extends JFrame {

	private static GUIPROYECTO instancia;

	public static synchronized GUIPROYECTO getInstancia() {
		if (instancia == null) instancia = new GUIPROYECTOImp();
		
		return instancia;
	}

	public abstract void actualizar(Context context);
}