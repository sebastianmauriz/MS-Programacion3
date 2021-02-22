package com.programacionutn.microservicios.app.usuarios.service;



import java.util.List;


import com.programacionutn.microservicios.commons.alumnos.models.entity.Alumno;
import com.programacionutn.microservicios.commons.services.CommonService;

public interface AlumnoService extends CommonService<Alumno>{
	//Metodo creado en AlumnoRepository
	public List<Alumno> findByNombreOrApellido(String texto);
	
	//metodo que busca todos los alumnos por sus ids
	public Iterable<Alumno>findAllById(Iterable<Long> ids);
	
	public void eliminarCursoAlumnoPorId( Long id);

	
}
