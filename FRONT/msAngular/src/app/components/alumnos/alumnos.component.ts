import { Component, OnInit } from '@angular/core';
import { Alumno } from 'src/app/models/alumno';
import Swal from 'sweetalert2';   //es para hacer alertas mas bonitos. https://sweetalert2.github.io/
import { AlumnoService } from 'src/app/services/alumno.service';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-alumnos',
  templateUrl: './alumnos.component.html',
  styleUrls: ['./alumnos.component.css']
})
export class AlumnosComponent implements OnInit {

  //para poder pasar datos a la vista, debemos hacer los atributos publicos
  //cuando el atributo se inicializa, no hace falta poner el tipo
  titulo = 'Listado de Alumnos';
  alumnos: Alumno[]=[]; //la inicializamos vacio para que no de error (por detras, si bien anda perfecto)
  //cuando se carga la lista de alumnos para mostrarla por pantalla. Como angular es reactivo desde que
  //se ejecuta el front hasta que va al back a buscar la lista y la trae (esos milisegundos) ve la lista
  //de alumnos como indefinida y larga error (por consola se puede ver) pero cuando la encuentra, la trae
  //y desaparece el error, pero al inicializarla vacia ya no da error

  totalRegistros= 0;
  paginaActual=0;
  totalPorPagina= 5;
  pageSizeOptions: number []= [3, 5, 10, 25, 100];

  constructor(private service: AlumnoService) { }

  ngOnInit(): void {

    
    this.calcularRango();
             
      }
    
      paginar(event: PageEvent): void{
      this.paginaActual = event.pageIndex;
      this.totalPorPagina = event.pageSize;
      this.calcularRango();
      }

      //metodo para las paginas
      private calcularRango (){
        //como listarPaginas es un observable(creado en el service) nos debemos suscribir y dentro de este creamos
    //el observador
    //lo que hacemos es tomar el stream de alumnos que recibimos en el observador (lo recibimos del back)
    //y se lo asignamos al otro atributo alumnos de esta clase (this.alumnos). Con el subscribe lo que
    //hace es escuchar este evento y cuando se comunique con el back, produce esta accion de asigna

        const paginaActual= this.paginaActual + ''; //se ahce esto porque el listarPagina recibe string
    const totalPorPagina= this.totalPorPagina + '';;//y estos son number que aca lo pasamos a string

    this.service.listarPaginas(paginaActual,totalPorPagina )
    .subscribe(p=>
      {
        this.alumnos= p.content as Alumno[];
        this.totalRegistros = p.totalElements as number;
      });

      }

  public eliminar(alumno: Alumno): void{
    Swal.fire({
      title: 'CUIDADO!! ',
      text: `Seguro que desea eliminar a ${alumno.nombre}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!!'
    }).then((result) => {
      if(result.value){
      this.service.eliminar(alumno.id).subscribe(()=>{  //lleva parentesis vacios porque al ser void, no se emite nada
        //el metodo filter lo que hace es retornar una nueva lista con los objetos ya filtrados
        ///es decir que si a (que es el alumno a eliminar) es != de los alumnos, retorna los alumnos
        //menos el alumno eliminado
        this.calcularRango();//cada vez que se elimina un alumno vuleve a paginar para ordenar
        //this.alumnos= this.alumnos.filter(a => a !== alumno); //al ser inmutable queiere decir que la lista anterior
        //se mantiene, es por eso que debemos asignarle a la anterior los valores de la lista nueva que retorna el filter
        Swal.fire('Eliminado:',`El alumno ${alumno.nombre} ha sido eliminado con éxito`,'success');
      });
    }
    });
    
  }
}
