package com.programacionutn.microservicios.app.respuestas.services;

//import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.programacionutn.microservicios.app.respuestas.clients.ExamenFeignClient;
import com.programacionutn.microservicios.app.respuestas.models.entity.Respuesta;
import com.programacionutn.microservicios.app.respuestas.models.repository.RespuestaRepository;
//import com.programacionutn.microservicios.commons.examenes.models.entity.Examenes;
//import com.programacionutn.microservicios.commons.examenes.models.entity.Pregunta;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	private RespuestaRepository repository;
	
	//@Autowired
	//private ExamenFeignClient examenClient;
	
	@Override
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		
		return repository.saveAll(respuestas);
	}

	@Override
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		
		/* COMENTAMOS TODO YA QUE AHORAS AL HACER AL ALUMNO Y LA PREGUNTA PARTE DE LA DATABESE DE MONGO
		 * Y BO TENER QUE IOR A BUSCAR LOS DATOS A OTRO MS, PODEMOS HACER ESTE METODO MAS CORTO Y MAS 
		 * RAPIDO PARA LA CONSULTA
		 * //necesitamos ir a buscar los examenes al MSExamenes ya que estan en otra database (sql)
		//lo hacemos con feign
		Examenes examen= examenClient.obtenerExamenPorId(examenId);
		//Ahora las preguntas
		List<Pregunta> preguntas= examen.getPreguntas(); 
		//ahora debemos convertir esta lista de preguntas a un tipo List del id de las preguntas, ya que esta lista
		//tiene las preguntas con todos sus atributos.
		//con el stream y el map podemos convertir este flujo de datos a otro tipo: (p ->p.getId() aca es donde se hace el cambio
		//y despues con el collect lo volvemos a un List pero ya de Long y no de Pregunta
		List<Long> preguntaIds= preguntas.stream().map(p ->p.getId()).collect(Collectors.toList());
		List<Respuesta> respuestas= (List<Respuesta>) repository.findRespuestaByAlumnoByPreguntaIds(alumnoId, preguntaIds);
		respuestas = respuestas.stream().map(r ->{
			preguntas.forEach(p ->{
				if(p.getId()== r.getPreguntaId()) {
					r.setPregunta(p);
				}
			});
			return r;
		}).collect(Collectors.toList());    */
		
		List<Respuesta> respuestas= (List<Respuesta>) repository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
		return respuestas; 
	}

	@Override
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
		
		/*COMENTAMOS TODO POR LA MISMA RAZON QUE EL ANTERIOR
		 * 
		List<Respuesta> respuestasAlumno = (List<Respuesta>) repository.findByAlumnoId(alumnoId);
		List<Long> examenIds= Collections.emptyList();
		if(respuestasAlumno.size()>0) {
			List<Long> preguntaIds= respuestasAlumno.stream().map(r ->r.getPreguntaId()).collect(Collectors.toList());
			examenIds= examenClient.obtenerExamenesIdsPorPreguntasIdRespondidas(preguntaIds);
		}
		*/
		List<Respuesta> respuestasAlumno = (List<Respuesta>) repository.findExamenesIdsConRespuestasByAlumno(alumnoId);
		List<Long> examenIds = respuestasAlumno.stream().map(r -> r.getPregunta().getExamen().getId())
		.distinct().collect(Collectors.toList()); //el distintic agurpa los valores repetidos en uno solo ya que 
		//en este caso, serian 3 id iguales de examen (1 por cada pregunta)
		return examenIds;
	}

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
		
		return repository.findByAlumnoId(alumnoId);
	}

}
