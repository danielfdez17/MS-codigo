/**
 * 
 */
package presentacion.oficina;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.oficina.SAOficina;
import negocio.oficina.TOficina;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.Panel;
import utilities.Utils;
import presentacion.factoriaVistas.Context;


@SuppressWarnings("serial")
public class GUIMostrarNomina extends JFrame implements GUI {
	
	private static final String FROM_WHERE = "GUIMostarNomina";
	private JTextField textId;
	private static GUIMostrarNomina instancia;
	
	public GUIMostrarNomina() {
		this.initGUI();
	}
	
	public static synchronized GUIMostrarNomina getInstancia() {
		if (instancia == null) instancia = new GUIMostrarNomina();
		return instancia;
	}
	
	private void initGUI() {
		this.setTitle("Mostrar nomina");
		
		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panelMostrarNomina = new JPanel(new GridLayout(1, 1));
		mainPanel.add(panelMostrarNomina, BorderLayout.CENTER);
		
		// Oficina nombre
		
		Panel panelId = new Panel("ID");
		this.textId = panelId.getJTextField();
		panelMostrarNomina.add(panelId.getJPanel());
		
		JButton buttonBuscar = new JButton("Buscar oficina");
		buttonBuscar.addActionListener(l -> {
			if (!this.areTextFieldsEmpty()) {
				try {
					int id = Integer.parseInt(this.textId.getText());
					this.setVisible(false);
					this.clear();
					Controlador.getInstancia().accion(new Context(Eventos.MOSTRAR_NOMINA, id));
				} catch (NumberFormatException nfe) {
					GUIMSG.showMessage("El campo 'ID' solo requiere de numeros", FROM_WHERE, true);
				}
			}
			else {
				GUIMSG.showMessage("El campo 'ID' esta vacio", FROM_WHERE, true);
			}
		});
		
		// EMPTY JTEXTFIELDS BUTTON
		JButton emptyTextButton = new JButton("Vaciar");
		emptyTextButton.addActionListener((e) -> {
			this.clear();
		});
		
		// BUTTONS PANEL
		JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
		buttonsPanel.add(emptyTextButton);
		buttonsPanel.add(buttonBuscar);
		
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
		mainPanel.add(panelMostrarNomina, BorderLayout.CENTER);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
//		this.setBounds(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200, 400, 400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
//		this.setVisible(true);
	}
	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case MOSTRAR_NOMINA_OK:
			double p = (double)context.getDatos();
			GUIMSG.showMessage("Nomina de la oficina: " + p, FROM_WHERE, false);
			break;
		case MOSTRAR_NOMINA_KO:
			GUIMSG.showMessage("La oficina no existe", FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		this.textId.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
	switch (error) {
		case SAOficina.OFICINA_INEXISTENTE:
			return "La oficina no existe en la BBDD";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
	}
}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty();
	}
}