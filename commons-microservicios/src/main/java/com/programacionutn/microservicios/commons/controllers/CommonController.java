package com.programacionutn.microservicios.commons.controllers;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.programacionutn.microservicios.commons.services.CommonService;

//esta anotacion es para darle acceso al front, es decir para que el front se conecte con la api
//Esto se hace en cada MS o sino se hace en forma global en el application.yml
//@CrossOrigin({"http://localhost:4200"})
public class CommonController<E, S extends CommonService<E>> {

	@Autowired
	protected S service;

	// nos permite mapear una ruta url al metodo, si no se especifica, se determina
	// que se mapea a la raiz
	@GetMapping
	// ResponseEntity es un objeto que nos permite construir la respuesta, el
	// httpEntity
	// y el tipo agregamos ? que significa que puede ser cualquier tipo (any), es
	// decir que el
	// json que retorne puede estar construido de diferentes tipos de objetos
	public ResponseEntity<?> listar() {
		return ResponseEntity.ok().body(service.findAll());
		// el ok quiere decir que es el status 200, y le pasamos como parametro el
		// objeto de AlumnoService
		// con el metodo findAll ya que este metodo es el de listar, En el cuerpo del
		// ResponseEntity le
		// pasamos el objeto
	}
	
	@GetMapping ("/pagina") //a este getMapping debemos asignarle una ruta porque sino se va a 
	//confundir con el de arriba
	public ResponseEntity<?> listar(Pageable pageable) {
		return ResponseEntity.ok().body(service.findAll(pageable));
	}

	@GetMapping("/{id}") // en este caso le pasamos una ruta, ésta va a ser variable ya que el id va a ir
							// cambiando
	// es por eso que se pone / y el id entre {}
	// con @PathVariable spring automaticamente extrae el id de la ruta

	public ResponseEntity<?> verLista(@PathVariable Long id) {
		Optional<E> opcional = service.findById(id);
		// ahora en el caso de que ese id no exista, hacemos que devuelva un body vacio
		// (lo crea con el build) para que no de error
		if (opcional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(opcional.get()); // opcional.get(): obtiene el objeto de tipo Alumno
		// puede ser asi o como en el anterior ok().body()
		// return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping // crear
	// @RequestBody Alumno alumno: aca se debe crear una request del cuerpo del json
	// que se va a enviar
	// por eso se pone asi, y ese Json debe ser igual al que va a soportar el front,
	// es por eso que
	// debemos especificar el tipo, en este caso es del tipo Alumno. O sea que el
	// json que viene desde
	// el front mediante el @RequestBody lo va poblar en alumno.
	// BindingResult result: a traves del resultado obtenemos los mensajes de error. Mas abajo hacemos el emtodo
	public ResponseEntity<?> crear(@Valid @RequestBody E entity, BindingResult result) {

		if(result.hasErrors()) {
			return this.validar(result);
		}
		E entityDdbb = service.save(entity);

		return ResponseEntity.status(HttpStatus.CREATED).body(entityDdbb);
		// este estatus es de tipo 201 que es crear
	}

	@DeleteMapping("/{id}") // eliminar

	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		service.DeleteById(id);
		return ResponseEntity.noContent().build(); // retorna un responseEntity sin contenido, el .build() es
//para crear ese responseEntity
	}
	
	protected ResponseEntity<?> validar(BindingResult result){
		Map<String, Object> errores= new HashMap<>();
		//con un for vamos a recorrer cada mensaje de error y lo vamos a ir guardando en el mapa
		//para luego asignarlo segun corresponda
		result.getFieldErrors().forEach(error->{
			errores.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage()); //getField()hace referencia al campo donde se produce el error (en el nombre o apellido etc)
		});
		return ResponseEntity.badRequest().body(errores);
	}
}
