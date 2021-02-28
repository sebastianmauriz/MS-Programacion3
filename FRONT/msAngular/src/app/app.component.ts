import { Component } from '@angular/core';

@Component({
  selector: 'app-root',  //selector para poder mostrar el componente
  templateUrl: './app.component.html',  
  styleUrls: ['./app.component.css']
})

//el componente tiene una clase que se puede exportar pra poder importarla enotro lado
export class AppComponent {
  title = 'msAngular';
}
