package com.programacionutn.microservicios.app.cursos.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.programacionutn.microservicios.commons.alumnos.models.entity.Alumno;
import com.programacionutn.microservicios.commons.examenes.models.entity.Examenes;





@Entity
@Table(name = "cursos")
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //esta estrategia es autoincremental
	private Long id;
	
	@Column(name = "nombre")
	@NotEmpty
	private String nombre;
	
	@Column(name = "creat_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creatAt;
	
	 //aca hacemos la relacion entre curso y alumno
    @JsonIgnoreProperties(value = {"curso"}, allowSetters = true)//para evitar el loop infinito
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "curso",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CursoAlumno>cursoAlumnos;
	
	//relacion one to many, el curso es el dueño de la cardinalidad, es una relacion unidireccional
	//no es recomendable relaciones bidireccionales,para obtener el curso desde un alumno, se hace
	//con la url
	//@OneToMany(fetch = FetchType.LAZY)
	
	//La relacion se ha comentado ya que en este momento estamos usando postgre para la ddbb de alumnos
	//entonces debemos realizar la sgte anotacion ya que ahora no van a relacionarse los alumnos y el
	//curso con una clave foranea sino que lo haran con un id. la anotacion @Transient la usamos para
	//indicar que este campo no se va a persistir en la base de datos
    @Transient
    private List<Alumno>alumnos;
    
   
	
	@ManyToMany(fetch = FetchType.LAZY)//muestra el objeto a demanda
	private List<Examenes> examenes;
	
	@PrePersist
	public void prePersist() {
		this.creatAt= new Date();		
	}

	
	
	//generamos este constructor para poder inicializar el array de alumnos
	public Curso() {
	this.alumnos= new ArrayList<>();
	this.examenes = new ArrayList<>();
	this.cursoAlumnos= new ArrayList<>();
	}




	public List<Alumno> getAlumnos() {
		return alumnos;
	}


	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}
	
	//con este metodo podemos ir agregando a la lista alumnos de uno en uno, o sea asignarlos al curso
	public void addAlumno(Alumno alumno) {
		this.alumnos.add(alumno) ;
	}

	//metodo para removerlos de la lista
	public void removeAlumno(Alumno alumno) {
		this.alumnos.remove(alumno) ;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getCreatAt() {
		return creatAt;
	}

	public void setCreatAt(Date creatAt) {
		this.creatAt = creatAt;
	}



	public List<Examenes> getExamenes() {
		return examenes;
	}



	public void setExamenes(List<Examenes> examenes) {
		this.examenes = examenes;
	}
	
	public void addExamen(Examenes examen) {
		this.examenes.add(examen);
	}
	public void removeExamen(Examenes examen) {
		this.examenes.remove(examen);
	}



	public List<CursoAlumno> getCursoAlumnos() {
		return cursoAlumnos;
	}



	public void setCursoAlumnos(List<CursoAlumno> cursoAlumnos) {
		this.cursoAlumnos = cursoAlumnos;
	}
	
	public void addCursoAlumno(CursoAlumno cursoAlumno) {
		this.cursoAlumnos.add(cursoAlumno);
	}
	
	public void removeCursoAlumno(CursoAlumno cursoAlumno) {
		this.cursoAlumnos.remove(cursoAlumno);
	}

}
