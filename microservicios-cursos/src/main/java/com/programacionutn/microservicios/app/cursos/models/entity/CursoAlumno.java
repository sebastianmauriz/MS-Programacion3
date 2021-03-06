package com.programacionutn.microservicios.app.cursos.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name= "cursos_alumnos") //esta clase es para almacenar los id de los alumnos que pertenecen a un curso 
//nada mas que eso ya que ahora al tener ddbb diferentes, no se relacionan con una clave foranea
public class CursoAlumno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name= "alumno_id", unique = true) //el unique es para que un alumno se registra solo en un curso
	private Long alumnoId;
	
	@JsonIgnoreProperties(value = {"cursoAlumnos"})//para evitar el loop infinito
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "curso_id")
	private Curso curso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAlumnoId() {
		return alumnoId;
	}

	public void setAlumnoId(Long alumnoId) {
		this.alumnoId = alumnoId;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public boolean equals(Object obj) {
		if(this==obj) {
			return true;
		}
		//ahora debemos comprobar que esa instancia pertenezca al mismo tipo
		if(!(obj instanceof CursoAlumno)) {
			return false;
		}
		//el objeto que pasamos lo casteamos a Alumno
		CursoAlumno alu= (CursoAlumno) obj;
		//si el id no es nulo y el id es igul al que le estamos pasando, retorna true		
		return this.alumnoId !=null && this.alumnoId.equals(alu.getAlumnoId());
	}
	
	
}
