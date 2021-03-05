import { SelectionModel } from '@angular/cdk/collections';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Alumno } from 'src/app/models/alumno';
import { Curso } from 'src/app/models/curso';
import { AlumnoService } from 'src/app/services/alumno.service';
import { CursoService } from 'src/app/services/curso.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-asignar-alumnos',
  templateUrl: './asignar-alumnos.component.html',
  styleUrls: ['./asignar-alumnos.component.css']
})
export class AsignarAlumnosComponent implements OnInit {

  curso!: Curso;
  tabIndex = 0;//este es de mat-tab, el 1 es para asignarAlumnos y el 2 para los Alumnos,está en el html
  alumnosAsignar: Alumno[]= [];
  alumnos: Alumno[]= [];
  mostrarColumnas: string[]= ['nombre','apellido', 'seleccion'];
  //atributo para poder seleccionar en la lista de alumnos en el checkbox. 
  mostrarColumnasAlumnos: string[]= ['id','nombre','apellido', 'email','eliminar'];

  //True es que permite seleccion multiple. En el arreglo va a guardar los alumnos seleccionados
  seleccion: SelectionModel<Alumno>= new SelectionModel<Alumno>(true, []);

  //atributo para paginar
  dataSource!: MatTableDataSource<Alumno>;
   @ViewChild(MatPaginator,{static:true}) paginator!: MatPaginator;
   pageSizeOptions: number[]= [3,5,10,15,20];


  constructor(private route: ActivatedRoute,
    private alumnoService: AlumnoService,
    private cursoService: CursoService) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params=>{
      //primero obtenemos el id y lo asignamos a una constante
      const id: number= Number(params.get('id'));
      //ahora buscamos el curso al back
      this.cursoService.ver(id).subscribe(c => {
        this.curso=c;
        this.alumnos = this.curso.alumnos;
        this.iniciarPaginador();
      }); //lo que hacemos en el suscribe es que 
      //emitimos un curso (c) y a cada curso de ese emitido se lo asignamos al curso (curso)(this.curso=c)
      //ese curso emitido (c)es el que traemos del back (this.cursoService.ver(id).subscribe())
   
    });
   
  }

  private iniciarPaginador():void{
    this.dataSource= new MatTableDataSource<Alumno>(this.alumnos);
    //asignamos al dataSource el paginador que viene de la vista
    this.dataSource.paginator= this.paginator;
    //traducimos
    this.paginator._intl.itemsPerPageLabel= 'Registros por página';
  }

  
  filtrar(nombre: string): void{
    //validamos que lo que escribe el usuario no sea indefinido y sacamos los espacios en blanco
    //if el nombre es != de indefinido retornamos el nombre si espacios else retornamos un espacio vacio
    nombre = nombre!== undefined? nombre.trim(): '';
    if (nombre !== ''){   
      //debemos suscribirnos porq filtrarPorNombre es un observable
    this.alumnoService.filtrarPorNombre(nombre)
    .subscribe(alumnos => this.alumnosAsignar = alumnos.filter(a=>{
      //el filter retorna un booleano
      let filtrar= true;
      this.alumnos.forEach(ca=>{
        if(a.id===ca.id){
          filtrar=false;
        }
      });
      return filtrar;
    }));
  }
 
  }

  estanTodosSeleccionados(): boolean{
    const seleccionados= this.seleccion.selected.length;
    const numAlumnos = this.alumnosAsignar.length;
    return (seleccionados === numAlumnos);
  }

  seleccionarDesSeleccionarTodos(): void{
//esto es un if, pregunta si estan todos seleccionados, si es SI los borra, si es No con un foreach los asigna
    this.estanTodosSeleccionados()?
    this.seleccion.clear():
    this.alumnosAsignar.forEach(a => this.seleccion.select(a));

  }

  asignar(): void{
    console.log(this.seleccion.selected);
    this.cursoService.asignarAlumnos(this.curso, this.seleccion.selected)
    .subscribe(curs => {
      this.tabIndex = 2; //aca le indicamos que cargue el tab 2 (alumnos)asi lo mustra una vez que 
      //asignamos un alumno 
      Swal.fire(
        'Asignados:',
        `Alumnos asignados con éxito al curso ${this.curso.nombre}`,
        'success'        
      );
      this.alumnos =this.alumnos.concat(this.seleccion.selected);//a la lista de alumnos le concatenamos 
      //la lista de alumnos seleccionados para que en la tabla de Alumnos veamos los alumnos del curso 
      //mas los alumnos ya selecionados
      this.iniciarPaginador();
      this.alumnosAsignar =[];  //dejamos todo en 0 para la proxima seleccion
      this.seleccion.clear();
    },
    //ahora hay que salvar el error de que no se puede agregar un alumno a un curso si ya 
    //pertenece a otro curso (da error 500)
    e=>{
      if(e.status ===500){
        const mensaje= e.error.message as string;
        if(mensaje.indexOf('ConstraintViolationException') > -1){  //si es >-1 significa que se encontro
          Swal.fire(
            'Cuidado!!!!',
            'No se puede asignar el alumno, pertenece a otro curso',
            'error'
          );  //este error solo se puede manejar a nivel de usuario, es un error que no rompe el programa
        } //ya que de tomas maneras no lo va a poder cargar a otro curso porque el backend no se lo
        //permite 
      }
    
    });

  }

  eliminarAlumno(alumno: Alumno):void{

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

        this.cursoService.eliminarAlumno(this.curso, alumno)
    .subscribe(curso=>{
      this.alumnos = this.alumnos.filter(a => a.id !==  alumno.id);
      this.iniciarPaginador();
      Swal.fire(
        'Eliminado',
        `Alumno ${alumno.nombre} eliminado con éxito del curso ${curso.nombre}`,
        'success'
      );
    });
    }
    });
    
    
  } 

}
