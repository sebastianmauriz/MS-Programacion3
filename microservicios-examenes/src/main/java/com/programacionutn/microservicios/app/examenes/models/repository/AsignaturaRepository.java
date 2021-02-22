package com.programacionutn.microservicios.app.examenes.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.programacionutn.microservicios.commons.examenes.models.entity.Asignatura;

public interface AsignaturaRepository extends CrudRepository<Asignatura, Long >{
	
	@Query("select a from Asignatura a where a.nombre like %?1%")
	public List<Asignatura> findByNombre(String termino);

}
