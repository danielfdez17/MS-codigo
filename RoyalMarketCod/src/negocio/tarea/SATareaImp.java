package negocio.tarea;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import negocio.entitymanagerfactory.SingletonEntityManager;
import negocio.factoriaNegocio.ComprobadorSintactico;
import negocio.proyecto.ONProyecto;

public class SATareaImp implements SATarea {
	
	@Override
	public int altaTarea(TTarea tt) {
		int res = ERROR_SINTACTICO;
		if (this.isValid(tt)) {
			EntityManagerFactory emf = SingletonEntityManager.getInstance();
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			try {
				TypedQuery<ONTarea> query = em.createNamedQuery("negocio.tarea.ONTarea.findBynombre", ONTarea.class);
//				TypedQuery<ONTarea> query = em.createQuery("SELECT t FROM ontarea WHERE t.nombre = '" + tt.getNombre() + "'", ONTarea.class);
				query.setParameter("nombre", tt.getNombre());
				List<ONTarea> data = query.getResultList();
				ONTarea ontarea = data.isEmpty() ? null : data.get(0);
				ONProyecto onproyecto = em.find(ONProyecto.class, tt.getIdProyecto(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				if (ontarea == null) {
					if (onproyecto != null) {
						if (onproyecto.isActivo()) {
							ontarea = new ONTarea(tt);
							ontarea.setProyecto(onproyecto);
							onproyecto.getTask().add(ontarea);
							em.persist(ontarea);
							et.commit();
							res = ontarea.getId();
							tt.setId(ontarea.getId());
						}
						else {
							res = PROYECTO_INACTIVO;
							et.rollback();
						}
					}
					else {
						res = PROYECTO_INEXISTENTE;
						et.rollback();
					}
				}
				else {
					if (ontarea.isActivo()) {
						res = TAREA_ACTIVA;
						et.rollback();
					}
					else {
						ontarea.setActivo(true);
						ontarea.setNombre(tt.getNombre());
						ontarea.setProyecto(onproyecto);
						em.persist(ontarea);
						tt.setId(ontarea.getId());
						res = TAREA_INACTIVA;
						et.commit();
					}
				}				
			} catch (Exception e) {
				res = ERROR_INESPERADO;
			} finally {
				em.close();
			}
		}
		return res;
	}

	@Override
	public TTarea buscarTarea(int id) {
		TTarea tarea = null;
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			ONTarea ontarea = em.find(ONTarea.class, id, LockModeType.OPTIMISTIC);
			if (ontarea != null) {
				tarea = ontarea.toTransfer();
				et.commit();
			}
			else {
				et.rollback();
			}
		} catch(Exception e) {
			tarea = null;
		} finally {
			em.clear();
		}
		return tarea;
	}

	@Override
	public List<TTarea> listarTareas() {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		List<TTarea> res = new ArrayList<TTarea>();
		final TypedQuery<ONTarea> query = em.createNamedQuery("negocio.tarea.ONTarea.findAll", ONTarea.class);
		for (ONTarea o : query.getResultList()) 
			res.add(o.toTransfer());
		em.close();
		return res;
	}

	@Override
	public List<TTarea> listarTareasPorProyecto(int idProyecto) {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		List<TTarea> res = new ArrayList<TTarea>();
		et.begin();
		try {
			ONProyecto onproyecto = em.find(ONProyecto.class, idProyecto, LockModeType.OPTIMISTIC);
			if (onproyecto != null) {			
				TypedQuery<ONTarea> query = em.createNamedQuery("negocio.tarea.ONTarea.findAllByProyecto", ONTarea.class);
				query.setParameter("idProyecto", idProyecto);
				for (ONTarea o : query.getResultList()) 
					res.add(o.toTransfer());
			}
		} catch (Exception e) {
			res = null;
		} finally {
			em.close();
		}
		return res;
	}

	@Override
	public int actualizarTarea(TTarea tarea) {
		int res = ERROR_SINTACTICO;
		if (this.isValid(tarea)) {
			EntityManagerFactory emf = SingletonEntityManager.getInstance();
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			try {
				ONTarea ontarea = em.find(ONTarea.class, tarea.getId());
				ONProyecto onproyecto = em.find(ONProyecto.class, tarea.getIdProyecto(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				if (ontarea != null) {
					if (onproyecto != null) {
						if (onproyecto.isActivo()) {					
							ontarea.setNombre(tarea.getNombre());
							ontarea.setActivo(true);
							em.persist(ontarea);
							res = ontarea.getId();
							et.commit();
						}
						else {
							res = PROYECTO_INACTIVO;
							et.rollback();
						}
					}
					else {
						res = PROYECTO_INEXISTENTE;
						et.rollback();
					}
				}
				else {
					res = TAREA_INEXISTENTE;
					et.rollback();
				}
			} catch (Exception e) {
				res = ERROR_INESPERADO;
			} finally {
				em.close();
			}
		}
		return res;
	}

	@Override
	public int bajaTarea(int id) {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		int res = TAREA_INEXISTENTE;
		try {
			ONTarea ontarea = em.find(ONTarea.class, id);
			if (ontarea != null) {
				TTarea tt = ontarea.toTransfer();
				if (tt.isActivo()) {
					ontarea.setActivo(false);
					em.persist(ontarea);
					res = ontarea.getId();
					et.commit();
				}
				else {
					res = TAREA_INACTIVA;
					et.rollback();
				}
			}
			else {
				et.rollback();
			}
		} catch (Exception e) {
			res = ERROR_INESPERADO;
		} finally {
			em.close();
		}
		return res;
	}
	
	private boolean isValid(TTarea tarea) {
		return tarea != null && ComprobadorSintactico.isNombre(tarea.getNombre()) && tarea.getIdProyecto() > 0;
	}
	
}