package com.programacionutn.microservicios.app.respuestas.models.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.programacionutn.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaRepository extends CrudRepository<Respuesta, Long> {
	
	 //aca vamos a ahcer un metodo para poder saber que respondió un alumno en particular
	//cual fue la pregunta y su respuesta
	
	//el fetch es para que nos traiga todo, al traer el alumno, éste viene con la pregunta y el examen
	
	@Query("select r from Respuesta r join fetch r.pregunta p join fetch p.examen e where r.alumnoId=?1 and e.id=?2")
	 public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);

	//Creamos el metodo que busca todos los examenes (a traves de su ID) que fueronrespondidos por un alumno
	//en una sola consulta entrega todos los examenes respondidos por ese alumno
	@Query("select e.id from Respuesta r join r.pregunta p join p.examen e where r.alumnoId =?1 group by e.id")
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
}
