package presentacion.trabajador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.trabajador.TTrabajador;
import negocio.trabajador.TTrabajadorJCompleta;
import negocio.trabajador.TTrabajadorJParcial;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public class GUITRABAJADORImp extends GUITRABAJADOR {
	
	private static final String FROM_WHERE = "GUITRABAJADORImp.actualizar()";
	private static final int BUTTONS = 8;
	
	public GUITRABAJADORImp() {
		super();
		this.initGUI();
	}
	private void initGUI() {
		this.setTitle("Trabajadores");
		// MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);
        
        // BUTTONS PANEL
        JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS, 1));
        
        // button_AltaC BUTTON
        JButton button_altaTrabajadorC = new JButton("Alta Trabajador Jornada Completa");
        button_altaTrabajadorC.addActionListener((e) -> {
        	GUIAltaTrabajadorJCompleta.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_altaTrabajadorC);
        // button_AltaP BUTTON
        JButton button_altaTrabajadorP = new JButton("Alta Trabajador Jornada Parcial");
        button_altaTrabajadorP.addActionListener((e) -> {
        	GUIAltaTrabajadorJParcial.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_altaTrabajadorP);
     // button_baja BUTTON
        JButton button_bajaTrabajador = new JButton("Baja Trabajador");
        button_bajaTrabajador.addActionListener((e) -> {
        	GUIBajaTrabajador.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_bajaTrabajador);
        // button_buscar BUTTON
        JButton button_buscar = new JButton("Buscar Trabajador");
        button_buscar.addActionListener((e) -> {
        	GUIBuscarTrabajador.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_buscar);
        // UPDATE Button
        JButton button_actualizar = new JButton("Actualizar Trabajador Jornada Completa");
        button_actualizar.addActionListener((e) -> {
        	GUIActualizarTrabajadorJCompleta.getInstancia().comprobar(true);
        	GUIActualizarTrabajadorJCompleta.getInstancia().setVisible(false);
        });
        buttonsPanel.add(button_actualizar);
        // UPDATE JP Button
        JButton button_actualizarJP = new JButton("Actualizar Trabajador Jornada Parcial");
        button_actualizarJP.addActionListener((e) -> {
        	GUIActualizarTrabajadorJParcial.getInstancia().comprobar(true);
        	GUIActualizarTrabajadorJParcial.getInstancia().setVisible(false);
        });
        buttonsPanel.add(button_actualizarJP);
     // button_listar BUTTON
        JButton button_listar = new JButton("Listar Trabajadores");
        button_listar.addActionListener((e) -> {
        	GUIListarTrabajadores.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_listar);
     // button_listar almacen
        JButton button_listar_por_almacen = new JButton("Listar Trabajadores por almacen");
        button_listar_por_almacen.addActionListener((e) -> {
        	GUIListarTrabajadoresPorAlmacen.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_listar_por_almacen);
        
        JButton buttonListarPorRangoSueldo = new JButton("Listar trabajadores por rango de sueldo");
        buttonListarPorRangoSueldo.addActionListener(e -> {
        	GUIListarRangoSueldo.getInstancia().setVisible(true);
        });
        buttonsPanel.add(buttonListarPorRangoSueldo);
        
        JButton buttonListarTrabajadoresDespedidos = new JButton("Listar trabajadores despedidos");
        buttonListarTrabajadoresDespedidos.addActionListener(e -> {
        	GUIListarTrabajadoresDespedidos.getInstancia().setVisible(true);
        });
        buttonsPanel.add(buttonListarTrabajadoresDespedidos);
        
        JButton buttonListarTrabajadoresJCompleta = new JButton("Listar trabajadores a joranda completa");
        buttonListarTrabajadoresJCompleta.addActionListener(e -> {
        	GUIListarTrabajadoresJCompleta.getInstancia().setVisible(true);
        });
        buttonsPanel.add(buttonListarTrabajadoresJCompleta);
        
		mainPanel.add(buttonsPanel, BorderLayout.CENTER);
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
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
		TTrabajador trabajador = null;
		switch (context.getEvent()) {
		case COMPROBAR_TRABAJADOR_KO:
			break;
		case COMPROBAR_TRABAJADOR_OK:
			trabajador = (TTrabajador)context.getDatos();
			if (trabajador instanceof TTrabajadorJCompleta) {
				GUIActualizarTrabajadorJCompleta.getInstancia().setttjc((TTrabajadorJCompleta)trabajador);
				GUIActualizarTrabajadorJCompleta.getInstancia().init();
				GUIActualizarTrabajadorJCompleta.getInstancia().setVisible(true);;
			}
			else if (trabajador instanceof TTrabajadorJParcial) {
				GUIActualizarTrabajadorJParcial.getInstancia().setttjp((TTrabajadorJParcial)trabajador);
				GUIActualizarTrabajadorJParcial.getInstancia().init();
				GUIActualizarTrabajadorJParcial.getInstancia().setVisible(true);;
			}
			break;

		    default:
			        GUIMSG.showMessage("Esto no deberia salir", FROM_WHERE, true);
			        break;
		}

	}

}