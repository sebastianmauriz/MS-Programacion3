package com.programacionutn.microservicios.app.examenes.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.programacionutn.microservicios.commons.examenes.models.entity.Examenes;

public interface ExamenRepository extends PagingAndSortingRepository<Examenes, Long>{

	
	@Query("select e from Examenes e where e.nombre like %?1%")
	public List<Examenes> findByNombre(String termino);
	
	
	
}
