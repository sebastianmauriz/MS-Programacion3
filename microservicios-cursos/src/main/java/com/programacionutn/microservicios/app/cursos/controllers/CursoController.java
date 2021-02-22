 package com.programacionutn.microservicios.app.cursos.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.programacionutn.microservicios.app.cursos.models.entity.Curso;
import com.programacionutn.microservicios.app.cursos.models.entity.CursoAlumno;
import com.programacionutn.microservicios.app.cursos.services.CursoService;
import com.programacionutn.microservicios.commons.alumnos.models.entity.Alumno;
import com.programacionutn.microservicios.commons.controllers.CommonController;
import com.programacionutn.microservicios.commons.examenes.models.entity.Examenes;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {

	@Value("${config.balanceador.test}") //el config.balanceador.test lo sacamos del applicationProperties, para
	//que encuentre el valor que le dimos en el appliPrope
	private String balanceadorTest;
	
	//metodo que elimina el alumno (el id) de la tabla CursoAlumno de mySQL
	@DeleteMapping("/eliminar-alumno/{id}")
	public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id){
		service.eliminarCursoAlumnoPorId(id);
		//retornamos un not content porque es de tipo delete
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	@Override
	public ResponseEntity<?> listar() {
		//lo que tenemos que hacer es ir llenando esa coleccion 'cursos' de alumnos
		List<Curso> cursos = ((List<Curso>) service.findAll()).stream().map(c ->{
			c.getCursoAlumnos().forEach(ca ->{
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				c.addAlumno(alumno);
			});
			return c;
			//ahora se debe volver a List ya que esta en stream
		}).collect(Collectors.toList());	
		
		return ResponseEntity.ok().body(cursos);
		
	}
	
	@GetMapping ("/pagina") 
	@Override
	public ResponseEntity<?> listar(Pageable pageable) {
		Page<Curso> cursos=service.findAll(pageable).map(curso ->{
			curso.getCursoAlumnos().forEach(ca ->{
				Alumno alumno = new Alumno();
				alumno.setId(ca.getAlumnoId());
				curso.addAlumno(alumno);
			});
			return curso;
		});
		return ResponseEntity.ok().body(cursos);
	}
	

	@GetMapping("/{id}") 
	@Override					

	public ResponseEntity<?> verLista(@PathVariable Long id) {
		Optional<Curso> opcional = service.findById(id);
		// ahora en el caso de que ese id no exista, hacemos que devuelva un body vacio
		// (lo crea con el build) para que no de error
		if (opcional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Curso curso = opcional.get();
		
		if(curso.getCursoAlumnos().isEmpty()==false) {
			
			List<Long>ids =curso.getCursoAlumnos().stream().map(ca-> ca.getAlumnoId())
					.collect(Collectors.toList());
			List<Alumno> alumnos= (List<Alumno>) service.obtenerAlumnosPorCurso(ids);
			curso.setAlumnos(alumnos);
		}

		return ResponseEntity.ok().body(curso);
	}
	
	@GetMapping("/balanceador-test")
	public ResponseEntity<?> balanceadorTest() {

		Map<String, Object> response = new HashMap<String, Object>();
		response.put("balanceador", balanceadorTest);
		response.put("cursos", service.findAll());
		return ResponseEntity.ok(response);                     
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
		if (result.hasErrors()) {
			return this.validar(result);
		}
		Optional<Curso> opcional = this.service.findById(id);
		if (!opcional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso dbCurso = opcional.get();
		dbCurso.setNombre(curso.getNombre());
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

//el metodo a continuacion es para asigna alumnos al curso, estos se van a asignar mediante una lista
	// en el front la idea es con un checkBox ir seleccionando alumnos a asignar al
	// curso, Es por esto
	// que hacemos una lista para asignar alumnos al curso
	@PutMapping("/{id}/asignar-alumnos")
	public ResponseEntity<?> asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
		// ahora con el id tenemos que buscar el curso al cual agregaremos a los alumnos
		Optional<Curso> opcional = this.service.findById(id);
		if (!opcional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso dbCurso = opcional.get();
		alumnos.forEach(a -> {
			CursoAlumno cursoAlumno = new CursoAlumno();
			cursoAlumno.setAlumnoId(a.getId());
			cursoAlumno.setCurso(dbCurso);
			dbCurso.addCursoAlumno(cursoAlumno);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

	// ahora vamos a eliminar alumnos del curso, pero en este caso se eliminaran de
	// a uno, ya que en el
	// front cada alumno tendra un boton de eliminar
	@PutMapping("/{id}/eliminar-alumno")
	public ResponseEntity<?> eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id) {
		// ahora con el id tenemos que buscar el curso al cual agregaremos a los alumnos
		Optional<Curso> opcional = this.service.findById(id);
		if (!opcional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso dbCurso = opcional.get();
		CursoAlumno cursoAlumno = new CursoAlumno();
		cursoAlumno.setAlumnoId(alumno.getId());
		dbCurso.removeCursoAlumno(cursoAlumno);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

	@GetMapping("/alumno/{id}")
	public ResponseEntity<?> buscarPorAlumnoId(@PathVariable Long id) {
		Curso curso = service.findCursoByAlunoId(id);
		if(curso !=null) {
			List <Long> examenesIds= (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);
			List<Examenes> examenes= curso.getExamenes().stream().map(examen ->{
				if(examenesIds.contains(examen.getId())) {
					examen.setRespondido(true);
				}
				return examen;
			}).collect(Collectors.toList());
			curso.setExamenes(examenes);
		}
		return ResponseEntity.ok(curso);
	}

	// metodo para asiganr examenes, es igual al de asignar alumnos
	@PutMapping("/{id}/asignar-examenes")
	public ResponseEntity<?> asignarExamenes(@RequestBody List<Examenes> examenes, @PathVariable Long id) {
		// ahora con el id tenemos que buscar el curso al cual agregaremos a los alumnos
		Optional<Curso> opcional = this.service.findById(id);
		if (!opcional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso dbCurso = opcional.get();
		examenes.forEach(e -> {
			dbCurso.addExamen(e);
		});
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}

	@PutMapping("/{id}/eliminar-examen")
	public ResponseEntity<?> eliminarExamen(@RequestBody Examenes examen, @PathVariable Long id) {
		// ahora con el id tenemos que buscar el curso al cual agregaremos a los alumnos
		Optional<Curso> opcional = this.service.findById(id);
		if (!opcional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Curso dbCurso = opcional.get();
		dbCurso.removeExamen(examen);
		return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(dbCurso));
	}
}
