package com.programacionutn.microservicios.app.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer  //con esta anotacion conectamos el proyecto con el server de eureka
@SpringBootApplication
public class MicroserviciosEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosEurekaApplication.class, args);
	}

}
