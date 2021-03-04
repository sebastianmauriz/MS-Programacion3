import { Asignatura } from "./asignatura";
import { Generic } from "./generic";
import { Pregunta } from "./pregunta";

export class Examen implements Generic{
    id!: number;
    nombre!: string;
    createAt!: string;
    preguntas: Pregunta[]=[];
    asignaturaPadre!: Asignatura;
    asignaturaHija!: Asignatura;
    respondido!: boolean;
    
}
