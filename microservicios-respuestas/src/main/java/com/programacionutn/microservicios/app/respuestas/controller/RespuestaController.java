package com.programacionutn.microservicios.app.respuestas.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.programacionutn.microservicios.app.respuestas.models.entity.Respuesta;
import com.programacionutn.microservicios.app.respuestas.services.RespuestaService;

@RestController
public class RespuestaController {

	@Autowired
	private RespuestaService service;
	
	@PostMapping //si no ponemos ruta quiere decir que lo dejamos en la raiz
	public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas){
		//recordar que la respuesta ya viene con el objeto pregunta y con el alumno
		respuestas= ((List<Respuesta>)respuestas).stream().map(r->{
			r.setAlumnoId(r.getAlumno().getId());
			r.setPreguntaId(r.getPregunta().getId());
			return r;
		}).collect(Collectors.toList());
		
		Iterable<Respuesta> respuestasDb = service.saveAll(respuestas); 
		
		return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);
	}
	
	@GetMapping("/alumno/{alumnoId}/examen/{examenId}")
	public ResponseEntity<?> obtenerRespuestasPorAlumnoPorExamen(@PathVariable Long alumnoId, @PathVariable Long examenId){
		//de esto arriba se obtiene un iterable que debemos asignar a una variable
		Iterable<Respuesta> respuestas = service.findRespuestaByAlumnoByExamen(alumnoId, examenId);
		
		return ResponseEntity.ok(respuestas);
	}
	
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public ResponseEntity<?> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId){
		Iterable<Long> examenesIds= service.findExamenesIdsConRespuestasByAlumno(alumnoId);
		return ResponseEntity.ok(examenesIds);
	}
	
}
