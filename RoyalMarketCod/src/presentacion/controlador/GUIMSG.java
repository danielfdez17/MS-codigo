/**
 * 
 */
package presentacion.controlador;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class GUIMSG extends JDialog {

	public static void showMessage(String msg, String fromWhere, boolean error) {
		JOptionPane.showMessageDialog(new JFrame(), msg, fromWhere, ((error) ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE));
	}
}