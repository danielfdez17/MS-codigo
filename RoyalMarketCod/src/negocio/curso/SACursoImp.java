package negocio.curso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import negocio.empleado.ONEmpleado;
import negocio.empleado_curso.ONEmpleadoCurso;
import negocio.empleado_curso.ONEmpleadoCursoEmbed;
import negocio.entitymanagerfactory.SingletonEntityManager;
import negocio.factoriaNegocio.ComprobadorSintactico;

public class SACursoImp implements SACurso {

	ComprobadorSintactico CS=new ComprobadorSintactico();

	@Override
	public int altaCursoPresencial(TCursoPresencial tcp) {
		int res = ERROR_SINTACTICO;
		if (this.isPresencialValid(tcp)) {
			EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			try{
				TypedQuery<ONCurso> query = em.createNamedQuery("negocio.curso.ONCurso.findBynombre", ONCurso.class);
				query.setParameter("nombre", tcp.getNombre()); 
				List<ONCurso> data = query.getResultList();
				ONCurso exist = null;
				if (data != null && !data.isEmpty()) {
					exist = data.get(0);
					if (exist == null) {
						throw new Exception();
					}
					else {
						if (exist.isActivo()) {
							res = CURSO_ACTIVO;
							transaction.rollback();
						}
						else {
							exist.setActivo(true);
							((ONCursoPresencial) exist).setAula(tcp.getAula());
							exist.setHorasDia(tcp.getHorasDia());
							exist.setPlazas(tcp.getPlazas());
							exist.setNombre(tcp.getNombre());
							em.merge(exist);

							transaction.commit();
							res = exist.getId();
						}
					}
				}
				else {
					ONCursoPresencial cusoP = new ONCursoPresencial(tcp);
					em.persist(cusoP);
					transaction.commit();
					res = cusoP.getId(); // Obtener el ID generado por la base de datos
					tcp.setId(res);

				}
			}
			catch(Exception e){
				System.out.println(e.getMessage());
				res = ERROR_BBDD;
				transaction.rollback();
			} finally {
				em.close();
			}
		}
		return res;
	}

	@Override
	public int altaCursoOnline(TCursoOnline tco) {
		int res = ERROR_SINTACTICO;
		if (this.isOnlineValid(tco)) {
			EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			try{
				TypedQuery<ONCurso> query = em.createNamedQuery("negocio.curso.ONCurso.findBynombre", ONCurso.class);
				query.setParameter("nombre", tco.getNombre()); // Establecer el par�metro 'nombre' con el valor correspondiente
				List<ONCurso> data = query.getResultList();
				ONCurso exist = null;
				if (data != null && !data.isEmpty()) {
					exist = data.get(0);
					if (exist == null) {
						throw new Exception();
					}
					else {
						if (exist.isActivo()) {
							res = CURSO_ACTIVO;
							transaction.rollback();
						}
						else {
							exist.setActivo(true);
							((ONCursoOnline) exist).setEnlaceSesion(tco.getEnlaceSesion());
							exist.setHorasDia(tco.getHorasDia());
							exist.setPlazas(tco.getPlazas());
							exist.setNombre(tco.getNombre());
							em.merge(exist);
							transaction.commit();
							res = exist.getId();
						}
					}
				}
				else {
					ONCursoOnline cursoO = new ONCursoOnline(tco.getNombre(),tco.getPlazas(),tco.getHorasDia(),tco.getEnlaceSesion(),true);
					em.persist(cursoO);
					transaction.commit();
					res = cursoO.getId();
					tco.setId(res);
				}
			}
			catch(Exception e){
				res = ERROR_BBDD;
				transaction.rollback();
			} finally {
				em.close();
			}
		}
		return res;
	}

	@Override
	public TCurso buscarCurso(int id) {
		
		TCurso res = null;
		EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try{
			ONCurso curso = em.find(ONCurso.class, id, LockModeType.OPTIMISTIC);
			if(curso == null){
				throw new Exception ("Curso no encontrado");
			}
			transaction.commit();
			res = curso.toTransfer();
		}
		catch(Exception e){
			transaction.rollback();
		}
		finally{
			em.close();
		}
		return res;
	}

	@Override
	public List<TCurso> listarCursos() {
		List<TCurso> listTransCurso = new ArrayList<>();
		EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
		final TypedQuery<ONCurso> query = em.createNamedQuery("negocio.curso.ONCurso.findAll", ONCurso.class);
		final List<ONCurso> listCursos = query.getResultList(); 
		listTransCurso = listCursos.stream()
				.map(ONCurso::toTransfer)
				.collect(Collectors.toList());
		em.close();
		return listTransCurso;
	}

	@Override
	public List<TCurso> listarCursosPorEmpleado(int idEmpleado) {
		List<TCurso> listTransCurso = new ArrayList<>();
		EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		try {
			final TypedQuery<ONCurso> query = em.createNamedQuery("negocio.curso.ONCurso.findByIdEmpleado", ONCurso.class);
			query.setParameter("idEmpleado", idEmpleado);
			final List<ONCurso> listCurso = query.getResultList(); 
			if (listCurso.isEmpty()) 
				throw new Exception();
			listTransCurso = listCurso.stream()
					.peek(p -> em.lock(p, LockModeType.OPTIMISTIC))
					.map(p -> new TCurso(p.getId(), p.getNombre(),p.getPlazas(),p.getHorasDia() ,p.isActivo()))
					.collect(Collectors.toList());
			transaction.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			listTransCurso = new ArrayList<>();
			transaction.rollback();
		} finally {
			em.close();
		}
		return listTransCurso;
		
	}

	@Override
	public int actualizarCursoPresencial(TCursoPresencial tcp) {
		int res = ERROR_SINTACTICO;
		
		if (ComprobadorSintactico.isNombre(tcp.getNombre())) {
			EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			try {
				TypedQuery<ONCursoPresencial> query = em.createNamedQuery("negocio.curso.ONCursoPresencial.findByid", ONCursoPresencial.class);
				query.setParameter("id", tcp.getId());
				List<ONCursoPresencial> listCursoP = query.getResultList();
				ONCursoPresencial Exist=null;
				
				if (listCursoP != null && !listCursoP.isEmpty()) {
					Exist = listCursoP.get(0);
					if (Exist == null) {
						throw new Exception();
					}
					else {
//						TypedQuery<ONCursoPresencial> queryName = em.createNamedQuery("negocio.curso.ONCursoPresencial.findByname", ONCursoPresencial.class);
//						query.setParameter("nombre", tcp.getNombre());
//						List<ONCursoPresencial> listCursoName = queryName.getResultList();
//						if (listCursoName == null || listCursoName.isEmpty()) {
//							
//							TypedQuery<ONCursoPresencial> queryAula= em.createNamedQuery("negocio.curso.ONCursoPresencial.findByname", ONCursoPresencial.class);
//							query.setParameter("aula", tcp.getNombre());
//							List<ONCursoPresencial> listCursoEnlace = queryAula.getResultList();
							
							Exist.setActivo(true);
							Exist.setAula(tcp.getAula());
							Exist.setHorasDia(tcp.getHorasDia());
							Exist.setNombre(tcp.getNombre());
							Exist.setPlazas(tcp.getPlazas());
							em.merge(Exist);
							transaction.commit();
							res = tcp.getId();
						}
//						else
//						{
//							//if()
//							res = CURSO_DUPLICADO;
//						}
//					}
				} 
				else
				{
					res = CURSO_INEXISTENTE;
				}
					
			}
			catch(Exception e){
				res = ERROR_BBDD;
				transaction.rollback();
			} finally {
				em.close();
			}
		} 
		
		return res;
	}
	@Override
	public int actualizarCursoOnline(TCursoOnline tco) {
		int res = ERROR_SINTACTICO;
		
		if (ComprobadorSintactico.isNombre(tco.getNombre())) {
			EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			try {
				TypedQuery<ONCursoOnline> query = em.createNamedQuery("negocio.curso.ONCursoOnline.findByid", ONCursoOnline.class);
				query.setParameter("id", tco.getId());
				List<ONCursoOnline> listCursoP = query.getResultList();
				ONCursoOnline Exist=null;
				
				if (listCursoP != null && !listCursoP.isEmpty()) {
					Exist = listCursoP.get(0);
					if (Exist == null) {
						throw new Exception();
					}
					else {
						Exist.setActivo(true);
						Exist.setEnlaceSesion(tco.getEnlaceSesion());
						Exist.setHorasDia(tco.getHorasDia());
						Exist.setNombre(tco.getNombre());
						Exist.setPlazas(tco.getPlazas());
						em.merge(Exist);
						transaction.commit();
						res = tco.getId();
					}
				} 
				else
				{
					res = CURSO_INEXISTENTE;
				}
					
			}
			catch(Exception e){
				res = ERROR_BBDD;
				transaction.rollback();
			} finally {
				em.close();
			}
		} 
		
		return res;
	}

	@Override
	public int bajaCurso(int id) {
	
		int res = CURSO_INEXISTENTE;
		
		EntityManager em = SingletonEntityManager.getInstance().createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		
		ONCurso oncurso = em.find(ONCurso.class, id);
		if (oncurso != null) {
			if (oncurso.isActivo()) {
				TypedQuery<ONEmpleadoCurso> query = em.createNamedQuery("negocio.empleado_curso.ONEmpleadoCurso.findByoncurso", ONEmpleadoCurso.class);
				query.setParameter("id_curso", id);
				List<ONEmpleadoCurso> listEmployeeCurse = query.getResultList();
			
				boolean courseWithActiveEmployees = false;
				for (ONEmpleadoCurso o : listEmployeeCurse) {
					ONEmpleado onempleado = em.find(ONEmpleado.class, o.getEmpleado().getId(), LockModeType.OPTIMISTIC);
					if (onempleado.isActivo()) {
						courseWithActiveEmployees = true;
						break;
					}
				}
				if (courseWithActiveEmployees) {
					transaction.rollback();
					res = CURSO_CON_EMPLEADOS;
				} else {
					oncurso.setActivo(false);
					transaction.commit();
					res = id;
				}
				
				
//				if (oncurso.getEmpleados().isEmpty()) {
//					oncurso.setActivo(false);
//					em.persist(oncurso);
//					transaction.commit();
//					res = id;
//				}
//				else if (oncurso.getEmpleados() != null && !oncurso.getEmpleados().isEmpty()) {
//						res = CURSO_CON_EMPLEADOS;
//						transaction.rollback();
//				}
			}
			else {
				res = CURSO_INACTIVO;
				transaction.rollback();
			}
		}
		else {
			transaction.rollback();
		}
		em.close();
		return res;
	
	}
	
	private boolean isValid(TCurso tc) {
		return tc != null && ComprobadorSintactico.isNombre(tc.getNombre())
				&& tc.getPlazas() > 0 && tc.getHorasDia() > 0;
	}
	private boolean isOnlineValid(TCursoOnline tco) {
		return this.isValid(tco) && tco.getEnlaceSesion().length() > 0;
	}
	private boolean isPresencialValid(TCursoPresencial tcp) {
		return this.isValid(tcp) && tcp.getAula() > 0;
	}
	

}