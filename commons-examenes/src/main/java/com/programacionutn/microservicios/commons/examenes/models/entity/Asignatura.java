package com.programacionutn.microservicios.commons.examenes.models.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "asignaturas")
public class Asignatura {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// A continuacion se hace un esquema de arbol, donde una clase entity esta relacionada con sigo misma
	//donde tenga elementos padre y a su vez elementos hijos de la misma entity
	
	private String nombre;
	
	@JsonIgnoreProperties(value = {"hijos", "handler", "hibernateLazyInitializer"})//"handler", "hibernateLazyInitializer"}) esto es para que no
	//de error el proxy que suele hacerlo
	//muchas asignaturas hijas asociadas a un padre
	@ManyToOne
	private Asignatura padre;
	
	//aca hacemos la relacion inversa con el mappedBy para no hacer la bidireccionalidad, se usa el mappedBy
	//una lista de asignaturas hijos que son mappeadas por el padre. Ademas de nada sirve tener un elemento
	//hijo si se borra al padre, para eso usamos el cascade = CascadeType.ALL
	
	@JsonIgnoreProperties(value = {"padre", "handler", "hibernateLazyInitializer"},allowSetters = true)
	//Esto es para limitar las relaciones en cascada que se llaman una y otra vez en forma infinita, produciendo
	//que el Json se rompa y largando una excepcion		
	//en value se hace un arreglo y se ponen los atributos que vamos a omitir de ignorar, en el caso de los
	// hijos omitimos al padre. Ademas lo configuramos para que permita setters
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "padre", cascade = CascadeType.ALL)
	private List<Asignatura> hijos;

	
	public Asignatura() {
		this.hijos = new ArrayList<>();
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

	public Asignatura getPadre() {
		return padre;
	}

	public void setPadre(Asignatura padre) {
		this.padre = padre;
	}

	public List<Asignatura> getHijos() {
		return hijos;
	}

	public void setHijos(List<Asignatura> hijos) {
		this.hijos = hijos;
	}
	

}
