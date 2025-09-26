export interface IletraCambio {
  letraN?: number;
  letraHasta?: number;
  fechaVencimientoLetra?: Fecha;
  fechaGiro: Fecha;
  monto?: number;
  montoLetra?: string;
  ordenante: Persona;
  beneficiario: Persona;
}
export interface Ifecha {
  dd?: number;
  mm?: number;
  yyyy?: number;
}
export class Fecha implements Ifecha {}
export interface Ipersona {
  nombreCompleto?: string;
  domicilio?: string;
  ciudad?: string;
  comuna?: string;
  rut?: string;
}

export class Persona implements Ipersona {}
