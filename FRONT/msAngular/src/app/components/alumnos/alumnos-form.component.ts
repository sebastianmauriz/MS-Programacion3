import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Alumno } from 'src/app/models/alumno';
import Swal from 'sweetalert2'; 
import { AlumnoService } from 'src/app/services/alumno.service';

@Component({
  selector: 'app-alumnos-form',
  templateUrl: './alumnos-form.component.html',
  styleUrls: ['./alumnos-form.component.css']
})
export class AlumnosFormComponent implements OnInit {
titulo= 'Crear Alumnos';
alumno: Alumno = new Alumno();
error: any; //es any ya que se va a usar para cualquier atributo de alumno

  constructor(private service: AlumnoService, 
    private router: Router,  //enrutador
    private route: ActivatedRoute) { }  //ruta para poder obtener el id

  ngOnInit(): void {

    //verificamos que el id exista
    //cmoo en angular todo es reactivo debemos suscibirnos y en el caso de que cambie la ruta con
    //el parametro id, lo capturamos
    this.route.paramMap.subscribe(params=>{
      const id: number = Number(params.get('id')); //el atributo id es el mismo que pusimos en la ruta del AppRoutingModule
      if(id){
        this.service.ver(id).subscribe(alumno=> this.alumno=alumno)
      }    
    });
  }

  
  public crear(): void  {
    //como es un evento nos tenemos que suscribir, y en el subscribe podemos poner mas de una expresion
    //lambda
      this.service.crear(this.alumno).subscribe(alumno=>{
        console.log(alumno);
        Swal.fire('Nuevo',`Alumno ${alumno.nombre} creado con éxito`, 'success');
        //una vez que se crea el alumno, nos redirijimos al componente Alumnos donde esta la lista
        this.router.navigate(['/alumnos']);
      }, err =>{
        if(err.status === 400){
          this.error = err.error;
          console.log(this.error);
        }
      });
  }

  public editar(): void  {
    //como es un evento nos tenemos que suscribir, y en el subscribe podemos poner mas de una expresion
    //lambda
      this.service.editar(this.alumno).subscribe(alumno=>{
        console.log(alumno);
        Swal.fire('Modificado:',`Alumno ${alumno.nombre} actualizado con éxito`, 'success');
        //una vez que se crea el alumno, nos redirijimos al componente Alumnos donde esta la lista
        this.router.navigate(['/alumnos']);
      }, err =>{
        if(err.status === 400){
          this.error = err.error;
          console.log(this.error);
        }
      });
  }
}
