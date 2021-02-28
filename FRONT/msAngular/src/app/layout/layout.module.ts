import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';
import { AppRoutingModule } from '../app-routing.module';



@NgModule({
  declarations: [NavbarComponent],
  exports:[NavbarComponent],  //creamos el export con el navbar dentro para que lo podamos usar
  //en otro lado, de esta manera podemos exportar el componente navbar. Y este modulo(layout), debemos
  //importarlo en el app.module para poder usarlo
  imports: [
    CommonModule,
    AppRoutingModule    //tenemos que importar este modulo para que funcionen las rutas del navbar
    //ya que en éste modulo (layout) esta el navbar
  ]
})
export class LayoutModule { }
