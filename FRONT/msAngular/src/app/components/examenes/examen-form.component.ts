import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Asignatura } from 'src/app/models/asignatura';
import { Examen } from 'src/app/models/examen';
import { Pregunta } from 'src/app/models/pregunta';
import { ExamenService } from 'src/app/services/examen.service';
import Swal from 'sweetalert2';
import { CommonFormComponent } from '../common-form.component';

@Component({
  selector: 'app-examen-form',
  templateUrl: './examen-form.component.html',
  styleUrls: ['./examen-form.component.css']
})
export class ExamenFormComponent extends CommonFormComponent<Examen, ExamenService> implements OnInit {

  asignaturasPadre: Asignatura[] = [];
  asignaturasHija: Asignatura[] = [];

  constructor(service: ExamenService,
    router: Router,  //enrutador
    route: ActivatedRoute) {
      super(service, router, route);
    this.titulo = 'crear examen';
    this.model = new Examen();
    this.redirect = '/examenes';
    this.nombreModel = Examen.name;

  }

  ngOnInit(): void {

    //verificamos que el id exista
    //cmoo en angular todo es reactivo debemos suscibirnos y en el caso de que cambie la ruta con
    //el parametro id, lo capturamos
    this.route.paramMap.subscribe(params => {
      const id: number = Number(params.get('id')); //el atributo id es el mismo que pusimos en la ruta del AppRoutingModule
      if (id) {
        this.service.ver(id).subscribe(m => {
          this.model = m;
          this.titulo = 'Editar ' + this.nombreModel;
          this.cargarHijos(); //esto es lo mismo que lo que se comentó abajo, pero esta forma es mejor
          //ya que no va al backend a buscar los datos, sino que los buscqa en el front
          /*this.service.findAllAsignatura().subscribe(asignaturas=>
            this.asignaturasHija = asignaturas.
            filter(a =>a.padre &&a.padre.id===this.model.asignaturaPadre.id));*/
        });
      };
    });

    this.service.findAllAsignatura()
      .subscribe(asignaturas => this.asignaturasPadre = asignaturas.
        filter(a => !a.padre)); //quiere decir que no tengan padre,que sean raiz o sea son hijos nada mas. 
  }


  public crear(): void  {
    if(this.model.preguntas.length===0){
      Swal.fire('ERROR!!!','El examen debe tener preguntas', 'error');
      return;
    }
    this.eliminarPreguntasVacias();
    super.crear();

  }

  public editar(): void  {
    if(this.model.preguntas.length===0){
      Swal.fire('ERROR!!!','El examen debe tener preguntas', 'error');
      return;
    }
    this.eliminarPreguntasVacias();
super.editar();
  }



  cargarHijos(): void {
    this.asignaturasHija = this.model.asignaturaPadre ? this.model.asignaturaPadre.hijos : [];
    //esto quiere decir que si existe asignatura padre(this.model.asignaturaPadre?)que carga  
    //a los hijos, sino que retorne un arreglo vacio (this.model.asignaturaPadre.hijos: [])
  }

  compararAsignatura(a1: Asignatura, a2: Asignatura): boolean {
    //los 3= significan que es identico en tipo y en valor

    if (a1 === undefined && a2 === undefined) {
      return true;
    }else if (a1 === null || a2 === null || a1 === undefined || a2 === undefined) {
      return false;
    }else if (a1.id === a2.id) {
      return true;
    }else{
      return false
    }
    //angular al ser asincrono y demorarse algun tiempo en traer los datos del back
    //puede llegar a tomar algunos objetos como indefinidos porque aun no los ha traido
    //es por eso que debemos hacer estas validaciones
        
  }

  agregarPregunta(): void{
    this.model.preguntas.push(new Pregunta());
  }

  asignarTexto(pregunta: Pregunta, event: any):void{
    //event contiene el texto que estamos escriibiendo en el input
    pregunta.texto= event.target.value as string;
    console.log(this.model); //vamos a revisar el objeto examen a medida que agregramos preguntas
  }

  eliminarPregunta(pregunta: Pregunta): void{
    //vamos a eliminar todas las preguntas menos la que estamos pasando por parametro
    //y despues se las asignamos nuevamente a la lista de preguntas, ya que el filter() lo
    //que hace es crear una nueva lista y es inmutable (no cambia la lista anteriror de preguntas)

    this.model.preguntas= this.model.preguntas.filter(pAEliminar => pregunta.texto!==pAEliminar.texto);
  }

  eliminarPreguntasVacias(): void{
    this.model.preguntas= this.model.preguntas.filter(p=> p.texto != null && p.texto.length > 0);
    //si hay preguntas vacias o nulas se eliminan de la lista
  }
}
