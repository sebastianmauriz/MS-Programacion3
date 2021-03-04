import { Alumno } from "./alumno";
import { Examen } from "./examen";
import { Generic } from "./generic";

export class Curso implements Generic{
    id!: number;
    nombre!: string;
    creatAt!: string;
    alumnos: Alumno[]=[]; //un arreglo siempre se debe inicializar para que no de null
    examenes: Examen[]= [];
}
