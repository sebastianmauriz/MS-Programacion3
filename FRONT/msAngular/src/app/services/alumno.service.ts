
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams,} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Alumno } from '../models/alumno';
 
//Esta annotation (inyectable) que trae cuando creamos el service, nos permite inyectar el service
//en el componente que necesitemos
@Injectable({
  providedIn: 'root'
})
export class AlumnoService {

  //cada service se va a comunicar con el respectivo MS con la direccion que sigue
  private baseEndpoint = 'http://localhost:8090/api/alumnos';
  //aca configuramos la cabecera a un tipo Json para poder mandar el objeto Alumno en el metodo
//crear y editar, ya que el back recibe un Json
private cabeceras: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient) { } //este atributo al ser del tipo HttpClient tiene los
  //metodos post, put, get y delete

  //vamos a crear los metodos para poder editar, crer, listar, etc alumnos

  //Con el observable creamos un evento que se ejecutará cuando algo suceda
  //en este caso vamos a hacerlo de una lista de alumnos, es una funcion

  public listar(): Observable<Alumno[]>{
    return this.http.get<Alumno[]>(this.baseEndpoint)  //despues del get lo que se hace es castear a alumno
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

  public ver(id:number):Observable<Alumno>{
    return this.http.get<Alumno>(`${this.baseEndpoint}/${id}`);
  }
  
  public crear(alumno: Alumno): Observable<Alumno> {
    return this.http.post<Alumno>(this.baseEndpoint, alumno,
      { headers: this.cabeceras });
  }

  public editar(alumno: Alumno): Observable<Alumno> {
    return this.http.put<Alumno>(`${this.baseEndpoint}/${alumno.id}`, alumno,
      { headers: this.cabeceras });
  }

  public eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseEndpoint}/${id}`);
  }
}
