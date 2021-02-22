package com.programacionutn.microservicios.commons.alumnos.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="alumnos")
public class Alumno {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty //este debe ser de javaxValidation
	private String nombre;
	@NotEmpty
	private String apellido;
	@NotEmpty
	@Email //este debe ser de javaxValidation
	private String email;
	
	@Column(name = "creat_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creatAt;
	
	@Lob //esta annotation permite guardar en ddbb objetos como fotos, pdf, etc
	@JsonIgnore //esto es porque no nos interesa pasarlo por el json ya que al ser bytes es muy grande y el
	//json crece exponencialmente y no nos conviene
	private byte[]foto;
	
	
	@PrePersist
	public void prePersist() {
		this.creatAt= new Date();
	}
	
	//este metodo es para que despues en el front nos retorne un codigo distinto de cada foto
	public Integer getFotoHashCode() {
		return (this.foto!=null) ? this.foto.hashCode():null;
		
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreatAt() {
		return creatAt;
	}

	public void setCreatAt(Date creatAt) {
		this.creatAt = creatAt;
	}
	
	

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	//metodo para poder verificar si el alumno esta duplicado y asi poder eliminarlo de la lista
	//en la clase curso con el metodo remove. Se hace aca ya que ésta es la libreria que usa
	@Override
	public boolean equals(Object obj) {
		if(this==obj) {
			return true;
		}
		//ahora debemos comprobar que esa instancia pertenezca al mismo tipo
		if(!(obj instanceof Alumno)) {
			return false;
		}
		//el objeto que pasamos lo casteamos a Alumno
		Alumno alu= (Alumno) obj;
		//si el id no es nulo y el id es igul al que le estamos pasando, retorna true		
		return this.id !=null && this.id.equals(alu.getId());
	}
	
	

}
