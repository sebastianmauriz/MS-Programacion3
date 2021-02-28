package com.programacionutn.microservicios.app.examenes.services;

import java.util.List;

import com.programacionutn.microservicios.commons.examenes.models.entity.Asignatura;
import com.programacionutn.microservicios.commons.examenes.models.entity.Examen;
import com.programacionutn.microservicios.commons.services.CommonService;

public interface ExamenService extends CommonService<Examen>{
	
	//como asignatura y preguntas estan muy relacionados con examenes, todos estos pueden ir en el mismo
	//service que Examenes
	public List<Examen> findByNombre(String termino);
	
	public List<Asignatura> findAllAsignaturas();
	
	public Iterable<Long> findExamenesIdsConRespuestasByPreguntaIds(Iterable<Long> preguntaIds);
	
}
