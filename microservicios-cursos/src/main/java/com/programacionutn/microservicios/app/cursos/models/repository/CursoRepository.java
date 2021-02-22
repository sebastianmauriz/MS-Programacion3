package com.programacionutn.microservicios.app.cursos.models.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.programacionutn.microservicios.app.cursos.models.entity.Curso;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long> {
	
	//aca se hace la relacion inversa de curso y alumnos, Recordar que la relacion es OneToMany
	//y la tiene curso, un curso tiene muchos Alumnos. Ahora vamos a realizar la busqueda inversa
	//pero sin dejar de ser unidireccional
	
	@Query("select c from Curso c join fetch c.cursoAlumnos a where a.alumnoId=?1") //el join fetch trae al curso con todos los alumnos y no a cada alumno individual
	public Curso findCursoByAlunoId(Long id); //este id del argumento es el del alumno que en la consulta se reemplaza en el ?1

	//Como alumnos y cursos estan en ddbb diferentes (alumnos en Postres, cursos en mySQL), debemos crear una relacion en cascada para que si se elimina
	//un alumno de un curso, éste tambien se elimine de la tabla cursoAlumno
	
	@Modifying//esta anotaion se usa para poder modificar de la base de datos
	@Query("delete from CursoAlumno ca where ca.alumnoId=?1")
	public void eliminarCursoAlumnoPorId(Long id);
}
