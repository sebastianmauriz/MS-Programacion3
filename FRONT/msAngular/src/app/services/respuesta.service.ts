import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BASE_ENDPOINT } from '../config/app';
import { Respuesta } from '../models/respuesta';

@Injectable({
  providedIn: 'root'
})
export class RespuestaService {

  private cabeceras: HttpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });
  private baseEndpoint = BASE_ENDPOINT +'/respuestas';

  constructor(private http: HttpClient) { }

crear(respuestas: Respuesta[]): Observable<Respuesta[]>{
  return this.http.post<Respuesta[]>(this.baseEndpoint, respuestas, {headers:this.cabeceras});
}

}

