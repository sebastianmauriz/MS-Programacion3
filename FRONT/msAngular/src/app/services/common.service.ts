
import { Directive, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams,} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Generic } from '../models/generic';
 
//la ahcemos abstracta porque es la que van a heredar los demas service que tienen en comun esta clase
@Directive() //esto se pone en las clases abstractas
export abstract class CommonService<E extends Generic>{

  //cada service se va a comunicar con el respectivo MS con la direccion que pondremos en el string
  protected baseEndpoint !:string;
  //aca configuramos la cabecera a un tipo Json para poder mandar el objeto Alumno en el metodo
//crear y editar, ya que el back recibe un Json
protected cabeceras: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(protected http: HttpClient) { } //este atributo al ser del tipo HttpClient tiene los
  //metodos post, put, get y delete

  //vamos a crear los metodos para poder editar, crer, listar, etc alumnos

  //Con el observable creamos un evento que se ejecutará cuando algo suceda
  //en este caso vamos a hacerlo de una lista de alumnos, es una funcion

  public listar(): Observable<E[]>{
    return this.http.get<E[]>(this.baseEndpoint)  //despues del get lo que se hace es castear a alumno
    //ya que el http es de tipo Object
  }

  public listarPaginas(page: string, size: string): Observable<any>{
    //ahora seteamos los atributos ya que al ser una cosnstante, cuando se cree uno el otro se 
    //elimina. De esta manera ya quedan seteados ambos. Se invocan en base a la misma instancia
    //como buena practica, el httpParams se usa con const
    const params= new HttpParams()
    .set('page', page)
    .set('size', size); //al ser dos set en cadena, ahora el params contiene ambos set: page y size
    return this.http.get<any>(`${this.baseEndpoint}/pagina`, {params: params});
  }

  public ver(id:number):Observable<E>{
    return this.http.get<E>(`${this.baseEndpoint}/${id}`);
  }
  
  public crear(e: E): Observable<E> {
    return this.http.post<E>(this.baseEndpoint, e,
      { headers: this.cabeceras });
  }

  public editar(e: E): Observable<E> {
    return this.http.put<E>(`${this.baseEndpoint}/${e.id}`, e,
      { headers: this.cabeceras });
  }

  public eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseEndpoint}/${id}`);
  }
}
