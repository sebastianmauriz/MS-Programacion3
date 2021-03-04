import { Component, OnInit, ViewChild } from '@angular/core';
import { BASE_ENDPOINT } from 'src/app/config/app';
import { Alumno } from 'src/app/models/alumno';
import { AlumnoService } from 'src/app/services/alumno.service';
import { commonListarComponent } from '../common-listar.component';

@Component({
  selector: 'app-alumnos',
  templateUrl: './alumnos.component.html',
  styleUrls: ['./alumnos.component.css']
})
export class AlumnosComponent extends commonListarComponent<Alumno, AlumnoService> implements OnInit {

  baseEndpoint = BASE_ENDPOINT +'/alumnos';
  constructor(service: AlumnoService) {
    super(service);
    this.titulo = 'Listado de Alumnos';
    this.nombreModel = Alumno.name;
  }


}
