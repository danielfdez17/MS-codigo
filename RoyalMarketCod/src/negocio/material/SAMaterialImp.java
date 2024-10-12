package negocio.material;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import negocio.empleado.ONEmpleado;
import negocio.factoriaNegocio.ComprobadorSintactico;
import negocio.entitymanagerfactory.SingletonEntityManager;

public class SAMaterialImp implements SAMaterial {

	@Override
	public int altaMaterial(TMaterial material) {
		int res = ERROR_SINTACTICO;
		if(this.isValid(material)) {
			EntityManagerFactory emf = SingletonEntityManager.getInstance();
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			try {
				ONMaterial onMaterial = null;
				ONEmpleado onEmpleado = em.find(ONEmpleado.class, material.getIdEmpleado(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
				if(onEmpleado != null) {
					if(onEmpleado.isActivo()) {
						TypedQuery<ONMaterial> query = em.createNamedQuery("negocio.material.ONMaterial.findBynombre", ONMaterial.class);
						query.setParameter("nombre", material.getNombre());
						List<ONMaterial> mat = query.getResultList();
						if(mat.isEmpty()) {
							onMaterial = new ONMaterial(material);
							onMaterial.setEmpleado(onEmpleado);
							em.persist(onMaterial);
							et.commit();
							res = onMaterial.getId();
							material.setId(res);
						}
						else {
							onMaterial = mat.get(0); 
							if(onMaterial.isActivo()) {
								res = MATERIAL_ACTIVO;
								et.rollback(); 
							}
							else {
								onMaterial.setActivo(true);
								onMaterial.setNombre(material.getNombre());
								onMaterial.setEmpleado(onEmpleado);
								em.merge(onMaterial);
								et.commit();
								res = MATERIAL_INACTIVO;
							}
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
		}
		return res;
	}

	@Override
	public TMaterial buscarMaterial(int id) {
		TMaterial res = null;
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			ONMaterial result = em.find(ONMaterial.class, id, LockModeType.OPTIMISTIC);
			if(result != null) {
				res = result.toTransfer();
				et.commit();
			}
			else 
				et.rollback();
		} catch (Exception e) {
			res = null;
		} finally {
			em.close();
		}
		return res;
	}

	@Override
	public List<TMaterial> listarMateriales() {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();

		List<ONMaterial> result = em.createNamedQuery("negocio.material.ONMaterial.findAll", ONMaterial.class).getResultList();
		List<TMaterial> res = new ArrayList<>();
		for(int i = 0; i < result.size(); i++){
			res.add(result.get(i).toTransfer());
		}

		em.close();
		return res;
	}

	@Override
	public List<TMaterial> listarMaterialesPorEmpleado(int idEmpleado) {
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		List<TMaterial> res = new ArrayList<>();
		et.begin();
		try {
			ONEmpleado onEmpleado = em.find(ONEmpleado.class, idEmpleado, LockModeType.OPTIMISTIC);
			
			if(onEmpleado != null && onEmpleado.isActivo()) {
				for(ONMaterial onMat : onEmpleado.getMateriales()) {
					em.lock(onMat, LockModeType.OPTIMISTIC);
					res.add(onMat.toTransfer());
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
	public int actualizarMaterial(TMaterial material) {
		int res = ERROR_SINTACTICO;
		if (this.isValid(material)) {
			EntityManagerFactory emf =  SingletonEntityManager.getInstance();
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			try {
				ONMaterial onmaterial = em.find(ONMaterial.class, material.getId());
				ONEmpleado onempleado = em.find(ONEmpleado.class, material.getIdEmpleado());
				if (onempleado != null) {
					if (onempleado.isActivo()) {
						if (onmaterial != null) {
							onmaterial.setActivo(true);
							onmaterial.setNombre(material.getNombre());
							onmaterial.setEmpleado(onempleado);
							et.commit();
							res = onmaterial.getId();
						}
						else {
							et.rollback();
							res = MATERIAL_INEXISTENTE;
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
		}
		return res;
	}

	@Override
	public int bajaMaterial(int id) {
		int idMaterial = MATERIAL_INEXISTENTE;
		EntityManagerFactory emf = SingletonEntityManager.getInstance();
		EntityManager em = emf.createEntityManager();
		EntityTransaction et = em.getTransaction();
		et.begin();
		try {
			ONMaterial onMaterial = em.find(ONMaterial.class, id);
			if(onMaterial != null) {
				if(onMaterial.isActivo()) {
					onMaterial.setActivo(false);
					et.commit();
					idMaterial = id;
				}
				else {
					et.rollback();
					idMaterial = MATERIAL_INACTIVO;
				}
			}
			else {
				et.rollback();
				idMaterial = MATERIAL_INEXISTENTE;
			}
			
		} catch (Exception e) {
			idMaterial = ERROR_INESPERADO;
		} finally {
			em.close();
		}
		
		return idMaterial;
	}
	
	private boolean isValid(TMaterial material) {
		return material != null && ComprobadorSintactico.isNombre(material.getNombre()) && material.getIdEmpleado() > 0;
	}
	
}