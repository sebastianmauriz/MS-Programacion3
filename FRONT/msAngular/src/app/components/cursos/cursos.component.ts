import { Component, OnInit } from '@angular/core';
import { Curso } from 'src/app/models/curso';
import { CursoService } from 'src/app/services/curso.service';
import { commonListarComponent } from '../common-listar.component';

@Component({
  selector: 'app-cursos',
  templateUrl: './cursos.component.html',
  styleUrls: ['./cursos.component.css']
})
export class CursosComponent extends commonListarComponent<Curso, CursoService> implements OnInit {

  constructor(service: CursoService) {
    super(service);
    this.titulo= 'listado de cursos';
    this.nombreModel= Curso.name;
   }


}
