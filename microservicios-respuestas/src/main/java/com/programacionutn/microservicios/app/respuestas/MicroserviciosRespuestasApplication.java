package com.programacionutn.microservicios.app.respuestas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

//esta etiqueta se pone porque respuestas tiene mongo, pero este MS en su pom tiene el commons alumnos y el
//common examenes que si tiene el dataJPA de sql, sin esta etiqueta daría error. Se excluye de la autoconfiguracion
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
/*esto que sigue es muy improtante para que Spring sepa donde se encuentran las clases entity del microservicio
//Como Respuesta está muy relacionado con elalumno y el examen, debemos tambien pegar el package de esas entidades
//para que spring las pueda usar. Recordar que estas entity están hechas en una librería aparte para poder
hacer uso de ellas ya que tienen cosas en comun con algunos MS */

/*@EntityScan({"com.programacionutn.microservicios.app.respuestas.models.entity",	
	"com.programacionutn.microservicios.commons.examenes.models.entity"})*/  //sacamos el entityScan ya que ahora se hara en 
//mongoDB y no necesita esto
public class MicroserviciosRespuestasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosRespuestasApplication.class, args);
	}

}
