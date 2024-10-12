package presentacion.almacen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.almacen.TAlmacen;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIALMACENImp extends GUIALMACEN {
	
	private static final String FROM_WHERE = "GUIALMACENImp";
	private static final int BUTTONS = 5;
	private static final String TITLE = Utils.ALMACENES;

	public GUIALMACENImp() {
		this.initGUI();
	}

	private void initGUI() {
		this.setTitle(TITLE);
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS, 1));
		
		JButton button_alta = new JButton("Alta almacen");
		button_alta.addActionListener(e -> {
			GUIAltaAlmacen.getInstancia().setVisible(true);
		});
		buttonsPanel.add(button_alta);
		
		JButton button_buscar = new JButton("Buscar almacen");
		button_buscar.addActionListener(e -> {
			GUIBuscarAlmacen.getInstancia().setVisible(true);
		});
		buttonsPanel.add(button_buscar);
		
		JButton button_listar = new JButton("Listar almacenes");
		button_listar.addActionListener(e -> {
			GUIListarAlmacenes.getInstancia().setVisible(true);
		});
		buttonsPanel.add(button_listar);
		
		JButton button_actualizar = new JButton("Actualizar almacen");
		button_actualizar.addActionListener(e -> {
			GUIActualizarAlmacen.getInstancia().comprobar(true);
		});
		buttonsPanel.add(button_actualizar);
		
		JButton button_baja = new JButton("Baja almacen");
		button_baja.addActionListener(e -> {
			GUIBajaAlmacen.getInstancia().setVisible(true);
		});
		buttonsPanel.add(button_baja);
		
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
		TAlmacen almacen;
		switch ((Eventos)context.getEvent()) {
		case COMPROBAR_ALMACEN_KO:
			GUIMSG.showMessage("No existe almacen con dicho id  \n", FROM_WHERE, true);
			break;
		case COMPROBAR_ALMACEN_OK:
			almacen = (TAlmacen)context.getDatos();
			GUIActualizarAlmacen.getInstancia().setAlmacen(almacen);
			GUIActualizarAlmacen.getInstancia().init();
			GUIActualizarAlmacen.getInstancia().setVisible(true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
		
	}
}