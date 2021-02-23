package com.programacionutn.microservicios.app.respuestas.models.entity;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.programacionutn.microservicios.commons.alumnos.models.entity.Alumno;
import com.programacionutn.microservicios.commons.examenes.models.entity.Pregunta;

//esta clase ahora será con la database de mongo, es por eso que se comentan todas las anotaciones de jpa
//@Entity
//@Table(name = "respuestas")

@Document(collection = "respuestas")  //Ahora es un documento de mongo
public class Respuesta {
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id  // org.springframework.data.annotation.Id; es de este import, no de javax. Al igual que los demas
	private String id;  //el id es alfanumerico si o si en mongo, por eso es de tipo String
	
	
	private String texto;
	
	//@ManyToOne(fetch = FetchType.LAZY)  //se comenta porque esta tabla con esta mas en la misma ddbb
	//@Transient
	//@Transient  //se comenta pórque ahora este atributo y el de pregunta serán locales a mongo
	private Alumno alumno;
	
	//@Column(name = "alumno_id")
	private Long alumnoId;  //sera parte del json de mongo
	
	//@OneToOne(fetch = FetchType.LAZY)
	//@Transient
	private Pregunta pregunta;

	private Long preguntaId;  //es parte del Json de mongo

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public Alumno getAlumno() {
		return alumno;
	}


	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}


	public Pregunta getPregunta() {
		return pregunta;
	}


	public void setPregunta(Pregunta pregunta) {
		this.pregunta = pregunta;
	}


	public Long getAlumnoId() {
		return alumnoId;
	}


	public void setAlumnoId(Long alumnoId) {
		this.alumnoId = alumnoId;
	}


	public Long getPreguntaId() {
		return preguntaId;
	}


	public void setPreguntaId(Long preguntaId) {
		this.preguntaId = preguntaId;
	}


	
}

