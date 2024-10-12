package presentacion.producto;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.producto.SAProducto;
import negocio.producto.SAProductoImp;
import negocio.producto.TProducto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import presentacion.viewhelper.GUIComprobar;
import presentacion.viewhelper.Panel;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIActualizarProducto extends JFrame implements GUI {
	
	private static final String PRODUCTO = "producto";
	private static final String FROM_WHERE = "GUIActualizarProducto";
	private static GUIActualizarProducto instancia;
	private TProducto producto;
	private int fila = 6; 
	private GUIComprobar guiComprobar;
	private JTextField textId, textNombre, textStock, textPrecio, textIdAlmacen;
	
	public GUIActualizarProducto(){
		this.guiComprobar = new GUIComprobar(PRODUCTO);
	}
	
	public synchronized static GUIActualizarProducto getInstancia() {
		if (instancia == null) instancia = new GUIActualizarProducto();
		return instancia;
	}
	
	private void initGUI() {
		
		
		this.setTitle(FROM_WHERE);

		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panelActualizar =  new JPanel(new GridLayout(fila, 1));
		mainPanel.add(panelActualizar);
		
		Panel panelId = new Panel("ID");
		this.textId = panelId.getJTextField();
		this.textId.setEditable(false);
		panelActualizar.add(panelId.getJPanel());
		
		Panel panelNombre = new Panel("Nombre");
		this.textNombre = panelNombre.getJTextField();
		panelActualizar.add(panelNombre.getJPanel());
		
		
		Panel panelPrecio = new Panel("Precio");
		this.textPrecio = panelPrecio.getJTextField();
		panelActualizar.add(panelPrecio.getJPanel());
		
		Panel panelStock = new Panel("Stock");
		this.textStock = panelStock.getJTextField();
		panelActualizar.add(panelStock.getJPanel());
		
		Panel panelIdAlmacen = new Panel("ID almacen");
		this.textIdAlmacen = panelIdAlmacen.getJTextField();
		panelActualizar.add(panelIdAlmacen.getJPanel());
		

		JButton emptyButton = new JButton("Vaciar");
		emptyButton.addActionListener((e) -> {
			this.clear();
		});
		
		JButton restoreButton = new JButton("Restaurar valores");
		restoreButton.addActionListener((e) -> {
			this.textId.setText("" + this.producto.getId());
			this.textNombre.setText(this.producto.getNombre());
			this.textIdAlmacen.setText(this.producto.getIdAlmacen() + "");
			this.textPrecio.setText(this.producto.getPrecio() + "");
			this.textStock.setText(this.producto.getStock() + "");
		});
		
		
		JButton button = new JButton("Actualizar");
		JPanel panelButton = new JPanel();
		panelButton.add(button);
		panelActualizar.add(panelButton);
		panelButton.add(emptyButton);
		panelButton.add(restoreButton);
		
		button.addActionListener((e)->{
			
			if (this.areTextFieldsEmpty()) {
				GUIMSG.showMessage("Faltan campos por rellenar", FROM_WHERE, true);
			}
			else {
				try {
					int id = Integer.parseInt(this.textId.getText());
					double precio = Double.parseDouble(this.textPrecio.getText());
					int stock = Integer.parseInt(this.textStock.getText());
					String nombre = this.textNombre.getText();
					boolean activo = this.producto.isActivo();
					int idAlmacen = Integer.parseInt(this.textIdAlmacen.getText());
					this.clear();
					this.setVisible(false);
					Controlador.getInstancia().accion(new Context(Eventos.ACTUALIZAR_PRODUCTO, new TProducto(id, nombre, precio, stock, activo, idAlmacen)));
				} catch(NumberFormatException nfe){
					GUIMSG.showMessage("Campos erroneos", FROM_WHERE, true);
				}
			}	
		});
		
		Dimension dims_frame = this.getContentPane().getSize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = new Dimension((int) dims_frame.getWidth(), (int) dims_frame.getHeight());
		
		this.setPreferredSize(new Dimension((fila +1)*100, 300));
		this.setLocation(dim.width / 2 - frame.getSize().width / 2 - 200, dim.height / 2 - frame.getSize().height / 2 - 200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
	}
	
	public void setProducto(TProducto p){
		this.producto = p;
	}

	public void comprobar(boolean b) {
		this.guiComprobar.setVisible(b);
	}
	
	public void init(){
		this.initGUI();
	}

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case ACTUALIZAR_PRODUCTO_OK:
			TProducto p = (TProducto) context.getDatos();
			GUIMSG.showMessage("Producto actualizado \n" + p.toString(), FROM_WHERE, false);
			break;
		case ACTUALIZAR_PRODUCTO_KO:
			switch ((int)context.getDatos()) {
			case SAProductoImp.PRODUCTO_INEXISTENTE:
				GUIMSG.showMessage("El producto no existe \n", FROM_WHERE, true);
			break;
			case SAProductoImp.ALMACEN_INEXISTENTE:
				GUIMSG.showMessage("El almacen asociado no existe \n", FROM_WHERE, true);
				break;
			case SAProductoImp.ALMACEN_INACTIVO:
				GUIMSG.showMessage("El almacen asociado esta inactivo \n", FROM_WHERE, true);
				break;
			}
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
		this.textId.setText("");
		this.textNombre.setText("");
		this.textIdAlmacen.setText("");
		this.textPrecio.setText("");
		this.textStock.setText("");
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAProducto.ERROR_SINTACTICO:
			return "Error sintactico";
		case SAProducto.PRODUCTO_INEXISTENTE:
			return "El producto no existe";
		case SAProducto.ALMACEN_INEXISTENTE:
			return "El almacen asociado no existe";
		case SAProducto.ALMACEN_INACTIVO:
			return "El almacen asociado esta inactivo";
		case SAProducto.ERROR_BBDD:
			return "Error en la BBDD";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return this.textId.getText().isEmpty()
				&& this.textNombre.getText().isEmpty()
				&& this.textIdAlmacen.getText().isEmpty()
				&& this.textPrecio.getText().isEmpty()
				&& this.textStock.getText().isEmpty();
	}

}