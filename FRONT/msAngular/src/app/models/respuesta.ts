import { Alumno } from "./alumno";
import { Pregunta } from "./pregunta";

export class Respuesta {
    id!: string; //es string ya que en mongo el id es de tipo string
    texto!: string;
    alumno!: Alumno;
    pregunta!: Pregunta;
}
