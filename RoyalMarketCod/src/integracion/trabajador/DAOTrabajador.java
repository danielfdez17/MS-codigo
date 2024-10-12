package integracion.trabajador;

import negocio.trabajador.TTrabajador;
import negocio.trabajador.TTrabajadorJCompleta;
import negocio.trabajador.TTrabajadorJParcial;

import java.util.List;


public interface DAOTrabajador {
	public int altaTrabajadorJCompleta(TTrabajadorJCompleta ttjc);
	public int altaTrabajadorJParcial(TTrabajadorJParcial ttjp);
	public TTrabajador buscarTrabajador(int id);
	public TTrabajador buscarTrabajadorPorNIF(String nif);
	public List<TTrabajador>listarTrabajadores();
	public List<TTrabajador>listarTrabajadoresPorAlmacen(int idAlmacen);
	public int actualizarTrabajadorJCompleta(TTrabajadorJCompleta ttjc);
	public int actualizarTrabajadorJParcial(TTrabajadorJParcial ttjp);
	public int bajaTrabajador(int id);
}