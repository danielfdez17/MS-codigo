package negocio.cliente;

public class TClienteNormal extends TCliente {
	private int id_tarjeta_normal;
	
	public TClienteNormal(int id, int id_tarjeta_normal, int tlf, String nif, String nombre, String direccion, boolean activo) {
		super(id, tlf, nif, nombre, direccion, activo);
		this.id_tarjeta_normal = id_tarjeta_normal;
	}
	public TClienteNormal(int id_tarjeta_normal, int tlf, String nif, String nombre, String direccion, boolean activo) {
		super(tlf, nif, nombre, direccion);
		this.setIdTarjetaNormal(id_tarjeta_normal);;
	}

	public int getIdTarjetaNormal() {
		return this.id_tarjeta_normal;
	}
	
	public void setIdTarjetaNormal(Integer id_tarjeta_normal) {
		this.id_tarjeta_normal = id_tarjeta_normal;
	}
	
	@Override
	public String toString() {
		return super.toString() + "ID tarjeta normal: " + this.id_tarjeta_normal + SALTO;
	}

}