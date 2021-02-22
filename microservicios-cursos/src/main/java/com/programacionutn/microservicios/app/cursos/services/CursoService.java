package com.programacionutn.microservicios.app.cursos.services;

import java.util.List;



import com.programacionutn.microservicios.app.cursos.models.entity.Curso;
import com.programacionutn.microservicios.commons.alumnos.models.entity.Alumno;
import com.programacionutn.microservicios.commons.services.CommonService;

public interface CursoService extends CommonService<Curso> {
	public Curso findCursoByAlunoId(Long id);
	
	//metodo del feignClient
	public Iterable <Long> obtenerExamenesIdsConRespuestasAlumno( Long alumnoId);

	public Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids);
	
	public void eliminarCursoAlumnoPorId(Long id);
}
