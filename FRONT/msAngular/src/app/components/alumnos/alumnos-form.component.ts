import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Alumno } from 'src/app/models/alumno';
import Swal from 'sweetalert2';
import { AlumnoService } from 'src/app/services/alumno.service';
import { CommonFormComponent } from '../common-form.component';

@Component({
  selector: 'app-alumnos-form',
  templateUrl: './alumnos-form.component.html',
  styleUrls: ['./alumnos-form.component.css']
})
export class AlumnosFormComponent extends CommonFormComponent<Alumno, AlumnoService> implements OnInit {

  private fotoSeleccionada!: File;

  constructor(service: AlumnoService,
    router: Router,  //enrutador
    route: ActivatedRoute) {  //ruta para poder obtener el id
    super(service, router, route);
    this.titulo = 'Crear Alumnos';
    this.model = new Alumno();
    this.redirect = '/alumnos';
    this.nombreModel = Alumno.name;
  }

  public seleccionarFoto(event:any): void {
    this.fotoSeleccionada = event.target.files[0]; //se pone asi (files[0]) porque es 1 sola foto
    console.info(this.fotoSeleccionada);
    //ahora validamos que lo que suba el usuario sea una foto y no un pdf o un ecxel o cualquier cosa
    //si es menor que 0 quiere decir que no la contiene, es negativo
    if(this.fotoSeleccionada.type.indexOf('image')<0){ //todas las imagenes contienen la palabra image/jpg, png, etc
      //this.fotoSeleccionada=null; //la dejamos en null para que no se pueda subir
      Swal.fire('Error al seleccionar la foto:', 'el archivo debe ser del tipo imagen', 'error');

    }

  }

  public crear(): void {

    if (!this.fotoSeleccionada ) { //en el caso de que sea sin foto
      super.crear(); //invoca el metodo crear del padre
    } else { //si viene con foto, crea el nuevo objeto
      this.service.crearConFoto(this.model, this.fotoSeleccionada).subscribe(alumno => {
        console.log(alumno);
        Swal.fire('Nuevo', `${this.nombreModel} ${alumno.nombre} creado con éxito`, 'success');
        //una vez que se crea el alumno, nos redirijimos al componente Alumnos donde esta la lista
        this.router.navigate([this.redirect]);
      }, err => {
        if (err.status === 400) {
          this.error = err.error;
          console.log(this.error);
        }
      });
    }
    
  }

  public editar(): void {

    if (this.fotoSeleccionada == null) { //en el caso de que sea sin foto
      super.editar(); //invoca el metodo editar del padre
    } else { //si viene con foto, edita el nuevo objeto
      this.service.editarConFoto(this.model, this.fotoSeleccionada).subscribe(alumno => {
        console.log(alumno);
        Swal.fire('Modificado:', `${this.nombreModel} ${alumno.nombre} actualizado con éxito`, 'success');
        //una vez que se crea el alumno, nos redirijimos al componente Alumnos donde esta la lista
        this.router.navigate([this.redirect]);
      }, err => {
        if (err.status === 400) {
          this.error = err.error;
          console.log(this.error);
        }
      });
    }
  }
  

  
}
