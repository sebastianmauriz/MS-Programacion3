package com.programacionutn.microservicios.app.usuarios.models.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.programacionutn.microservicios.commons.alumnos.models.entity.Alumno;

public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {
	
	//metodo para buscar un alumno con nombre y apellido
	//consulta Query con lenguaje de JPA
	@Query("select alumn from Alumno alumn where upper(alumn.nombre) like upper(concat('%',?1,'%')) or upper(alumn.apellido) like upper(concat('%',?1,'%'))")
	public List<Alumno> findByNombreOrApellido(String texto);
	

	//metodo para ordenar los alumnos en forma ascendente (todas palabras claves)
	
	public Iterable<Alumno> findAllByOrderByIdAsc();
	
	//y este metodo lista los alumnos ordenados por id pero con paginacion
	public Page<Alumno> findAllByOrderByIdAsc(Pageable pageable);
	
}
