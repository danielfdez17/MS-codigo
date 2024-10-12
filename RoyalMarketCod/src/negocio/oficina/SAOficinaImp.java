package negocio.oficina;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import negocio.empleado.ONEmpleado;
import negocio.entitymanagerfactory.SingletonEntityManager;
import negocio.factoriaNegocio.ComprobadorSintactico;

public class SAOficinaImp implements SAOficina {

	
	@Override
	public int altaOficina(TOficina tof) {
		int res = ERROR_SINTACTICO;
		if (this.isValid(tof)) {
			EntityManagerFactory emf =  SingletonEntityManager.getInstance();
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			ONOficina onoficina = null;
			TypedQuery<ONOficina> query = em.createNamedQuery("negocio.oficina.ONOficina.findBynombre", ONOficina.class);
			query.setParameter("nombre", tof.getNombre());
			try {
				if (query.getResultList().isEmpty()) {
					onoficina = new ONOficina(tof);
					em.persist(onoficina);
					et.commit();
					res = onoficina.getId();
					tof.setId(res);
				}
				else {
					onoficina = query.getResultList().get(0);
					if (onoficina.isActivo()) {
						et.rollback();
						res = OFICINA_ACTIVA;
					}
					else {
						onoficina.setNombre(tof.getNombre());
						et.commit();
						res = OFICINA_INACTIVA;
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
	public TOficina buscarOficina(int id) {
		TOficina res = null;
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		ONOficina result = em.find(ONOficina.class, id, LockModeType.OPTIMISTIC);
		try {
			if (result == null) {
				et.rollback();
			}
			else {
				et.commit();
				res = result.toTransfer();
			}
		} catch (Exception e) {
			res = null;
		} finally {
			em.close();
		}
		return res;
	}


	@Override
	public List<TOficina> listarOficinas() {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		List<ONOficina> result = em.createNamedQuery("negocio.oficina.ONOficina.findAll", ONOficina.class).getResultList();
		List<TOficina> res = new ArrayList<>();
		for (ONOficina o : result) {
			res.add(o.toTransfer());
		}
		em.close();
		return res;
	}

	@Override
	public double mostrarNomina(int id) {
		double total = 0;
		EntityManagerFactory emf =  SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			ONOficina oficina = em.find(ONOficina.class, id, LockModeType.OPTIMISTIC);
			if (oficina == null) {
				et.rollback();
				total = OFICINA_INEXISTENTE;
			}
			else {
				if (oficina.isActivo()) {
					for (ONEmpleado emp : oficina.getEmpleados()) {
						em.lock(emp, LockModeType.OPTIMISTIC);
						total += emp.getNomina(); 
					}
					et.commit();
				}
				else {
					et.rollback();
					total = 0;
				}
			}
		} catch (Exception e) {
			total = ERROR_INESPERADO;
		} finally {
			em.close();
		}
		
		return total;
	}

	@Override
	public int actualizarOficina(TOficina tof) {
		int res = ERROR_SINTACTICO;
		if (this.isValid(tof)) {
			EntityManagerFactory emf =  SingletonEntityManager.getInstance();
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			try {
				ONOficina onoficina = em.find(ONOficina.class, tof.getId());
				if (onoficina == null) {
					et.rollback();
					res = OFICINA_INEXISTENTE;
				}
				else {
					onoficina.setActivo(true);
					onoficina.setNombre(tof.getNombre());
					et.commit();
					res = onoficina.getId();
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
	public int bajaOficina(int id) { 
		int res = OFICINA_ACTIVA;
		EntityManagerFactory emf =  SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			ONOficina oficina = em.find(ONOficina.class, id);
			if (oficina == null) {
				et.rollback();
				res = OFICINA_INEXISTENTE;
			}
			else {
				if (oficina.isActivo()) {
					boolean activos = false;
					for (ONEmpleado o : oficina.getEmpleados()) {
						if (o.isActivo()) {
							activos = true;
							break;
						}
					}
					if (activos) {
						et.rollback();
						res = ERROR_OFICINA_CON_EMPLEADOS_ACTIVOS;
					}
					else {
						oficina.setActivo(false);
						for (ONEmpleado o : oficina.getEmpleados())
							oficina.getEmpleados().remove(o);
						et.commit();
						res = id;
					}
				}
				else {
					et.rollback();
					res = OFICINA_INACTIVA;
				}
			}
		} catch(Exception e) {
			res = ERROR_INESPERADO;
		} finally {
			em.close();
		}
		return res;
	}
	
	private boolean isValid(TOficina tof) {
		return ComprobadorSintactico.isNombre(tof.getNombre());
	}

}