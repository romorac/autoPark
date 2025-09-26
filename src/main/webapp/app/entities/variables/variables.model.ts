import { EstadoEnum } from 'app/entities/enumerations/estado-enum.model';

export interface IVariables {
  id?: number;
  nombre?: string;
  valor?: string;
  estado?: EstadoEnum;
}

export class Variables implements IVariables {
  constructor(public id?: number, public nombre?: string, public valor?: string, public estado?: EstadoEnum) {}
}

export function getVariablesIdentifier(variables: IVariables): number | undefined {
  return variables.id;
}
