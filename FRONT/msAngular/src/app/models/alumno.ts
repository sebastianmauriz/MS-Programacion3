import { Generic } from "./generic";

export class Alumno implements Generic{
    id!: number;
    nombre!: string;
    apellido!: string;
    email!: string;
    creatAt!: string;
    fotoHashCode!: number;
}
