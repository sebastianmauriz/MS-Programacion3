package com.programacionutn.microservicios.app.examenes.services;

import java.util.List;

import com.programacionutn.microservicios.commons.examenes.models.entity.Asignatura;
import com.programacionutn.microservicios.commons.examenes.models.entity.Examenes;
import com.programacionutn.microservicios.commons.services.CommonService;

public interface ExamenService extends CommonService<Examenes>{
	
	//como asignatura y preguntas estan muy relacionados con examenes, todos estos pueden ir en el mismo
	//service que Examenes
	public List<Examenes> findByNombre(String termino);
	
	public List<Asignatura> findAllAsignaturas();
	
}
