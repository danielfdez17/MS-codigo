package presentacion.viewhelper;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Panel {

	private JPanel panel;
	private JTextField text;
	
	public Panel(String field_name) {
		this.initPanel(field_name);
	}
	private void initPanel(String field_name) {
		this.panel = new JPanel();
		Dimension dimension = new Dimension(100, 30);
		this.text = new JTextField(10);
		this.text.setPreferredSize(dimension);
		JLabel label = new JLabel(" " + field_name + " ");
		this.panel.add(label);
		this.panel.add(text);
	}
	public JPanel getJPanel() {
		return this.panel;
	}
	public JTextField getJTextField() {
		return this.text;
	}
}
