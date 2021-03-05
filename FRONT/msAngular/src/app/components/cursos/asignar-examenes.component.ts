import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Curso } from 'src/app/models/curso';
import { Examen } from 'src/app/models/examen';
import { CursoService } from 'src/app/services/curso.service';
import { ExamenService } from 'src/app/services/examen.service';
import { map, flatMap } from 'rxjs/operators';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import Swal from 'sweetalert2';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';


@Component({
  selector: 'app-asignar-examenes',
  templateUrl: './asignar-examenes.component.html',
  styleUrls: ['./asignar-examenes.component.css']
})
export class AsignarExamenesComponent implements OnInit {

  curso!: Curso;
  autoCompleteControl = new FormControl();
  examenesFiltrados: Examen[] = [];
  examenesAsignar: Examen[] = [];
  mostrarColumnas = ['nombre', 'asignatura', 'eliminar'];
  examenes: Examen[] = [];
  mostrarColumnasExamenes = ['id', 'nombre', 'asignaturas', 'eliminar'];
  tabIndex: number = 0;
  dataSource!: MatTableDataSource<Examen>;
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  pageSizeOptions = [3, 5, 10, 20, 50];

  constructor(private route: ActivatedRoute,
    private router: Router,
    private cursoService: CursoService,
    private examenService: ExamenService) { }


  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = Number(params.get('id'));
      this.cursoService.ver(id).subscribe(c => {
        this.curso = c;
        this.examenes = this.curso.examenes;
        this.iniciarPaginador();
      });

    });
     //debemos convertir el examen de tipo examen a un string, debido a que cuando estamos buscando el examen
    //lo hacemos con el atributo nombre del mismo y es de tipo string, etonces para evitar error
    //con el map pasamos el tipo examen a string
    this.autoCompleteControl.valueChanges.pipe(
      map(valor => typeof valor === 'string' ? valor : valor.nombre),
      flatMap(valor => valor ? this.examenService.filtrarPorNombre(valor) : [])
    ).subscribe(examenes => this.examenesFiltrados = examenes);
  }

  private iniciarPaginador(): void {
    this.dataSource = new MatTableDataSource<Examen>(this.examenes);
    this.dataSource.paginator = this.paginator; //asignamos el paginador a la tabla de la vista
    this.paginator._intl.itemsPerPageLabel = 'Registros por página';
  }

  mostrarNombre(examen?: Examen): string {
    return examen ? examen.nombre : '';
  }

  seleccionarExamen(event: MatAutocompleteSelectedEvent): void {
    const examen = event.option.value as Examen;
//si el id del examen no existe lo agregamos a la lista, en el caso de que lo encuentr
    //no se agrega el examen
    if (!this.existe(examen.id)) {
      //usamos concat en vez de push porque en la tabla no se ven reflejados los cambios (cdo agregamos
    //un examen), pero con concat si ya que concatena a la lista anterior el nuevo examen agregado
      this.examenesAsignar = this.examenesAsignar.concat(examen);

      console.log(this.examenesAsignar);
    
    } else {
      Swal.fire('error',
       `El examen ${examen.nombre} ya esta asignado al  curso`,
        "error");
    }
    this.autoCompleteControl.setValue('');
    event.option.deselect();
    event.option.focus();

  }

  private existe(id: number): boolean {
    let existe = false;
    this.examenesAsignar.concat(this.examenes)
      .forEach(e => {
        if (id === e.id) {
          existe = true;
        }
      });
    return existe;
  }

  eliminarDelAsignar(examen: Examen): void {

    this.examenesAsignar = this.examenesAsignar.filter(e => examen.id !== e.id);
  }

  asignar(): void {
    console.log(this.examenesAsignar);
    this.cursoService.asignarExamenes(this.curso, this.examenesAsignar)
      .subscribe(c => {
        this.examenes = this.examenes.concat(this.examenesAsignar);
        this.iniciarPaginador();
        this.examenesAsignar = [];

        Swal.fire('Asignados',
          `Examenes asignados con exito al curso ${c.nombre}`,
          'success');
        this.tabIndex = 2;
      });
  }

  eliminarExamenDelCurso(examen: Examen): void {

    Swal.fire({
      title: 'CUIDADO!! ',
      text: `Seguro que desea eliminar a ${examen.nombre}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Si, eliminar!!'
    }).then((result) => {
      if (result.value) {
        this.cursoService.eliminarExamen(this.curso, examen)
          .subscribe(curso => {
            this.examenes = this.examenes.filter(e => e.id !== examen.id);
            this.iniciarPaginador();
            Swal.fire(
              'Eliminado',
              `Examen ${examen.nombre} eliminado con éxito del curso ${curso.nombre}`,
              'success'
            );
          });
      }
    });


  }


}