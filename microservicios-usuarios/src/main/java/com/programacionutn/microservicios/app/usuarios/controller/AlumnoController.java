package com.programacionutn.microservicios.app.usuarios.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.programacionutn.microservicios.app.usuarios.service.AlumnoService;
import com.programacionutn.microservicios.commons.alumnos.models.entity.Alumno;
import com.programacionutn.microservicios.commons.controllers.CommonController;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {
	
	//endpoint que busca los alumnos por curso, deriva del service que busca todos los alumnos del curso por su id
	@GetMapping("/alumnos-por-curso")
	public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
		return ResponseEntity.ok(service.findAllById(ids));
	}
	
	//metodo para poder ver las fotos
	@GetMapping("/uploads/img/{id}")
	public ResponseEntity<?> verFoto(@PathVariable Long id){
		
		Optional<Alumno> opcional= service.findById(id);
		
		if(opcional.isEmpty()|| opcional.get().getFoto()==null) {
			return ResponseEntity.notFound().build();
		}
		
		Resource imagen = new ByteArrayResource(opcional.get().getFoto());
		
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
		
	}

	@PutMapping("/{id}")  //actualizar o editar
	//necesitamos el PathVariable para capturar el id y el Alumno para poder editar el cuerpo del Json
	public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id){
	
		if(result.hasErrors()) {
			return this.validar(result);
		}
	Optional<Alumno> opcional= service.findById(id);
	
	if(opcional.isEmpty()) {
		return ResponseEntity.notFound().build();
	}
	//una vez que corroboramos que el alumno (opcional) existe, lo obtenemos y se lo agregamos a alumnoddbb
	Alumno alumnoddbb = opcional.get();
	//ahora indicamos que datos del alumno vamos a editar, ya que hay veces que no se requiere editar todo
	//por ejemplo la foto, el id, esas son cosas que por ahi no se quieren editar, entonces
	alumnoddbb.setNombre(alumno.getNombre()); //obtengo el nombre del objeto que me mandan en elRequest y se lo seteo al nuevo objeto
	alumnoddbb.setApellido(alumno.getApellido());
	alumnoddbb.setEmail(alumno.getEmail());
	
	//ahora antes de retornar el objeto ya editado, se debe persistir, es por eso que en 
	//el body hacemos el service.save de ese objeto
	return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoddbb));
	
	}
	
	@GetMapping("/filtrar/{texto}")
	public ResponseEntity<?> filtrar(@PathVariable String texto){
		return ResponseEntity.ok(service.findByNombreOrApellido(texto));
	}

	//metodo para crear las fotos de los alumnos, es un override del metodo crear. Aca solo validamos que
	//la foto no este vacia, despues en el return se llama a la superclase, se le pasan los parametros y listo
	//la suprclase es la encargada de hacer el resto
	@PostMapping("/crear-con-foto")
	public ResponseEntity<?> crearConFoto(@Valid Alumno alumno, BindingResult result, 
			@RequestParam MultipartFile archivo) throws IOException {
		//el multiPartFile es porque ya no nos envian un json solamente sino que ademas un archivo con bytes que es la foto
		//validamos que el archivo no este vacio
		if(!archivo.isEmpty()) {
			alumno.setFoto(archivo.getBytes());
		}
		
		return super.crear(alumno, result);
	}

	@PutMapping("/editar-con-foto/{id}")  
	public ResponseEntity<?> editarConFoto(@Valid Alumno alumno, BindingResult result,
			@PathVariable Long id, @RequestParam MultipartFile archivo) throws IOException{
	
		if(result.hasErrors()) {
			return this.validar(result);
		}
	Optional<Alumno> opcional= service.findById(id);
	
	if(opcional.isEmpty()) {
		return ResponseEntity.notFound().build();
	}
	
	Alumno alumnoddbb = opcional.get();
	
	alumnoddbb.setNombre(alumno.getNombre()); 
	alumnoddbb.setApellido(alumno.getApellido());
	alumnoddbb.setEmail(alumno.getEmail());
	
	if(!archivo.isEmpty()) {
		alumnoddbb.setFoto(archivo.getBytes());
	}
	return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoddbb));
	
	}
	
}
	
	
	
	
	
	
	
	
	
	
	
