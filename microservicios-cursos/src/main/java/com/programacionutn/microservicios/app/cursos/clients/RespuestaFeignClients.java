package com.programacionutn.microservicios.app.cursos.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Consultas del tipo HTTP
@FeignClient(name = "microservicio-respuestas")//se anota el nombre del MS con el que nos vamos a comunicar
public interface RespuestaFeignClients {

	//en este metodo indicamos el endpoint, los parametro, el tipo de retorno
	//este metodo lo hacemos para obtener los examenes respondidos por el alumno
	//es el mismo estilo que los metodos del controller, con el mapping, la ruta
	//debe ser la misma que la que usamos en RespuestaController
	
	
	//el  GetMapping indicamos el endpoint con una variable{alumnoId} por eso en el metodo
	//desspues debemos indicar de que tipo y cual es esa variable con la anotacion @PathVariable 
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	
	public Iterable <Long> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId);
						
			
	}
