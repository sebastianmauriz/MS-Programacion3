import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Alumno } from 'src/app/models/alumno';
import { Curso } from 'src/app/models/curso';
import { Examen } from 'src/app/models/examen';
import { Respuesta } from 'src/app/models/respuesta';
import { AlumnoService } from 'src/app/services/alumno.service';
import { CursoService } from 'src/app/services/curso.service';
import { RespuestaService } from 'src/app/services/respuesta.service';
import Swal from 'sweetalert2';
import { ResponderExamenModalComponent } from '../alumnos/responder-examen-modal.component';
import { VerExamenModalComponent } from '../alumnos/ver-examen-modal.component';

@Component({
  selector: 'app-responder-examen',
  templateUrl: './responder-examen.component.html',
  styleUrls: ['./responder-examen.component.css']
})
export class ResponderExamenComponent implements OnInit {

  examenes : Examen[]=[];
  curso!: Curso;
  alumno!: Alumno;
  mostrarColumnasExamenes =['id', 'nombre','asignaturas', 'preguntas', 'responder', 'ver' ];
  pageSizeOptions=[3, 5, 10, 15, 20];

  dataSource!: MatTableDataSource<Examen>;
  @ViewChild(MatPaginator, {static:true}) paginator!: MatPaginator;


  constructor(private route: ActivatedRoute,
    private alumnoService: AlumnoService,
    private cursoService: CursoService,
    private respuestaService: RespuestaService,
    public dialog: MatDialog) { }

    
  ngOnInit(): void {
    this.route.paramMap.subscribe(params=>{
      const id = Number(params.get('id'));
      this.alumnoService.ver(id).subscribe(alumno =>{
        this.alumno= alumno;
        this.cursoService.obtenerCursoPorAlumnoId(this.alumno).subscribe(
          curso => {
            this.curso= curso;
            this.examenes= (curso && curso.examenes)? curso.examenes: [];
            this.dataSource= new MatTableDataSource<Examen>(this.examenes);
            this.dataSource.paginator= this.paginator;
            this.paginator._intl.itemsPerPageLabel='Registros por página:';
          }
        );
      });
    });

    
  }
//metodo donde configuramos la ventana modal
  responderExamen(examen: Examen): void{
    const modelRef = this.dialog.open(ResponderExamenModalComponent, {
      width:'750px',
      //pasamos los datos que se verán en la tabla
      data:{curso: this.curso, alumno: this.alumno, examen: examen}
    });
    modelRef.afterClosed().subscribe((respuestasMap: Map<number, Respuesta>) =>{ //aca enviamos los datos al backend
      console.log('modale repsonder examen ha sido enviado y cerrado');
      console.log(respuestasMap);
      if(respuestasMap){
        //el metodo array.from()crea una nueva onstancia de array a partir de un objet iterable
        const respuestas: Respuesta[]= Array.from(respuestasMap.values());//asi convertimos los valores de
        //respuestasMap en un arreglo
        this.respuestaService.crear(respuestas).subscribe(rs=>{
          examen.respondido=true; //asi deshabilitamos el boton para que no vuelva a responder
          Swal.fire(
            'Enviadas:',
            'Preguntas enviadas con éxito',
            'success'
          );
          console.log(rs);
        });
      }

    });

  }

  verExamen(examen: Examen): void{
    this.respuestaService.obtenerRespuestasPorAlumnoPorExamen(this.alumno, examen)
    .subscribe(rs=>{
      const modalRef= this.dialog.open(VerExamenModalComponent,{
        width:'750px',
        data: {curso: this.curso, examen: examen, respuestas: rs}
      });
      modalRef.afterClosed().subscribe(()=>{
        console.log('Modal ver examen cerrado');
      });
    });
  }

}
