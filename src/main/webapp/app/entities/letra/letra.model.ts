export interface ILetra {
  nombre?: string;
}

export class Letra implements ILetra {
  constructor(public nombre?: string) {}
}
