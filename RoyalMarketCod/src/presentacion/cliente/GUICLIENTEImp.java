package presentacion.cliente;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.cliente.TClienteNormal;
import negocio.cliente.TClienteVIP;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUICLIENTEImp extends GUICLIENTE {
	
	private static final String FROM_WHERE = "GUICLIENTEImp.actualizar()";
	private static final int BUTTONS = 7;
	
	public GUICLIENTEImp() {
		this.initGUI();
	}

	private void initGUI() {
		this.setTitle("Clientes");
		
	   	// MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);
        
        // BUTTONS PANEL
        JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS, 1));
        
        // buttonAlta BUTTON
        JButton buttonAltaVIP = new JButton("Alta cliente vip");
        buttonAltaVIP.addActionListener((e) -> {
        	GUIAltaClienteVIP.getInstancia().setVisible(true);
        });
        buttonsPanel.add(buttonAltaVIP);

		JButton buttonAltaNormal = new JButton("Alta cliente normal");
		buttonAltaNormal.addActionListener((e) -> {
			GUIAltaClienteNormal.getInstancia().setVisible(true);
		});
		buttonsPanel.add(buttonAltaNormal);

        // button_buscar BUTTON
        JButton button_buscar = new JButton("Buscar cliente");
        button_buscar.addActionListener((e) -> {
        	GUIBuscarCliente.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_buscar);
        // button_baja BUTTON
        JButton button_baja = new JButton("Baja cliente");
        button_baja.addActionListener((e) -> {
        	GUIBajaCliente.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_baja);
        
        JButton button_actualizar_vip = new JButton("Actualizar cliente vip");
        button_actualizar_vip.addActionListener((e) -> {
        	GUIActualizarClienteVIP.getInstancia().comprobar(true);
        	this.setVisible(false);
        });
        buttonsPanel.add(button_actualizar_vip);
        
        JButton button_actualizar_normal = new JButton("Actualizar cliente normal");
        button_actualizar_normal.addActionListener(e -> {
        	GUIActualizarClienteNormal.getInstancia().comprobar(true);
        	this.setVisible(false);
        });
        buttonsPanel.add(button_actualizar_normal);
        
        
        // button_listar BUTTON
        JButton button_listar = new JButton("Listar clientes");
        button_listar.addActionListener((e) -> {
        	GUIListarClientes.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_listar);
        
        
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
		switch (context.getEvent()) {
		case COMPROBAR_CLIENTE_OK:
			if (context.getDatos() instanceof TClienteVIP) {
				TClienteVIP tcv = (TClienteVIP)context.getDatos();
				GUIActualizarClienteVIP.getInstancia().setCliente(tcv);
				GUIActualizarClienteVIP.getInstancia().init();
				GUIActualizarClienteVIP.getInstancia().setVisible(true);
			}
			else {
				TClienteNormal tcn = (TClienteNormal)context.getDatos();
				GUIActualizarClienteNormal.getInstancia().setCliente(tcn);
				GUIActualizarClienteNormal.getInstancia().init();
				GUIActualizarClienteNormal.getInstancia().setVisible(true);
			}
			break;
		case COMPROBAR_CLIENTE_KO:
			GUIMSG.showMessage("No existe el cliente con dicho id", FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}
}