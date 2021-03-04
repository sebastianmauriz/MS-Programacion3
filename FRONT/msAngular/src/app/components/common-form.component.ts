import { Component, Directive, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2'; 
import { CommonService } from '../services/common.service';
import { Generic } from '../models/generic';

@Directive() //esto se pone en las clases abstractas
export abstract class CommonFormComponent <E extends Generic, S extends CommonService<E>> implements OnInit {
titulo!: string;
model!: E;
error: any; //es any ya que se va a usar para cualquier atributo de alumno
protected redirect!: string;
protected nombreModel!: string;

  constructor(protected service: S, 
              protected router: Router,  //enrutador
              protected route: ActivatedRoute) { }  //ruta para poder obtener el id

  ngOnInit(): void {

    //verificamos que el id exista
    //cmoo en angular todo es reactivo debemos suscibirnos y en el caso de que cambie la ruta con
    //el parametro id, lo capturamos
    this.route.paramMap.subscribe(params=>{
      const id: number = Number(params.get('id')); //el atributo id es el mismo que pusimos en la ruta del AppRoutingModule
      if(id){
        this.service.ver(id).subscribe(m=> {
          this.model=m;
          this.titulo= 'editar ' + this.nombreModel;
      });   
    };
  });
}

  
  public crear(): void  {
    //como es un evento nos tenemos que suscribir, y en el subscribe podemos poner mas de una expresion
    //lambda
      this.service.crear(this.model).subscribe(m=>{
        console.log(m);
        Swal.fire('Nuevo',`${this.nombreModel} ${m.nombre} creado con éxito`, 'success');
        //una vez que se crea el alumno, nos redirijimos al componente Alumnos donde esta la lista
        this.router.navigate([this.redirect]);
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
      this.service.editar(this.model).subscribe(m=>{
        console.log(m);
        Swal.fire('Modificado:',`${this.nombreModel} ${m.nombre} actualizado con éxito`, 'success');
        //una vez que se crea el alumno, nos redirijimos al componente Alumnos donde esta la lista
        this.router.navigate([this.redirect]);
      }, err =>{
        if(err.status === 400){
          this.error = err.error;
          console.log(this.error);
        }
      });
  }
}
