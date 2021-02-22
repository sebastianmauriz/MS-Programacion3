package com.programacionutn.microservicios.app.usuarios.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programacionutn.microservicios.app.usuarios.client.CursoFeignClient;
import com.programacionutn.microservicios.app.usuarios.models.repository.AlumnoRepository;
import com.programacionutn.microservicios.commons.alumnos.models.entity.Alumno;
import com.programacionutn.microservicios.commons.services.CommonServiceImpl;

@Service
public class AlumnoServiceImpl extends CommonServiceImpl<Alumno, AlumnoRepository> implements AlumnoService {

	@Autowired //Esta anotacion es para que se pueda inyectar este atributo
	private CursoFeignClient clientCurso;
	
	@Override
	@Transactional(readOnly = true)
	public List<Alumno> findByNombreOrApellido(String texto) {
		
		return repository.findByNombreOrApellido(texto);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Alumno> findAllById(Iterable<Long> ids) {
		
		return repository.findAllById(ids);
	}

	@Override
	public void eliminarCursoAlumnoPorId(Long id) {
		clientCurso.eliminarCursoAlumnoPorId(id);
		
	}

	@Override
	@Transactional //aca ponemos esta anotacion porque si la eliminacion de la tabla cursoAlumno sale bien, va a realizar
	//la eliminacion de postgres; si sale mal, no elimina de postgres, de esta manera los datos quedan protegidos y
	//no se eliminan en una y si en la otra
	public void DeleteById(Long id) {
		super.DeleteById(id);
		this.eliminarCursoAlumnoPorId(id);
	}

	

}
