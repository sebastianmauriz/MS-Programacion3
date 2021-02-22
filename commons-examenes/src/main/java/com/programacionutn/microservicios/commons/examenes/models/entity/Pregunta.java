package com.programacionutn.microservicios.commons.examenes.models.entity;

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
@Table(name = "preguntas")
public class Pregunta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String texto;
	
	@JsonIgnoreProperties(value = {"preguntas"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "examen_id")//esta seria la clave foranea
	private Examenes examen;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Examenes getExamen() {
		return examen;
	}

	public void setExamen(Examenes examen) {
		this.examen = examen;
	}

	@Override
	public boolean equals(Object obj) {
		if(this==obj) {
			return true;
		}
		//ahora debemos comprobar que esa instancia pertenezca al mismo tipo
		if(!(obj instanceof Pregunta)) {
			return false;
		}
		//el objeto que pasamos lo casteamos a Alumno
		Pregunta preg= (Pregunta) obj;
		//si el id no es nulo y el id es igul al que le estamos pasando, retorna true		
		return this.id !=null && this.id.equals(preg.getId());
		
	}
	
	

}
