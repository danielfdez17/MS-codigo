/**
 * 
 */
package presentacion.oficina;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.oficina.TOficina;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public class GUIOFICINAImp extends GUIOFICINA {

	private final String nombEnt = "OFICINA";
	private static final String HEADERS[] = {"ID", "Nombre"};
	private static final String FROM_WHERE = "GUIOFICINAImp.actualizar()";
	private static final int BUTTONS = 6;
	
	public GUIOFICINAImp(){
		super();
		this.initGUI();
	}
	
	private void initGUI() {
		this.setTitle("Oficinas");
    	
    	// MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);
        
        // BUTTONS PANEL
        JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS, 1));

        // button_buscar BUTTON
        JButton button_buscar = new JButton("Buscar oficina");
        button_buscar.addActionListener((e) -> {
        	GUIBuscarOficina.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_buscar);
        
     // button_baja BUTTON
        JButton button_baja = new JButton("Baja oficina");
        button_baja.addActionListener((e) -> {
        	GUIBajaOficina.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_baja);
        
        // button_alta BUTTON
        JButton button_alta = new JButton("Alta oficina");
        button_alta.addActionListener((e) -> {
        	GUIAltaOficina.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_alta);
        
        // button_actualizar BUTTON
        JButton button_actualizar = new JButton("Actualizar oficina");
        button_actualizar.addActionListener((e) -> {
        	GUIActualizarOficina.getInstancia().comprobar(true);
        });
        buttonsPanel.add(button_actualizar);
        
        // button_mostrarNomina BUTTON
        JButton button_mostrarNomina = new JButton("Mostrar nomina");
        button_mostrarNomina.addActionListener((e) -> {
        	GUIMostrarNomina.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_mostrarNomina);
        
        // button_listar BUTTON
        JButton button_listar = new JButton("Listar oficinas");
        button_listar.addActionListener((e) -> {
        	GUIListarOficinas.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_listar);
        
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        
        
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
//		this.setBounds(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200, 400, 400);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	setVisible(false);
            }
        });
	}

	@Override
	public void actualizar(Context context) {
		TOficina oficina = null;
		switch (context.getEvent()) {
		case COMPROBAR_OFICINA_OK:
			TOficina to = (TOficina)context.getDatos();
			GUIActualizarOficina.getInstancia().setOficina(to);
			GUIActualizarOficina.getInstancia().init();
			GUIActualizarOficina.getInstancia().setVisible(true);
			break;
		case COMPROBAR_OFICINA_KO:
			GUIMSG.showMessage("No existe la oficina con esa id", FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage("Respuesta no contemplada", FROM_WHERE, true);
			break;
		}
	}
}