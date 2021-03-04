import { Component, OnInit } from '@angular/core';
import { Examen } from 'src/app/models/examen';
import { ExamenService } from 'src/app/services/examen.service';
import { commonListarComponent } from '../common-listar.component';

@Component({
  selector: 'app-examenes',
  templateUrl: './examenes.component.html',
  styleUrls: ['./examenes.component.css']
})
export class ExamenesComponent extends commonListarComponent<Examen, ExamenService> implements OnInit {

  constructor(service: ExamenService) {
    super(service);
    this.titulo='listado de exámenes';
    this.nombreModel= Examen.name;
   }

   

}
