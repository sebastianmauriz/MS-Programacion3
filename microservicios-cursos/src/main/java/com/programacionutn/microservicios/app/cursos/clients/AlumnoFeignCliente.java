package com.programacionutn.microservicios.app.cursos.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.programacionutn.microservicios.commons.alumnos.models.entity.Alumno;


//El MS usuario se va a comunicar con el MS Curso mediante su endpoint
@FeignClient(name = "microservicio-usuarios")
public interface AlumnoFeignCliente {
	
	//este metodo hace referencia al endpoint que vamos a consumir del MS usuarios
	@GetMapping("/alumnos-por-curso")
	public Iterable<Alumno> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);
	//este metodo despues se debe implementar en el service del MS Curso
}
