package main;

import presentacion.controlador.Controlador;
import negocio.entitymanagerfactory.*;
import presentacion.controlador.Eventos;
import presentacion.factoriaVistas.Context;

public class Main {
	public static void main(String args[]) {
		SingletonEntityManager.getInstance();
		Controlador.getInstancia().accion(new Context(Eventos.GUI_MAIN, null));
	}
} 