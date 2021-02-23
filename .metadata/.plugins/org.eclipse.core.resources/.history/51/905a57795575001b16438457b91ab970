package com.programacionutn.microservicios.app.respuestas.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.programacionutn.microservicios.commons.examenes.models.entity.Examenes;

@FeignClient(name = "microservicio-examenes")
public interface ExamenFeignClient {
	
	@GetMapping("/{id}")
	public Examenes obtenerExamenPorId(@PathVariable Long id);
	
	@GetMapping("/respondidos-por-preguntas")
	public List<Long> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntaIds);

}
