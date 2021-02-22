package com.programacionutn.microservicios.commons.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

//con <E> le decimos que va a ser genereico para cualquier microservicio
public interface CommonService<E> {

	public Iterable<E> findAll();

	public Page<E> findAll(Pageable pageable);
	
	public Optional<E> findById(Long id);

	public E save(E entity);

	public void DeleteById(Long id);
}
