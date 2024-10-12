package presentacion.producto;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.producto.SAProducto;
import negocio.producto.TProducto;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.controlador.GUIMSG;
import presentacion.factoriaVistas.Context;
import presentacion.viewhelper.GUI;
import utilities.Utils;

@SuppressWarnings("serial")
public class GUIAltaProducto extends JFrame implements GUI {
	
	private int fila = 5;
	private static final String FROM_WHERE = "GUIAltaProducto";
	private static GUIAltaProducto instancia;
	
	public GUIAltaProducto(){
		this.iniGUI();
	}	

	public synchronized static GUIAltaProducto getInstancia() {
		if (instancia == null) instancia = new GUIAltaProducto();
		return instancia;
	}
	
	public void iniGUI(){
		setLayout(null);
		this.setTitle("Alta producto");

		// MAIN PANEL
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		// OPEN PANEL
		JPanel panel_alta = new JPanel(new GridLayout(fila, 1));
		mainPanel.add(panel_alta);
		
		
		JLabel lNombre = new JLabel("Nombre: ");
		JTextField tNombre = new JTextField();
		tNombre.setPreferredSize(new Dimension(100,30));
		
		JPanel pNombre = new JPanel();
		pNombre.add(lNombre);
		pNombre.add(tNombre);
		panel_alta.add(pNombre);


		JLabel lPrecio =new JLabel("Precio: ");
		JTextField tPrecio = new JTextField();
		tPrecio.setPreferredSize(new Dimension(100,30));
		
		JPanel pPrecio = new JPanel();
		pPrecio.add(lPrecio);
		pPrecio.add(tPrecio);
		panel_alta.add(pPrecio);
		
		JLabel lStock =new JLabel("Stock: ");
		JTextField tStock = new JTextField();
		tStock.setPreferredSize(new Dimension(100,30));
		
		JPanel pStock = new JPanel();
		pStock.add(lStock);
		pStock.add(tStock);
		panel_alta.add(pStock);
		
		JLabel lIdAlm = new JLabel("Identificador del almacen: ");
		JTextField tIdAlm = new JTextField();
		tIdAlm.setPreferredSize(new Dimension(100,30));
		
		JPanel pIdAlm = new JPanel();
		pIdAlm.add(lIdAlm);
		pIdAlm.add(tIdAlm);
		panel_alta.add(pIdAlm);

		
		JButton button = new JButton("Alta");
		JPanel panelButton =new JPanel();
		
		panelButton.add(button);
		panel_alta.add(panelButton, BorderLayout.PAGE_END);	
		
    	JButton emptyButton = new JButton("Vaciar");
    	emptyButton.addActionListener((e) -> {
    		tNombre.setText("");
    		tPrecio.setText("");
    		tStock.setText("");
    		tIdAlm.setText("");
    	});
    	panelButton.add(emptyButton);
		
		button.addActionListener((e)->{
			
			if(tNombre.getText().isEmpty() || 
			tPrecio.getText().isEmpty() ||
			tStock.getText().isEmpty() ||
			tIdAlm.getText().isEmpty() 	
			){
				
				GUIMSG.showMessage("Faltan campos por rellenar", "ALTA PRODUCTO", true);
				
			}
			else{
				
				try{ 
					double precio = Double.parseDouble(tPrecio.getText());
					int stock = Integer.parseInt(tStock.getText());
					int idAlm = Integer.parseInt(tIdAlm.getText());
					String nombre = tNombre.getText();
					TProducto p = new TProducto(nombre, precio, stock, idAlm);
					
					Controlador.getInstancia().accion(new Context(Eventos.ALTA_PRODUCTO, p));
					
					this.setVisible(false);
		    		tNombre.setText("");
		    		tPrecio.setText("");
		    		tStock.setText("");
		    		tIdAlm.setText("");
					
				}catch(NumberFormatException nfe){
			
					GUIMSG.showMessage("Campos erroneos", "ALTA PRODUCTO", true);
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

	@Override
	public void update(Context context) {
		switch (context.getEvent()) {
		case ALTA_PRODUCTO_OK:
			GUIMSG.showMessage("Producto dada de alta correctamente \n", FROM_WHERE, false);
			break;
		case ALTA_PRODUCTO_KO:
			GUIMSG.showMessage(this.getErrorMsg((int)context.getDatos()), FROM_WHERE, true);
			break;
		default:
			GUIMSG.showMessage(Utils.RESPUESTA_NO_CONTEMPLADA, FROM_WHERE, true);
			break;
		}
	}

	@Override
	public void clear() {
	}

	@Override
	public String getErrorMsg(int error) {
		switch (error) {
		case SAProducto.ERROR_BBDD:
			return "No se ha podido actualizar el producto por error en la BBDD";
		case SAProducto.ERROR_SINTACTICO:
			return "No se ha podido actualizar el producto por error sintactico";
		case SAProducto.ALMACEN_INEXISTENTE:
			return "No se ha podido actualizar el producto porque el almacen asociado no existe";
		case SAProducto.ALMACEN_INACTIVO:
			return "No se ha podido actualizar el producto porque el almacen asociado esta inactivo";
		case SAProducto.PRODUCTO_ACTIVO:
			return "No se ha podido actualizar el producto porque esta activo";
		case SAProducto.PRODUCTO_INACTIVO:
			return "El producto estaba inactivo, queda reactivado";
		default:
			return Utils.RESPUESTA_NO_CONTEMPLADA;
		}
	}

	@Override
	public boolean areTextFieldsEmpty() {
		return false;
	}
	
}