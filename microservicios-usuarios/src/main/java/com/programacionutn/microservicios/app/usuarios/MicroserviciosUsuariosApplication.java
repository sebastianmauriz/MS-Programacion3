package com.programacionutn.microservicios.app.usuarios;

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
//libreria creada (commons-alumnos)
@EntityScan({"com.programacionutn.microservicios.commons.alumnos.models.entity"})
public class MicroserviciosUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosUsuariosApplication.class, args);
	}

}
