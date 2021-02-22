package com.programacionutn.microservicios.app.examenes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
//debemos importar el package de la libreria examenes ya que los datos estan ahí(commons-examenes) y de esta
//manera puede funcionar el programa. Por eso se hace en el main
@EntityScan({"com.programacionutn.microservicios.commons.examenes.models.entity"})
public class MicroserviciosExamenesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosExamenesApplication.class, args);
	}

}
