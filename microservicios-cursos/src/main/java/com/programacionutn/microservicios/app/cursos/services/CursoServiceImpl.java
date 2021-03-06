 package com.programacionutn.microservicios.app.cursos.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programacionutn.microservicios.app.cursos.clients.AlumnoFeignCliente;
import com.programacionutn.microservicios.app.cursos.clients.RespuestaFeignClients;
import com.programacionutn.microservicios.app.cursos.models.entity.Curso;
import com.programacionutn.microservicios.app.cursos.models.repository.CursoRepository;
import com.programacionutn.microservicios.commons.alumnos.models.entity.Alumno;
import com.programacionutn.microservicios.commons.services.CommonServiceImpl;

@Service
public class CursoServiceImpl extends CommonServiceImpl<Curso, CursoRepository> implements CursoService {

	@Autowired
	private RespuestaFeignClients client;
	
	@Autowired
	private AlumnoFeignCliente clientAlumno;
	@Override
	@Transactional(readOnly = true)
	public Curso findCursoByAlunoId(Long id) {

		return repository.findCursoByAlunoId(id);
	}

	
	//aca el @Transactional no va ya que no es un repository (una comunicacion con la base de datos)
	@Override
	public Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId) {
		
		return client.obtenerExamenesIdsConRespuestasAlumno(alumnoId);
	}


	@Override
	public Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids) {
		
		return clientAlumno.obtenerAlumnosPorCurso(ids);
	}


	@Override
	@Transactional
	public void eliminarCursoAlumnoPorId(Long id) {
		repository.eliminarCursoAlumnoPorId(id);
		
	}

	

}
