package presentacion.main;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GUIMAIN extends JFrame {
	
	private static GUIMAIN instancia;
	
	public synchronized static GUIMAIN getInstancia() {
		if (instancia == null) instancia = new GUIMAINImp();
		instancia.setVisible(true);
		return instancia;
	}
}