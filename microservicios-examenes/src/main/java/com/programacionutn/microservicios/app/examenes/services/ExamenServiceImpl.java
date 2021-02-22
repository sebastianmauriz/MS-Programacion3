package com.programacionutn.microservicios.app.examenes.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programacionutn.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.programacionutn.microservicios.app.examenes.models.repository.ExamenRepository;
import com.programacionutn.microservicios.commons.examenes.models.entity.Asignatura;
import com.programacionutn.microservicios.commons.examenes.models.entity.Examenes;
import com.programacionutn.microservicios.commons.services.CommonServiceImpl;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examenes, ExamenRepository> implements ExamenService {

	@Autowired
	private AsignaturaRepository asignaturaRepository; //creamos este atributo para poder acceder al repositorio
	//de asignatura y asi acceder al metodo 
	
	@Override
	@Transactional(readOnly = true)
	public List<Examenes> findByNombre(String termino) {
		
		return repository.findByNombre(termino);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Asignatura> findAllAsignaturas() {
		
		return (List<Asignatura>) asignaturaRepository.findAll(); //el metodo findAll()es de la clase iterable
		//que es la mas generica, por eso tenemos que castear List<Asignatura> o en el repository en vez
		//de hacer la lista del tipo List lo hacemos del tipo Iterable, es lo mismo
	}

	
	
}
