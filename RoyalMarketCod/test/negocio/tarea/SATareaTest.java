package negocio.tarea;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import negocio.factoriaNegocio.FactoriaNegocio;
import negocio.proyecto.SAProyecto;
import negocio.proyecto.TProyecto;
import negocio.tarea.SATarea;
import negocio.tarea.TTarea;

public class SATareaTest {
	
	private SATarea saTarea;
	private SAProyecto saProyecto;
	
	@Before
	public void setUp() {
		FactoriaNegocio fn = FactoriaNegocio.getInstancia();
		this.saTarea = fn.crearSATarea();
		this.saProyecto = fn.crearSAProyecto();
	}
	
	@Test
	public void altaOK() {
		TProyecto proyecto = new TProyecto("proyectoAltaTareaOK");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea = new TTarea("tareaAltaTareaOK", proyecto.getId());
		assertTrue(this.saTarea.altaTarea(tarea) > 0);
	}
	
	@Test
	public void altaKOProyectoInactivo() {
		TProyecto proyecto = new TProyecto("proyectoInacticoAltaTareaKO");
		this.saProyecto.altaProyecto(proyecto);
		this.saProyecto.bajaProyecto(proyecto.getId());
		TTarea tarea = new TTarea("tareaAltaTareaKOProyectoInactivo", proyecto.getId());
		assertTrue(this.saTarea.altaTarea(tarea) == SATarea.PROYECTO_INACTIVO);
	}
	@Test
	public void altaKOProyectoInexistente() {
		TTarea tarea = new TTarea("tareaAltaTareaKOProyectoInexistente", 1000);
		assertTrue(this.saTarea.altaTarea(tarea) == SATarea.PROYECTO_INEXISTENTE);
	}
	@Test
	public void altaKOTareaActiva() {
		TProyecto proyecto = new TProyecto("proyectoAltaTareaKOTareaActiva");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea = new TTarea("tareaAltaTareaKOTareaActiva", proyecto.getId());
		this.saTarea.altaTarea(tarea);
		assertTrue(this.saTarea.altaTarea(tarea) == SATarea.TAREA_ACTIVA);
	}
	@Test
	public void altaKOTareaInactiva() {
		TProyecto proyecto = new TProyecto("proyectoAltaTareaKOTareaInactiva");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea = new TTarea("tareaAltaTareaKOTareaInactiva", proyecto.getId());
		this.saTarea.altaTarea(tarea);
		this.saTarea.bajaTarea(tarea.getId());
		assertTrue(this.saTarea.altaTarea(tarea) == SATarea.TAREA_INACTIVA);
	}
	
	@Test
	public void buscarOK() {
		TProyecto proyecto = new TProyecto("proyectoBuscarTareaOK");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea = new TTarea("tareaBuscarTareaKO", proyecto.getId());
		this.saTarea.altaTarea(tarea);
		assertTrue(this.saTarea.buscarTarea(tarea.getId()) != null);
	}

	@Test
	public void buscarKO() {
		assertTrue(this.saTarea.buscarTarea(-1) == null);
	}
	
	@Test
	public void listarTareaOK() {
		TProyecto proyecto = new TProyecto("proyectoListarTareas");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea = new TTarea("tareaListarTareas", proyecto.getId());
		this.saTarea.altaTarea(tarea);
		List<TTarea> res = this.saTarea.listarTareas();
		assertTrue(res != null && !res.isEmpty());
		
	}
	
	@Test
	public void listarPorProyectoOK() {
		TProyecto proyecto = new TProyecto("proyectoListarTareasPorProyectoOK");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea1 = new TTarea("tareaUno", proyecto.getId());
		TTarea tarea2 = new TTarea("tareaDos", proyecto.getId());
		this.saTarea.altaTarea(tarea1);
		this.saTarea.altaTarea(tarea2);
		List<TTarea> res = this.saTarea.listarTareasPorProyecto(proyecto.getId());
		assertTrue(res != null && !res.isEmpty());
	}
	
	@Test
	public void listarPorProyectoKO() {
		assertTrue(this.saTarea.listarTareasPorProyecto(-1).isEmpty());
	}
	
	@Test
	public void actualizarTareaOK() {
		TProyecto proyecto = new TProyecto("proyectoActualizarTareaOK");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea = new TTarea("tareaActualizarOK", proyecto.getId());
		this.saTarea.altaTarea(tarea);
		assertTrue(this.saTarea.actualizarTarea(tarea) > 0);
	}
	
	@Test
	public void actualizarTareaKOErrorSintactico() {
		TProyecto proyecto = new TProyecto("proyectoActualizarTareaKOErrorSintactico");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea = new TTarea("tareaActualizarKOErrorSintactico1", proyecto.getId());
		this.saTarea.altaTarea(tarea);
		assertTrue(this.saTarea.actualizarTarea(tarea) == SATarea.ERROR_SINTACTICO);
	}
	
	@Test
	public void actualizarTareaKOTareaInexistente() {
		assertTrue(this.saTarea.actualizarTarea(new TTarea("actualizarTareaKOTareaInexistente", 15)) == SATarea.TAREA_INEXISTENTE);
	}
	
	@Test
	public void actualizarTareaKOProyectoInexistente() {
		TProyecto proyecto = new TProyecto("actualizarTareaKOProyectoInexistente");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea = new TTarea("actualizarTareaKOProyectoInexistente", proyecto.getId());
		this.saTarea.altaTarea(tarea);
		tarea.setIdProyecto(1000);
		assertTrue(this.saTarea.actualizarTarea(tarea) == SATarea.PROYECTO_INEXISTENTE);
	}

	@Test
	public void actualizarTareaKOProyectoInactivo() {
		TProyecto proyecto = new TProyecto("actualizarTareaKOProyectoInactivo");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea = new TTarea("actualizarTareaKOProyectoInactivo", proyecto.getId());
		this.saTarea.altaTarea(tarea);
		this.saTarea.bajaTarea(tarea.getId());
		this.saProyecto.bajaProyecto(proyecto.getId());
		assertTrue(this.saTarea.actualizarTarea(tarea) == SATarea.PROYECTO_INACTIVO);
	}
	
	@Test
	public void bajaOK() {
		TProyecto proyecto = new TProyecto("bajaTareaOK");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea = new TTarea("bajaTareaOK", proyecto.getId());
		this.saTarea.altaTarea(tarea);
		assertTrue(this.saTarea.bajaTarea(tarea.getId()) == tarea.getId());
	}
	
	@Test
	public void bajaKOTareaInexistente() {
		assertTrue(this.saTarea.bajaTarea(-1) == SATarea.TAREA_INEXISTENTE);
	}
	
	@Test
	public void bajaKOTareaInactiva() {
		TProyecto proyecto = new TProyecto("bajaTareaKOTareaInactiva");
		this.saProyecto.altaProyecto(proyecto);
		TTarea tarea = new TTarea("bajaTareaKOTareaInactiva", proyecto.getId());
		this.saTarea.altaTarea(tarea);
		this.saTarea.bajaTarea(tarea.getId());
		assertTrue(this.saTarea.bajaTarea(tarea.getId()) == SATarea.TAREA_INACTIVA);
	}
	
	
}
