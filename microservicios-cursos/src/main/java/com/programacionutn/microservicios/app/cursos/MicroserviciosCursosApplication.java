package com.programacionutn.microservicios.app.cursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
//esto que sigue es el nombre del paquete de la libreria alumnos que creamos, se agrega como un array
//en la etiqueta @EntityScan para que no nos de error, De esta forma encuentra la clase Alumno de la 
//libreria creada (commons-alumnos) y tambien la entidad curso y examenes.
//debemos importar el package de la libreria examenes, curso, alumnos ya que los datos estan ahí(commons-examenes) y de esta
//manera puede funcionar el programa. Por eso se hace en el main
@EntityScan({"com.programacionutn.microservicios.commons.examenes.models.entity",
	         "com.programacionutn.microservicios.app.cursos.models.entity"})
public class MicroserviciosCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosCursosApplication.class, args);
	}

}
