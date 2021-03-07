import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Curso } from 'src/app/models/curso';
import { Examen } from 'src/app/models/examen';
import { Respuesta } from 'src/app/models/respuesta';

@Component({
  selector: 'app-ver-examen-modal',
  templateUrl: './ver-examen-modal.component.html',
  styleUrls: ['./ver-examen-modal.component.css']
})
export class VerExamenModalComponent implements OnInit {

  curso!: Curso;
  examen!: Examen;
  respuestas!:Respuesta[];
  constructor(@Inject(MAT_DIALOG_DATA)public data:any,
  public modalRef: MatDialogRef<VerExamenModalComponent>) { }

  
  ngOnInit(): void {
    this.curso = this.data.curso as Curso;
    this.examen = this.data.examen as Examen;
    this.respuestas = this.data.respuestas as Respuesta[];
  }

  cerrar(): void{
    this.modalRef.close();
  }

}
