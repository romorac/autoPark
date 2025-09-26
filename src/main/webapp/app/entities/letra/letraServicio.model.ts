export interface ILetraServicio {
  nombrePagador?: string;
  ciudadGiro?: string;
  fechaGiro?: string;
  montoNumerico?: string;
  nombreBeneficiario?: string;
  domicilioBeneficiario?: string;
  ciudadBeneficiario?: string;
  comunaBeneficiario?: string;
  rutBeneficiario?: string;
  lstDetalleLetra?: DetalleLetraPago[];
}
export class LetraServicio implements ILetraServicio {
  constructor(
    public nombrePagador?: string,
    public ciudadGiro?: string,
    public fechaGiro?: string,
    public montoNumerico?: string,
    public nombreBeneficiario?: string,
    public domicilioBeneficiario?: string,
    public ciudadBeneficiario?: string,
    public comunaBeneficiario?: string,
    public rutBeneficiario?: string,
    public lstDetalleLetra?: DetalleLetraPago[]
  ) {}
}

export interface IDetalleLetraPago {
  numeroLetra?: string;
  fechaVencimiento?: string;
  monto?: string;
}
export class DetalleLetraPago implements IDetalleLetraPago {
  constructor(public numeroLetra?: string, public fechaVencimiento?: string, public monto?: string) {}
}
