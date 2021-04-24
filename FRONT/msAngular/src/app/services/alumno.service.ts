
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Alumno } from '../models/alumno';
import { CommonService } from './common.service';
import {BASE_ENDPOINT} from '../config/app';
 
//Esta annotation (inyectable) que trae cuando creamos el service, nos permite inyectar el service
//en el componente que necesitemos
@Injectable({
  providedIn: 'root'
})
export class AlumnoService extends CommonService<Alumno>{

  //cada service se va a comunicar con el respectivo MS con la direccion que sigue
  protected baseEndpoint = BASE_ENDPOINT +'/alumnos';

  constructor(http: HttpClient) { 
    super(http);
  }


  //en este metodo crear enviamos ademas del Json del alumno, el formData para enviar la foto
  //recordar que el crear solo esta en el commonService
  public crearConFoto(alumno: Alumno, archivo:File): Observable<Alumno>{
    const formData = new FormData(); //este es como el MultiPartFile de spring
    formData.append('archivo', archivo);//append recibe llave y valor: la llave es la misma que en el back
    //y va en string 'archivo'
    formData.append('nombre', alumno.nombre);
    formData.append('apellido', alumno.apellido);
    formData.append('email', alumno.email);
    return this.http.post<Alumno>(this.baseEndpoint + '/crear-con-foto', formData);
  }

  
  public editarConFoto(alumno: Alumno, archivo:File): Observable<Alumno>{
    const formData = new FormData(); //este es como el MultiPartFile de spring
    formData.append('archivo', archivo);//append recibe llave y valor: la llave es la misma que en el back
    //y va en string 'archivo'
    formData.append('nombre', alumno.nombre);
    formData.append('apellido', alumno.apellido);
    formData.append('email', alumno.email);
    return this.http.put<Alumno>(`${this.baseEndpoint}/editar-con-foto/${alumno.id}` , formData);
  }

  filtrarPorNombre(nombre: string): Observable<Alumno[]>{
return this.http.get<Alumno[]>(`${this.baseEndpoint}/filtrar/${nombre}`);
//filtrar/${nombre}`) esta ruta es la misma que el get de filtrar en la clase alumnoController del MS usuario
  }
  
}
