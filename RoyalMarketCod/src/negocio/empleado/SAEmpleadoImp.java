package negocio.empleado;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import negocio.curso.ONCurso;
import negocio.curso.TCurso;
import negocio.empleado_curso.ONEmpleadoCurso;
import negocio.empleado_curso.ONEmpleadoCursoEmbed;
import negocio.empleado_curso.TEmpleadoCurso;
import negocio.entitymanagerfactory.SingletonEntityManager;
import negocio.oficina.ONOficina;
import negocio.oficina.TOficina;
import negocio.proyecto.ONProyecto;
import negocio.proyecto.TProyecto;
import negocio.factoriaNegocio.ComprobadorSintactico;
import negocio.material.ONMaterial;

public class SAEmpleadoImp implements SAEmpleado {

	@Override
	public int altaEmpleadoAdminsitrador(TEmpleadoAdministrador empleado) {
		int res = ERROR_SINTACTICO;;
			
			if (this.isValid(empleado)){
				
				EntityManagerFactory emf =  SingletonEntityManager.getInstance();
				EntityManager em = emf.createEntityManager();
				EntityTransaction et = em.getTransaction();

				et.begin();
				try {
					ONOficina onoficina = em.find(ONOficina.class, empleado.getIdOficina(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
					if (onoficina != null) {
						if (onoficina.isActivo()) {
							TypedQuery<ONEmpleado> query = em.createNamedQuery("negocio.empleado.ONEmpleado.findBynif", ONEmpleado.class);
							query.setParameter("nif", empleado.getNif());
							List<ONEmpleado> data = query.getResultList();
							ONEmpleado onadmin = data.isEmpty() ? null : data.get(0);
							if (onadmin == null) {
								onadmin = new EAdministrador(empleado);
								onadmin.setOficina(onoficina);
								onoficina.getEmpleados().add(onadmin);
								em.persist(onadmin);
								et.commit();
								res = onadmin.getId();
								empleado.setId(onadmin.getId());
							}
							else {
								if (onadmin.isActivo()) {
									res = EMPLEADO_ACTIVO;
									et.rollback();
								}
								else {
									onadmin.setActivo(true);
									onadmin.setNombre(empleado.getNombre());
									onadmin.setOficina(onoficina);
									empleado.setId(onadmin.getId());
									res = EMPLEADO_INACTIVO;
									et.commit();
								}
							}				
						}
						else {
							et.rollback();
							res = OFICINA_INACTIVA;
						}
					}
					else {
						et.rollback();
						res = OFICINA_INEXISTENTE;
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
	public int altaEmpleadoTecnico(TEmpleadoTecnico empleado) {
		int res = ERROR_SINTACTICO;;
		
		if (this.isValid(empleado)){
			
			EntityManagerFactory emf =  SingletonEntityManager.getInstance();
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();

			et.begin();
			try {
				ONOficina onoficina = em.find(ONOficina.class, empleado.getIdOficina(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				if (onoficina != null) {
					if (onoficina.isActivo()) {
						TypedQuery<ONEmpleado> query = em.createNamedQuery("negocio.empleado.ONEmpleado.findBynif", ONEmpleado.class);
						query.setParameter("nif", empleado.getNif());
						List<ONEmpleado> data = query.getResultList();
						ONEmpleado onadmin = data.isEmpty() ? null : data.get(0);
						if (onadmin == null) {
							onadmin = new ETecnico(empleado);
							onadmin.setOficina(onoficina);
							onoficina.getEmpleados().add(onadmin);
							em.persist(onadmin);
							et.commit();
							res = onadmin.getId();
							empleado.setId(onadmin.getId());
						}
						else {
							if (onadmin.isActivo()) {
								res = EMPLEADO_ACTIVO;
								et.rollback();
							}
							else {
								onadmin.setActivo(true);
								onadmin.setNombre(empleado.getNombre());
								onadmin.setOficina(onoficina);
								empleado.setId(onadmin.getId());
								res = EMPLEADO_INACTIVO;
								et.commit();
							}
						}				
					}
					else {
						et.rollback();
						res = OFICINA_INACTIVA;
					}
				}
				else {
					et.rollback();
					res = OFICINA_INEXISTENTE;
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
	public TEmpleado buscarEmpleado(int id) {
		TEmpleado res = null;
		
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			ONEmpleado onempleado = em.find(ONEmpleado.class, id);
			if (onempleado == null) {
				et.rollback();
			}
			else {
				res = onempleado.toTransfer();
				et.commit();
			}
		} catch(Exception e) {
			res = null;
		} finally {
			em.clear();
		}
		return res;
	}

	@Override
	public List<TEmpleado> listarEmpleados() {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		List<TEmpleado> res = new ArrayList<TEmpleado>();
		final TypedQuery<ONEmpleado> query = em.createNamedQuery("negocio.empleado.ONEmpleado.findAll", ONEmpleado.class);
		for (ONEmpleado o : query.getResultList()) 
			res.add(o.toTransfer());
		em.close();
		return res;
	}

	@Override
	public List<TEmpleado> listarEmpleadosPorCurso(int idCurso) {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		List<TEmpleado> res = new ArrayList<TEmpleado>();
		et.begin();
		try {
			ONCurso oncurso = em.find(ONCurso.class, idCurso, LockModeType.OPTIMISTIC);
			if (oncurso != null) {
				TypedQuery<ONEmpleado> query = em.createNamedQuery("negocio.empleado.ONEmpleado.findAllByCurso", ONEmpleado.class);
				query.setParameter("idCurso", idCurso);
				for (ONEmpleado o : query.getResultList()) {
					res.add(o.toTransfer());
				}
				et.commit();
			}
			else {
				et.rollback();
			}
		} catch (Exception e) {
			res = null;
		} finally {
			em.close();
		}
		return res;
	}

	@Override
	public List<TEmpleado> listarEmpleadosPorOficina(int idOficina) {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		List<TEmpleado> res = new ArrayList<TEmpleado>();
		et.begin();
		try {
			ONOficina onoficina = em.find(ONOficina.class, idOficina, LockModeType.OPTIMISTIC);
			if (onoficina != null) {
				for (ONEmpleado o : onoficina.getEmpleados()) {
					res.add(o.toTransfer());
				}
			}
		} catch (Exception e) {
			res = null;
		} finally {
			em.close();
		}
		return res;
	}

	@Override
	public List<TEmpleado> listarEmpleadosPorProyecto(int idProyecto) {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		List<TEmpleado> res = new ArrayList<TEmpleado>();
		et.begin();
		try {
			ONProyecto onproyecto = em.find(ONProyecto.class, idProyecto, LockModeType.OPTIMISTIC);
			if (onproyecto != null) {			
				for (ONEmpleado o : onproyecto.getEmpleados()) {
					res.add(o.toTransfer());
				}
			}
		} catch (Exception e) {
			res = null;
		} finally {
			em.close();
		}
		return res;
	}

	@Override
	public int vincularCurso(TEmpleadoCurso tec) {
		int res = -1;
		EntityManagerFactory emf =  SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			ONEmpleado onempleado = em.find(ONEmpleado.class, tec.getIdEmpleado());
			ONCurso oncurso = em.find(ONCurso.class, tec.getIdCurso());
			if (onempleado != null) {
				if (onempleado.isActivo()) {
					if (oncurso != null) {
						if (oncurso.isActivo()) {
							TypedQuery<ONEmpleadoCurso> query = em.createNamedQuery("negocio.empleado_curso.ONEmpleadoCurso.findByEmpAndCurso", ONEmpleadoCurso.class);
							query.setParameter("idEmpleado", tec.getIdEmpleado());
							query.setParameter("idCurso", tec.getIdCurso());
							if (query.getResultList().isEmpty()) {
								ONEmpleadoCurso onEmpCurs = new ONEmpleadoCurso(onempleado, oncurso, tec.getNivel());
								em.persist(onEmpCurs);
								et.commit();
								res = 1;
							}
							else {
								et.rollback();
								res = CURSO_YA_VINCULADO;
							}
						}
						else {
							et.rollback();
							res = CURSO_INACTIVO;
						}
					}
					else {
						et.rollback();
						res = CURSO_INEXISTENTE;
					}
				}
				else {
					et.rollback();
					res = EMPLEADO_INACTIVO;
				}
			}
			else {
				et.rollback();
				res = EMPLEADO_INEXISTENTE;
			}
		} catch (Exception e) {
			res = ERROR_INESPERADO;
		} finally {
			em.close();
		}
		
		return res;
	}

	@Override
	public int desvincularCurso(TEmpleadoCurso tec) {
		int idEmpleado = tec.getIdEmpleado(), idCurso = tec.getIdCurso();
		int res = CURSO_YA_DESVINCULADO;
		EntityManagerFactory emf =  SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			ONEmpleado onempleado = em.find(ONEmpleado.class, idEmpleado);
			ONCurso oncurso = em.find(ONCurso.class, idCurso);
			if (onempleado != null) {
				if (onempleado.isActivo()) {
					if (oncurso != null) {
						if (oncurso.isActivo()) {
							TypedQuery<ONEmpleadoCurso> query = em.createNamedQuery("negocio.empleado_curso.ONEmpleadoCursoEmbed.findByEmpAndCurso", ONEmpleadoCurso.class);
							query.setParameter("idEmpleado", idEmpleado);
							query.setParameter("idCurso", idCurso);
							if (query.getResultList().isEmpty()) {
								et.rollback();
								res = CURSO_YA_DESVINCULADO;
							}
							else {
								em.remove(query.getResultList().get(0));
								et.commit();
								res = 1;
							}
						}
						else {
							et.rollback();
							res = CURSO_INACTIVO;
						}
					}
					else {
						et.rollback();
						res = CURSO_INEXISTENTE;
					}
				}
				else {
					et.rollback();
					res = EMPLEADO_INACTIVO;
				}
			}
			else {
				et.rollback();
				res = EMPLEADO_INEXISTENTE;
			}
		} catch (Exception e) {
			res = ERROR_INESPERADO;
		} finally {
			em.close();
		}
		
		return res;
	}

	@Override
	public int vincularProyecto(TEmpleadoProyecto tep) {
		int idEmpleado = tep.getIdEmpleado(), idProyecto = tep.getIdProyecto();
		int res = PROYECTO_YA_VINCULADO;
		EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			ONEmpleado onempleado = em.find(ONEmpleado.class, idEmpleado);
			ONProyecto onproyecto = em.find(ONProyecto.class, idProyecto);
			if (onempleado != null) {
				if (onempleado.isActivo()) {
					if (onproyecto != null) {
						if (onproyecto.isActivo()) {
							if (onempleado.getProyectos().contains(onproyecto)) {
								et.rollback();
								res = PROYECTO_YA_VINCULADO;
							}
							else {
								onempleado.getProyectos().add(onproyecto);
								onproyecto.getEmpleados().add(onempleado);
								et.commit();
								res = 1;
							}
						}
						else {
							et.rollback();
							res = PROYECTO_INACTIVO;
						}
					}
					else {
						et.rollback();
						res = PROYECTO_INEXISTENTE;
					}
				}
				else {
					et.rollback();
					res = EMPLEADO_INACTIVO;
				}
			}
			else {
				et.rollback();
				res = EMPLEADO_INEXISTENTE;
			}
		} catch (Exception e) {
			
		} finally {
			em.close();
		}
		return res;
	}

	@Override
	public int desvincularProyecto(TEmpleadoProyecto tep) {
		int idEmpleado = tep.getIdEmpleado(), idProyecto = tep.getIdProyecto();
		int res = PROYECTO_YA_DESVINCULADO;
		EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			ONEmpleado onempleado = em.find(ONEmpleado.class, idEmpleado);
			ONProyecto onproyecto = em.find(ONProyecto.class, idProyecto);
			if (onempleado != null) {
				if (onempleado.isActivo()) {
					if (onproyecto != null) {
						if (onproyecto.isActivo()) {
							if (onempleado.getProyectos().contains(onproyecto)) {
								onempleado.getProyectos().remove(onproyecto);
								et.commit();
								res = 1;
							}
							else {
								et.rollback();
								res = PROYECTO_YA_DESVINCULADO;
							}
						}
						else {
							et.rollback();
							res = PROYECTO_INACTIVO;
						}
					}
					else {
						et.rollback();
						res = PROYECTO_INEXISTENTE;
					}
				}
				else {
					et.rollback();
					res = EMPLEADO_INACTIVO;
				}
			}
			else {
				et.rollback();
				res = EMPLEADO_INEXISTENTE;
			}
		} catch (Exception e) {
			
		} finally {
			em.close();
		}
		return res;
	}
	


	@Override
	public int actualizarEmpleadoAdministrador(TEmpleadoAdministrador empleado) {
		int res = ERROR_SINTACTICO;
		if (this.isValid(empleado)) {
			EntityManagerFactory emf = SingletonEntityManager.getInstance();
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			try {
				ONOficina onoficina = em.find(ONOficina.class, empleado.getId());
				if (onoficina != null) {
					if (onoficina.isActivo()) {
						ONEmpleado onempleado = em.find(ONEmpleado.class, empleado.getId());
						if (onempleado != null) {
							onempleado.setActivo(true);
							onempleado.setDireccion(empleado.getDireccion());
							onempleado.setNif(empleado.getNif());
							onempleado.setSueldo(empleado.getSueldo());
							onempleado.setTlf(empleado.getTlf());
							onempleado.setOficina(onoficina);
							((EAdministrador)onempleado).setReporteSemanal(empleado.getReporteSemanal());
							et.commit();
							res = onempleado.getId();
						}
						else {
							res = EMPLEADO_INEXISTENTE;
							et.rollback();
						}
					}
					else {
						et.rollback();
						res = OFICINA_INACTIVA;
					}
				}
				else {
					et.rollback();
					res = OFICINA_INEXISTENTE;
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
	public int actualizarEmpleadoTecnico(TEmpleadoTecnico empleado) {
		int res = ERROR_SINTACTICO;
		if (this.isValid(empleado)) {
			EntityManagerFactory emf = SingletonEntityManager.getInstance();
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			try {
				ONOficina onoficina = em.find(ONOficina.class, empleado.getId());
				if (onoficina != null) {
					if (onoficina.isActivo()) {
						ONEmpleado onempleado = em.find(ONEmpleado.class, empleado.getId());
						if (onempleado != null) {
							onempleado.setActivo(true);
							onempleado.setDireccion(empleado.getDireccion());
							onempleado.setNif(empleado.getNif());
							onempleado.setSueldo(empleado.getSueldo());
							onempleado.setTlf(empleado.getTlf());
							onempleado.setOficina(onoficina);
							((ETecnico)onempleado).setReporteTrabajo(empleado.getReporteTrabajo());
							et.commit();
							res = onempleado.getId();
						}
						else {
							res = EMPLEADO_INEXISTENTE;
							et.rollback();
						}
					}
					else {
						et.rollback();
						res = OFICINA_INACTIVA;
					}
				}
				else {
					et.rollback();
					res = OFICINA_INEXISTENTE;
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
	public int bajaEmpleado(int id) {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		int res = EMPLEADO_INEXISTENTE;
		try {
			ONEmpleado onempleado = em.find(ONEmpleado.class, id);
			if (onempleado != null) {
				if (onempleado.isActivo()) {
					// DEAL WITH MATERIALS
					boolean empWithActiveMats = false;
					for (ONMaterial o : onempleado.getMateriales()) {
						if (o.isActivo()) {
							em.lock(o, LockModeType.OPTIMISTIC);
							empWithActiveMats = true;
							break;
						}
					}
					if (empWithActiveMats) {
						et.rollback();
						res = EMPLEADO_CON_MATERIALES_ACTIVOS;
					}
					else {
						
						// REMOVE COURSES AND PROJECTS
						for (ONEmpleadoCurso o : onempleado.getCursos()) {
							em.remove(o);
						}
						onempleado.getProyectos().removeAll(onempleado.getProyectos());
						onempleado.setActivo(false);
						et.commit();
						res = id;
					}
				}
				else {
					et.rollback();
					res = EMPLEADO_INACTIVO;
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

	private boolean isValid(TEmpleado empleado) {
		return empleado != null && ComprobadorSintactico.isNombre(empleado.getNombre()) && ComprobadorSintactico.isNIF(empleado.getNif()) && ComprobadorSintactico.isDireccion(empleado.getDireccion());
	}

	@Override
	public int actualizarNivelCursoEmpleado(TEmpleadoCurso employeeWithCurse) {
		int success = SAEmpleado.ERROR_INESPERADO;
		EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			final Exception exception = new Exception();
			final ONEmpleado employee = em.find(ONEmpleado.class, employeeWithCurse.getIdEmpleado(), LockModeType.OPTIMISTIC);
			if (employee != null && !employee.isActivo()) {
				success = SAEmpleado.EMPLEADO_INACTIVO;
				throw exception;
			}

			final ONCurso curse = em.find(ONCurso.class, employeeWithCurse.getIdCurso(), LockModeType.OPTIMISTIC);
			if (employee != null && !curse.isActivo()) {
				success = SAEmpleado.CURSO_INACTIVO;
				throw exception;
			}
			final TypedQuery<ONEmpleadoCurso> query = em.createNamedQuery("negocio.empleado_curso.ONEmpleadoCurso.findByEmpAndCurso", ONEmpleadoCurso.class);
			query.setParameter("idEmpleado", employeeWithCurse.getIdEmpleado());			
			query.setParameter("idCurso", employeeWithCurse.getIdCurso());			
			final List<ONEmpleadoCurso> vinculate = query.getResultList();
			final ONEmpleadoCurso entityVinculate = vinculate.isEmpty() ? null : vinculate.get(0);
			em.lock(entityVinculate, LockModeType.OPTIMISTIC);
			if (entityVinculate == null) {
				success = SAEmpleado.CURSO_YA_DESVINCULADO;
				throw exception;
			}

			entityVinculate.setNivel(employeeWithCurse.getNivel());
			transaction.commit();
			success = 1;
		} catch (Exception e) {
			transaction.rollback();
		} finally {
			em.close();
		}

		return success;
	}

}