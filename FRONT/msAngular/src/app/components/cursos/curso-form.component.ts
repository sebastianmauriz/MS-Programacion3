import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Curso } from 'src/app/models/curso';
import { CursoService } from 'src/app/services/curso.service';
import { CommonFormComponent } from '../common-form.component';

@Component({
  selector: 'app-curso-form',
  templateUrl: './curso-form.component.html',
  styleUrls: ['./curso-form.component.css']
})
export class CursoFormComponent extends CommonFormComponent<Curso, CursoService> implements OnInit {

  constructor( service: CursoService, 
     router: Router,  //enrutador
     route: ActivatedRoute) { super(service, router, route);
      this.titulo='crear curso';
      this.model= new Curso();
      this.redirect ='/cursos';
      this.nombreModel= Curso.name;
      
    }

}
