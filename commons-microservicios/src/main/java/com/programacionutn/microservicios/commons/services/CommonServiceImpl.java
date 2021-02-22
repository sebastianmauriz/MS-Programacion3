package com.programacionutn.microservicios.commons.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

//esta va a ser una clase generica para los crud, por eso hereda de CrudRepository(la cual recibe una
//entidad y un id). R es un repositorio generico que extiende de CrudRepository

public class CommonServiceImpl<E, R extends PagingAndSortingRepository<E, Long>> implements CommonService<E> {

	
	@Autowired  //es protected para que las clases que heredan de esta puedan hacer uso de los metodos
	protected R repository;
	
	@Override
	@Transactional(readOnly = true) 
	public Iterable<E> findAll() {
		
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<E> findById(Long id) {
		
		return repository.findById(id);
	}

	@Override
	@Transactional 
	public E save(E entity) {
		
		return repository.save(entity);
	}

	@Override
	@Transactional
	public void DeleteById(Long id) {
		
		repository.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<E> findAll(Pageable pageable) {
		
		return repository.findAll(pageable);
	}

}
