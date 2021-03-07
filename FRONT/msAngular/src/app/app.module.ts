import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import { AppComponent } from './app.component';
import { AlumnosComponent } from './components/alumnos/alumnos.component';
import { CursosComponent } from './components/cursos/cursos.component';
import { ExamenesComponent } from './components/examenes/examenes.component';
import { LayoutModule } from './layout/layout.module';
import { AlumnosFormComponent } from './components/alumnos/alumnos-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatPaginatorModule} from '@angular/material/paginator';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CursoFormComponent } from './components/cursos/curso-form.component';
import { ExamenFormComponent } from './components/examenes/examen-form.component';
import {MatTableModule} from '@angular/material/table';
import {MatInputModule} from '@angular/material/input';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatButtonModule} from '@angular/material/button';
import {MatTabsModule} from '@angular/material/tabs';
import {MatCardModule} from '@angular/material/card';
import {MatDialogModule} from '@angular/material/dialog';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { AsignarAlumnosComponent } from './components/cursos/asignar-alumnos.component';
import { AsignarExamenesComponent } from './components/cursos/asignar-examenes.component';
import { ResponderExamenComponent } from './components/examenes/responder-examen.component';
import {MatExpansionModule} from '@angular/material/expansion';
import { ResponderExamenModalComponent } from './components/alumnos/responder-examen-modal.component';



@NgModule({
  declarations: [
    AppComponent,
    AlumnosComponent,
    CursosComponent,
    ExamenesComponent,
    AlumnosFormComponent,
    CursoFormComponent,
    ExamenFormComponent,
    AsignarAlumnosComponent,
    AsignarExamenesComponent,
    ResponderExamenComponent,
    ResponderExamenModalComponent,
  ],
  entryComponents:[ResponderExamenModalComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    LayoutModule,
    HttpClientModule,
    FormsModule,
    MatPaginatorModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatInputModule,
    MatCheckboxModule,
    MatButtonModule,    
    MatCardModule,
    MatTabsModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatExpansionModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
