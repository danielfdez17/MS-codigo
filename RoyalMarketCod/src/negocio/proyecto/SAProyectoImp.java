package negocio.proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import negocio.entitymanagerfactory.SingletonEntityManager;
import negocio.factoriaNegocio.ComprobadorSintactico;

public class SAProyectoImp implements SAProyecto {

	@Override
	public int altaProyecto(TProyecto proyecto) {
		int success = ERROR_INESPERADO;
		
		if (ComprobadorSintactico.isNombre(proyecto.getNombre())) {
			EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			try {
				TypedQuery<ONProyecto> query = em.createNamedQuery("negocio.proyecto.ONProyecto.findBynombre", ONProyecto.class);
				query.setParameter("nombre", proyecto.getNombre());
				List<ONProyecto> data = query.getResultList();
				ONProyecto proyectExist = data.isEmpty() ? null 
														 : data.get(0); 						
				if (proyectExist != null) 	
					throw new Exception();
				
				ONProyecto saveProject = new ONProyecto(proyecto.getNombre());
				em.persist(saveProject);
				transaction.commit();
				success = saveProject.getId();
				proyecto.setId(saveProject.getId());
				proyecto.setActivo(saveProject.isActivo());
			} catch (Exception e) {
				success = ERROR_PROYECTO_DUPLICADO;	
				transaction.rollback();
			} finally {
				em.close();
			}
			
		} else {
			success = ERROR_SINTACTICO_NOMBRE_PROYECTO;
		}
		
		
		return success;
	}

	@Override
	public TProyecto buscarProyecto(int id) {
		TProyecto transferProject = null;
		EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			ONProyecto project = em.find(ONProyecto.class, id, LockModeType.OPTIMISTIC);
			if (project == null)
				throw new Exception("Proyecto no encontrado");
			
			transaction.commit();
			transferProject = project.toTransfer(); 
		} catch (Exception e) {
			transaction.rollback();
		} finally {
			em.close();
		}
		return transferProject;
	}

	@Override
	public List<TProyecto> listarProyectos() {
		List<TProyecto> listTransferProject = new ArrayList<>();
		EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
		final TypedQuery<ONProyecto> query = em.createNamedQuery("negocio.proyecto.ONProyecto.findAllOnlyActive", ONProyecto.class);
		final List<ONProyecto> listProjects = query.getResultList(); 
		listTransferProject = listProjects.stream()
				.map(ONProyecto::toTransfer)
				.collect(Collectors.toList());

		em.close();
		return listTransferProject;
	}

	@Override
	public List<TProyecto> listarProyectosPorEmpleado(int idEmpleado) {
		List<TProyecto> listTransferProject = new ArrayList<>();
		EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			final TypedQuery<ONProyecto> query = em.createNamedQuery("negocio.proyecto.ONProyecto.findByIdEmpleado", ONProyecto.class);
			query.setParameter("idEmpleado", idEmpleado);
			final List<ONProyecto> listProjects = query.getResultList(); 
			if (listProjects.isEmpty()) 
				throw new Exception();
			listTransferProject = listProjects.stream()
					.peek(p -> em.lock(p, LockModeType.OPTIMISTIC))
					.map(ONProyecto::toTransfer)
					.collect(Collectors.toList());
			transaction.commit();
		} catch (Exception e) {
			listTransferProject = new ArrayList<>();
			transaction.rollback();
		} finally {
			em.close();
		}
		return listTransferProject;
	}

	@Override
	public int actualizarProyecto(TProyecto proyecto) {
		int success = ERROR_INESPERADO;
		if (ComprobadorSintactico.isNombre(proyecto.getNombre())) {
			EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			try {
				TypedQuery<ONProyecto> query = em.createNamedQuery("negocio.proyecto.ONProyecto.findProjectByIdAndNotExistOtheProjectWithNameSame", ONProyecto.class);
				query.setParameter("projectId", proyecto.getId());
				query.setParameter("nameProject", proyecto.getNombre());
				List<ONProyecto> listProject = query.getResultList();
				ONProyecto projectModify = listProject.isEmpty()
											? null
											: listProject.get(0);
				if (projectModify == null) 
					throw new Exception();
				
				projectModify.setNombre(proyecto.getNombre());
				em.merge(projectModify);
				transaction.commit();
				success = projectModify.getId();
			} catch (Exception e) {
				success = ERROR_PROYECTO_DUPLICADO;
				transaction.rollback();
			} finally {
				em.close();
			}
		} else {
			success = ERROR_SINTACTICO_NOMBRE_PROYECTO;
		}
		
		return success;
	}

	@Override
	public int bajaProyecto(int id) {
		int success = ERROR_INESPERADO;
		EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			final TypedQuery<ONProyecto> query = em.createNamedQuery("negocio.proyecto.ONProyecto.getEmployeeActiveAndTaskActive", ONProyecto.class);
			query.setParameter("idProject", id);
			final List<ONProyecto> listProject = query.getResultList();
			final ONProyecto projectWithEmployeeAndTaskActive = listProject.isEmpty() 
																 ? null
																 : listProject.get(0);
			final Exception exception = new Exception();
			if (projectWithEmployeeAndTaskActive == null) {
				success = ERROR_PROYECTO_DADO_DE_BAJA;
				throw exception; 	
			}

			final boolean allEmployeeInactive = projectWithEmployeeAndTaskActive.getEmpleados()
			        	.stream()
			        	.peek(e -> em.lock(e, LockModeType.OPTIMISTIC))
			        	.allMatch(e -> !e.isActivo());
			if (!allEmployeeInactive) {
				success = ERROR_PROYECTO_CON_EMPLEADOS_ACTIVOS;
				throw exception; 				
			}
			final boolean allTaskInactive = projectWithEmployeeAndTaskActive
					.getTask()
					.stream()
					.peek(t -> em.lock(t, LockModeType.OPTIMISTIC))
					.allMatch(t -> !t.isActivo());
			
			if (!allTaskInactive) {
				success = ERROR_PROYECTO_CON_TAREAS_ACTIVAS;
				throw exception; 
			}
			
			projectWithEmployeeAndTaskActive.setActivo(false);
			em.merge(projectWithEmployeeAndTaskActive);
			transaction.commit();
			success = id;
		} catch (Exception e) {
			transaction.rollback();
		} finally {
			em.close();
		}
	
		return success;
	}
	
}