import { Alumno } from "./alumno";
import { Examen } from "./examen";

export class Curso {
    id!: number;
    nombre!: string;
    creatAt!: string;
    alumnos: Alumno[]=[]; //un arreglo siempre se debe inicializar para que no de null
    examenes: Examen[]= [];
}
