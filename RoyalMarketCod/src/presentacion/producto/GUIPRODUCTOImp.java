package presentacion.producto;

import java.awt.BorderLayout;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import utilities.Utils;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

import negocio.producto.TProducto;

@SuppressWarnings("serial")
public class GUIPRODUCTOImp extends GUIPRODUCTO {
	
	private static final String FROM_WHERE = "GUIPRODUCTOImp.actualizar()";
	private static final int BUTTONS = 7;
	
	public GUIPRODUCTOImp() {
		super();
		this.initGUI();
	}
	
	private void initGUI() {
		
		this.setTitle("Productos");
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension(400, 400));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);

		//Panel principal
		JPanel mainPanel = new JPanel (new BorderLayout());
		this.setContentPane(mainPanel);
		
		//Panel de botones
		JPanel buttonsPanel = new JPanel(new GridLayout(BUTTONS,1));
		mainPanel.add(buttonsPanel, BorderLayout.CENTER);
		
		//Botones
		
		JButton button_ALTA = new JButton("Alta producto");
		buttonsPanel.add(button_ALTA);
		button_ALTA.addActionListener((e)->{
			GUIAltaProducto.getInstancia().setVisible(true);
		});
		
		
		
		JButton button_BAJA = new JButton("Baja producto");
		buttonsPanel.add(button_BAJA);
		button_BAJA.addActionListener((e)->{
			GUIBajaProducto.getInstancia().setVisible(true);
		});
		
		
		JButton button_ACTUALIZAR = new JButton("Actualizar producto");
		buttonsPanel.add(button_ACTUALIZAR);
		button_ACTUALIZAR.addActionListener((e)->{
			GUIActualizarProducto.getInstancia().comprobar(true);
		});
		
		
		JButton button_BUSCAR = new JButton("Buscar producto");
		buttonsPanel.add(button_BUSCAR);
		button_BUSCAR.addActionListener((e)->{
			GUIBuscarProducto.getInstancia().setVisible(true);
		});
		
		JButton button_LISTAR = new JButton("Listar productos");
		buttonsPanel.add(button_LISTAR);
		button_LISTAR.addActionListener((e)->{
			GUIListarProductos.getInstancia().setVisible(true);
		});
		
		
		JButton button_LISTARPORPROVEEDOR = new JButton("Listar productos por proveedor");
		buttonsPanel.add(button_LISTARPORPROVEEDOR);
		button_LISTARPORPROVEEDOR.addActionListener((e)->{
			GUIListarProductosPorProveedor.getInstancia().setVisible(true);
		});
		
		JButton button_listar_por_almacen = new JButton("Listar productos por almacen");
		buttonsPanel.add(button_listar_por_almacen);
		button_listar_por_almacen.addActionListener(l -> {
			GUIListarProductosPorAlmacen.getInstancia().setVisible(true);
		});
		
		this.pack();
		this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	setVisible(false);
            }
        });
    	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    
	}

	@Override
	public void actualizar(Context context) {
		switch(context.getEvent()){
			case COMPROBAR_PRODUCTO_KO:
				GUIMSG.showMessage("No existe producto con dicho id  \n", FROM_WHERE, true);
			break;
			case COMPROBAR_PRODUCTO_OK:
				TProducto p = (TProducto) context.getDatos();
				GUIActualizarProducto.getInstancia().setProducto(p);
				GUIActualizarProducto.getInstancia().init();
				GUIActualizarProducto.getInstancia().setVisible(true);
			break;
		default:
				GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
				break;
			
		}
		
	}
}