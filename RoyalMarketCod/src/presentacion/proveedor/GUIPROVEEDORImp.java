package presentacion.proveedor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.proveedor.TProveedor;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public class GUIPROVEEDORImp extends GUIPROVEEDOR {
	
	private static final String FROM_WHERE = "GUIPROVEEDORImp.actualizar()";
	private static final int BUTTONS = 8;
	
	public GUIPROVEEDORImp() {
		super();
		this.initGUI();
	}
	
	private void initGUI() {
		
		this.setTitle("Proveedores");
		// MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);
        
        // BUTTONS PANEL
        JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS, 1));
        
        // button_Alta BUTTON
        JButton button_altaProveedor = new JButton("Alta proveedor");
        button_altaProveedor.addActionListener((e) -> {
        	GUIAltaProveedor.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_altaProveedor);
     // button_baja BUTTON
        JButton button_bajaProveedor = new JButton("Baja proveedor");
        button_bajaProveedor.addActionListener((e) -> {
        	GUIBajaProveedor.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_bajaProveedor);
        // button_buscar BUTTON
        JButton button_buscar = new JButton("Buscar proveedor");
        button_buscar.addActionListener((e) -> {
        	GUIBuscarProveedor.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_buscar);
     // Vincular BUTTON
        JButton button_vincular = new JButton("Vincular producto");
        button_vincular.addActionListener((e) -> {
        	GUIVincularProducto.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_vincular);
        // DesVincular BUTTON
        JButton button_desvincular = new JButton("Desvincular producto");
        button_desvincular.addActionListener((e) -> {
        	GUIDesvincularProducto.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_desvincular);
        // UPDATE Buttom
        JButton button_actualizar = new JButton("Actualizar proveedor");
        button_actualizar.addActionListener((e) -> {
			GUIActualizarProveedor.getInstancia().comprobar(true);
        	this.setVisible(false);
        });
        buttonsPanel.add(button_actualizar);
     // button_listar BUTTON
        JButton button_listar = new JButton("Listar proveedores");
        button_listar.addActionListener((e) -> {
        	GUIListarProveedores.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_listar);
     // button_listar BY producto
        JButton button_listar_por_producto = new JButton("Listar proveedores por producto");
        button_listar_por_producto.addActionListener((e) -> {
        	GUIListarProveedoresPorProducto.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_listar_por_producto);
        mainPanel.add(buttonsPanel);
        this.pack();
        this.setVisible(true);
        this.setPreferredSize(new Dimension(350, 400));
        this.setBounds(200, 150, 400, 400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	setVisible(false);
            }
        });
	
	}
	@Override
	public void actualizar(Context context) {
		TProveedor p;
		
		switch(context.getEvent()){
		
		case COMPROBAR_PROVEEDOR_KO:
			GUIMSG.showMessage("No existe producto con dicho id  \n", FROM_WHERE, true);
			break;
		case COMPROBAR_PROVEEDOR_OK:
			p = (TProveedor) context.getDatos();
			GUIActualizarProveedor.getInstancia().setProveedor(p);
			GUIActualizarProveedor.getInstancia().init();
			GUIActualizarProveedor.getInstancia().setVisible(true);
			break;
		default:
			GUIMSG.showMessage("Error Inesperado realizando la peticion", FROM_WHERE, true);
			break;
			
		}
		
	}
}