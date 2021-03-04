import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Curso } from 'src/app/models/curso';
import { Examen } from 'src/app/models/examen';
import { CursoService } from 'src/app/services/curso.service';
import { ExamenService } from 'src/app/services/examen.service';
import { map, flatMap } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-asignar-examenes',
  templateUrl: './asignar-examenes.component.html',
  styleUrls: ['./asignar-examenes.component.css']
})
export class AsignarExamenesComponent implements OnInit {

  curso!:Curso;
  autoCompleteControl = new FormControl();
  examenesFiltrados: Examen[]=[];
  examenesAsignar: Examen[]=[];
  mostrarColumnas= ['nombre', 'asignatura'];

  constructor(private route: ActivatedRoute,
              private router: Router,
              private cursoService: CursoService,
              private examenService: ExamenService) { }


  ngOnInit(): void {
    this.route.paramMap.subscribe(params =>{
      const id = Number(params.get('id'));
      this.cursoService.ver(id).subscribe(c => this.curso=c);
    });
    //debemos convertir el examen de tipo examen a un string, debido a que cuando estamos buscando el examen
    //lo hacemos con el atributo nombre del mismo y es de tipo string, etonces para evitar error
    //con el map pasamos el tipo examen a string
    this.autoCompleteControl.valueChanges.pipe(
      map(valor=> typeof valor ==='string'? valor: valor.nombre),
      flatMap(valor => valor? this.examenService.filtrarPorNombre(valor): [])
    ).subscribe(examenes => this.examenesFiltrados=examenes);
  }

  mostrarNombre(examen? : Examen): any{
    return examen? examen.nombre: '';
  }

  seleccionaExamen(event: MatAutocompleteSelectedEvent): void{
    const examen= event.option.value as Examen;
    //si el id del examen no existe lo agregamos a la lista, en el caso de que lo encuentr
    //no se agrega el examen
    if(!this.existe(examen.id)){
    //usamos concat en vez de push porque en la tabla no se ven reflejados los cambios (cdo agregamos
    //un examen), pero con concat si ya que concatena a la lista anterior el nuevo examen agregado
    this.examenesAsignar= this.examenesAsignar.concat(examen);
    console.log(this.examenesAsignar);
    this.autoCompleteControl.setValue('');
    event.option.deselect();
    event.option.focus();
  } else {
    Swal.fire(
      'Error!!!',
      'El examen que intenta agregar ya existe',
      'error'
    )
  }
  }

  private existe(id:number): boolean{
    let existe=false;
    this.examenesAsignar.concat(this.curso.examenes)
    .forEach(e =>{
      if(id =e.id){
        existe=true;
      }
    });
    return existe;
  }
}
