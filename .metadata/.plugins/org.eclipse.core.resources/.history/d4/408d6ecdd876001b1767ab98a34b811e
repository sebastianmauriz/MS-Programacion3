package com.programacionutn.microservicios.app.examenes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programacionutn.microservicios.app.examenes.services.ExamenService;
import com.programacionutn.microservicios.commons.controllers.CommonController;
import com.programacionutn.microservicios.commons.examenes.models.entity.Examenes;
import com.programacionutn.microservicios.commons.examenes.models.entity.Pregunta;

@RestController
public class ExamenController extends CommonController<Examenes, ExamenService> {

	@GetMapping("/respondidos-por-preguntas")
	public ResponseEntity<?> obtenerExamenesIdsPorPreguntasIdRespondidas(@RequestParam List<Long> preguntaIds){
		return ResponseEntity.ok().body(service.findExamenesIdsConRespuestasByPreguntaIds(preguntaIds));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@Valid @RequestBody Examenes examen, BindingResult result, @PathVariable Long id){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Optional<Examenes> exam = service.findById(id);
		
		if(!exam.isPresent()) {
			return ResponseEntity.notFound().build(); //si no esta el examen retornamos un error 404
		}
		 Examenes examenDb =exam.get(); //a una variable nueva le asignamos el examen encontrado mas arriba con el id
		examenDb.setNombre(examen.getNombre());//a esta nueva variable le seteamos el nombre del examen que pasamos por parametro
		 
		//ahora lo que hacemos es verificar que las preguntas enviadas en el examen están o no, es decir,
		//puede ser que algunas preguntas las hayan cambiado, otras eliminado. Es por eso que debemos comprobar
		//que esten y sean las mismas. Esto lo hacemnos con una List de preguntas y las recorremos con un forEach
		//en el caso que no se encuntren, las eliminamos, sino nos daria error
		
		List<Pregunta> eliminadas = new ArrayList<>();
		//de examenDb extraemos las preguntas y las recorremos
		examenDb.getPreguntas().forEach(pregDataBase ->{
			//el metodo contain sive para preguntar si ese elemento existe en las preguntas (eso en este caso ya que usamos el getPreguntas)
			//pero aca preguntamos si el elemento NO existe (!)
			if(!examen.getPreguntas().contains(pregDataBase)) {
				//todas las preguntas que no existan las alojamos en la List para luego elimnar esa lista
				eliminadas.add(pregDataBase);				
			}
		});
		
		//con un forEach vamos eliminando las preguntas de a una en la lista
		eliminadas.forEach(p ->{
			examenDb.removePregunta(p); //este metodo que creamos retorna un null, es decir que quita el examen de sa pregunta
			
			//forEach mejorado u optimizado:
			//eliminadas.forEach(examenDb::removePregunta);
			
		});
		//una vez eliminadas las preguntas que no estan, debemos setear las que si estan en el Json que nos 
		//envian en la variable examen
		examenDb.setPreguntas(examen.getPreguntas());
		//despues retornamos en ResponseEntity con el status de creado y en el body pasamos el examen con
		//las preguntas ya guardado
		
		 return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
	}
	
	@GetMapping("/filtrar/{termino}")
	public ResponseEntity<?> filtrar (@PathVariable String termino){
		return ResponseEntity.ok(service.findByNombre(termino));
	}
	
	
	//podemos hacer este mapeo de asignatura aca o bien poodemos crear un controller Asignatura y hacerlo ahi
	//pero como es sol para listar una sola cosa, lo ahcemos aca y no estamnos creando una clase nueva.
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignaturas());
		
	}
}
