package presentacion.factura;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.factura.TCarrito;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;

@SuppressWarnings("serial")
public class GUIFACTURAImp extends GUIFACTURA {
	
	private static final String FROM_WHERE = "GUIFACTURAImp.actualizar()";
	private static final int BUTTONS = 10;
	
	public GUIFACTURAImp() {
		super();
		this.initGUI();
	}

	private void initGUI() {
		this.setTitle("Facturas");
    	
    	// MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.setContentPane(mainPanel);
        
        // BUTTONS PANEL
        JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS, 1));
        
        // button_arbir BUTTON
        JButton button_arbir = new JButton("Abrir factura");
        button_arbir.addActionListener((e) -> {
        	GUIAbrirFactura.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_arbir);

        // button_buscar BUTTON
        JButton button_buscar = new JButton("Buscar factura");
        button_buscar.addActionListener((e) -> {
        	GUIBuscarFactura.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_buscar);
        
        JButton buttonBuscarCarrito = new JButton("Buscar carrito");
        buttonBuscarCarrito.addActionListener((e) -> {
        	GUIBuscarCarrito.getInstancia().setVisible(true);
        });
        buttonsPanel.add(buttonBuscarCarrito);
        
        // button_cerrar BUTTON
        JButton button_cerrar = new JButton("Cerrar factura");
        button_cerrar.addActionListener((e) -> {
        	GUICerrarFactura.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_cerrar);
        
        // SALE DEVOLUTION BUTTON
        JButton button_devolver = new JButton("Devolver factura");
        button_devolver.addActionListener((e) -> {
        	GUIDevolverFactura.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_devolver);
        
        JButton buttonDevolverProducto = new JButton("Devolver producto");
        buttonDevolverProducto.addActionListener((e) -> {
        	GUIDevolverProducto.getInstancia().setVisible(true);
        });
        
        // UPDATE SHOPPING CAR
        JButton button_carrito = new JButton("Actualizar carrito");
        button_carrito.addActionListener((e) -> {
        	GUIActualizarCarrito.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_carrito);
        
        // button_listar BUTTON
        JButton button_listar = new JButton("Listar facturas");
        button_listar.addActionListener((e) -> {
        	GUIListarFacturas.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_listar);
        
        // button_listar BY EMPLOYEE BUTTON
        JButton button_listar_por_cliente = new JButton("Listar facturas por cliente");
        button_listar_por_cliente.addActionListener((e) -> {
        	GUIListarFacturasPorCliente.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_listar_por_cliente);
        
        // button_listar BY EMPLOYEE BUTTON
        JButton button_listar_por_producto = new JButton("Listar facturas por producto");
        button_listar_por_cliente.addActionListener((e) -> {
        	GUIListarFacturasPorProducto.getInstancia().setVisible(true);
        });
        buttonsPanel.add(button_listar_por_producto);
        
        JButton button_mostrar_factura_actual = new JButton("Mostrar factura abierta");
        button_mostrar_factura_actual.addActionListener(l -> {
        	if (this.carrito != null) GUIMSG.showMessage(this.carrito.toString() , FROM_WHERE, false);
        	else GUIMSG.showMessage("No hay una factura abierta", FROM_WHERE, true);
        });
        buttonsPanel.add(button_mostrar_factura_actual);
        
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
		default:
			GUIMSG.showMessage("Respuesta no contemplada", FROM_WHERE, true);
			break;
		}
	}

	@Override
	public TCarrito getCarrito() {
		return this.carrito;
	}

	@Override
	public void setCarrito(TCarrito carrito) {
		this.carrito = carrito;
	}
}