  package com.programacionutn.microservicios.app.respuestas.models.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.programacionutn.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaRepository extends MongoRepository<Respuesta, String> {
	
	 //aca vamos a ahcer un metodo para poder saber que respondió un alumno en particular
	//cual fue la pregunta y su respuesta
	
	//el fetch es para que nos traiga todo, al traer el alumno, éste viene con la pregunta y el examen
	
	//@Query("select r from Respuesta r join fetch r.pregunta p join fetch p.examen e where r.alumnoId=?1 and e.id=?2")
	 //public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);

	//Creamos el metodo que busca todos los examenes (a traves de su ID) que fueronrespondidos por un alumno
	//en una sola consulta entrega todos los examenes respondidos por ese alumno
	//@Query("select e.id from Respuesta r join r.pregunta p join p.examen e where r.alumnoId =?1 group by e.id")
	//public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
	
	
	//MONGO
	//el 0 corresponde al primero parametro y el 1 al segundo, el segundo va entre llaves porque es un conjunto de preguntas
	//son 2 json: el primero con dos parametros: alumno y pregunta y el segundo anidado con el json de las preguntas
	@Query("{'alumnoId': ?0, 'preguntaId': {$in:?1}}") //de mongo no de jpa
	public Iterable<Respuesta> findRespuestaByAlumnoByPreguntaIds(Long alumnoId, Iterable<Long> preguntaIds);
	
	
	@Query("{'alumnoId': ?0}")
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId);
	
	@Query("{'alumnoId': ?0, 'pregunta.examen.id':?1}")
	 public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
	
	@Query(value = "{'alumnoId': ?0}",fields = "{'pregunta.examen.id':1}") //el 1 es para que lo muestre en el json
	public Iterable<Respuesta> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
}
