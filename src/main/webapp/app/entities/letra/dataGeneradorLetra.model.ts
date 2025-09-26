export interface ICuota {
  letraN?: number;
  fechaVencimientoLetra?: string;
  montoLetra?: number;
  fechaGiro?: string;
  fechaPago?: string;
}
export class Cuota implements ICuota {
  constructor(
    public letraN?: number,
    public fechaVencimientoLetra?: string,
    public montoLetra?: number,
    public fechaGiro?: string,
    public fechaPago?: string
  ) {}
}

export interface IDataGeneradorLetra {
  ordenante?: string;
  beneficiario?: string;
  domicilio?: string;
  ciudad?: string;
  comuna?: string;
  rut?: string;
  cuota?: ICuota[];
}

export class DataGeneradorLetra implements IDataGeneradorLetra {
  constructor(
    public ordenante?: string,
    public beneficiario?: string,
    public domicilio?: string,
    public ciudad?: string,
    public comuna?: string,
    public rut?: string,
    public cuota?: ICuota[]
  ) {}
}
