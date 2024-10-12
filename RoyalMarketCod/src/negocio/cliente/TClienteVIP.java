/**
 * 
 */
package negocio.cliente;

public class TClienteVIP extends TCliente {
	private int id_tarjeta_vip;
	
	public TClienteVIP(int id, int id_tarjeta_vip, int tlf, String nif, String nombre, String direccion, boolean activo) {
		super(id, tlf, nif, nombre, direccion, activo);
		this.id_tarjeta_vip = id_tarjeta_vip;
	}
	public TClienteVIP(int id_tarjeta_vip, int tlf, String nif, String nombre, String direccion, boolean activo) {
		super(tlf, nif, nombre, direccion);
		this.setIdTarjetaVIP(id_tarjeta_vip);
	}

	public int getIdTarjetaVIP() {
		return this.id_tarjeta_vip;
	}

	public void setIdTarjetaVIP(Integer id_tarjeta_vip) {
		this.id_tarjeta_vip = id_tarjeta_vip;
	}

	
	@Override
	public String toString() {
		return super.toString() + "ID tarjeta VIP: " + this.id_tarjeta_vip + SALTO;
	}

	
}